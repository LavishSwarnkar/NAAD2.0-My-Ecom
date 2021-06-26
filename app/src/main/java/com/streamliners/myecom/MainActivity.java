package com.streamliners.myecom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.streamliners.myecom.databinding.ActivityMainBinding;
import com.streamliners.myecom.fcmsender.FCMSender;
import com.streamliners.myecom.fcmsender.MessageBuilder;
import com.streamliners.myecom.models.Sample;
import com.streamliners.myecom.models.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private FirebaseFirestore db;
    private ListenerRegistration listener;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        sendNotification();
    }

    private void sendNotification() {
        String message = MessageBuilder
                .buildNewOrderMessage("Ramesh", 10, 1000);

        new FCMSender().send(message, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> showDialog("Failure!", "Error : " + e.toString()));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                runOnUiThread(() -> showDialog("Success!", "Notification sent."));
            }
        });
    }

    private void showDialog(String title, String msg) {
        new MaterialAlertDialogBuilder(MainActivity.this)
                .setTitle(title)
                .setMessage(msg)
                .show();
    }
}