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
		LANDINGZONE_EXISTENCIAS("existencias.landingzone"),
		LANDINGZONE_VOLCADO("volcado.landingzone"),
		CANTIDAD_RESPALDOS("cantidad.respaldos"),
		MYSQL_USUARIO("mysql.usuario"),
		MYSQL_PASSWORD("mysql.password"),
		MYSQL_PATH("mysql.url"),
		MYSQL_BASE("mysql.basededatos");
		
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
	
	public String landingzoneExistencias()
	{
		return prop.getProperty(key.LANDINGZONE_EXISTENCIAS.toString());
	}
	
	public String conexionCola()
	{
		return prop.getProperty(key.CONEXION_COLA.toString());
	}
	
	public String colaExistencias()
	{
		return prop.getProperty(key.EXISTENCIAS_COLA.toString());
	}
	
	public String landingzoneVolcado()
	{
		return prop.getProperty(key.LANDINGZONE_VOLCADO.toString());
	}
	
	public int cantidadRespaldos()
	{
		return Integer.parseInt(prop.getProperty(key.CANTIDAD_RESPALDOS.toString()));
	}
	
	public String mysqlUsuario()
	{
		return prop.getProperty(key.MYSQL_USUARIO.toString());
	}
	
	public String mysqlPassword()
	{
		return prop.getProperty(key.MYSQL_PASSWORD.toString());
	}
	
	public String mysqlPath()
	{
		return prop.getProperty(key.MYSQL_PATH.toString());
	}
	
	public String mysqlBaseDeDatos()
	{
		return prop.getProperty(key.MYSQL_BASE.toString());
	}
}
