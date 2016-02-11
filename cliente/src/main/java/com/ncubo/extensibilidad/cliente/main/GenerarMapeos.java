package com.ncubo.extensibilidad.cliente.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class GenerarMapeos {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException
	{
		generar();
	}

	public static void generar() throws IOException, SQLException, ClassNotFoundException 
	{
		final Logger logger = Logger.getLogger(GenerarMapeos.class);
		MapeoDao csvMapeo = new MapeoDao();
		MapeoCSV crearCsv = new MapeoCSV(new Configuracion().landingzoneVolcado());
		crearCsv.crear(csvMapeo.obtener());
		File archivo = new File("src/main/resources/volcadoDB.csv");
		if(archivo.exists())
		{
			System.out.println("Archivo creado en: " + new Configuracion().landingzoneVolcado());
		}
		else
			throw new RuntimeException("No se pudo encontrar el archivo.");
	}

}
