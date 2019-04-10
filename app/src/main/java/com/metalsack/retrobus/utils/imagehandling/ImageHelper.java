package com.metalsack.retrobus.utils.imagehandling;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.metalsack.retrobus.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Provides functions for scaling and saving images.
 */
public class ImageHelper {
    /**
     * The format for a timestamp in the form "yyyyMMdd_HHmmss". Used for creating image file
     * names.
     */
    public static final SimpleDateFormat TIMESTAMP_FORMAT =
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

    /**
     * The prefix for creating image file names.
     */
    public static final String IMG_PREFIX = "img_";

    /**
     * The prefix for creating temporary image file names.
     */
    public static final String TMP_IMG_PREFIX = "tmpimg_";

    private final int MIN_IMG_WIDTH = 600;
    private final int MIN_IMG_HEIGHT = 200;

    /**
     * Folder name of the ad images which are stored the old way. Kept for deleting cached images
     * for existing ads.
     */
    private static final String IMAGES_PATH = "imgs";

    private Context appCtx;

    /**
     * Creates a new ImageHelper with the passed Context.
     */
    public ImageHelper(Context context) {
        this.appCtx = context.getApplicationContext();
    }

    /**
     * Opens a stream on to the content of the passed uri and validates the picture dimensions.
     *
     * @param imgUri The image uri.
     * @throws FileNotFoundException Thrown if the file with the passed uri does not exist.
     */
    public boolean isPictureValidForUpload(final Uri imgUri) throws FileNotFoundException {
        BitmapFactory.Options opts = getImageDimensions(imgUri);
        int orientation = getOrientation(imgUri);
        return isPictureDimensionsValid(opts.outWidth, opts.outHeight, orientation);
    }

    /**
     * Does the same as {@link #isPictureValidForUpload(Uri)} but takes a file path instead of an
     * Uri.
     *
     * @param filePath The path to the image.
     * @throws IOException Thrown if the file cannot be found.
     */
    public boolean isPictureValidForUpload(final String filePath) throws IOException {
        BitmapFactory.Options opts = getImageDimensions(filePath);

        ExifInterface exif = new ExifInterface(filePath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        // Translate the orientation constant to degrees.
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                orientation = 90;
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                orientation = 270;
                break;

            default:
                break;
        }

        return isPictureDimensionsValid(opts.outWidth, opts.outHeight, orientation);
    }

    /**
     * Check if the passed picture has a valid aspect ratio. Minimum width is 320 px, minimum height
     * 107 px. The aspect ratio must be between 3:1 and 1:3.
     *
     * @param width       The width of the image.
     * @param height      The height of the image.
     * @param orientation The orientation of the image.
     * @return True if the dimensions are valid, false otherwise.
     */
    private boolean isPictureDimensionsValid(double width, double height, int orientation) {
        if (orientation == 90 || orientation == 270) {
            // The image will be rotated later and width and height will be swapped.
            double temp = width;
            //noinspection SuspiciousNameCombination
            width = height;
            height = temp;
        }

        if (width < MIN_IMG_WIDTH || height < MIN_IMG_HEIGHT) {
            return false;
        }

        double aspect = width / height;
        return !(aspect > 3 || aspect < (1 / 3));
    }

    /**
     * Scales down the image with the passed uri taking into account the passed orientation.
     *
     * @param imgUri      The uri of the image to scale down.
     * @param orientation The orientation of the image.
     * @return The downscaled image.
     * @throws FileNotFoundException Thrown if the file with the passed uri does not exist.
     */
    public Bitmap getDownscaledBitmap(Uri imgUri, int orientation) throws FileNotFoundException {
        BitmapFactory.Options opts = getImageDimensions(imgUri);
        int inSampleSize;

        if (orientation == 90 || orientation == 270) {
            // The image has to be rotated later. The width and height will be swapped.
            inSampleSize = opts.outHeight / MIN_IMG_WIDTH;

        } else {
            inSampleSize = opts.outWidth / MIN_IMG_WIDTH;
        }

        InputStream is = appCtx.getContentResolver().openInputStream(imgUri);
        opts = new BitmapFactory.Options();
        opts.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);

        try {
            is.close();
        } catch (IOException e) {
            // Do not care.
        }

