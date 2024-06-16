package com.librosnestor.librosbusqueda.Principal;

import com.librosnestor.librosbusqueda.model.Datos;
import com.librosnestor.librosbusqueda.model.DatosLibros;
import com.librosnestor.librosbusqueda.service.ConsumoAPI;
import com.librosnestor.librosbusqueda.service.ConvierteDatos;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);


    public void muestraMenu(){
        //System.out.println("escriba el titulo del libro");
        //var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        // top 10 de libros mas descargados
        System.out.println("estos son los 10 libros mas descargados");
        datos.listaDeLibros().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

    }
}
