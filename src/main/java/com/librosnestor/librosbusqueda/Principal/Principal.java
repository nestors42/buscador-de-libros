package com.librosnestor.librosbusqueda.Principal;

import com.librosnestor.librosbusqueda.model.Datos;
import com.librosnestor.librosbusqueda.model.DatosLibros;
import com.librosnestor.librosbusqueda.service.ConsumoAPI;
import com.librosnestor.librosbusqueda.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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
//        System.out.println("estos son los 10 libros mas descargados");
//        datos.listaDeLibros().stream()
//                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
//                .limit(10)
//                .map(l -> l.titulo().toUpperCase())
//                .forEach(System.out::println);

        // busqueda de libros por nombre
        System.out.println("Que libro desea buscar");
        var tituloBuscadoUsuario = teclado.nextLine();
        json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloBuscadoUsuario.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.listaDeLibros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloBuscadoUsuario.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("libro no encontrado");
        }

        // trabajando con estadisticas
        DoubleSummaryStatistics estadistica = datos.listaDeLibros().stream()
                .filter(l -> l.numeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("la media de evaluaciones es: " + estadistica.getAverage());
        System.out.println("puntuacion maxima es: " + estadistica.getMax());
        System.out.println("puntuacion minima es: " + estadistica.getMin());
        System.out.println("cantidad de registros evaluados para el calculo: " + estadistica.getCount());

    }
}
