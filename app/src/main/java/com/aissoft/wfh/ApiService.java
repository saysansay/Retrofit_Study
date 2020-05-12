package com.aissoft.wfh;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login")
    @FormUrlEncoded
   Call<JsonObject> postLogin(@Field("email") String email,
                              @Field("password") String password);
    @POST("register")
    @FormUrlEncoded
    Call<JsonObject> postRegister(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("password_confirmation") String password_confirmation);
    @GET("users")
    Call<JsonObject> getAllUser(@Header("Authorization")String token);

   // @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("users/{id}")
     Call<JsonElement> getSingleUser(@Path("id") String id, @Header("Authorization") String auth);
    // Cara Manggil Pakai token  client.getSingleUser(0034", "Bearer "+token);
    @GET("profile")
    Call<JsonObject> getProfile(@Header("Authorization")String token);

    @Multipart
    @POST("uploadprofile")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part photo,
                                   @Field ("email") String email);

}
