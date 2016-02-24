package com.ncubo.extensibilidad.cliente.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

<<<<<<< HEAD
import org.testng.AssertJUnit;
=======
import org.testng.Assert;
>>>>>>> 91fe0714c16e7aabe9ff04bf7f3fc3a692d6e0b7
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.dao.Persistencia;
import com.ncubo.extensibilidad.cliente.main.GenerarMapeos;


public class GenerarArchivosMapeo extends Testcase
{
	@BeforeTest
	public void cargarDatos() throws ClassNotFoundException, SQLException, IOException
	{
		Persistencia dao = new Persistencia();
		executeSchema(dao.openConBD(true));
		Connection con = dao.openConBD();
		executeDBScripts("src/test/resources/ConsumirProductosNimbus.sql", con);
	}
	@Test
	public void archivo() throws ClassNotFoundException, IOException, SQLException
	{
		final String PATH_VOLCADO = "src/test/resources/GenerarArchivosMapeo.csv";
		
		GenerarMapeos.main(new String []{});
		
		File file = new File(PATH_VOLCADO);
		Assert.assertTrue(file.exists());
	}
	
	@Test
	public void mapeo() throws IOException, ClassNotFoundException, SQLException
	{	String lineaActual;
		String textoDeArchivo = "";
		final String PATH_VOLCADO = "src/test/resources/GenerarArchivosMapeo.csv";
		final File archivo = new File(PATH_VOLCADO);
		
		GenerarMapeos.main(new String []{});

		// Abre el archivo para leer su contenido y lo escribe en una variable
		BufferedReader lector = new BufferedReader(new FileReader(archivo));
		while((lineaActual = lector.readLine()) != null)
			textoDeArchivo += lineaActual + " ";
		lector.close();
		textoDeArchivo = textoDeArchivo.replace(',', ' ');
		
		// Texto conocido: sé existe en la BD
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
