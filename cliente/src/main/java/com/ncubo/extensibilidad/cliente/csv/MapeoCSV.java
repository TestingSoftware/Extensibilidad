package com.ncubo.extensibilidad.cliente.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class MapeoCSV {

	private String pathArchivo;
	
	private enum Columna 
	{
		ID_ERP (),
		DESC_P_ERP (),
		ID_NIMBUS (),
		DESC_P_NIMBUS ();
	}
	
	private final String [] CABECERAS = {Columna.ID_ERP.name() 
			,Columna.DESC_P_ERP.name()
			,Columna.ID_NIMBUS.name()
			,Columna.DESC_P_NIMBUS.name()};
	
	public MapeoCSV(String pathArchivo)
	{
		this.pathArchivo = pathArchivo;
	}
	
	public MapeoCSV()
	{
		
	}
	public void crear(List<Mapeo> list) throws IOException 
	{
		FileWriter escritorArchivo = null;
		CSVPrinter csvPrinter = null;
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
		escritorArchivo = new FileWriter(new Configuracion().pathVolcadoDb());
		csvPrinter = new CSVPrinter(escritorArchivo, csvFileFormat);
		csvPrinter.printRecord(CABECERAS);
		
		for(Mapeo elemento: list)
		{
			List<String> camposCsv = new ArrayList<String>();
			camposCsv.add(elemento.getIdERP());
			camposCsv.add(elemento.getIdNimbus());
			camposCsv.add(elemento.getDescNimbus());
			camposCsv.add(elemento.getDescERP());
			csvPrinter.printRecord(camposCsv); 
		}
	
		escritorArchivo.flush();
		escritorArchivo.close();
		csvPrinter.close();
	}
	
	
	public List<Mapeo> obtener() throws IOException
	{
		File file = new File(pathArchivo);
		if( !file.exists() )
		{
			throw new RuntimeException("El archivo no existe.");
		}
		
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(CABECERAS);

		List<Mapeo> mapeos = new ArrayList<Mapeo>();
		fileReader = new FileReader(file);
		csvFileParser = new CSVParser(fileReader, csvFileFormat);
		
		List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
		for ( int i = 1 ; i < csvRecords.size() ; i++ )
		{
			CSVRecord record = csvRecords.get(i);
			Mapeo mapeo = null;
			
			if( !record.get(Columna.ID_ERP.name()).equals("") && record.get(Columna.ID_NIMBUS.name()).equals("") )
			{
				mapeo = Mapeo.erp(record.get(Columna.ID_ERP.name()), record.get(Columna.DESC_P_ERP.name()));
			}
			else if( record.get(Columna.ID_ERP.name()).equals("") && !record.get(Columna.ID_NIMBUS.name()).equals("") )
			{
				mapeo = Mapeo.nimbus(record.get(Columna.ID_NIMBUS.name()), record.get(Columna.DESC_P_NIMBUS.name()));
			}
			else 
			{
				mapeo = new Mapeo(record.get(Columna.ID_ERP.name())
						, record.get(Columna.DESC_P_ERP.name())
						, record.get(Columna.ID_NIMBUS.name())
						, record.get(Columna.DESC_P_NIMBUS.name()));
			}
			
			mapeos.add(mapeo);
		}
		
		fileReader.close();
		csvFileParser.close();
		return mapeos;
	}
	
}