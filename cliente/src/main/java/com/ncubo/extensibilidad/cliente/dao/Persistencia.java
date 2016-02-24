package com.ncubo.extensibilidad.cliente.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class Persistencia 
{
	Connection conector;
	
	public Connection openConBD() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		conector = (Connection) DriverManager.getConnection
				(String.format("jdbc:mysql://%s/%s", 
						new Configuracion().mysqlPath(),new Configuracion().mysqlBaseDeDatos()), 
						new Configuracion().mysqlUsuario(), new Configuracion().mysqlPassword());
		return conector;
	}
	
	public void closeConBD() throws SQLException
	{
		this.conector.close();
	}
	
	
}
