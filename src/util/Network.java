package util;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Network {
    public Response getRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) return response;

            return response;
        }
    }

    public String getRequestWithCookie(String url, String sessao) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", sessao)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) return response.body().string();

            return response.body().string();
        }
    }

    public String postRequest(String url, String postbody, String sessao) throws IOException {
        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("application/x-www-form-urlencoded");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postbody))
                .header("Host", "sipac.ufopa.edu.br")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Length", "61")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Origin", "https://sipac.ufopa.edu.br")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("Sec-Fetch-Mode", "navigate")
                .addHeader("Sec-Fetch-User", "?1")
                .addHeader("Sec-Fetch-Dest", "document")
                .addHeader("Referer", "https://sipac.ufopa.edu.br/sipac/")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept-Language", "pt-BR,pt;q=0.9")
                .addHeader("Cookie", sessao)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) return response.body().string();

            return response.body().string();
        }

    }
}