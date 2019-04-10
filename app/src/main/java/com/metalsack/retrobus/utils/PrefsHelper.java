package com.metalsack.retrobus.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dhruv on 18/6/16.
 */
public class PrefsHelper {

    private static final String LOGININFO = "logininfo";
    private static final String PREF_IS_NORMAL_LOGIN_KEY = "is_normal_login_key";
    private static final String PREF_SOCIAL_USER_KEY = "social_user_key";
    private static final String PREF_NORMAL_USER_KEY = "normal_user_key";
    private static final String PREF_GMAIL_USER_KEY = "gmail_user_key";
    private static final String PREF_IS_SOCIAL_LOGIN_KEY = "is_social_login_key";
    private static final String PREF_IS_GMAIL_LOGIN_KEY = "is_gmail_login_key";
    private static final String PREF_GMAIL_ADDRESS="gmailaddress";
    private static final String FCM_KEY = "fcm_key";
    private static final String USERMOBILE = "user_mobile";
    private static final String USERID = "u_id";
    private static final String USER_Email = "u_email";
    private static final String USER_NAME="user_name";
    private static final String USER_PROFILEPIC="user_profilepic";
    private static final String FOOTER_MENU_STATUS="footermenustatus";
    private static final String STOREED_PRODUCT_NAME="storedproductname";
    private static final String STORE_SIMILARPRODUCT_POSITION="storesimilarproductposition";
    private static final String USE_STOREED_PRODUCT_NAME="usestoredproductname";
    private static final String IS_RATED="israted";
    private static final String USERRATEING="userrating";
    private static final String ALLERGNES="allergens";
    private static final String IS_LIKED="isliked";
    private static final String PUBLICFORUM_DIALOGSHOW="publicforum_dialogshow";
    private static final String HOMESCREEN_BARCODE_DIALOGSHOW="homescreen_barcode_dialogshow";
    private static final String SCANINFO_DIALOGSHOW="scaninfo_dialogshow";
    private static final String ALLERGENREMOVEDFROM_SEARCH="allergenremovedfromsearch";

    public static final String ARRAY_SUBSCRIBE_ISSUES = "array_subscribe_issues";
    public static final String IS_DRAWER_OPEN = "isdraweropen";

    private static final String LATEST_ISSUE_NO = "latest_issue_no";
    private static final String END_ISSUE_NO = "end_issue_no";
    private static final String SEARCHCATEGORY="searchcategory";
    private static  final String SEARCHNAME="searchname";
    private static  final String SEARCHBARCODE="searchbarcode";
    private static  final String STORENAME="storename";
    private static  final String STOREBARCODE="storebarcode";
    public static final String IS_ENDUSER_AGREEMENT_SHOWN="isenduseragreementshown";
    public static final String MOBNO_AS_QUICKBLOX_ID_PW="mobilenoasquickbloxisow";
    public static final String PACKAGE_IS_ACTIVE="packageisactive";


    public static void clearLoginData() {
        Prefs.remove(LOGININFO);
        Prefs.remove(USER_NAME);
        Prefs.remove(USER_PROFILEPIC);
        Prefs.remove(USER_Email);
        Prefs.remove(USERID);
        Prefs.remove(PREF_IS_NORMAL_LOGIN_KEY);
        Prefs.remove(PREF_SOCIAL_USER_KEY);
        Prefs.remove(PREF_NORMAL_USER_KEY);
        Prefs.remove(PREF_IS_SOCIAL_LOGIN_KEY);

        //Prefs.clear();
    }


    public static void setEndUserLicenceAgreementShown(boolean isShown) {
        saveBooleanData(IS_ENDUSER_AGREEMENT_SHOWN, isShown);
    }

    public static boolean isEndUserLicenceAgreementShown() {
        return getBooleanData(IS_ENDUSER_AGREEMENT_SHOWN);
    }
   /* public static void saveLoginInfo(LoginInfo loginInfo) {
        if (loginInfo != null) {
            saveData(LOGININFO, loginInfo);
        }
    }

    public static LoginInfo getLoginInfo() throws InstantiationException {
        return (LoginInfo) getData(LOGININFO, new LoginInfo());
    }*/


   /* public static void saveAllergenInfo(ArrayList<AllergenInfo> allergenInfo) {
        if (allergenInfo != null) {
            saveData(ALLERGNEINFO, allergenInfo);
        }
    }

    public static ArrayList<AllergenInfo> getAllergenInfo() throws InstantiationException {
        return (ArrayList<AllergenInfo> ) getData(ALLERGNEINFO, new AllergenInfo());
    }*/

    public static void setPrefNormalLoginUser(boolean isNormalUser) {
        saveBooleanData(PREF_IS_NORMAL_LOGIN_KEY, isNormalUser);
    }

