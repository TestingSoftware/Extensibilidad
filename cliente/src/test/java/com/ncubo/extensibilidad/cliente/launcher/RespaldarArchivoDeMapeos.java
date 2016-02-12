package com.ncubo.extensibilidad.cliente.launcher;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class RespaldarArchivoDeMapeos {
	String folder = "src/test/resources/";
	MapeoCSV mapeoCSV = new MapeoCSV(folder);
	
	/*
	 * En este m�todo que aseguro que ingresa solo la cantidad de respaldos delimitado en 
	 * properties y borra el m�s antiguo.
	 */
	@Test
	public void respaldaDiezArchivos() throws IOException, ClassNotFoundException, SQLException, InterruptedException
	{
		borrarArchivos();
		
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		AssertJUnit.assertEquals(mapeoCSV.numeroDeRespaldos(), new Configuracion().cantidadRespaldos());
		
		borrarArchivos();
	}

	/*
	 * Se prueba que el �ltimo registro que se inserta es el que se utiliza
	 * */
	@Test
	public void obtieneElMasReciente() throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		borrarArchivos();
		mapeoCSV.crear(new MapeoDao().obtener());
		Thread.sleep(1000);
		AssertJUnit.assertEquals(mapeoCSV.obtener().toString(), new MapeoDao().obtener().toString());
		borrarArchivos();
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
