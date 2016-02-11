package com.ncubo.extensibilidad.cliente;

import java.io.IOException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.ncubo.extensibilidad.cliente.librerias.Nimbus;
import com.ncubo.extensibilidad.cliente.librerias.Producto;

public class NimbusTest {
	@Test
	public void crear() throws IOException{
		Producto producto = new Producto("1");
		producto.setNombre("c");
		producto.setCantidad(1);
		producto.setDescripcion("des");
		producto.setPrecio(3);
		
		Nimbus nimbus = new Nimbus();
		nimbus.crear(producto);
		
		AssertJUnit.assertEquals(producto, producto);
		
	}
	
	@Test
	public void hayNuevo() throws IOException{
		Producto producto = new Producto("1");
		Gson gson = new Gson();
		producto.setNombre("c");
		producto.setCantidad(1);
		producto.setDescripcion("des");
		producto.setPrecio(3);
		
		Nimbus nimbus = new Nimbus();
		nimbus.hayNuevo(producto);
		
		AssertJUnit.assertEquals(producto, producto);
	}
}
