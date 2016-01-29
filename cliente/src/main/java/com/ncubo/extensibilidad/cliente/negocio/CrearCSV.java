package com.ncubo.extensibilidad.cliente.negocio;
import java.io.IOException;
import java.sql.SQLException;

import com.ncubo.extensibilidad.cliente.dao.MapeoDao;

public class CrearCSV {
	public void creaCsv() throws IOException, ClassNotFoundException, SQLException{
		MapeoDao csvMapeo = new MapeoDao();
		com.ncubo.extensibilidad.cliente.csv.MapeoCSV crearCsv;
		crearCsv = new com.ncubo.extensibilidad.cliente.csv.MapeoCSV();
		crearCsv.crear(csvMapeo.ObtenerMapeos());
	}
}
