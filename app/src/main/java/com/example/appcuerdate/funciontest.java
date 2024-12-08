package com.example.appcuerdate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class funciontest extends AppCompatActivity {

    private int score = 0;
    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";
    private ArrayList<Map<String, Object>> questions;

    // Vistas de la UI
    private TextView questionTextView, totalQuestionsTextView;
    private android.widget.Button ansA, ansB, ansC, ansD;
    private ImageView submitBtn,imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funciontest);


        questionTextView = findViewById(R.id.contador);
        totalQuestionsTextView = findViewById(R.id.tituloPrincipalAñadirTest);
        ansA = findViewById(R.id.botontest1);
        ansB = findViewById(R.id.botontest2);
        ansC = findViewById(R.id.botontest3);
        ansD = findViewById(R.id.botontest4);
        submitBtn = findViewById(R.id.imageView2);
        imageView = findViewById(R.id.imageView);

        ansA.setOnClickListener(this::onAnswerClicked);
        ansB.setOnClickListener(this::onAnswerClicked);
        ansC.setOnClickListener(this::onAnswerClicked);
        ansD.setOnClickListener(this::onAnswerClicked);
        submitBtn.setOnClickListener(this::onSubmitClicked);
        imageView.setOnClickListener(v -> finish());
        obtenerTestDeFirestore();
    }

    private void obtenerTestDeFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("tests").document("test1").get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String title = documentSnapshot.getString("title");
                        questions = (ArrayList<Map<String, Object>>) documentSnapshot.get("questions");

                        mostrarTestEnUI(title, questions);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al obtener el test", e);
                });
    }

    private void mostrarTestEnUI(String title, ArrayList<Map<String, Object>> questions) {
        totalQuestionsTextView.setText("Test: " + title);

        if (questions != null && !questions.isEmpty()) {
            loadNewQuestion();
        } else {
            Toast.makeText(this, "No hay preguntas disponibles.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == questions.size()) {
            finishQuiz();
            return;
        }

        Map<String, Object> currentQuestion = questions.get(currentQuestionIndex);
        String questionText = (String) currentQuestion.get("question");
        ArrayList<String> options = (ArrayList<String>) currentQuestion.get("options");


        questionTextView.setText(questionText);

        ansA.setText(options.get(0));
        ansB.setText(options.get(1));
        ansC.setText(options.get(2));
        ansD.setText(options.get(3));

        resetButtonColors();
    }


    private void onAnswerClicked(View view) {

        resetButtonColors();

        Button clickedButton = (Button) view;

        clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));

        selectedAnswer = clickedButton.getText().toString();
    }

    private void resetButtonColors() {
        ansA.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        ansB.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        ansC.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        ansD.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
    }


    private void onSubmitClicked(View view) {
        if (selectedAnswer.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona una respuesta.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = (String) currentQuestion.get("answer");


        if (selectedAnswer.equals(correctAnswer)) {
            score++;
        }


        currentQuestionIndex++;


        loadNewQuestion();
    }

    private void finishQuiz() {


        new android.app.AlertDialog.Builder(this)

                .setMessage("Tu puntaje es: " + score + " de " + questions.size())
                .setPositiveButton("Reiniciar", (dialog, which) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}
