package com.ncubo.extensibilidad.test.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.negocio.CrearCSV;


public class CsvMapeo
{
	private static final File FILE_PATH = new File("src/main/resources/volcadoDB.csv");

	@Test
	public void mapeo() throws IOException, ClassNotFoundException, SQLException
	{
		//TODO AQUI LLAMAR UN SQL QUE CARGUE LOS DATOS
		CrearCSV crearCsv = new CrearCSV();
		crearCsv.creaCsv();
		String lineaActual;
		String outPutText = "";
		BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
		
		while((lineaActual = br.readLine()) != null)
			outPutText += lineaActual+ " ";
		outPutText = outPutText.replace(',', ' ');
		String [] expectedText = {"ID ERP","ID NIMBUS","Desc P ERP","Desc P Nimbus"
				,"1","1","1","1"
				,"1","1","1","1"
				,"2","2","2","2"
				,"3","3","3","3"};
		String texto = "";
		for(String elemento : expectedText)
		texto +=  elemento + " ";
		
		br.close();
		Assert.assertEquals(outPutText, texto);
	}
}
