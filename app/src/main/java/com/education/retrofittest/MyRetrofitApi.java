package com.education.retrofittest;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zhonghang on 2016/10/17.
 */

public interface MyRetrofitApi {
    //此接口包含所有进行网络连接的方法,方法包含三个主要内容
    //GET是注解,表示下面的网络连接使用的方法是GET
    @GET("square/retrofit")
    Call<ResponseBody> getHttp();

    @GET("square/{xxxx}")
//leakcanary
    Call<ResponseBody> getLeak(@Path("xxxx") String path);

    @GET
    Call<ResponseBody> getBaidu(@Url() String url);

    //    http://localhost:8080/Test/MyTest?name=zhangsan
    //传递参数
    @GET("Test/MyTest")
    Call<ResponseBody> getMyTest(@Query("name") String param, @Query("clazz") String clazz);

    @GET("Test/MyTest")
    Call<ResponseBody> getMyTest(@QueryMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("Test/MyTest")
    Call<ResponseBody> getMyFiled(@Field("name") String param, @Field("clazz") String clazz);

    @GET("Test/MyTest")
    Call<ResponseBody> getMyHead(@Header("token") String token);

    //上传文件需要注意的地方 1必须使用post方法 2必须使用Mulitipart注解 3上传文件的参数必须使用@part
    @Multipart
    @POST("Test/UploadFile")
    Call<ResponseBody> upload(@Part("ceshi") RequestBody body);


    @POST("Test/UploadFile")
    Call<ResponseBody> uploadFile(@Body RequestBody body);
//    ![](http://ww1.sinaimg.cn/large/006y8lVajw1f8p8qab7dkj31kw0zjah5.jpg)
    @Streaming
    @POST
    Call<ResponseBody> downloadFile(@Url()String url);
}

