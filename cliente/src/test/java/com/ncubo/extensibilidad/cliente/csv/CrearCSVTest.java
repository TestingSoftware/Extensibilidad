package com.ncubo.extensibilidad.cliente.csv;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.negocio.CrearCSV;

public class CrearCSVTest {
	
	CrearCSV crearCSV = new CrearCSV();
	
	@Test
	public void prueba1() throws ClassNotFoundException, IOException, SQLException{
		
		crearCSV.creaCsv();
	}

}
