package com.ncubo.extensibilidad.cliente.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class MapeoDao 
{
	private final String TABLA = "mapeo";
	
	private enum Columna 
	{
		id_erp (),
		descripcion_erp (),
		id_nimbus (),
		descripcion_nimbus ();
	}
	
	public boolean insertar(Mapeo mapeo) throws SQLException, ClassNotFoundException
	{
		String query = String.format("INSERT INTO %s (%s, %s, %s, %s) "
					 + "VALUES ('%s', '%s', '%s', '%s');"
					 ,TABLA
					 ,Columna.id_erp.name()
					 ,Columna.id_nimbus.name()
					 ,Columna.descripcion_erp.name()
					 ,Columna.descripcion_nimbus.name()
					 ,mapeo.getIdERP()
					 ,mapeo.getIdNimbus()
					 ,mapeo.getDescERP()
					 ,mapeo.getDescNimbus());

		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		Statement ejecutor = con.createStatement();
		boolean inserto = ejecutor.execute(query);
		dao.closeConBD();
		return !inserto;
	}
	
	public List<Mapeo> obtenerMapeosAmbosLados() throws ClassNotFoundException, SQLException
	{
		List<Mapeo> mapeados = new ArrayList<Mapeo>();
		String query = String.format("SELECT %s, %s, %s, %s "
				+ " FROM %s WHERE id_erp is not null and id_nimbus is not null;" 
				,Columna.id_erp.name(), Columna.descripcion_erp.name(), Columna.id_nimbus.name(), Columna.descripcion_nimbus.name()
				, TABLA);
		
		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		Statement ejecutor = con.createStatement();
		ResultSet rs = ejecutor.executeQuery(query);
		
		while (rs.next())
		{
			mapeados.add(new Mapeo(
					rs.getString(Columna.id_erp.name()),
					rs.getString(Columna.descripcion_erp.name()),
					rs.getString(Columna.id_nimbus.name()),
					rs.getString(Columna.descripcion_nimbus.name())
					));
		}
		
		dao.closeConBD();
		return mapeados;
	}
	
	public List<Mapeo> obtener() throws IOException,SQLException, ClassNotFoundException
	{
		String query = String.format("SELECT %s, %s, %s, %s FROM %s" 
				,Columna.id_erp.name(), Columna.descripcion_erp.name(), Columna.id_nimbus.name(), Columna.descripcion_nimbus.name()
				, TABLA);
		Persistencia persistencia = new Persistencia();
		Connection conn = persistencia.openConBD();
		ResultSet result;
		Statement estado = conn.createStatement();
		result = estado.executeQuery(query);
		
		List<Mapeo> rsList = new ArrayList<Mapeo>();
		while(result.next())
		{
			Mapeo mapeo = null;
			
			if(result.getString(Columna.id_erp.name()) != null && result.getString(Columna.id_nimbus.name()) == null)
			{
				mapeo = Mapeo.erp(result.getString(Columna.id_erp.name()), result.getString(Columna.descripcion_erp.name()));
			}
			else if(result.getString(Columna.id_erp.name()) == null && result.getString(Columna.id_nimbus.name()) != null)
			{
				mapeo = Mapeo.nimbus(result.getString(Columna.id_nimbus.name()), result.getString(Columna.descripcion_nimbus.name()));
			}
			else
			{
				mapeo = new Mapeo(result.getString(Columna.id_erp.name()),
						result.getString(Columna.descripcion_erp.name()),
						result.getString(Columna.id_nimbus.name()),
						result.getString(Columna.descripcion_nimbus.name()));
			}
			
			rsList.add(mapeo);
		}
		result.close();
		conn.close();
		return rsList;
	}

	public void insertar(List<Mapeo> mapeos) throws SQLException, ClassNotFoundException
	{
		Persistencia dao = new Persistencia();
		Connection con = dao.openConBD();
		con.setAutoCommit(false);
		Statement ejecutor = con.createStatement();
		
		try
		{
			String query = String.format("DELETE FROM %s;", TABLA);
			ejecutor.execute(query);
			for (Mapeo mapeoActual : mapeos) 
			{
				query = String.format("INSERT INTO %s (%s, %s, %s, %s) "
						+ "VALUES (%s, %s, %s, %s);"
						, TABLA
						, Columna.id_erp.name()
						, Columna.id_nimbus.name()
						, Columna.descripcion_erp.name()
						, Columna.descripcion_nimbus.name()
						, (mapeoActual.getIdERP() != null ?"'" + mapeoActual.getIdERP() + "'" : "null")
						, (mapeoActual.getIdNimbus( ) != null ?"'" + mapeoActual.getIdNimbus() + "'" : "null")
						, (mapeoActual.getDescERP()  != null ?"'" + mapeoActual.getDescERP() + "'" : "null")
						, (mapeoActual.getDescNimbus()  != null ?"'" + mapeoActual.getDescNimbus() + "'" : "null"));
				ejecutor.execute(query);
			}
			con.commit();
		}catch(Exception e)
		{
			con.rollback();
			throw new RuntimeException();
		}
		finally
		{
			ejecutor.close();
			dao.closeConBD();
		}
		
	}

	
}
