package com.ncubo.extensibilidad.cliente.librerias;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracion 
{
	private Properties prop = new Properties();
	public enum key {
		URL_NIMBUS("nimbus.url"),
		CONEXION_COLA("conexion.cola"),
		EXISTENCIAS_COLA("existencias.cola"),
		PATH_EXISTENCIAS("existencias.path");
		
		private String nombre;
		key(String nombre)
		{
			this.nombre = nombre;
		}
		
		public String toString()
		{
			return this.nombre;
		}
	}
	public Configuracion()
	{
		try {
			InputStream input = new FileInputStream("config.properties");
			prop.load(input);
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Properties getProp()
	{
		return prop;
	}
	
	public String urlNimbus()
	{
		return prop.getProperty(key.URL_NIMBUS.toString());
	}
	
	public String pathExistencias()
	{
		return prop.getProperty(key.PATH_EXISTENCIAS.toString());
	}
	
	public String conexionCola()
	{
		return prop.getProperty(key.CONEXION_COLA.toString());
	}
	
	public String colaExistencias()
	{
		return prop.getProperty(key.EXISTENCIAS_COLA.toString());
	}
}
