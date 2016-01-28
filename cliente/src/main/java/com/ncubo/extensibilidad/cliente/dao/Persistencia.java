package com.ncubo.extensibilidad.cliente.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Persistencia 
{
	Connection conector;
	
	public Connection openConBD() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		conector = (Connection) DriverManager.getConnection
				("jdbc:mysql://localhost/extensibilidad", "root", "");
		return conector;
	}
	
	public void closeConBD() throws SQLException
	{
		this.conector.close();
	}
	
	
}
