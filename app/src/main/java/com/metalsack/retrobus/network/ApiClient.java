package com.metalsack.retrobus.network;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.metalsack.retrobus.BuildConfig;
import com.metalsack.retrobus.events.RequestFinishedEvent;
import com.metalsack.retrobus.network.requests.AbstractApiRequest;
import com.metalsack.retrobus.network.requests.ExpertLIstRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides request functions for all api calls. This class maintains a map of running requests to
 * provide request cancellation.
 */
public class ApiClient {

    private static final String LOGTAG = ApiClient.class.getSimpleName();

    private static final int HTTP_TIMEOUT = 30;

    // Cache size for the OkHttpClient
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    /*LIVE URL*/
    public static final String API_BASE_URL ="http://chatblitz.in/work/foodetect/web_health/";

    /*Local URL*/
    //public static final String API_BASE_URL ="http://192.168.1.235/foodetect/web_health/";

    /**
     * Makes the APIService calls.`
     */
    private final APIService mAPIService;

    /**
     * The list of running requests. Used to cancel requests.
     */
    private final Map<String, AbstractApiRequest> requests;

    private static OkHttpClient httpClient;
    private static Retrofit.Builder builder;

    private static Interceptor requestInterceptor = new Interceptor() {
        @Override
          public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            /*// Request Intercepting...
            JSONArray similararticle = null;
            ResponseBody body;
            Request original = chain.request();

            // Response Intercepting...
            response = chain.proceed(original);

            String res = response.body().string();

            JSONObject jMainObj = null;
            try {
                *//*jMainObj = new JSONObject(res);
                if (original.url().toString().contains("get_all_public_forum")) {

                    JSONArray jMainArray =  jMainObj.getJSONArray("info");
                    for (int i = 0; i < jMainArray.length(); i++) {
                        JSONObject object;
                        t  similararticle = object.getJSONArray("similar_article");

                            for (int j = 0; j < similararticle.length(); j++) {

                                JSONObject jsonObjectData = similararticle.getJSONObject(j);

                                if (jsonObjectData.has("similar_article")) {
                                    Log.e("ApiClient", "&&&&&& &&&&&& intercept: " + jsonObjectData.getString("similar_article"));
                                }
                                else {
                                    Log.e("ApiClient", "intercept, no similar article available...");
                                }
                            }ry {
                            object = jMainArray.getJSONObject(i);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }*//*

                MediaType contentType = response.body().contentType();
                body = ResponseBody.create(contentType, res);

                return response.newBuilder().body(body).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
*/

            return response;


        }
        // Do anything with response here


    };

    public ApiClient(Application app) {

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder().cache(cache);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okBuilder.addInterceptor(loggingInterceptor);
                    //.addInterceptor(requestInterceptor);
        }
        //okBuilder.addInterceptor(requestInterceptor);
        okBuilder.addInterceptor(requestInterceptor);
        httpClient = okBuilder
                .connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
                .build();

        builder = new Retrofit.Builder();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

        retrofit = builder
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .build();

        mAPIService = retrofit.create(APIService.class);
        requests = new HashMap<>();
        EventBus.getDefault().register(this);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    static Retrofit retrofit;

/*
* ==========================================================================================================================
* API METHODS
* ==========================================================================================================================
* */
/*

    */
/*Registration API Method*//*


    public void registration(String requestTag,
                             String fullName,
                             String age,
                             String city,
                             String state,
                             String email,
                             String password,
                             String mobNo,
                             String profilePic,
                             String deviceId,
                             String deviceType) {
        SignUpRequest request = new SignUpRequest(mAPIService,
                requestTag,
                fullName,
                age,
                city,
                state,
                email,
                password,
                mobNo,
                profilePic,
                deviceId,
                deviceType);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Update Profile API Method*//*

    public void update_profile(String requestTag,
                               String fullName,
                               String age,
                               String city,
                               String state,
                               String email,
                               String mobNo,
                               String profilePic,
                               String userId
    ) {
        UpdateProfileRequest request = new UpdateProfileRequest(mAPIService,
                requestTag,
                fullName,
                age,
                city,
                state,
                email,
                mobNo,
                profilePic,
                userId);
        requests.put(requestTag, request);
        request.execute();
    }


    */
/*Login API Method*//*

    public void login(String requestTag,
                      String email, String password,
                      String deviceId, String deviceType) {
        LoginRequest request = new LoginRequest(mAPIService, requestTag,
                email, password, deviceId, deviceType);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Forgot Password API Method*//*

    public void forgot_password(String requestTag, String email) {
        ForgotPasswordRequest request = new ForgotPasswordRequest(mAPIService, requestTag,
                email);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Change Password API Method*//*

    public void change_password(String requestTag, String userId, String email, String currentPassword, String newPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(mAPIService, requestTag, userId,
                email, currentPassword, newPassword);
        requests.put(requestTag, request);


        request.execute();
    }

    */
