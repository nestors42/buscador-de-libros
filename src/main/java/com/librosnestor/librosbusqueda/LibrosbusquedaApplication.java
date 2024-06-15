package com.librosnestor.librosbusqueda;

import com.librosnestor.librosbusqueda.model.DatosLibros;
import com.librosnestor.librosbusqueda.service.ConsumoAPI;
import com.librosnestor.librosbusqueda.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrosbusquedaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LibrosbusquedaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoAPI();
		var json = consumoApi.obtenerDatos("https://gutendex.com/books/");
		//System.out.println(json);
		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosLibros.class);
		System.out.println(datos);
	}
}
