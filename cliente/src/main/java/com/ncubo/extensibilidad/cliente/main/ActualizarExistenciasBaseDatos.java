package com.ncubo.extensibilidad.cliente.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import com.google.gson.Gson;
import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.IdNimbus;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class ActualizarExistenciasBaseDatos 
{
	private static int numProductosInsertar;
	public int getNumProductosInsertar() {
		return numProductosInsertar;
	}
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, JMSException 
	{
		if(!args.equals(null))
		{
			MapeoDao mapeoDao = new MapeoDao();
			List<Mapeo> existenciasPorInsertar = new ArrayList<Mapeo>();
			existenciasPorInsertar = productosPorInsertar();
			
			for(Mapeo producto : existenciasPorInsertar)
			{
				mapeoDao.insertar(producto);
			}
			// Se envian todos los productos actualizados en un solo string a Nimbus
			Gson gson = new Gson();
			String gsonObject = gson.toJson(existenciasPorInsertar);
			ConectorActiveMQ connAMQ = new ConectorActiveMQ();
			connAMQ.enviarMensaje("PRODUCTO", gsonObject);
			//TODO se pueden enviar los productos uno por uno, CONSULTAR 
		}
		else
		{
			// Para el TestCase
			List<Mapeo> existenciasPorInsertar = new ArrayList<Mapeo>();
			existenciasPorInsertar = productosPorInsertar();
			numProductosInsertar = existenciasPorInsertar.size();
		}
	}
	private static List<Mapeo> productosPorInsertar() throws ClassNotFoundException, IOException, SQLException
	{
		List<Mapeo> existenciasDb = new ArrayList<Mapeo>();
		MapeoDao mapeoDao = new MapeoDao();
		existenciasDb = (ArrayList <Mapeo>)mapeoDao.obtener();
		IdNimbus idNimbus = new IdNimbus();
		return idNimbus.actualizarEnBd(existenciasDb);
	}
}
