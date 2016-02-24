package com.ncubo.extensibilidad.cliente.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import com.google.gson.Gson;
import com.ncubo.extensibilidad.cliente.activemq.ConectorActiveMQ;
import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;
import com.ncubo.extensibilidad.cliente.librerias.Nimbus;

public class ActualizarExistenciasBaseDatos 
{

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, JMSException 
	{
		boolean resultadoInsercion = false; //La idea es tener un parametro para saber si se inserto o no
		if(args == null)
		{
			return; 
		}

		MapeoDao mapeoDao = new MapeoDao();
		List<Mapeo> existenciasPorInsertar = new ArrayList<Mapeo>();
		existenciasPorInsertar = productosPorInsertar();
		for(Mapeo producto : existenciasPorInsertar)
		{
			if(mapeoDao.insertar(producto))
				resultadoInsercion = true; 
		}
		
		if(resultadoInsercion)
		{
			Gson gson = new Gson();
			String gsonObject = gson.toJson(existenciasPorInsertar);
			ConectorActiveMQ connAMQ = new ConectorActiveMQ();
			connAMQ.enviarMensaje("PRODUCTO", gsonObject);
		}
		else
			System.out.println("No se actualizó ningún producto.");
		//TODO se pueden enviar los productos uno por uno, CONSULTAR
	}
	private static List<Mapeo> productosPorInsertar() throws ClassNotFoundException, IOException, SQLException
	{
		List<Mapeo> existenciasDb = new ArrayList<Mapeo>();
		MapeoDao mapeoDao = new MapeoDao();
		existenciasDb = (ArrayList <Mapeo>)mapeoDao.obtener();
		Nimbus idNimbus = new Nimbus();
		return idNimbus.calcularMapeosNoExistentesEnBD(existenciasDb);
	}
}
