package com.ncubo.extensibilidad.cliente.launcher;


import java.io.IOException;
import java.sql.SQLException;

import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;
import com.ncubo.extensibilidad.cliente.csv.ProductoCSV;
import com.ncubo.extensibilidad.cliente.main.ProcesarArchivoGeneradoDeExistencias;

public class ProcesarArchivoGeneradoDeExistenciasTest {
	
	/*
	 * Ejecutar el sql ProcesarArchivoDeMapeosTest.sql para cargar los datos
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
	public void luncher() throws NumberFormatException, ClassNotFoundException, IOException, SQLException, JMSException
	{
		String cola = "TEST";
		ProductoCSV productoCSVpruebaLuncher = new ProductoCSV("src/test/resources/productosExistenciasTestLuncher.csv");
		ProcesarArchivoGeneradoDeExistencias procesarArchivoGeneradoDeExistencias = new ProcesarArchivoGeneradoDeExistencias();

		procesarArchivoGeneradoDeExistencias.launcher("src/test/resources/productosExistenciasTestLuncher.csv", cola);
		Assert.assertEquals( productoCSVpruebaLuncher.obtener().get(0).toString(), new ConectorActiveMQ().hayNuevo( cola ));
		Assert.assertEquals(new ConectorActiveMQ().hayNuevo( cola ), "");//me aseguro que solo uno fue el que se ingresó a la cola
	}
	
}
