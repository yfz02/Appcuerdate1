package com.example.appcuerdate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    Button floatingActionButton;
    FirebaseUser firebaseUser;
    android.widget.Button btnCrear,BtnRepasar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth= FirebaseAuth.getInstance();
        floatingActionButton =findViewById(R.id.floatingActionButtonLogOut);
        firebaseUser= firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            Intent intent= new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();

        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

         btnCrear = findViewById(R.id.BtnCrear);
        BtnRepasar = findViewById(R.id.BtnRepasar);
       btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearTest.class);
                startActivity(intent);
            }
        });
        subirTestAFirestore();
        BtnRepasar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Repasar.class);
                startActivity(intent);
            }
        });






    }
    public void subirTestAFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String testId = "test1";

        HashMap<String, Object> test = new HashMap<>();
        test.put("title", "Test de Ciencias");


        ArrayList<HashMap<String, Object>> preguntas = new ArrayList<>();

        HashMap<String, Object> pregunta1 = new HashMap<>();
        pregunta1.put("question", "1+1=X");
        pregunta1.put("options", new ArrayList<String>() {{
            add("2");
            add("1");
            add("3");
            add("4");
        }});
        pregunta1.put("answer", "2");

        HashMap<String, Object> pregunta2 = new HashMap<>();
        pregunta2.put("question", "9/3=X");
        pregunta2.put("options", new ArrayList<String>() {{
            add("6");
            add("3");
            add("9");
            add("12");
        }});
        pregunta2.put("answer", "3");

        preguntas.add(pregunta1);
        preguntas.add(pregunta2);



        test.put("questions", preguntas);

        db.collection("tests").document(testId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Log.d("Firestore", "El test con ID " + testId + " ya existe. No se subirá nuevamente.");
                    } else {
                        db.collection("tests").document(testId).set(test)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "Test subido correctamente.");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Error al subir el test", e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al verificar la existencia del test", e);
                });
    }
}