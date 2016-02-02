package com.ncubo.extensibilidad.cliente.launcher;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jms.JMSException;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.dao.Persistencia;
import com.ncubo.extensibilidad.cliente.main.ProcesarArchivoDeMapeos;

public class ProcesarArchivoDeMapeosTest extends Testcase {
	
	
	@BeforeTest
	public void cargarDatos() throws ClassNotFoundException, SQLException, IOException
	{
		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		Statement ejecutor = con.createStatement();
		executeDBScripts("src/test/resources/ProcesarArchivoDeMapeosTest.sql", ejecutor);
	}
	
	
	/*
	 * Se prueba que el contenido del archivo csv es el mismo que se inserta en la base de datos y de la 
	 * forma correcta
	 */
	@Test
	public void pruebLauncher() throws ClassNotFoundException, SQLException, IOException, NumberFormatException, JMSException
	{
		final String RUTA_DEL_ARCHIO= "src/test/resources/ProcesarArchivoDeMapeosTest.csv";
		MapeoCSV mapeoCSV = new MapeoCSV(RUTA_DEL_ARCHIO);
		MapeoDao mapeoDao = new MapeoDao();
		
		ProcesarArchivoDeMapeos.main(new String[]{RUTA_DEL_ARCHIO});
		
		AssertJUnit.assertEquals(mapeoCSV.obtener().toString(), mapeoDao.obtener().toString());
	}

}
