package com.app.facestudent.facestudentapp.Model;

import java.util.Date;

public class Usuario {
    private String nome;
    private String email;
    private Date dataCriacaoConta;
    private Date dataNascimento;
    private String genero;
    private String foto;
    private String descricaoPessoal;
    private String log;
    private Date ultimoAcesso;
    private String permissao;
    private boolean status;

    public Usuario() {
        this.permissao = "usuario";
        this.status = false;
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

    public Date getDataCriacaoConta() {
        return dataCriacaoConta;
    }

    public void setDataCriacaoConta(Date dataCriacaoConta) {
        this.dataCriacaoConta = dataCriacaoConta;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
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
}
