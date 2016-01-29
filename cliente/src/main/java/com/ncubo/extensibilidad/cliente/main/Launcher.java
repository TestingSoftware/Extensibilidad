package com.ncubo.extensibilidad.cliente.main;

import java.io.IOException;
import java.sql.SQLException;

import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class Launcher {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException 
	{
		System.out.println( new Configuracion().urlNimbus() );
		MapeoDao csvMapeo = new MapeoDao();
		MapeoCSV crearCsv = new MapeoCSV();
		crearCsv.crear(csvMapeo.ObtenerMapeos());
	}
	
}
