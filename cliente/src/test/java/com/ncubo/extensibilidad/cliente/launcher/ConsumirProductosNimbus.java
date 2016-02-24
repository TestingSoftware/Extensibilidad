package com.ncubo.extensibilidad.cliente.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.dao.Persistencia;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;
import com.ncubo.extensibilidad.cliente.main.ActualizarExistenciasBaseDatos;

public class ConsumirProductosNimbus extends Testcase
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
	 public void consumir() throws ClassNotFoundException, IOException, SQLException, JMSException
	 {
		String [] test = {"1"};
		List<Mapeo> listaBD = new ArrayList<Mapeo>();
		ActualizarExistenciasBaseDatos.main(test);

		
		MapeoDao mapeoDao = new MapeoDao();
		listaBD = mapeoDao.obtener();
		List<String> listTemp1 = new ArrayList<String>();
		
		List<Mapeo> idsDeRest = retornaIdsNimbusRest(listaBD, listTemp1);
		for(Mapeo elemento : idsDeRest)
			Assert.assertTrue(listTemp1.remove(elemento.getIdNimbus()));
		

	 }
	private List<Mapeo> retornaIdsNimbusRest(List<Mapeo> listaBD, List<String> listTemp1)
			throws MalformedURLException, IOException, ProtocolException 
	{
		for(Mapeo idProductoBD : listaBD)
			listTemp1.add(idProductoBD.getIdNimbus());

		
		List<Mapeo> idsDeRest = new ArrayList<Mapeo>();
		
		URL url = new URL(new Configuracion().urlNimbus());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) 
		{
			throw new RuntimeException(String.format("No se pudo conectar al server de Nimbus en %s o lanzó un error.", new Configuracion().urlNimbus() ));
		}	
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String output;
		while ((output = br.readLine()) != null) 
		{
			JsonParser jSonParser = new JsonParser();
			JsonArray arrayProductos = jSonParser.parse(output).getAsJsonArray();
			
			for(JsonElement producto : arrayProductos)
			{
				String idNimbus = producto.toString().replace('"', ' ').trim();
				Mapeo mapeo = new Mapeo(null, null,idNimbus , null);
				idsDeRest.add(mapeo);
			}	
		}
		return idsDeRest;
	}
}
