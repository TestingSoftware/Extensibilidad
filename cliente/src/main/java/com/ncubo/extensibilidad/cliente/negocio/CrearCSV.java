package com.ncubo.extensibilidad.cliente.negocio;
import java.io.IOException;
import java.sql.SQLException;

import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class CrearCSV {
	
	public void creaCsv() throws IOException, ClassNotFoundException, SQLException{
		MapeoDao csvMapeo = new MapeoDao();
		MapeoCSV crearCsv = new MapeoCSV(new Configuracion().landingzoneVolcado());
		crearCsv.crear(csvMapeo.obtener());
	}
}
