package com.ncubo.extensibilidad.cliente.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Producto;

public class ProductoCSV {
	
	private String csvPath;
	
	public ProductoCSV(String csvPath){
		this.csvPath = csvPath;
	}
	
	public LinkedList<Producto> ObtenerProductos() throws NumberFormatException, IOException
	{
		LinkedList<Producto> productos = new LinkedList<Producto>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		br = new BufferedReader(new FileReader(csvPath));
		line = br.readLine();
		while ((line = br.readLine()) != null)
		{
			String[] productoString = line.split(cvsSplitBy);
			Producto producto = new Producto(productoString[0]);
			producto.setNombre(productoString[1]);
			producto.setCantidad(Integer.parseInt(productoString[2]));
			
			productos.add(producto);
		}
		
		br.close();
		return productos;
	}

}
