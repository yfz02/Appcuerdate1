package com.example.appcuerdate;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class precedentecreartest extends BottomSheetDialogFragment {

    public static final String TAG = "precedentecreartest";

    private EditText editTextTitulo;
    private EditText editTextSubTitulo;
    private android.widget.Button botonGuardar;
    private FirebaseFirestore firestore;
    private Context context;
    public String id = "";
        public static precedentecreartest newInstance(){
            return new precedentecreartest();
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_precedentecreartest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTitulo = view.findViewById(R.id.editTextTitulo);
        editTextSubTitulo = view.findViewById(R.id.editTextSubTitulo);
        botonGuardar = view.findViewById(R.id.botonGuardar);
        firestore= FirebaseFirestore.getInstance();
        final Bundle bundle = getArguments();
        if (bundle != null){
            String padreTest = bundle.getString("padreTest");
            id = bundle.getString("id");
            if (padreTest != null && !padreTest.isEmpty()){
                botonGuardar.setEnabled(false);
                botonGuardar.setBackgroundColor(Color.GRAY);
            }
        }
        editTextTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    botonGuardar.setEnabled(false);
                    botonGuardar.setBackgroundColor(Color.GRAY);
                }else{
                    botonGuardar.setEnabled(true);
                    botonGuardar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {




            }
        });
        editTextSubTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    botonGuardar.setEnabled(false);
                    botonGuardar.setBackgroundColor(Color.GRAY);
                }else{
                    botonGuardar.setEnabled(true);
                    botonGuardar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info1 = editTextTitulo.getText().toString();
                String info2 = editTextSubTitulo.getText().toString();
                    if (info1.isEmpty()||info2.isEmpty()) {
                        Toast.makeText(context, "Añadir texto", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Map<String, Object> taskMap = new HashMap<>();
                        taskMap.put("titulo", info1);
                        taskMap.put("subtitulo", info2);

                        firestore.collection("padreTest").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Guardado", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                dismiss();
            }
        });

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof cerrar) {
            ((cerrar) activity).onDialogClose(dialog);
        }
    }

}
