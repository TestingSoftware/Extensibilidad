package com.ncubo.extensibilidad.cliente.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.ncubo.extensibilidad.cliente.librerias.Configuracion;
import com.ncubo.extensibilidad.cliente.librerias.Mapeo;

public class MapeoCSV {

	private String pathArchivo, pathFolder;
	private final String SALTO_LINEA = "\n";
	private final String NOMBRE_ARCHIVO = "volcado";
	
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
	
	public MapeoCSV(String pathFolder)
	{
		this.pathFolder = pathFolder;
		
		File f = new File(pathFolder);
		if (f.isDirectory())
		{
			this.pathArchivo = obtenerPathMasRecienteRespaldo();
		}
		else if (f.isFile())
		{
			this.pathArchivo = f.getName();
			this.pathFolder = f.getParent();
		}
	}
	
	
	public List<Mapeo> obtener() throws IOException
	{
		File file = new File(pathFolder + "/"+pathArchivo);
		if( !file.exists() )
		{
			throw new RuntimeException("El archivo "+file.getAbsolutePath()+" no existe.");
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
	
	public void crear(List<Mapeo> rsList) throws IOException 
	{
		FileWriter escritorArchivo = null;
		CSVPrinter csvPrinter = null;
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(SALTO_LINEA);
		
		if(numeroDeRespaldos() == new Configuracion().cantidadRespaldos())
		{
			eliminarArchivo(pathFolder + "/" + obtenerPathMasAntiguoRespaldo());
		}
		
		pathArchivo = NOMBRE_ARCHIVO + fechaActual() + ".csv";
		escritorArchivo = new FileWriter(pathFolder + "/" + pathArchivo);
		
		csvPrinter = new CSVPrinter(escritorArchivo, csvFileFormat);
		csvPrinter.printRecord((Object []) CABECERAS);
		
		for(Mapeo elemento: rsList)
		{
			List<String> camposCsv = new ArrayList<String>();
			camposCsv.add(elemento.getIdERP());
			camposCsv.add(elemento.getDescERP());
			camposCsv.add(elemento.getIdNimbus());
			camposCsv.add(elemento.getDescNimbus());
			csvPrinter.printRecord(camposCsv); 
		}
	
		escritorArchivo.flush();
		escritorArchivo.close();
		csvPrinter.close();
	}
	
	public int numeroDeRespaldos()
	{
		File dir = new File(pathFolder);
		String[] ficheros = dir.list();
		int respaldos = 0;
		
		if (ficheros != null)
		{
			for (int x=0;x<ficheros.length;x++)
			{
				if (ficheros[x].startsWith(NOMBRE_ARCHIVO))
				{
					respaldos++;
				}

			}
		}
		
		return respaldos;
	}
	
	private String obtenerPathMasAntiguoRespaldo()
	{
		File dir = new File(pathFolder);
		String[] ficheros = dir.list();
		
		if (ficheros != null)
		{
			for (int x=0;x<ficheros.length;x++)
			{
				if (ficheros[x].startsWith(NOMBRE_ARCHIVO))
				{
					return ficheros[x];
				}
			}
		}
		
		return null;
	}
	
	public String obtenerPathMasRecienteRespaldo()
	{
		File dir = new File(pathFolder);
		String[] ficheros = dir.list();
		
		if (ficheros != null)
		{
			for (int x=ficheros.length-1; x >= 0 ; x--)
			{
				if (ficheros[x].startsWith(NOMBRE_ARCHIVO))
				{
					return ficheros[x];
				}
			}
		}
		return null;
	}
	
	private void eliminarArchivo(String path)
	{
		File fichero = new File(path);
		if ( !fichero.delete() )
		{
			throw new RuntimeException("El archivo "+fichero.getAbsolutePath()+" no puede ser borrado.");
		}
		
	}
	
	private String fechaActual()
	{
		Calendar fecha = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
		return sdf.format(fecha.getTime());
	}
}
