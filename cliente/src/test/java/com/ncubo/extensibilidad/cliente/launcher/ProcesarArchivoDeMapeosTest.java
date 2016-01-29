package com.ncubo.extensibilidad.cliente.launcher;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;

public class ProcesarArchivoDeMapeosTest {
	
	/*
	 * Ejecutar el sql ProcesarArchivoDeMapeosTest.sql para cargar los datos
	 */
	@Test
	public void pruebLuncher() throws ClassNotFoundException, SQLException, IOException
	{
		MapeoCSV mapeoCSV = new MapeoCSV("src/test/resources/ProcesarArchivoDeMapeosTest.csv");
		MapeoDao mapeoDao = new MapeoDao();
		mapeoDao.insertar(mapeoCSV.obtener());
		Assert.assertEquals(mapeoCSV.obtener().toString(), mapeoDao.obtener().toString());
	}

}
