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
		String pathVolcado;
		
		if( args == null || args.length == 0)
		{
			pathVolcado = new Configuracion().landingzoneVolcado();
		}
		else
		{
			pathVolcado = args[0];
		}
		
		MapeoCSV mapeoCSV = new MapeoCSV(pathVolcado);
		MapeoDao mapeoDao = new MapeoDao();
		mapeoDao.insertar(mapeoCSV.obtener());
		System.out.println("Se insertó correctamente el archivo de mapeos");
	}
}