/*Autosuggest Product names API Method used for getting suggestion of product names when user types something in scan by
    * product name field*//*


    public void get_Autosuggest_productName(String requestTag, String userId, String search) {
        AutoSuggestProductRequest request = new AutoSuggestProductRequest(mAPIService, requestTag, userId, search);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Scan/Search Product API Method used for searching by category and scanning by name and barcode*//*

    public void scan_search_product(String requestTag, String userId, String productId,
                                    String productBarcode, String productName,
                                    String companyName, String catName,
                                    String allergenId, String inngredientId,
                                    String searchType, String startId) {
        ScanSearchProductRequest request = new ScanSearchProductRequest(mAPIService, requestTag, userId, productId
                , productBarcode, productName, companyName, catName, allergenId, inngredientId, searchType, startId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Product Feedback API Method used for submitting user's comment on perticular product to server*//*

    public void product_feedback(String requestTag, String productId, String userId, String comment) {
        ProductFeedbackRequest request = new ProductFeedbackRequest(mAPIService, requestTag, productId, userId,
                comment);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/* Add Product Rating API Method used to submit user rating of perticular product to server*//*

    public void add_product_rating(String requestTag, String userId, String rating, String productId) {
        ProductRatingRequest request = new ProductRatingRequest(mAPIService, requestTag, userId, rating, productId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Autosuggest ingredient API Method used for getting suggestion of product ingredients when user types something in
    ingredients field of error report and product request screen *//*

    public void get_Autosuggest_ingredients(String requestTag, String userId, String search) {
        AutoSuggestIngredientsRequest request = new AutoSuggestIngredientsRequest(mAPIService, requestTag, userId, search);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Get Categories API method for getting list of category in Search by category,error report,product request and
    * public forum new post screen*//*

    public void get_Categories(String requestTag, String userId) {
        CategoryRequest request = new CategoryRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Check package validity API method used for checking package status and for managing first 3 free search/scans of new user*//*

    public void check_package_validity(String requestTag, String userId) {
        CheckPackageValidityRequest request = new CheckPackageValidityRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }


    */
/*Error Report API that will be used in error report screen*//*

    public void report_error(String requestTag, String userId, String productId,
                             String productName, String productCat,
                             String company, String ingredients,
                             String barcode, String price, String userFile) {
        ReportAnErrorRequest request = new ReportAnErrorRequest(mAPIService, requestTag,
                userId, productId, productName,
                productCat, company, ingredients,
                barcode, price, userFile);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Product Request API that will be used to make a request for new product*//*

    public void product_request(String requestTag, String userId,
                                String productName, String productCat,
                                String company, String ingredients,
                                String barcode, String price, String quantity, String description, String userFile) {
        RequestAProductRequest request = new RequestAProductRequest(mAPIService, requestTag,
                userId, productName,
                productCat, company, ingredients,
                barcode, price, quantity,
                description, userFile);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Get Appergen API will be used to get all available allergen*//*

    public void get_Allergens(String requestTag, String userId) {
        AllergenRequest request = new AllergenRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Add selected Allergen API will be used to add user's selected allergen back to server*//*

    public void add_Selected_Allergens(String requestTag, String userId, String allergenId) {
        AddSelectedAllergenRequest request = new AddSelectedAllergenRequest(mAPIService, requestTag, userId, allergenId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*PUublic Forum API that will be used to get list of public forum*//*

    public void get_all_public_forum(String requestTag, String userId, String forumId,
                                     String searchText,
                                     String createdDate, String articleType,
                                     String uploadedBy, String start,
                                     String limit, String startId) {
        PublicForumRequest request = new PublicForumRequest(mAPIService, requestTag,
                userId,
                forumId,
                searchText, createdDate,
                articleType, uploadedBy,
                start, limit, startId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Lile API that will be used so that user can like/dislike any post of public forum*//*

    public void PublicForum_Like(String requestTag, String userId, String forumId) {
        PublicForumLikeRequest request = new PublicForumLikeRequest(mAPIService, requestTag, userId, forumId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Public forum New Post API will be used to add a public forum post*//*

    public void addPUblicForum_Newpost(String requestTag, String userId,
                                       String title, String text,
                                       String allergenId, String mediaType,
                                       String userFile) {
        PublicForumNewPostRequest request = new
                PublicForumNewPostRequest(mAPIService,
                requestTag,
                userId,
                title,
                text,
                allergenId,
                mediaType,
                userFile);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Add search history API wilkl add a product to server as a history item it requires product id for that*//*

    public void Add_Search_History(String requestTag, String userId, String productId, String searchBy) {
        AddtoSearchHistoryRequest request = new AddtoSearchHistoryRequest(mAPIService, requestTag, userId, productId, searchBy);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Get Search history API that gets the search history from server*//*

    public void Get_Sarch_History(String requestTag, String userId, String start) {
        SearchHistoryRequest request = new SearchHistoryRequest(mAPIService, requestTag, userId, start);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Public Forum Comment API will get submit user's comment on perticular public forum post to server *//*

    public void publicforum_comment(String requestTag, String userId, String forumId, String comment) {
        PublicForumCommentRequest request = new PublicForumCommentRequest(mAPIService, requestTag, userId, forumId,
                comment);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/**
     * USed to delete comments
     * @param requestTag
     * @param userId
     * @param commentId
     * @param comment_type app or post
     *//*

    public void delete_comment(String requestTag, String userId, String commentId, String comment_type) {
        DeleteCommentRequest request = new DeleteCommentRequest(mAPIService, requestTag, userId, commentId,
                comment_type);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Public Forum Get Comment will get the comments of perticular public forum comment*//*

    public void public_forum_get_comment(String requestTag, String userId, String forumId) {
        PublicForumGetCommentRequest request = new PublicForumGetCommentRequest(mAPIService, requestTag, userId, forumId
        );
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*NOTE IN USE*//*

    */
