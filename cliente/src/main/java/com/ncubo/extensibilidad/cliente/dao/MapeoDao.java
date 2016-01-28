package com.ncubo.extensibilidad.cliente.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class MapeoDao 
{
	
	public void insertar(Mapeo mapeo) throws SQLException, ClassNotFoundException
	{
		String queryDatos = "'" + mapeo.getIdSAP() + "'"
							+"," + "'" + mapeo.getIdNimbus( )+ "'"
							+"," + "'" + mapeo.getDescSAP() + "'"
							+"," + "'" + mapeo.getDescNimbus() + "'";
		String query = "INSERT INTO mapeo "
					 + "(id_erp, "
					 + "id_nimbus, "
					 + "descripcion_erp, "
					 + "descripcion_nimbus) "
					 + "VALUES ("+queryDatos+");";

		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		Statement ejecutor = con.createStatement();
		ejecutor.execute(query);
		dao.closeConBD();
	}
	
	public LinkedList<Mapeo> ObtenerMapeosAmbosLados() throws ClassNotFoundException, SQLException
	{
		LinkedList<Mapeo> mapeados = new LinkedList<Mapeo>();
		String query = "SELECT * FROM mapeo WHERE id_erp is not null and id_nimbus is not null";
		
		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		Statement ejecutor = con.createStatement();
		ResultSet rs = ejecutor.executeQuery(query);
		
		while (rs.next())
		{
			mapeados.add(new Mapeo(
					rs.getString("id_erp"),
					rs.getString("id_nimbus"),
					rs.getString("descripcion_erp"),
					rs.getString("descripcion_nimbus")
					));
		}
		
		dao.closeConBD();
		return mapeados;
	}
	
	public LinkedList<Mapeo> ObtenerMapeos() throws IOException,SQLException, ClassNotFoundException
	{
		String query = "SELECT * From mapeo;";
		Persistencia persistencia = new Persistencia();
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