package com.ncubo.extensibilidad.dao;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import java.sql.Statement;
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
}
