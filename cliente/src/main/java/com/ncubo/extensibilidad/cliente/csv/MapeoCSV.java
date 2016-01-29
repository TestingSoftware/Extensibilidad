package com.ncubo.extensibilidad.cliente.csv;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class MapeoCSV {
	private String pathArchivo;
	private static final String ID_ERP = "ID ERP";
	private static final String ID_NIMBUS = "ID NIMBUS";
	private static final String DESC_P_ERP = "Desc P ERP";
	private static final String DESC_P_NIMBUS = "Desc P Nimbus";
	private final String [] CABECERAS1 = {ID_ERP,ID_NIMBUS,DESC_P_ERP,};
	
	private final String SALTO_LINEA = "\n";
	private final Object [] CABECERAS = {"ID ERP","ID NIMBUS","Desc P ERP","Desc P Nimbus"};
	
	public MapeoCSV(String pathArchivo){
		this.pathArchivo = pathArchivo;
	}
	
	public  MapeoCSV(){
		
	}
	public void crear(LinkedList<Mapeo> rsList) throws IOException 
	{
		FileWriter escritorArchivo = null;
		CSVPrinter csvPrinter = null;
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(SALTO_LINEA);
		escritorArchivo = new FileWriter(new Configuracion().pathVolcadoDb());
		csvPrinter = new CSVPrinter(escritorArchivo, csvFileFormat);
		csvPrinter.printRecord(CABECERAS);
		
		for(Mapeo elemento: rsList)
		{
			List<String> camposCsv = new ArrayList<String>();
			camposCsv.add(elemento.getIdSAP());
			camposCsv.add(elemento.getIdNimbus());
			camposCsv.add(elemento.getDescNimbus());
			camposCsv.add(elemento.getDescSAP());
			csvPrinter.printRecord(camposCsv); 
		}
	
		escritorArchivo.flush();
		escritorArchivo.close();
		csvPrinter.close();
	}
	
	
	
	public LinkedList<Mapeo> ObtenerMapeos() throws IOException
	{
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(CABECERAS1);

		LinkedList<Mapeo> mapeos = new LinkedList<Mapeo>();
		fileReader = new FileReader(pathArchivo);
		csvFileParser = new CSVParser(fileReader, csvFileFormat);
		
		List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
		for (int i = 1; i < csvRecords.size(); i++)
		{
			CSVRecord record = csvRecords.get(i);
			Mapeo mapeo = new Mapeo(record.get(0), record.get(1), 
					record.get(DESC_P_ERP), record.get(3));
			
			if(!mapeo.getIdSAP().equals("") && mapeo.getIdNimbus().equals(""))
			{
				mapeo = Mapeo.erp(mapeo.getIdSAP(), mapeo.getDescSAP());
			}
			if(mapeo.getIdSAP().equals("") && !mapeo.getIdNimbus().equals(""))
			{
				mapeo = Mapeo.nimbus(mapeo.getIdNimbus(), mapeo.getDescNimbus());
			}
			
			mapeos.add(mapeo);
		}
		
		fileReader.close();
		csvFileParser.close();
		return mapeos;
	}
}
