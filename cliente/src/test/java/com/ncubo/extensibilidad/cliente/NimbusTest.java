package com.ncubo.extensibilidad.cliente;

import java.io.IOException;
import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.cliente.librerias.Nimbus;
import com.ncubo.extensibilidad.cliente.librerias.Producto;

public class NimbusTest {

	@Test
	public void crear() throws IOException, JMSException
	{
		Producto producto = new Producto("1");
		producto.setNombre("c");
		producto.setCantidad(1);
		producto.setDescripcion("des");
		producto.setPrecio(3);
		
		Nimbus nimbus = new Nimbus();
		nimbus.crear("TEST", producto);
			
		Assert.assertEquals(producto.toString(), nimbus.hayNuevo("TEST"));
		
	}
	

}
