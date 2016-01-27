package com.ncubo.extensibilidad.cliente.main;

import java.io.IOException;

import com.ncubo.extensibilidad.cliente.librerias.Configuracion;

public class Launcher {

	public static void main(String[] args) throws IOException 
	{
		System.out.println( new Configuracion().urlNimbus() );
	}
	
}
