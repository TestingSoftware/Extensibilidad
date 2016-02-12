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
		String landingzoneExistencias, cola;
		
		if( args == null || args.length == 0)
		{
			landingzoneExistencias = new Configuracion().landingzoneExistencias();
			cola = new Configuracion().colaExistencias();
		}
		else
		{
			landingzoneExistencias = args[0];
			cola = args[1];//TODO Dalaian cambiar esto
		}
		
		MapeoDao mapeoDao = new MapeoDao();
		ProductoCSV productoCSV = new ProductoCSV(landingzoneExistencias);
		
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
		System.out.println("Se ha enviado a la cola "+ cola+ " las existencias de los productos que est�n mapeadas en ambos lados.");
	}

}
