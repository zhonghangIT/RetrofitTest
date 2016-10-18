package com.education.restrofittest2.http;

import android.os.Environment;

import com.education.restrofittest2.bean.QuanminBean;
import com.education.restrofittest2.config.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static volatile HttpHelper singleton;

    private HttpHelper() {
        initRetrofit();
        initApi();
    }

    public static HttpHelper getInstance() {
        if (singleton == null) {
            synchronized (HttpHelper.class) {
                if (singleton == null) {
                    singleton = new HttpHelper();
                }
            }
        }
        return singleton;
    }

    private Retrofit mRetrofit;
    private Gson mGson;

    private void initGson() {
        mGson = new GsonBuilder()
                .serializeNulls()
                .create();
    }

    private void initRetrofit() {
        initGson();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.BASE_URL)
                .addConverterFactory(new FileConvertFactory())
                //自定义的convert需要写到GsonConvert之前添加
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    private MyRetrofitApi myRetrofitApi;

    private void initApi() {
        myRetrofitApi = mRetrofit.create(MyRetrofitApi.class);
    }

    /**
     * 访问新闻的接口
     *
     * @param params   接受的参数是 id新闻的分类 plat设备名称 version当前版本号 page分页
     * @param callback 回调方法
     **/
    public void getNews(Map<String, String> params, Callback<QuanminBean> callback) {
        Call<QuanminBean> call = myRetrofitApi.getNews(params);
        call.enqueue(callback);
    }

    public void uploadFile(String url,String path,Callback<ResponseBody> callback){
        File file=new File(path);
        Call<ResponseBody> call=myRetrofitApi.uploadFile(url,file);
        call.enqueue(callback);
    }
}