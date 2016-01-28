package com.ncubo.extensibilidad.cliente.negocio;
import java.io.IOException;
import java.sql.SQLException;

import com.ncubo.extensibilidad.dao.CsvMapeo;

public class CrearCSV {
	public void creaCsv() throws IOException, ClassNotFoundException, SQLException{
		CsvMapeo csvMapeo = new CsvMapeo();
		com.ncubo.extensibilidad.cliente.csv.CrearCSV crearCsv;
		crearCsv = new com.ncubo.extensibilidad.cliente.csv.CrearCSV();
		crearCsv.crearCSV(csvMapeo.mapear());
	}
}
