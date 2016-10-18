package com.education.restrofittest2.http;

import com.education.restrofittest2.bean.QuanminBean;
import com.education.restrofittest2.config.UrlConfig;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by zhonghang on 2016/10/17.
 */

public interface MyRetrofitApi {
    /**
     *
     * 访问新闻的接口
     *
     * @param params 接受的参数是 id新闻的分类 plat设备名称 version当前版本号 page分页
     * @return 返回网络连接得到的结果, 结果的类型是responseBody
     */
    @GET(UrlConfig.Path.GET_NEWS)
    Call<QuanminBean> getNews(@QueryMap() Map<String, String> params);

    @POST
    Call<ResponseBody> uploadFile(@Url() String url, @Body File file);


}
