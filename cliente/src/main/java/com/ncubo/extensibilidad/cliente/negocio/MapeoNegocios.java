package com.ncubo.extensibilidad.cliente.negocio;


import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.jms.JMSException;

import com.google.gson.Gson;
import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;
import com.ncubo.extensibilidad.cliente.csv.ProductoCSV;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;
import com.ncubo.extensibilidad.cliente.librerias.Producto;


public class MapeoNegocios 
{
	
	private MapeoDao mapeoDao;
	private ProductoCSV productoCSV;
	
	public MapeoNegocios(String csvPath)
	{
		mapeoDao = new MapeoDao();
		productoCSV = new ProductoCSV(csvPath);
	}
	
	public void Existencias() throws NumberFormatException, IOException, ClassNotFoundException, SQLException, JMSException
	{
		LinkedList<Producto> productos = productoCSV.ObtenerProductos();
		LinkedList<Mapeo> mapeados = mapeoDao.ObtenerMapeosAmbosLados();
		
		for (Producto productoActual : productos)
		{
			for (Mapeo mapeoActual : mapeados)
			{
				if(productoActual.getCodigo().equals(mapeoActual.getIdSAP()))
				{
					new ConectorActiveMQ().enviarMensaje(
							new Configuracion().colaExistencias(), 
							new Gson().toJson(productoActual));
					break;
				}
			}
		}

	}

}
