package com.ncubo.extensibilidad.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class CsvMapeo {
	
	String query = "SELECT * From mapeo;";
	Persistencia persistencia = new Persistencia();
	
	public LinkedList<Mapeo> mapear() throws IOException,SQLException, ClassNotFoundException
	{
		Connection conn = persistencia.openConBD();
		ResultSet result;
		Statement estado = conn.createStatement();
		result = estado.executeQuery(query);
		
		LinkedList<Mapeo> rsList = new LinkedList<Mapeo>();
		while(result.next())
		{
			Mapeo mapeo = new Mapeo(result.getString(1), result.getString(2),
							result.getString(3), result.getString(4));
			rsList.add(mapeo);
		}
		result.close();
		conn.close();
		return rsList;
		//crearCSV(rsList);
	}
	
	
}
