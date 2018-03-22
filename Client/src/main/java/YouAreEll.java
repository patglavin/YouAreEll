import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String get_ids(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return MakeURLCall("/ids", "GET", objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get_messages(User user) {
        return MakeURLCall("/messages", "GET", "");
    }

    public String MakeURLCall(String mainurl, String method, String jpayload) {
        System.out.println(jpayload);
        String fullUrl = base + mainurl;
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

//jpayload = "{\n" +
//        "\"name\":\"pat\",\n" +
//        "\"github\": \"patglavin\"\n" +
//        "}";
