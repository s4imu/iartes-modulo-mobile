package com.example.iartes.model;

public class Musica {
    private int id;
    private String nome;
    private String artista;
    private String album;

    private byte[] imagemBlob;

    public Musica(int id, String nome, String artista, String album, byte[] imagemBlob) {
        this.id = id;
        this.nome = nome;
        this.artista = artista;
        this.album = album;
        this.imagemBlob = imagemBlob;
    }

    public Musica(String nome, String artista, String album, byte[] imagemBlob) {
        this.nome = nome;
        this.artista = artista;
        this.album = album;
        this.imagemBlob = imagemBlob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public byte[] getImagemBlob() {
        return imagemBlob;
    }

    public void setImagemBlob(byte[] imagemBlob) {
        this.imagemBlob = imagemBlob;
    }
    }