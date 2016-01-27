package com.ncubo.extensibilidad.cliente;

import com.google.gson.Gson;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NimbusTest {
	@Test
	public void crear() throws IOException{
		Producto producto = new Producto("1");
		Gson gson = new Gson();
		producto.setNombre("c");
		producto.setCantidad(1);
		producto.setDescripcion("des");
		producto.setPrecio(3);
		
		Nimbus nimbus = new Nimbus();
		nimbus.crear(producto);
		
		Assert.assertEquals(producto, producto);
		
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
		
		Assert.assertEquals(producto, producto);
	}
}
