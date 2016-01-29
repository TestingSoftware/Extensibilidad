package com.ncubo.extensibilidad.cliente.csv;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.dao.MapeoDao;

public class MapeoCSVTest {
	
	MapeoCSV mapeo = new MapeoCSV("src/test/resources/volcadoTest1.csv");
	MapeoDao mapeoDao = new MapeoDao();
	
	@Test
	public void prueba1() throws IOException, ClassNotFoundException, SQLException{
		
		mapeoDao.InsertarMapeos(mapeo.ObtenerMapeos());
		
		Assert.assertEquals(mapeo.ObtenerMapeos().toString(), mapeoDao.ObtenerMapeos().toString());
		
	}

}
