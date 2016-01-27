package com.ncubo.extensibilidad.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

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
		hayNuevo(URL_PRODUCTO);
	}
	
	public void hayNuevo(Pedido pedido) throws IOException{

		hayNuevo(URL_PEDIDO);
	}
	
	private void hayNuevo(String urlP) throws IOException{
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
}
