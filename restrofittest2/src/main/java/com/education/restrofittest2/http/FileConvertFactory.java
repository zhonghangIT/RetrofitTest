package com.education.restrofittest2.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by zhonghang on 2016/10/17.
 */

public class FileConvertFactory extends Converter.Factory {
    public FileConvertFactory() {
        super();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FileConvert();
    }
    
    class FileConvert implements Converter<File, RequestBody> {

        @Override
        public RequestBody convert(File value) throws IOException {
            RequestBody body = new MultipartBody.Builder()
                    .addFormDataPart(value.getName(), value.getName(),
                            MultipartBody.create(MultipartBody.FORM, value)).build();
            return body;
        }
    }

}
