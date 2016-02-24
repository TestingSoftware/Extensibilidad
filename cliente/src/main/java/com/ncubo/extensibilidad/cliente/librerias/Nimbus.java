package com.ncubo.extensibilidad.cliente.librerias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;

public class Nimbus 
{
	
	public Nimbus()
	{		
	
	}
	
	public void crear(String URL_REST, Producto producto) throws IOException, JMSException
	{
		Gson jSonObject = new Gson();
		String jSonProducto = jSonObject.toJson(producto);
		
		crear(URL_REST, jSonProducto);
	}
	
	public void crear(String URL_REST, Pedido producto) throws IOException, JMSException
	{
		Gson jSonObject = new Gson();	
		crear(URL_REST, jSonObject.toJson(producto));
	}
	
	private void crear(String urlP, String mensaje) throws IOException, JMSException
	{
		ConectorActiveMQ connectorActiveMq = new ConectorActiveMQ();
		connectorActiveMq.enviarMensaje(urlP, mensaje);
	}
	
	public String hayNuevo(String URL_REST) throws IOException, JMSException
	{
		return consultar(URL_REST);
	}
	
	private String consultar(String urlP) throws IOException, JMSException
	{
		ConectorActiveMQ connectorActiveMq = new ConectorActiveMQ();
		return connectorActiveMq.hayNuevo(urlP);
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
