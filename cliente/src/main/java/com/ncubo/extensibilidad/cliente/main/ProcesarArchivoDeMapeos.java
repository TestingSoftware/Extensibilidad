package com.ncubo.extensibilidad.cliente.main;

import java.io.IOException;
import java.sql.SQLException;

import javax.jms.JMSException;

import com.ncubo.extensibilidad.cliente.csv.MapeoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class ProcesarArchivoDeMapeos {

	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, SQLException, JMSException 
	{
		new ProcesarArchivoDeMapeos().Luncher(new Configuracion().pathVolcado());
	}
	
	public void Luncher(String path) throws ClassNotFoundException, SQLException, IOException
	{
		MapeoCSV mapeoCSV = new MapeoCSV(path);
		MapeoDao mapeoDao = new MapeoDao();
		mapeoDao.insertar(mapeoCSV.obtener());
	}
}