/*Get transaction history API will get user's transaction history in Transaction history screen*//*

    public void get_trans_history(String requestTag, String userId) {
        TransactionHistoryRequest request = new TransactionHistoryRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Get Pacakages API will get the list of availale packages from server*//*

    public void get_packages(String requestTag, String userId) {
        SubscriptionPackagesRequest request = new SubscriptionPackagesRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Get Credit API is to get user's current credits
    * NOTE:: NOT IN USE*//*

    public void get_credit(String requestTag, String userId) {
        CreditRequest request = new CreditRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Subscribe package API, after Oxygen Payment procedure is successfully done. then this API will use the transaction id
    * and package id to subscribe that package*//*

    public void subscribe_package(String requestTag, String userId, String transId, String packageId, String payGateway) {
        SubscribeAPacakgeRequest request = new SubscribeAPacakgeRequest(mAPIService, requestTag, userId, transId, packageId, payGateway);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/**//*
*/
/*Add credit API, after Oxygen Payment procedure is successfully done. then this API will use the transaction id
    * and credit of that package to credit of user*//*

    public void add_credit(String requestTag, String userId, String transId, String credit,
                           int amount, String bonusCredit, String payGateway) {
        AddCreditRequest request = new AddCreditRequest(mAPIService, requestTag, userId, transId,
                credit, amount, bonusCredit, payGateway);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Package Subscription History, this API will get the history of user's previously subscribed packages*//*

    public void package_subscription_history(String requestTag, String userId) {
        PackageSubscriptionHistoryRequest request = new PackageSubscriptionHistoryRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Puvlic Forum Add viewer API will be called to to increse the number of view of perticular public forum post
     when that post is viewed by user*//*

    public void public_forum_add_viewer(String requestTag, String userId, String forumId) {
        PublicForumAddViewerRequest request = new PublicForumAddViewerRequest(mAPIService, requestTag, userId, forumId
        );
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Get notification API will be used to get  in app notification list from server*//*

    public void get_notification(String requestTag, String userId) {
        NotificationRequest request = new NotificationRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Get Advertisement API will be used to get advertisement every 10 seconds from server *//*

    public void get_Advertisement(String requestTag, String userId, String addType) {
        AdvertisementRequest request = new AdvertisementRequest(mAPIService, requestTag, userId, addType);
        requests.put(requestTag, request);
        request.execute();
    }

    */
/*Add click API will be called when user clicks on advertisement*//*

    public void add_click(String requestTag, String userId, String addId) {
        AdvertisementRequest request = new AdvertisementRequest(mAPIService, requestTag, userId, addId);
        requests.put(requestTag, request);
        request.execute();
    }

    public void checkPackageSubscriptionForChatFeature(String requestTag, String userId) {
        CheckPackageValidityForChatFeatureRequest request = new CheckPackageValidityForChatFeatureRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }
*/

    //=============================================================================================
    //Chat-Video-Audio Feature

    public void get_recommended_users(String requestTag, String userId) {
        ExpertLIstRequest request = new ExpertLIstRequest(mAPIService, requestTag, userId);
        requests.put(requestTag, request);
        request.execute();
    }


    /*public void getFullName(String requestTag, String quickblox_id) {
        FullUserNameRequest request = new FullUserNameRequest(mAPIService, requestTag, quickblox_id);
        requests.put(requestTag, request);
        request.execute();
    }*/


    /**
     * Execute a request to retrieve the update message. See  for
     * parameter details.
     *
     * @param requestTag The tag for identifying the request.
     */

    // ============================================================================================
    // Public functions
    // ============================================================================================

    /**
     * Look up the event with the passed tag in the event list. If the request is found, cancel it
     * and remove it from the list.
     *
     * @param requestTag Identifies the request.
     * @return True if the request was cancelled, false otherwise.
     */
    public boolean cancelRequest(String requestTag) {
        System.gc();
        AbstractApiRequest request = requests.get(requestTag);

        if (request != null) {
            request.cancel();
            requests.remove(requestTag);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the request with the passed tag is in the list of running requests, false
     * otherwise.
     */
    public boolean isRequestRunning(String requestTag) {
        return requests.containsKey(requestTag);
    }


    /**
     * A request has finished. Remove it from the list of running requests.
     *
     * @param event The event posted on the EventBus.
     */
    @Subscribe
    public void onEvent(RequestFinishedEvent event) {
        System.gc();
        requests.remove(event.getRequestTag());
    }


}
