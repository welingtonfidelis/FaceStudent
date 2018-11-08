package com.app.facestudent.facestudentapp.Model;

import com.google.firebase.database.Exclude;

public class Habilidade {
    private String id;
    private String idUsuario;
    private String nome;

    public Habilidade() {
    }

    public Habilidade(String nome, String id, String idUsuario) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nome = nome;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
