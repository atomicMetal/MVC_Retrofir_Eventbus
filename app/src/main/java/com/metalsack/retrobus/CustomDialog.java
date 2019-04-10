package com.metalsack.retrobus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomDialog {
    Context context;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    AlertDialog alertDialogExit;
    boolean isExit = false;
    Dialog dialog;
    boolean flag;

    private static CustomDialog instance;

    //public Typeface DINProMedium,DINProRegular,DINRegular;

    public CustomDialog(Context context) {
        this.context = context;
        alertDialog = new AlertDialog.Builder(context);
    }

    private CustomDialog() {
    }

    public static CustomDialog getInstance() {
        if (instance == null) {
            instance = new CustomDialog();
        }
        return instance;
    }

    public void show() {

        dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loader);

        MaterialProgressBar progressBar2 = (MaterialProgressBar) dialog.findViewById(
                R.id.progressBar2);
        progressBar2.setCircleBackgroundEnabled(false);

        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*Show dialog with Message*/
    public void show(Context context, String message, boolean isCancelable) {

        //dialog = new Dialog(context, R.style.DialogTheme);

        dialog = new Dialog(context, android.R.style.Animation_Dialog);
        Drawable d = new ColorDrawable(Color.TRANSPARENT);
        //d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);

        dialog.setCancelable(isCancelable);
        dialog.setContentView(R.layout.dialog_loader);

        TextView txtLoaderTitle = (TextView) dialog.findViewById(R.id.txtLoaderTitle);
        txtLoaderTitle.setText(message);
        //txtLoaderTitle.setTypeface(MyApplication.appInstance.fontFutura);

        MaterialProgressBar progressBar2 = (MaterialProgressBar) dialog.findViewById(
                R.id.progressBar2);
        progressBar2.setCircleBackgroundEnabled(false);

        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
    }

    /*Show dialog without text Message*/
    public void show(Context context, boolean isCancelable) {

        //dialog = new Dialog(context, R.style.DialogTheme);

        dialog = new Dialog(context, android.R.style.Animation_Dialog);
        Drawable d = new ColorDrawable(Color.TRANSPARENT);
        //d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);

        dialog.setCancelable(isCancelable);
        dialog.setContentView(R.layout.dialog_loader);

        TextView txtLoaderTitle = (TextView) dialog.findViewById(R.id.txtLoaderTitle);
        //txtLoaderTitle.setTypeface(MyApplication.appInstance.fontFutura);

        MaterialProgressBar progressBar2 = (MaterialProgressBar) dialog.findViewById(
                R.id.progressBar2);
        progressBar2.setCircleBackgroundEnabled(false);

        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
    }


    public void show(String strLoaderTitle) {

        dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loader);

        TextView txtLoaderTitle = (TextView) dialog.findViewById(R.id.txtLoaderTitle);
        MaterialProgressBar progressBar2 = (MaterialProgressBar) dialog.findViewById(
                R.id.progressBar2);
        progressBar2.setCircleBackgroundEnabled(false);

        txtLoaderTitle.setText(strLoaderTitle);
        //txtLoaderTitle.setTypeface(MyApplication.appInstance.fontFutura, Typeface.BOLD);

        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void hide() {
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDialogShowing() {
        if (dialog != null && dialog.isShowing())
            return dialog.isShowing();
        else
            return false;
    }

    public void showAlert(String msg) {

        dialog = new Dialog(context, R.style.DialogTheme);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_alert, null);

        TextView txtV = new TextView(context);
        txtV.setText(msg);
//		txtV.setMaxLines(5);
//		txtV.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//		txtV.setMovementMethod(new ScrollingMovementMethod());
        txtV.setTextAppearance(context, R.style.txtAlertstyle);
        txtV.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        txtV.setLayoutParams(lp);
        LinearLayout ln = (LinearLayout) view.findViewById(R.id.laymsgbox);
        ln.addView(txtV);

        Button btnOkPopup = (Button) view.findViewById(R.id.btnOkPopup);
        //txtV.setTypeface(MyApplication.appInstance.fontFutura);
        //btnOkPopup.setTypeface(MyApplication.appInstance.fontFutura);

        dialog.setContentView(view);

        /** Set Dialog width match parent */
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void showAlert(boolean isCancelable, String msg) {

        dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setCancelable(isCancelable);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_alert, null);

        TextView txtV = new TextView(context);
        txtV.setText(msg);
        txtV.setTextAppearance(context, R.style.txtAlertstyle);
        txtV.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        txtV.setLayoutParams(lp);
        LinearLayout ln = (LinearLayout) view.findViewById(R.id.laymsgbox);
        ln.addView(txtV);

        Button btnOkPopup = (Button) view.findViewById(R.id.btnOkPopup);
        // btnOkPopup.setOnClickListener(this);

        // Set Font Type
//		txtV.setTypeface(myApp.DINProRegular);
//		btnOkPopup.setTypeface(myApp.DINProRegular);

        dialog.setContentView(view);
        /** Set Dialog width match parent */
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void showAlertForInternetConection(String msg) {

        dialog = new Dialog(context, R.style.DialogTheme);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_alert, null);

        TextView txtV = new TextView(context);
        txtV.setText(msg);
//		txtV.setMaxLines(5);
//		txtV.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//		txtV.setMovementMethod(new ScrollingMovementMethod());
        txtV.setTextAppearance(context, R.style.txtAlertstyle);
        txtV.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        txtV.setLayoutParams(lp);
        LinearLayout ln = (LinearLayout) view.findViewById(R.id.laymsgbox);
        ln.addView(txtV);

        Button btnOkPopup = (Button) view.findViewById(R.id.btnOkPopup);

        //txtV.setTypeface(MyApplication.appInstance.fontFutura);
        //btnOkPopup.setTypeface(MyApplication.appInstance.fontFutura);
        btnOkPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.setContentView(view);

        /** Set Dialog width match parent */
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void showUpdate(Context myAccountActivity, String string, final LinearLayout detailsLay,
                           final LinearLayout editLay) {
        // TODO Auto-generated method stub
        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
        alertbox.setMessage(string);
        alertbox.setCancelable(false);
        alertbox.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        // alertbox.show();
        AlertDialog dialog = alertbox.show();

        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }
}
