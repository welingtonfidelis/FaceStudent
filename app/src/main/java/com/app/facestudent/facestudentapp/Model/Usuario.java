package com.app.facestudent.facestudentapp.Model;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private long dataCriacaoConta;
    private long dataNascimento;
    private String genero;
    private String foto;
    private String descricaoPessoal;
    private String log;
    private long ultimoAcesso;
    private String permissao;
    private boolean status;
    private double nota_absoluta;
    private int qtd_avaliacoes;

    public Usuario() {
        this.permissao = "usuario";
        this.status = false;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public long getDataCriacaoConta() {
        return dataCriacaoConta;
    }

    public void setDataCriacaoConta(long dataCriacaoConta) {
        this.dataCriacaoConta = dataCriacaoConta;
    }

    public long getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(long dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public long getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(long ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescricaoPessoal() {
        return descricaoPessoal;
    }

    public void setDescricaoPessoal(String descricaoPessoal) {
        this.descricaoPessoal = descricaoPessoal;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getNota_absoluta() {
        return nota_absoluta;
    }

    public void setNota_absoluta(double nota_absoluta) {
        this.nota_absoluta += nota_absoluta;
        this.qtd_avaliacoes++;
    }

    public int getQtd_avaliacoes() {
        return qtd_avaliacoes;
    }

    public void setQtd_avaliacoes(int qtd_avaliacoes) {
        this.qtd_avaliacoes = qtd_avaliacoes;
    }
}