        return bitmap;
    }

    /**
     * @param imgUri The image uri.
     * @return The dimensions of the passed image wrapped in the BitmapFactory.Options object.
     * @throws FileNotFoundException Thrown if the file with the passed uri does not exist.
     */
    private BitmapFactory.Options getImageDimensions(Uri imgUri) throws FileNotFoundException {
        InputStream is = appCtx.getContentResolver().openInputStream(imgUri);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, opts);

        try {
            is.close();
        } catch (IOException e) {
            // Do not care.
        }

        return opts;
    }

    /**
     * Does the same as {@link #getImageDimensions(Uri)} but takes a file path instead of an Uri.
     *
     * @param filePath The file path of the image.
     */
    private BitmapFactory.Options getImageDimensions(String filePath)
            throws FileNotFoundException {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        return opts;
    }

    /**
     * Create a File for saving an image.
     */
    public File getOutputMediaFile() {
        if (!isExternalStorageWritable()) {
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Quoka");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Logger.e("Failed to create directory 'Quoka'");
                return null;
            }
        }

        String timeStamp = TIMESTAMP_FORMAT.format(new Date());
        String fileName = IMG_PREFIX + timeStamp + ".jpg";
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    /**
     * Scales down the passed image, rotates it if needed and copies it into the Quoka app private
     * folder.
     *
     * @param imageUri The uri pointing to the image file.
     * @return The path to the temporary image file.
     * @throws IOException Thrown if the image does not exist or writing to a stream failed.
     */
    public String createTempImageFile(Uri imageUri) throws IOException {
        int orientation = getOrientation(imageUri);
        String timeStamp = TIMESTAMP_FORMAT.format(new Date());
        String fileName = TMP_IMG_PREFIX + timeStamp + ".jpg";
        Bitmap bitmap = getDownscaledBitmap(imageUri, orientation);

        if (orientation > 0) {
            bitmap = fixImageRotation(orientation, bitmap);
        }

        return saveBitmap(bitmap, fileName);
    }

    /**
     * Saves the passed bitmap in the app internal folder using the passed file name.
     *
     * @param bitmap   The bitmap to save.
     * @param fileName The file name for the bitmap.
     * @return The path to the saved bitmap.
     * @throws IOException Thrown if writing to a stream failed.
     */
    private String saveBitmap(Bitmap bitmap, String fileName) throws IOException {
        ByteArrayOutputStream bitmapOut = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapOut);
        OutputStream fileOut = appCtx.openFileOutput(fileName, Context.MODE_PRIVATE);
        bitmapOut.writeTo(fileOut);
        bitmapOut.close();
        fileOut.close();
        return new File(appCtx.getFilesDir(), fileName).getPath();
    }

    /**
     * Rotates the image to the right orientation if needed. Then saves it. This is needed for
     * images chosen via the gallery app on some devices.
     *
     * @param orientation The current orientation of the image.
     * @param source      The image to rotate.
     * @throws IOException Thrown if writing to a stream failed.
     */
    private Bitmap fixImageRotation(int orientation, Bitmap source) throws IOException {
        switch (orientation) {
            case 90:
                return rotateImage(source, 90);

            case 180:
                return rotateImage(source, 180);

            case 270:
                return rotateImage(source, 270);

            default:
                return source;
        }
    }

    /**
     * @return The orientation of the image with the passed uri. TODO: The album app uri on Sony
     * Xperia Z1 always returns orientation = 0. Google's photo app uri returns 90, the correct
     * value. Can this be fixed?
     */
    public int getOrientation(Uri uri) {
        String[] columns = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = appCtx.getContentResolver().query(uri, columns, null, null, null);
        int orientation = 0;

        if (cursor != null && cursor.moveToFirst()) {
            int colIndex = cursor.getColumnIndex(columns[0]);

            if (colIndex != -1) {
                orientation = cursor.getInt(colIndex);
            }

            cursor.close();
        }

        return orientation;
    }

    /**
     * Rotates the passed bitmap using the passed angle.
     *
     * @param source The image to rotate.
     * @param angle  The angle to use for the rotation.
     * @throws IOException Thrown if writing to a stream failed.
     */
    private Bitmap rotateImage(Bitmap source, float angle) throws IOException {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(
                source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * Returns a downscaled and compressed version of the bitmap at the passed file path.
     *
     * @param filePath The path to the bitmap which is to be optimized.
     * @return The downscaled bitmap in form of a byte array stream.
     */
    public ByteArrayOutputStream getOptimizedImageForUpload(String filePath, Context appCtx) {
        FutureTarget<Bitmap> futureTarget = Glide.with(appCtx)
                .load(filePath)
                .asBitmap()
                .skipMemoryCache(true)
                .into(MIN_IMG_WIDTH, MIN_IMG_HEIGHT);

        Bitmap bitmap = null;

        try {
            bitmap = futureTarget.get();
        } catch (InterruptedException | ExecutionException e) {
            Logger.e("Could not load bitmap.");
            // TODO Handle errors more gracefully!
        }

        if (bitmap == null) {
            // TODO Handle errors more gracefully!
            Logger.e("Could not decode bitmap.");
            throw new IllegalArgumentException("Could not decode bitmap.");
        }


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
        Glide.clear(futureTarget);
        return os;
    }

    /**
     * Deletes the temporary image files which were created when the user chose a picture from the
     * Storage Access Framework on the PictureSourceActivity.
     */
    public void deleteTempImages() {
        String[] imageFileNames = appCtx.getFilesDir().list();

        if (imageFileNames != null && imageFileNames.length > 0) {
            for (String fileName : imageFileNames) {
                // Check if image is temp. If it is older than 3 hours, delete it.
                if (fileName.startsWith(ImageHelper.TMP_IMG_PREFIX)) {
                    int start = fileName.indexOf("_") + 1;
                    int end = fileName.indexOf(".");
                    String timeStamp = fileName.substring(start, end);

                    if (isOlderThanThreeHours(timeStamp)) {
                        if (appCtx.deleteFile(fileName)) {
                            Logger.d("Deleted temp image: " + fileName);
                        }
                    }
                }
            }
        }
    }

    /**
     * Parses the passed time stamp and checks if it is more than three hours in the past.
     *
     * @param timeStamp The time stamp to check.
     * @return True if the time stamp is more then three hours in the past, false otherwise.
     */
    private boolean isOlderThanThreeHours(String timeStamp) {
        try {
            Date date = ImageHelper.TIMESTAMP_FORMAT.parse(timeStamp);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, -3);
            Date threeHoursBefore = calendar.getTime();
            return date.before(threeHoursBefore);

        } catch (ParseException e) {
            return false;
        }
    }

    private void deleteCachedImageIfExists(String imageFolderPath, String pic) {
        if (pic != null && pic.startsWith("file://" + imageFolderPath)) {
            // image was taken only for this ad -> can be deleted
            File f = new File(pic.substring("file://".length()));

            if (f.exists() && f.isFile()) {
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }
        }
    }

    /**
     * Checks if external storage is available for read and write.
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return MEDIA_MOUNTED.equals(state);
    }
}
