package com.jzli.client;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/3/19
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
@Component
public class HttpClient {
    private OkHttpClient client;

    @PostConstruct
    public void init() {
        client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder().url(url)
                .get().build();

        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
}
