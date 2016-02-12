package com.ncubo.extensibilidad.cliente.librerias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Nimbus {
	private static final String URL_PRODUCTO = "http://localhost:8080/Server/producto";
	private static final String URL_PEDIDO = "http://localhost:8080/Server/pedido";
	
	public Nimbus(){		
	}
	
	public void crear(Producto producto) throws IOException{
		Gson jSonObject = new Gson();
		String jSonProducto = jSonObject.toJson(producto);
		
		crear(URL_PRODUCTO, jSonProducto);
	}
	
	public void crear(Pedido producto) throws IOException{
		Gson jSonObject = new Gson();	
		crear(URL_PEDIDO, jSonObject.toJson(producto));
	}
	
	public void crear(String urlP, String mensaje) throws IOException{
		URL url = new URL(urlP);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		OutputStream os = conn.getOutputStream();
		os.write(mensaje.getBytes());
		os.flush();

		new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		conn.disconnect();
	}
	
	public void hayNuevo(Producto producto) throws IOException{
		consultar(URL_PRODUCTO);
	}
	
	public void hayNuevo(Pedido pedido) throws IOException{

		consultar(URL_PEDIDO);
	}
	
	private void consultar(String urlP) throws IOException{
		URL url = new URL(urlP);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}	
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Resultado:  \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
	}
	
	public List<Mapeo> calcularMapeosNoExistentesEnBD(List<Mapeo> idsDeBD) throws IOException
	{
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
				Mapeo mapeo = new Mapeo(null, null,idNimbus , null);//TODO VIctor falta meter la descripcion en este mapeo
				idsDeRest.add(mapeo);
			}	
		}
		
		List<Mapeo> listaTemporal = new ArrayList<Mapeo>();
		
		for(Mapeo idProNimbus : idsDeRest)
		{
			for(Mapeo idProNimbusDB : idsDeBD)
			{
				if(idProNimbus.getIdNimbus().equals(idProNimbusDB.getIdNimbus()))
				{
					listaTemporal.add(idProNimbus);
					break;
				}
			}
		}
		idsDeRest.removeAll(listaTemporal);
		
		return idsDeRest;
	}	
}
