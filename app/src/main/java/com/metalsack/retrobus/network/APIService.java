package com.metalsack.retrobus.network;


import com.metalsack.retrobus.network.response.ExpertListResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * The API interface for retrofit calls. This interface defines all the api calls made by the app.
 * Note: You have to pass a null value for a parameter to be skipped. Parameter values are URL
 * encoded by default. TODO: We may have to encode the parameter values before passing them to the
 * api calls here although they are URL encoded by retrofit automatically. We have to homemenu_fragment this
 * thoroughly.
 */
public interface APIService {
/*
   *//*====================================================================================================================
   * Phase-1 APIS*//*


    *//*Called in Signup Screen*//*
    @Multipart
    @POST("registration")
    Call<SignUpResponse> registration(
            @Part("full_name") RequestBody full_name,
            @Part("age") RequestBody age,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("mob_no") RequestBody mob_no,
            @Part MultipartBody.Part image,
            @Part("device_id") RequestBody device_id,
            @Part("device_type") RequestBody device_type
    );

    *//*Called in Update Profile Fragment*//*
    @Multipart
    @POST("update_profile")
    Call<SignUpResponse> update_profile(
            @Part("full_name") RequestBody full_name,
            @Part("age") RequestBody age,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("email") RequestBody email,
            @Part("mob_no") RequestBody mob_no,
            @Part MultipartBody.Part image,
            @Part("user_id") RequestBody user_id
    );

    *//*Called in Login Screen*//*
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_id") String device_id,
            @Field("device_type") String device_type);

    *//*Called in Login Screen*//*
    @FormUrlEncoded
    @POST("forgot_password")
    Call<AbstractApiResponse> forgot_password(
            @Field("email") String email);

    *//*Called in Change Password Fragment*//*
    @FormUrlEncoded
    @POST("change_password")
    Call<AbstractApiResponse> change_password(
            @Field("user_id") String user_id,
            @Field("email") String email,
            @Field("current_password") String current_password,
            @Field("new_password") String new_password);

    *//*Called in Scan by barcode faragment for getting auto suggested product name*//*
    @FormUrlEncoded
    @POST("get_product_name")
    Call<AutoSuggestProductResponse> get_product_name(
            @Field("user_id") String user_id,
            @Field("search") String search);

    *//*Called in Scan by barcode fragment, Search by category result listing fragment*//*
    @FormUrlEncoded
    @POST("list_product")
    Call<Scan_Search_Response> list_product(
            @Field("user_id") String user_id,
            @Field("id") String product_id,
            @Field("product_barcode") String product_barcode,
            @Field("product_name") String product_name,
            @Field("company_name") String company_name,
            @Field("cat_id") String cat_id,
            @Field("allergen_id") String allergen_id,
            @Field("ing_id") String ing_id,
            @Field("search_type") String search_type,
            @Field("start") String start_id);

    *//*Callded in Product Feedback Fragment for gettin comment list and also submitting user's comments*//*
    @FormUrlEncoded
    @POST("comment_product")
    Call<ProductFeedbackResponse> comment_product(
            @Field("product_id") String product_id,
            @Field("user_id") String user_id,
            @Field("comment") String comment);

    *//*Called in Product Feedback Screen *//*
    @FormUrlEncoded
    @POST("add_product_rating")
    Call<ProductRatingResponse> add_product_rating(
            @Field("user_id") String user_id,
            @Field("rating") String rating,
            @Field("product_id") String product_id);

    *//*Called in Product Detail Fragment to get all ingredients and in
     Public Forum new post fragment to get auto suggest ingredient*//*
    @FormUrlEncoded
    @POST("list_ingredient")
    Call<AutoSuggestIngredientsResponse> list_ingredient(
            @Field("user_id") String user_id,
            @Field("search") String search);

    *//*Called in Search by categoty fragment,public forum new post fragment, Error report fragment
     and Product resuest fragment to get list of category
    * *//*
    @FormUrlEncoded
    @POST("list_category")
    Call<CategoryResponse> list_category(
            @Field("user_id") String user_id);

    *//*Called in Error Report Fragment *//*
    @FormUrlEncoded
    @POST("product_report_an_error")
    Call<AbstractApiResponse> product_report_an_error(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Field("product_name") String product_name,
            @Field("product_category") String product_category,
            @Field("company_name") String company_name,
            @Field("ingredient_list") String ingredient_list,
            @Field("barcode") String barcode,
            @Field("product_price") String ing_id,
            @Field("userfile") String search_type);

    *//*NOTE:: NOT IN USE*//*
    @FormUrlEncoded
    @POST("product_request")
    Call<AbstractApiResponse> product_request(
            @Field("user_id") String user_id,
            @Field("product_name") String product_name,
            @Field("product_category") String product_category,
            @Field("product_company") String product_company,
            @Field("ingredient_list") String ingredient_list,
            @Field("barcode") String barcode,
            @Field("product_price") String product_price,
            @Field("product_desc") String product_desc,
            @Field("userfile") String search_type);

    *//*Called in Product Request Fragment*//*
    @Multipart
    @POST("product_request")
    Call<AbstractApiResponse> product_request1(
            @Part("user_id") RequestBody user_id,
            @Part("product_name") RequestBody product_name,
            @Part("product_category") RequestBody product_category,
            @Part("product_company") RequestBody company_name,
            @Part("ingredient_list") RequestBody ingredient_list,
            @Part("barcode") RequestBody barcode,
            @Part("product_price") RequestBody ing_id,
            @Part("quality") RequestBody quality,
            @Part("product_desc") RequestBody product_desc,
            @Part MultipartBody.Part image);

    *//*Called in Allergen Fragment,Search by category Fragment,Scan by barcode fragment andPublic forum new post fragment*//*
    @FormUrlEncoded
    @POST("list_allergen")
    Call<AllergenResponse> list_allergen(
            @Field("user_id") String user_id);


    *//*Called in Allergen Fragment and Search by Category Fragment*//*
    @FormUrlEncoded
    @POST("add_user_allergens")
    Call<AbstractApiResponse> add_user_allergens(
            @Field("user_id") String user_id,
            @Field("allergens_id") String allergens_id);


    //beside user id all params are optional so pass them accordingly
    *//*Called in public forum fragment*//*
    @FormUrlEncoded
    @POST("get_all_public_forum")
    Call<PublicForumResponse> get_all_public_forum(
            @Field("user_id") String user_id,
            @Field("forum_id") String forum_id,
            @Field("search_text") String search_text,
            @Field("created_date") String created_date,///(yyyy-mm-dd)
            @Field("article_type") String article_type,////1=image,2=video,0=text
            @Field("uploaded_by") String uploaded_by,  ////1=admin,0=user
            @Field("start") String start,              //start index for pagination
            @Field("limit") String limit,              //total no of records to fetch
            @Field("start") String start_id);       //pass when upper side lazy loading is used


    *//*Called in public forum fragment and public forum detail fragment*//*
    @FormUrlEncoded
    @POST("public_forum_like")
    Call<PublicForumLIkeResponse> public_forum_like(
            @Field("user_id") String user_id,
            @Field("forum_id") String forum_id);

    *//*NOTE:: NOTE IN USE*//*
    @FormUrlEncoded
    @POST("public_forum")
    Call<AbstractApiResponse> public_forum(
            @Field("user_id") String user_id,
            @Field("title") String title,
            @Field("text") String text,
            @Field("allergen_id") String allergen_id,
            @Field("media_type") String media_type,
            @Field("userfile") String userfile);

    *//* Called in PUblic Forum New Post Fragment*//*
    @Multipart
    @POST("public_forum")
    Call<AbstractApiResponse> public_forum1(
            @Part("user_id") RequestBody user_id,
            @Part("title") RequestBody title,
            @Part("text") RequestBody text,
            @Part("allergen_id") RequestBody allergen_id,
            @Part("media_type") RequestBody media_type,
            @Part MultipartBody.Part image
    );


    *//*Called in Product Detail Screen*//*
    @FormUrlEncoded
    @POST("add_serach_history")
    Call<AbstractApiResponse> add_search_history(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Field("search_by") String search_type); //search_by_name,search_by_cat,search_by_company,
                                                     //search_by_ing,search_by_allergen,
                                                     //search_by_barcode,search_by_similar_product

    *//*Called in History Fragment*//*
    @FormUrlEncoded
    @POST("get_search_history")
    Call<SearchHistroryResponse> get_search_history(
            @Field("user_id") String user_id,
            @Field("start") String start);

    *//*Called in Public Forum Comment Fragment*//*
    @FormUrlEncoded
    @POST("public_forum_comment")
    Call<PublicForumCommentResponse> public_forum_comment(
            @Field("forum_id") String forum_id,
            @Field("user_id") String user_id,
            @Field("comment") String comment);

    *//*Called in PUblic Forum Comment Fragment*//*
    @FormUrlEncoded
    @POST("public_forum_get_comment")
    Call<PublicForumCommentResponse> public_forum_get_comment(
            @Field("forum_id") String forum_id,
            @Field("user_id") String user_id
    );

    *//**
     * used to delete comment
     * @param forum_id
     * @param user_id
     * @param comment_type app of post
     * @return
     *//*
    @FormUrlEncoded
    @POST("delete_comment")
    Call<AbstractApiResponse> delete_comment(
            @Field("user_id") String forum_id,
            @Field("comment_id") String user_id,
            @Field("comment_type") String comment_type);
    *//*NOTE:: NOT IN USE*//*
    @FormUrlEncoded
    @POST("get_trans_history")
    Call<TransactionHistoryResponse> get_trans_history(
            @Field("user_id") String user_id);

    *//*Called in Scan by barcode fragment, Search by category fragment and
     product detail fragment(when user clicks on similar product )*//*
    @FormUrlEncoded
    @POST("check_package_validity")
    Call<AbstractApiResponse> check_package_validity(
            @Field("user_id") String user_id);

    *//*Called in Wallet Fragment*//*
    @FormUrlEncoded
    @POST("get_packages")
    Call<SubscriptionPackagesResponse> get_packages(
            @Field("user_id") String user_id);

    *//*NOTE:: NOT IN USE*//*
    @FormUrlEncoded
    @POST("get_credit")
    Call<CreditResponse> get_credit(
            @Field("user_id") String user_id);

    *//*Called in Wallet Fragment*//*
    @FormUrlEncoded
    @POST("subscribe_package")
    Call<Subscribe_A_PackageResponse> subscribe_package(
            @Field("user_id") String user_id,
            @Field("trans_id") String trans_id,
            @Field("package_id") String package_id,
            @Field("pay_gateway") String pay_gateway);

    *//*Called in Transaction History Fragment*//*
    @FormUrlEncoded
    @POST("package_subscription_history")
    Call<PackageSubscriptionHistoryResponse> package_subscription_history(
            @Field("user_id") String user_id);

    *//*Called in Public Forum Detail*//*
    @FormUrlEncoded
    @POST("public_forum_add_viewer")
    Call<PublicForumAddViewerResponse> public_forum_add_viewer(
            @Field("user_id") String user_id,
            @Field("forum_id") String forum_id
    );

    *//*Called in Wallet Fragment*//*
    @FormUrlEncoded
    @POST("add_credit")
    Call<AddCreditResponse> add_credit(
            @Field("user_id") String user_id,
            @Field("trans_id") String trans_id,
            @Field("credit") String credit,
            @Field("amount") int amount,
            @Field("bonus_credit") String bonus_credit,
            @Field("pay_gateway") String pay_gateway);

    *//*Called in Notification Fragment*//*
    @FormUrlEncoded
    @POST("get_notification")
    Call<NotificationResponse> get_notification(
            @Field("user_id") String user_id);

    *//*Called in Home Menu Activity*//*
    @FormUrlEncoded
    @POST("get_advertisement")
    Call<AdvertisementResponse> get_advertisement(
            @Field("user_id") String user_id,
            @Field("add_type") String add_type);// 1= interstial, 2= banner

    *//*Called in Home Menu Activity*//*
    @FormUrlEncoded
    @POST("add_click")
    Call<AbstractApiResponse> add_click(
            @Field("user_id") String user_id,
            @Field("add_id") String add_id);*/

    /*=====================================================================================================================
    Phase-2 API
    CHAT-VIDEO-AUDIO CALL FEATURE API*/

    /*Called in Chat Listing Acitivity*/
    //@FormUrlEncoded
    @GET("get_recommended_users")
    Call<ExpertListResponse> get_recommended_users(
            /*@Field("user_id") String user_id*/);


}

