package com.ncubo.extensibilidad.cliente.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.jms.JMSException;

import com.google.gson.Gson;
import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;
import com.ncubo.extensibilidad.cliente.csv.ProductoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;
import com.ncubo.extensibilidad.cliente.librerias.Producto;

public class ProcesarArchivoGeneradoDeExistencias {

	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, SQLException, JMSException 
	{
		new ProcesarArchivoGeneradoDeExistencias().launcher(new Configuracion().pathExistencias()
				, new Configuracion().colaExistencias());
	}
	
	public void launcher(String pathArchivo, String cola) throws NumberFormatException, IOException, ClassNotFoundException, SQLException, JMSException
	{
		MapeoDao mapeoDao = new MapeoDao();
		ProductoCSV productoCSV = new ProductoCSV(pathArchivo);
		
		List<Producto> productos = productoCSV.obtener();
		List<Mapeo> mapeados = mapeoDao.obtenerMapeosAmbosLados();
		
		for (Producto productoActual : productos)
		{
			for (Mapeo mapeoActual : mapeados)
			{
				if(productoActual.getCodigo().equals(mapeoActual.getIdERP()))
				{
					new ConectorActiveMQ().enviarMensaje(cola, new Gson().toJson(productoActual));
					break;
				}
			}
		}
	}

}
