package com.ncubo.extensibilidad.test.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.main.GenerarMapeos;


public class CsvMapeo
{
	@Test
	public void archivo() throws ClassNotFoundException, IOException, SQLException
	{
		final String PATH_VOLCADO = "src/test/resources/volcadoDB.csv";
		GenerarMapeos generarMapeos = new GenerarMapeos();
		generarMapeos.generar();
		File file = new File(PATH_VOLCADO);
		Assert.assertTrue(file.exists());
	
	}
	
	@Test
	public void mapeo() throws IOException, ClassNotFoundException, SQLException
	{	String lineaActual;
		String textoDeArchivo = "";
		final String PATH_VOLCADO = "src/test/resources/volcadoDB.csv";
		final File archivo = new File(PATH_VOLCADO);
		
		GenerarMapeos generarMapeos = new GenerarMapeos();
		generarMapeos.generar();

		// Abre el archivo para leer su contenido y lo escribe en una variable
		BufferedReader lector = new BufferedReader(new FileReader(archivo));
		while((lineaActual = lector.readLine()) != null)
			textoDeArchivo += lineaActual + " ";
		lector.close();
		textoDeArchivo = textoDeArchivo.replace(',', ' ');
		
		// Texto conocido: sé existe en la BD
		String [] arrayTextoEsperado = {
				"ID ERP","ID NIMBUS","Desc P ERP","Desc P Nimbus"
				,"1", "escoba", "producto1", "escoba"};
		// Agrega un espacio, puesto que en la cadena esperada fueron sustituidas las comas por espacios
		String textoEsperado = "";
		for(String caracter : arrayTextoEsperado)
			textoEsperado += caracter + " ";
		
		Assert.assertEquals(textoDeArchivo, textoEsperado);
	}

}
