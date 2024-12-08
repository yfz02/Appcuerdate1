package com.example.appcuerdate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuerdate.Model.coInfo;
import com.example.appcuerdate.R;
import com.example.appcuerdate.preptest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class adaptador extends RecyclerView.Adapter<adaptador.TestViewHolder> {
    private List<coInfo> testItems;
    private OnItemClickListener mListener;
    private FirebaseFirestore firestore;
    private Context context;

    public adaptador(List<coInfo> testItems, FirebaseFirestore firestore, Context context) {
        this.testItems = testItems;
        this.firestore = firestore;
        this.context = context;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.decoracionrecycleviewprincipal, parent, false);
        return new TestViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        if (position < 0 || position >= testItems.size()) return;

        coInfo currentItem = testItems.get(position);
        holder.textViewTitulo.setText(currentItem.getTitulo());
        holder.textViewTitulo.setOnClickListener(v -> {
            Intent intent = new Intent(context, preptest.class);
            intent.putExtra("titulo", currentItem.getTitulo());
            context.startActivity(intent);
        });
        holder.imageViewEliminar.setOnClickListener(v -> {
            deleteItemFromFirebase(currentItem, position);
        });
    }

    @Override
    public int getItemCount() {
        return (testItems != null) ? testItems.size() : 0;
    }

    private void deleteItemFromFirebase(coInfo item, int position) {
        if (item == null || item.getId() == null || item.getId().isEmpty()) {
            Toast.makeText(context, "Error: El ID del documento es inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("padreTest")
                .document(item.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    testItems.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Dato eliminado correctamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error al eliminar el dato: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void clearContext() {
        context = null;
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        ImageView imageViewEliminar;

        public TestViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            imageViewEliminar = itemView.findViewById(R.id.imageViewEliminar);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
