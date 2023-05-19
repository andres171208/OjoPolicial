package com.ojopolicial;

//Encapsulacion de los datos

public class Lista_entrada {
    private int Imagen;
    private String Titulo;
    private String Descripcion;

    public Lista_entrada (int Imagen, String Titulo, String Descripcion) {
        this.Imagen = Imagen;
        this.Titulo = Titulo;
        this.Descripcion = Descripcion;
    }

    public String get_Titulo() {
        return Titulo;
    }

    public String get_Descripcion() {
        return Descripcion;
    }

    public int get_Imagen() {
        return Imagen;
    }
}
