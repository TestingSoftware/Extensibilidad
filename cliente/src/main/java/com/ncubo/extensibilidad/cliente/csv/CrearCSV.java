package com.ncubo.extensibilidad.cliente.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ncubo.extensibilidad.cliente.librerias.Mapeo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CrearCSV {
	private static final String PATH_VOLCADO_DB = "src/main/resources/volcadoDB.csv";
	private final String SALTO_LINEA = "\n";
	private final Object [] CABECERAS = {"ID ERP","ID NIMBUS","Desc P ERP","Desc P Nimbus"};
	
	public void crearCSV(LinkedList<Mapeo> rsList) throws IOException 
	{
		FileWriter escritorArchivo = null;
		CSVPrinter csvPrinter = null;
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(SALTO_LINEA);
		escritorArchivo = new FileWriter(PATH_VOLCADO_DB);
		csvPrinter = new CSVPrinter(escritorArchivo, csvFileFormat);
		csvPrinter.printRecord(CABECERAS);
		
		for(Mapeo elemento: rsList)
		{
			List<String> camposCsv = new ArrayList<String>();
			camposCsv.add(elemento.getIdERP());
			camposCsv.add(elemento.getIdNimbus());
			camposCsv.add(elemento.getDescERP());
			camposCsv.add(elemento.getDescNimbus());
			csvPrinter.printRecord(camposCsv); 
		}
	
		escritorArchivo.flush();
		escritorArchivo.close();
		csvPrinter.close();
	}
}
