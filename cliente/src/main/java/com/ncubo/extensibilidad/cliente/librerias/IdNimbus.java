package com.ncubo.extensibilidad.cliente.librerias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class IdNimbus  
{
	
	public List<Mapeo> actualizarEnBd(List<Mapeo> idsDeBD) throws IOException
	{
		List<Mapeo> idsDeRest;
		idsDeRest = obtieneIdsNimbus();
		
		return compara(idsDeRest, idsDeBD);
	}
	private List<Mapeo> obtieneIdsNimbus() throws IOException
	{
		URL url = new URL(new Configuracion().urlNimbus());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Error : Codigo : "
					+ conn.getResponseCode());
		}	
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;

		List<Mapeo> idsNimbus = new ArrayList<Mapeo>();
		while ((output = br.readLine()) != null) 
		{
			JsonParser jSonParser = new JsonParser();
			JsonArray arrayProductos = jSonParser.parse(output).getAsJsonArray();
			
			for(JsonElement producto : arrayProductos)
			{
				String idNimbus = producto.toString().replace('"', ' ').trim();
				Mapeo mapeo = new Mapeo(null, null,idNimbus , null);
				idsNimbus.add(mapeo);
			}	
		}
		return idsNimbus;
	}
	
	private List<Mapeo> compara(List<Mapeo> idsDescNimbus, List<Mapeo> idsNimbusDB)
	{	
		List<Mapeo> listaTemporal = new ArrayList<Mapeo>();
		
		for(Mapeo idProNimbus : idsDescNimbus)
			for(Mapeo idProNimbusDB : idsNimbusDB)
				if(idProNimbus.getIdNimbus().equals(idProNimbusDB.getIdNimbus()))
				{
					listaTemporal.add(idProNimbus);
					break;
				}
		idsDescNimbus.removeAll(listaTemporal);
		
		return idsDescNimbus;
	}
}
