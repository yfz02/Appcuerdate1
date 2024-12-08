package com.example.appcuerdate.Model;

import com.google.firebase.firestore.Exclude;

public class coInfo {

    private String titulo;
    private String subtitulo;
    @Exclude
    private String id;  // Mark as excluded for Firestore

    public coInfo() {}

    public coInfo(String titulo, String subtitulo, String id) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.id = id;
    }

    // Getters and Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
