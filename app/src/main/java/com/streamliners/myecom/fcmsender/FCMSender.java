package com.streamliners.myecom.fcmsender;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FCMSender {

    //Constants
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send"
            , KEY_STRING = "key=AAAAnRHs6nc:APA91bG5t6BFC0ezdokx1s9w8GjFLUkYCBDan5ETp8_35IicjvapeXXPtmWUcGxZFCUnNBiChhzkYDRq-jplSJ7wMX6RB3uV6_ftyBeAsdAcD77HHZNPf0nptKiATMnjlosG5zPq7bn6";

    //Sends notification using Legacy HTTP Protocol
    public void send(String message, Callback callback) {
        RequestBody reqBody = RequestBody.create(message
                , MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(FCM_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", KEY_STRING)
                .post(reqBody)
                .build();

        Call call = new OkHttpClient().newCall(request);
        call.enqueue(callback);
    }
}
