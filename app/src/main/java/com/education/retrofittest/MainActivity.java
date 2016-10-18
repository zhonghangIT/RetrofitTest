package com.education.retrofittest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.ForwardingSink;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Button mBtnGet;
    private MyRetrofitApi api;

    //http://square.github.io/retrofit/2.x/retrofit/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.2.153.217:8080/")//必须以/结尾
                .build();
        api = retrofit.create(MyRetrofitApi.class);
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
    }

    private void downloadFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> call = api.downloadFile("http://ww1.sinaimg.cn/large/006y8lVajw1f8p8qab7dkj31kw0zjah5.jpg");
                try {
                    retrofit2.Response<ResponseBody> response = call.execute();
                    InputStream inputStream = response.body().byteStream();
                    try {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/bbb.png");
                        file.createNewFile();
                        FileOutputStream outputStream = new FileOutputStream(file);
                        byte[] array = new byte[1024];
                        int index = inputStream.read(array);
                        while (index != -1) {
                            outputStream.write(array, 0, index);
                            index = inputStream.read(array);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                        Log.d("MainActivity", "下载完成");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.d("MainActivity", "下载失败");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("MainActivity", "下载失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("MainActivity", "下载失败");
                }

            }
        }).start();
    }

    private void uploadFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/aa.png");
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("username", "测试")
                .addFormDataPart("app", "app.png",
                        MultipartBody.create(MultipartBody.FORM, file))
                .build();
        Call<ResponseBody> call = api.uploadFile(body);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("TAG", "上传成功");
//                        Log.d("MainActivity", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentTypeFor = filenameMap.getContentTypeFor(filename);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void useHeader() {
        Call<ResponseBody> call = api.getMyHead("asfdfgdfgjhrtyrtyurtyurtyue56u");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d("MainActivity", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void useField() {
        Call<ResponseBody> call = api.getMyFiled("wangwu", "一年一班");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d("MainActivity", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void useQueryMap() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "王二麻子");
        params.put("clazz", "二班");
        Call<ResponseBody> call = api.getMyTest(params);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d("MainActivity", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void useQuery() {
        Call<ResponseBody> call = api.getMyTest("wangwu", "一年一班");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d("MainActivity", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void useUrl() {
        Call<ResponseBody> call = api.getBaidu("https://www.baidu.com/");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                ResponseBody body = response.body();
                try {
                    String shtml = body.string();
                    Log.d("MainActivity", shtml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getGithub() {
        Call<ResponseBody> call = api.getHttp();
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                ResponseBody body = response.body();
                try {
                    String shtml = body.string();
                    Log.d("MainActivity", shtml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


//    OkHttpClient client=new OkHttpClient.Builder().build();
//    Request request=new Request.Builder().url("http://www.baidu.com").build();
//    Call call=client.newCall(request);
//    call.enqueue(new Callback() {
//        @Override
//        public void onFailure(Call call, IOException e) {
//
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//
//        }
//    });

}
