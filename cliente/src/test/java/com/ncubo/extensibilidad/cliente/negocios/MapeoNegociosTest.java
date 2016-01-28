package com.ncubo.extensibilidad.cliente.negocios;


import java.io.IOException;
import java.sql.SQLException;

import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;
import com.ncubo.extensibilidad.cliente.negocio.MapeoNegocios;


public class MapeoNegociosTest 
{
	
	@Test
	public void prueba1() throws NumberFormatException, ClassNotFoundException, IOException, SQLException, JMSException
	{
		new MapeoNegocios("src/test/resources/erpTest.csv").Existencias();
		Assert.assertNotEquals(new ConectorActiveMQ().hayNuevo("EXISTENCIAS"), "");
	}

	

}
