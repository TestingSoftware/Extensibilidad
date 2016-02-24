package com.ncubo.extensibilidad.cliente.launcher;


import java.io.IOException;
import java.sql.SQLException;

import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;
import com.ncubo.extensibilidad.cliente.csv.ProductoCSV;
import com.ncubo.extensibilidad.cliente.dao.Persistencia;
import com.ncubo.extensibilidad.cliente.main.ProcesarArchivoGeneradoDeExistencias;

public class ProcesarArchivoGeneradoDeExistenciasTest extends Testcase{
	
	@BeforeTest
	public void cargarDatos() throws ClassNotFoundException, SQLException, IOException
	{
		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		executeSchema(con);
		executeDBScripts("src/test/resources/extensibilidad_mapeo.sql", con);
	}
	
	/*
	 * Prueba que si se pasa por parametros un archivo inexisstente, lanza un error
	 */
	
	@Test
	public void archivoExiste() throws NumberFormatException, IOException 
	{
		try
		{
			new ProductoCSV("src/test/resources/archivoInexistente.csv").obtener();
		}catch(Exception ex)
		{
			Assert.assertEquals(ex.getMessage(), "El archivo no existe.");
		}
	}
	
	/**
	 * Solo se espera que se inserte el primer registro del archivo en la cola ya que solo este es el mapeado en la base
	 */
	@Test
	public void launcher() throws NumberFormatException, ClassNotFoundException, IOException, SQLException, JMSException
	{
		final String COLA = "TEST";
		final String RUTA_DEL_ARCHIVO = "src/test/resources/productosExistenciasTestLuncher.csv";
		
		ProductoCSV productoCSVpruebaLuncher = new ProductoCSV(RUTA_DEL_ARCHIVO);
		ProcesarArchivoGeneradoDeExistencias.main(new String[]{RUTA_DEL_ARCHIVO, COLA});
		
		Assert.assertEquals( productoCSVpruebaLuncher.obtener().get(0).toString(), new ConectorActiveMQ().hayNuevo( COLA ));
		Assert.assertEquals(new ConectorActiveMQ().hayNuevo( COLA ), "");//me aseguro que solo uno fue el que se ingresó a la cola
	}
	
}
