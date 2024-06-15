package com.librosnestor.librosbusqueda.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
