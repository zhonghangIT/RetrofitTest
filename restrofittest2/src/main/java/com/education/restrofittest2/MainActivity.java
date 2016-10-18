package com.education.restrofittest2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.education.restrofittest2.bean.QuanminBean;
import com.education.restrofittest2.config.UrlConfig;
import com.education.restrofittest2.http.HttpHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button mBtnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/tfboy.mp3";
                HttpHelper.getInstance().uploadFile("http://10.2.153.217:8080/Test/UploadFile", path, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("MainActivity", "上传成功");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void getNews() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(UrlConfig.Param.ID, "12");
        params.put(UrlConfig.Param.VERSION, UrlConfig.DefaultValue.VERSION);
        params.put(UrlConfig.Param.PLAT, UrlConfig.DefaultValue.PLAT);
        params.put(UrlConfig.Param.PAGE, UrlConfig.DefaultValue.PAGE);
        HttpHelper.getInstance().getNews(params, new Callback<QuanminBean>() {
            @Override
            public void onResponse(Call<QuanminBean> call, Response<QuanminBean> response) {
                QuanminBean bean = response.body();
                Log.d("MainActivity", bean.getThisPageNum());
            }

            @Override
            public void onFailure(Call<QuanminBean> call, Throwable t) {

            }
        });
    }
}
