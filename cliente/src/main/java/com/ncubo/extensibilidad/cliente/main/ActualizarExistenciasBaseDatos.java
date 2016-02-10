package com.ncubo.extensibilidad.cliente.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ncubo.extensibilidad.cliente.dao.MapeoDao;
import com.ncubo.extensibilidad.cliente.librerias.IdNimbus;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class ActualizarExistenciasBaseDatos 
{

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		actualizarExistencias();
	}

	private static void actualizarExistencias() throws IOException, SQLException, ClassNotFoundException {
		MapeoDao mapeoDao = new MapeoDao();
		List<Mapeo> existenciasDb = new ArrayList<Mapeo>();
		List<Mapeo> existenciasPorInsertar = new ArrayList<Mapeo>();
		
		existenciasDb = (ArrayList <Mapeo>)mapeoDao.obtener();
		IdNimbus idNimbus = new IdNimbus();
		existenciasPorInsertar = idNimbus.actualizarEnBd(existenciasDb);
		
		for(Mapeo producto : existenciasPorInsertar)
			mapeoDao.insertar(producto);
	}
}
