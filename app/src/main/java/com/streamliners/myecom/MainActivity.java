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
import com.streamliners.myecom.models.Sample;
import com.streamliners.myecom.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        setup();
        //write();
        read();
        //set();
    }

    private void set() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.goOnline();
        DatabaseReference ref = database.getReference("sample2/");
        ref.keepSynced(true);
    }

    private void read() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.goOffline();
        DatabaseReference ref = database.getReference("sample2/");
        ref.keepSynced(true);
        ref.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                //Sample sample = dataSnapshot.getValue(Sample.class);
                showDialog("Read", dataSnapshot.toString());
                set();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showDialog("Error"
                        ,e.toString());
            }
        });
    }

    private void write() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("sample1/");

        Sample sample = new Sample();
        sample.colors.put("red", 0);
        sample.digits.put("D1", 0);
        ref.setValue(sample)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showDialog("Error"
                                ,e.toString());
                    }
                });
    }

    public void rtdbInc(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("bids/21-06-11/colors");

        Map<String, Object> map = new HashMap<>();
        map.put("red", ServerValue.increment(1));

        ref.updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showDialog("Error"
                                ,e.toString());
                    }
                });
    }

    private void createInRTDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("bids/colors");

        Map<String, Object> map = new HashMap<>();
        map.put("red", 1);

        ref.setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showDialog("Error"
                                ,e.toString());
                    }
                });
    }

    public void firestoreInc(View view) {
        db.document("bids/21.06.11")
                .update("colors.red", FieldValue.increment(1))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "creating document...", Toast.LENGTH_SHORT).show();
                createFSDoc();
            }
        });
    }

    private void createFSDoc() {
        Map<String, Object> map = new HashMap<>();

        Map<String, Integer> nestedMap = new HashMap<>();
        nestedMap.put("red", 1);

        map.put("colors", nestedMap);

        db.document("bids/21.06.11").set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "done!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showDialog("Error"
                                ,e.toString());
                    }
                });
    }

    private void readSingleDoc() {
        db.collection("users").document("ram")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            user = documentSnapshot.toObject(User.class);
                            showDialog("Read successful"
                                    , user.toString());
                        }
                    }
                });
    }

    private void listen() {
        listener = db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null || snapshots == null) {
                            Log.w("MeraTag", "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d("MeraTag", "New user: " + dc.getDocument().toObject(User.class));
                                    break;
                                case MODIFIED:
                                    Log.d("MeraTag", "Modified user: " + dc.getDocument().toObject(User.class));
                                    break;
                                case REMOVED:
                                    Log.d("MeraTag", "Removed user: " + dc.getDocument().toObject(User.class));
                                    break;
                            }
                        }

                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener != null)
            listener.remove();
    }

    private void filterDocs() {
        db.collection("users")
                .whereGreaterThanOrEqualTo("name", "R")
                .whereLessThan("name", "T")
                .whereEqualTo("age", 20)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //List to save all docs
                        List<User> users = new ArrayList<>();

                        //Iterate over docs
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            if(documentSnapshot.exists())
                                users.add(documentSnapshot.toObject(User.class));
                        }

                        //Show output
                        if(users.size() > 0)
                            showDialog("Read successful"
                                    , users.toString());
                        else
                            showDialog("Error"
                                    , "No docs found!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showDialog("Error"
                        ,e.toString());
            }
        });
    }

    private void readAllDocs() {
        db.collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //List to save all docs
                        List<User> users = new ArrayList<>();

                        //Iterate over docs
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            if(documentSnapshot.exists())
                                users.add(documentSnapshot.toObject(User.class));
                        }

                        //Show output
                        if(users.size() > 0)
                            showDialog("Read successful"
                                    , users.toString());
                        else
                            showDialog("Error"
                                    , "No docs found!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showDialog("Error"
                                ,e.toString());
                    }
                });
    }

    private void showDialog(String title, String msg) {
        new MaterialAlertDialogBuilder(MainActivity.this)
                .setTitle(title)
                .setMessage(msg)
                .show();
    }

    private void setup() {
        db = FirebaseFirestore.getInstance();
    }

    private void addDataUsingSet() {
        //Data to be set
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Ram");
        map.put("age", 21);

        //Get docReference & then set()
        db.collection("users").document("ram")
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("MeraTag", "Data added successfully!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MeraTag", "onFailure error : " + e);
                    }
                });
    }

    private void addDataUsingAdd() {
        //Data to be set
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Ram");
        map.put("age", 21);
        map.put("dateCreated", Timestamp.now());

        //Get collReference & then add()
        db.collection("users").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("MeraTag", "document added with id : " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MeraTag", "onFailure error : " + e);
                    }
                });
    }

    private void createEmptyDoc() {
        //Get collReference & then add()
        db.collection("users").add(new HashMap<>())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("MeraTag", "document added with id : " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MeraTag", "onFailure error : " + e);
                    }
                });
    }
}