    public static boolean isNormalUser() {
        return getBooleanData(PREF_IS_NORMAL_LOGIN_KEY);
    }

    public static void setPrefFirebaseLoginUser(boolean isFirebaseUser) {
        saveBooleanData(PREF_IS_SOCIAL_LOGIN_KEY, isFirebaseUser);
    }

    public static boolean isSocialUser() {
        return getBooleanData(PREF_IS_SOCIAL_LOGIN_KEY);
    }

    public static void saveData(String key, Object object) {
        if (object != null) {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            Prefs.putString(key, json);
        }
    }

    public static Object getData(String key, Object object) throws InstantiationException {
        Gson gson = new Gson();
        String json = Prefs.getString(key, "");
        return gson.fromJson(json, object.getClass());
    }
    public static void saveArrayData(String key, ArrayList object) {
        if (object != null) {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            Prefs.putString(key, json);
        }
    }

    public static Object getArrayData(String key, Object object) throws InstantiationException {
        Gson gson = new Gson();
        String json = Prefs.getString(key, "");
        return gson.fromJson(json, object.getClass());
    }

    public static void saveBooleanData(String key, boolean value) {
        Prefs.putBoolean(key, value);
    }

    public static boolean getBooleanData(String key) {
        return Prefs.getBoolean(key, false);
    }

    public static void saveFCMKey(String fcmToken) {
        Prefs.putString(FCM_KEY, fcmToken);
    }

    public static String getFCMKey() {
        return Prefs.getString(FCM_KEY, "");
    }

    public static void saveuser_id(String u_id) {
        Prefs.putString(USERID, u_id);
    }

    public static String getuser_id() {
        return Prefs.getString(USERID, "");
    }

    public static void saveuser_email(String email) {
        Prefs.putString(USER_Email, email);
    }

    public static String getuser_email() {
        return Prefs.getString(USER_Email, "");
    }

    public static void save_user_name(String user_name) {
        Prefs.putString(USER_NAME, user_name);
    }

    public static String get_user_name() {
        return Prefs.getString(USER_NAME, "");
    }

    public static void save_user_profilepic(String user_profilepic) {
        Prefs.putString(USER_PROFILEPIC, user_profilepic);
    }

    public static String get_user_profilepic() {
        return Prefs.getString(USER_PROFILEPIC, "");
    }
    public static void saveMobile(String mobile) {
        Prefs.putString(USERMOBILE, mobile);
    }

    public static String getMobile() {
        return Prefs.getString(USERMOBILE, "");
    }

    public static void savegmailID(String gmailId)
    {
       Prefs.putString(PREF_IS_GMAIL_LOGIN_KEY, gmailId);
    }
    public static String getgmailID()
    {
        return Prefs.getString(PREF_IS_GMAIL_LOGIN_KEY,"");
    }

    public static void savegmailAddress(String gmailAddress)
    {
        Prefs.putString(PREF_GMAIL_ADDRESS,gmailAddress);
    }
    public static String getgmailAddress()
    {
        return Prefs.getString(PREF_GMAIL_ADDRESS,"");
    }

    public static void setEndIssuestring(String e_id) {
        Prefs.putString(END_ISSUE_NO, e_id);
    }

    public static String getEndIssuestring() {
        return Prefs.getString(END_ISSUE_NO, "");
    }

    public static void setLatestIssue(int l_id) {
        Prefs.putInt(LATEST_ISSUE_NO, l_id);
    }

    public static int getLatestIssue() {
        return Prefs.getInt(LATEST_ISSUE_NO, 0);
    }

    public static void setEndIssue(int e_id) {
        Prefs.putInt(END_ISSUE_NO, e_id);
    }

