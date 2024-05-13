package com.ausweglosigkeit.oidc;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AuthorizationUsingYandex {
    private static final ResourceBundle BOT_DATA = ResourceBundle.getBundle("com.ausweglosigkeit.oidc.RegistrationData");
    private static final String clientID = BOT_DATA.getString("oidc.yandex.client.id");
    private static final String clientSecret = BOT_DATA.getString("oidc.yandex.client.secret");
    private static final String URI = BOT_DATA.getString("oidc.yandex.uri");
    private static final String UriLogin = BOT_DATA.getString("oidc.yandex.uri.login");

    private static JSONObject getOAuthToken(String code) throws UnsupportedEncodingException {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(URI);
        final List<NameValuePair> pairList = new ArrayList<>();
        pairList.add(new BasicNameValuePair("Content-type", "application/x-www-form-urlencoded"));
        pairList.add(new BasicNameValuePair("grant_type", "authorization_code"));
        pairList.add(new BasicNameValuePair("code", code));
        pairList.add(new BasicNameValuePair("client_id", clientID));
        pairList.add(new BasicNameValuePair("client_secret", clientSecret));
        httpPost.setEntity(new UrlEncodedFormEntity(pairList));
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            final HttpEntity entity1 = response.getEntity();
            jsonObject = (JSONObject) parser.parse(EntityUtils.toString(entity1));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }


    private static JSONObject getUserData(String accessToken) {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        final HttpUriRequest httpGet = new HttpGet(UriLogin + clientSecret);
        httpGet.setHeader("Authorization", "OAuth " + accessToken);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            final HttpEntity entity2 = response.getEntity();
            jsonObject = (JSONObject) parser.parse(EntityUtils.toString(entity2));
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private JSONObject tokens;
    private JSONObject dataOfUser;
    private byte codeError = 0;
    public AuthorizationUsingYandex(String code) {
        try {
            tokens = AuthorizationUsingYandex.getOAuthToken(code);
            dataOfUser = AuthorizationUsingYandex.getUserData(tokens.get("access_token").toString());
        } catch (UnsupportedEncodingException | NullPointerException e) {
            codeError = 1;
            e.printStackTrace();
        }
    }
    public byte getCodeError() {
        return codeError;
    }
    public JSONObject getDataOfUser() {
        return dataOfUser;
    }
}
