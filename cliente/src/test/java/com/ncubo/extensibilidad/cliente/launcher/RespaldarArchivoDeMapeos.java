package com.ncubo.extensibilidad.cliente.launcher;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.dao.Persistencia;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class RespaldarArchivoDeMapeos extends Testcase{
	String folder = "src/test/resources/";
	MapeoCSV mapeoCSV = new MapeoCSV(folder);
	
	@BeforeTest
	public void cargarDatos() throws ClassNotFoundException, SQLException, IOException
	{
		Persistencia dao = new Persistencia();
		executeSchema(dao.openConBD(true));
		Connection con = dao.openConBD();
		executeDBScripts("src/test/resources/extensibilidad_mapeo.sql", con);
	}
	
	/*
	 * En este método que aseguro que ingresa solo la cantidad de respaldos delimitado en 
	 * properties y borra el más antiguo.
	 */
	@Test
	public void respaldaDiezArchivos() throws IOException, ClassNotFoundException, SQLException, InterruptedException
	{
		borrarArchivos();
		
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 1...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 2...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 3...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 4...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 5...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 6...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 7...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 8...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 9...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 10...");
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		System.out.println("Creé 11...");
		Thread.sleep(1000);
		AssertJUnit.assertEquals(mapeoCSV.numeroDeRespaldos(), new Configuracion().cantidadRespaldos());
		
		borrarArchivos();
		System.out.println("Termina test respaldaDiezArchivos");
	}

	/*
	 * Se prueba que el último registro que se inserta es el que se utiliza
	 * */
	@Test
	public void obtieneElMasReciente() throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		borrarArchivos();
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		AssertJUnit.assertEquals(mapeoCSV.obtener().toString(), new MapeoDao().obtener().toString());
		borrarArchivos();
		System.out.println("Termina test obtieneElMasReciente");
	}
	
	private void borrarArchivos()
	{
		File dir = new File(folder);
		String[] ficheros = dir.list();
		
		if (ficheros != null)
		{
			for (int x=0; x < ficheros.length ; x++)
			{
				if (ficheros[x].startsWith("volcado"))
				{
					new File (folder + "/" + ficheros[x]).delete();
				}
			}
		}
	}
	
}