    public static int getEndIssue() {
        return Prefs.getInt(END_ISSUE_NO, 0);
    }
    public static void storeSubscribedIssues(Context context, List favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(ARRAY_SUBSCRIBE_ISSUES, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString("SUBSCRIBED_ISSUE", jsonFavorites);
        editor.commit();
    }

    public static ArrayList<String> getSubscribedIssues(Context context) {
        SharedPreferences settings;
        List<String> favorites;

        settings = context.getSharedPreferences(ARRAY_SUBSCRIBE_ISSUES,
                Context.MODE_PRIVATE);

        if (settings.contains("SUBSCRIBED_ISSUE")) {
            String jsonFavorites = settings.getString("SUBSCRIBED_ISSUE", null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites,
                    String[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<String>(favorites);
        } else
            return null;

        return (ArrayList<String>) favorites;
    }
    public static void setFooterMenuStatus(String footerMenuStatus) {
        Prefs.putString(FOOTER_MENU_STATUS, footerMenuStatus);
    }

    public static String getFooterMenuStatus() {
        return Prefs.getString(FOOTER_MENU_STATUS,"");
    }


    public static void setIs_Rrated(boolean is_rrated) {
        saveBooleanData(IS_RATED, is_rrated);
    }

    public static boolean Is_Rated() {
        return getBooleanData(IS_RATED);
    }

    public static void setIs_Liked(boolean is_liked) {
        saveBooleanData(IS_LIKED, is_liked);
    }

    public static boolean Is_Liked() {
        return getBooleanData(IS_LIKED);
    }

    public static void Add_UserRating(float userrating) {
        Prefs.putFloat(USERRATEING, userrating);
    }

    public static float Get_UserRating() {
        return Prefs.getFloat(USERRATEING);
    }

    public static boolean Is_PublicForum_DialogShowen() {
        return getBooleanData(PUBLICFORUM_DIALOGSHOW);
    }

    public static void PublicForum_Dialog_IsShowed(boolean is_showed) {
        saveBooleanData(PUBLICFORUM_DIALOGSHOW, is_showed);
    }

    public static boolean Is_HomeScreenBarcode_DialogShowen() {
        return getBooleanData(HOMESCREEN_BARCODE_DIALOGSHOW);
    }

    public static void HomeScreenBarcode_DialogShowed(boolean is_showed) {
        saveBooleanData(HOMESCREEN_BARCODE_DIALOGSHOW, is_showed);
    }

    public static void saveAllergens(String allergens) {
        Prefs.putString(ALLERGNES, allergens);
    }

    public static String getAllergens() {
        return Prefs.getString(ALLERGNES, "");
    }

    public static boolean Is_Scaninfo_DialogShowen() {
        return getBooleanData(SCANINFO_DIALOGSHOW);
    }

    public static void ScanInfo_DialogShowed(boolean is_showed) {
        saveBooleanData(SCANINFO_DIALOGSHOW, is_showed);
    }

    public static boolean getAllergenremoved_fromSearch() {
        return getBooleanData(ALLERGENREMOVEDFROM_SEARCH);
    }
    public static void set_Allergenremoved_fromSearch(boolean is_showed) {
        saveBooleanData(ALLERGENREMOVEDFROM_SEARCH, is_showed);
    }

    public static boolean getDraweropen() {
        return getBooleanData(IS_DRAWER_OPEN);
    }
    public static void setDraweropen(boolean is_showed) {
        saveBooleanData(IS_DRAWER_OPEN, is_showed);
    }

    public static void StoreProduct(String stored_productname) {
        Prefs.putString(STOREED_PRODUCT_NAME, stored_productname);
    }

    public static String getStoreddProduct() {
        return Prefs.getString(STOREED_PRODUCT_NAME, "");
    }
    public static boolean Use_stored_productname() {
        return getBooleanData(USE_STOREED_PRODUCT_NAME);
    }
    public static void setUse_stored_productname(boolean use_stored_productname) {
        saveBooleanData(USE_STOREED_PRODUCT_NAME, use_stored_productname);
    }


    public static void Store_similarproduct_position(String position) {
        Prefs.putString(STORE_SIMILARPRODUCT_POSITION, position);
    }

    public static String get_similarproduct_position() {
        return Prefs.getString(STORE_SIMILARPRODUCT_POSITION, "");
    }


    public static void savesearch_category(String cat) {
        Prefs.putString(SEARCHCATEGORY, cat);
    }

    public static String getsearch_category() {
        return Prefs.getString(SEARCHCATEGORY, "");
    }

    public static void savesearch_pname(String pname) {
        Prefs.putString(SEARCHNAME, pname);
    }

    public static String getsearch_pname() {
        return Prefs.getString(SEARCHNAME, "");
    }


    public static void savesearch_barcode(String barcode) {
        Prefs.putString(SEARCHBARCODE, barcode);
    }

    public static String getsearch_barcode() {
        return Prefs.getString(SEARCHBARCODE, "");
    }


    public static void setStorePname(boolean isPname) {
        saveBooleanData(STORENAME, isPname);
    }

    public static boolean isStoredPname() {
        return getBooleanData(STORENAME);
    }


    public static void setStoreBarcode(boolean isBarcode) {
        saveBooleanData(STOREBARCODE, isBarcode);
    }

    public static boolean isStoredBarcode() {
        return getBooleanData(STOREBARCODE);
    }


    public static void savemob_QB_Id_Pw(String mob_QB_Id_Pw) {
        Prefs.putString(MOBNO_AS_QUICKBLOX_ID_PW, mob_QB_Id_Pw);
    }

    public static String getmob_QB_Id_Pw() {
        return Prefs.getString(MOBNO_AS_QUICKBLOX_ID_PW, "");
    }

    public static boolean getPackageISActive() {
        return getBooleanData(PACKAGE_IS_ACTIVE);
    }
    public static void setPackageIsActive(boolean packageIsActive) {
        saveBooleanData(PACKAGE_IS_ACTIVE, packageIsActive);
    }

}
