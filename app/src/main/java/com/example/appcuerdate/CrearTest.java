package com.example.appcuerdate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class CrearTest extends AppCompatActivity {
private android.widget.Button btnAnadir,botonCopiarTest, botonPegarTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_test);

         btnAnadir = findViewById(R.id.botonAnadirTest);
        botonCopiarTest= findViewById(R.id.botonCopiarTest);
        botonPegarTest= findViewById(R.id.botonPegarTest);

        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearTest.this, AnadirTest.class);
                startActivity(intent);
            }
        });
        botonCopiarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearTest.this, Copiar.class);
                startActivity(intent);
            }
        });
        botonPegarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearTest.this, Pegar.class);
                startActivity(intent);
            }
        });

    }

}