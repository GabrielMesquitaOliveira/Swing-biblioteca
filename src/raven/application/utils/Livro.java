package raven.application.utils;

import java.sql.Date;

public class Livro {
    private int id;
    private String titulo;
    private int autorId;
    private int categoriaId;
    private Date anoPublicacao;
    private Status status;

    public enum Status {
        disponivel,
        emprestado
    }

    public Livro(int id, String titulo, int autorId, int categoriaId, Date anoPublicacao, Status status) {
        this.id = id;
        this.titulo = titulo;
        this.autorId = autorId;
        this.categoriaId = categoriaId;
        this.anoPublicacao = anoPublicacao;
        this.status = status;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAutor() {
        return autorId;
    }

    public void setAutor(int autor) {
        this.autorId = autor;
    }

    public int getCategoria() {
        return categoriaId;
    }

    public void setCategoria(int categoria) {
        this.categoriaId = categoria;
    }

    public Date getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Date anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
