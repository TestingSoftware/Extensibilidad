package com.ncubo.extensibilidad.cliente.launcher;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jms.JMSException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.dao.Persistencia;
import com.ncubo.extensibilidad.cliente.main.ActualizarExistenciasBaseDatos;

public class ConsumirProductosNimbus extends Testcase
{
	@BeforeTest
	public void cargarDatos() throws ClassNotFoundException, SQLException, IOException
	{
		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		Statement ejecutor = con.createStatement();
		executeDBScripts("src/test/resources/ConsumirProductosNimbus.sql", ejecutor);
	}
	 @Test
	 public void consumir() throws ClassNotFoundException, IOException, SQLException, JMSException
	 {
		String [] test = {"1"};
		ActualizarExistenciasBaseDatos.main(test);
		ActualizarExistenciasBaseDatos actualizar = new ActualizarExistenciasBaseDatos();
		AssertJUnit.assertEquals(9,actualizar.getNumProductosInsertar());
	 }
}
