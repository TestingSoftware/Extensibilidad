package com.ncubo.extensibilidad.cliente.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.ncubo.extensibilidad.cliente.librerias.Producto;

public class ProductoCSV {
	
	private String pathArchivo;
	
	private enum Columna 
	{
		CODIGO (),
		NOMBRE (),
		CANTIDAD ();
	}
	
	private final String [] CABECERAS = {Columna.CODIGO.name() 
			,Columna.NOMBRE.name()
			,Columna.CANTIDAD.name()};
	
	public ProductoCSV(String pathArchivo)
	{
		this.pathArchivo = pathArchivo;
	}
	
	public List<Producto> obtener() throws NumberFormatException, IOException
	{
		File file = new File(pathArchivo);
		if( !file.exists() )
		{
			throw new RuntimeException("El archivo no existe.");
		}
		
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(CABECERAS);

		List<Producto> productos = new ArrayList<Producto>();
		fileReader = new FileReader(file);
		csvFileParser = new CSVParser(fileReader, csvFileFormat);
		
		List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
		for ( int i = 1 ; i < csvRecords.size() ; i++ )
		{
			CSVRecord record = csvRecords.get(i);
			Producto producto = new Producto(record.get(Columna.CODIGO.name()));
			producto.setNombre(record.get(Columna.NOMBRE.name()));
			producto.setCantidad(Integer.parseInt(record.get(Columna.CANTIDAD.name())));
			
			productos.add(producto);
		}
		
		fileReader.close();
		csvFileParser.close();
		return productos;
	}

}
