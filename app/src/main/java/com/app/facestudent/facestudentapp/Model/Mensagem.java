package com.app.facestudent.facestudentapp.Model;

import com.google.firebase.database.Exclude;

public class Mensagem {
    private String id;
    private String idRemetente;
    private String idDestinatario;
    private String texto;
    private Long dataEnvio;

    public Mensagem(){

    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(String idRemetente) {
        this.idRemetente = idRemetente;
    }

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Long dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
