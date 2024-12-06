package com.example.appcuerdate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AnadirTest extends AppCompatActivity {

    private RecyclerView recyclerView;
    private android.widget.Button botonPrecedenteTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_test);

        recyclerView = findViewById(R.id.recyclerViewAñadirTest);
/*        firestore = FirebaseFirestore.getInstance();*/

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AnadirTest.this));

        botonPrecedenteTest = findViewById(R.id.botonPrecedenteTest);
        botonPrecedenteTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precedentecreartest.newInstance().show(getSupportFragmentManager(),precedentecreartest.TAG);
            }
        });
    }
}