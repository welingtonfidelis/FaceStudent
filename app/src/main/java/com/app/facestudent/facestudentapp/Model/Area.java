package com.app.facestudent.facestudentapp.Model;

import com.google.firebase.database.Exclude;

public class Area {
    private int id;
    private String nome;
    private boolean ativado;

    @Exclude
    public int getId() {
        return id;
    }

    @Exclude
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome ;
    }

    @Exclude
    public boolean isAtivado() {
        return ativado;
    }

    @Exclude
    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }
}
