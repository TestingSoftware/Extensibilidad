package com.ncubo.extensibilidad.cliente.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.dao.Persistencia;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.main.GenerarMapeos;


public class GenerarArchivosMapeo extends Testcase
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
	public void archivo() throws ClassNotFoundException, IOException, SQLException
	{
		final String PATH_VOLCADO = "src/test/resources/GenerarArchivosMapeo.csv";
		GenerarMapeos.generar();
		File file = new File(PATH_VOLCADO);
		Assert.assertTrue(file.exists());
	
	}
	
	@Test
	public void mapeo() throws IOException, ClassNotFoundException, SQLException
	{	String lineaActual;
		String textoDeArchivo = "";
		final String PATH_VOLCADO = "src/test/resources/GenerarArchivosMapeo.csv";
		final File archivo = new File(PATH_VOLCADO);
		
		GenerarMapeos.generar();

		// Abre el archivo para leer su contenido y lo escribe en una variable
		BufferedReader lector = new BufferedReader(new FileReader(archivo));
		while((lineaActual = lector.readLine()) != null)
			textoDeArchivo += lineaActual + " ";
		lector.close();
		textoDeArchivo = textoDeArchivo.replace(',', ' ');
		
		// Texto conocido: s� existe en la BD
		String [] arrayTextoEsperado = {
				"ID_ERP","DESC_P_ERP","ID_NIMBUS","DESC_P_NIMBUS"
				,"1", "escoba", "producto1", "escoba"};
		// Agrega un espacio, puesto que en la cadena esperada fueron sustituidas las comas por espacios
		String textoEsperado = "";
		for(String caracter : arrayTextoEsperado)
			textoEsperado += caracter + " ";
		
		Assert.assertEquals(textoDeArchivo, textoEsperado);
	}

}
