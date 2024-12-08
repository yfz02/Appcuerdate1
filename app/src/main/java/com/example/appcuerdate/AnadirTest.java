package com.example.appcuerdate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuerdate.Adapter.adaptador;
import com.example.appcuerdate.Model.coInfo;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AnadirTest extends AppCompatActivity {

    private RecyclerView recyclerView;
    private android.widget.Button botonPrecedenteTest;
    private adaptador adapter;
    private List<coInfo> testItems;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_test);

        recyclerView = findViewById(R.id.recyclerViewAñadirTest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        firestore = FirebaseFirestore.getInstance();
        testItems = new ArrayList<>();
        adapter = new adaptador(testItems, firestore, this);
        recyclerView.setAdapter(adapter);

        botonPrecedenteTest = findViewById(R.id.botonPrecedenteTest);
        botonPrecedenteTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precedentecreartest.newInstance().show(getSupportFragmentManager(), precedentecreartest.TAG);
            }
        });

        firestore.collection("padreTest").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot value, @NonNull com.google.firebase.firestore.FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(AnadirTest.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error fetching documents: ", error);
                    return;
                }


            }
        });

    }
}
