import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.io.IOException;

public class YouAreEll {
    private String base = "http://zipcode.rocks:8085";
    private static final MediaType json = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient okClient = new OkHttpClient();

    YouAreEll() {
    }

    public static void main(String[] args) {
        YouAreEll urlhandler = new YouAreEll();
        System.out.println(urlhandler.MakeURLCall("/ids", "GET", ""));
        //System.out.println(urlhandler.MakeURLCall("/messages", "GET", ""));
    }

    public String get_ids() {
        return MakeURLCall("/ids", "GET", "");
    }

    public String get_messages() {
        return MakeURLCall("/messages", "GET", "");
    }

    public String MakeURLCall(String mainurl, String method, String jpayload) {
        String fullUrl = base + mainurl;
        jpayload = "{\n" +
                "\"name\":\"pat\",\n" +
                "\"github\": \"patglavin\"\n" +
                "}";
        Request request = new Request.Builder()
                .url(fullUrl)
                .build();
        try {
            Response response = okClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nada";
    }
}
