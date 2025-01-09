package com.alura.literalura.repository;

public interface IConvierteDatos {

    <T> T convertirDatos(String json, Class<T> clase);
}
