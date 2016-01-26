package com.ncubo.extensibilidad;

import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.server.ConectorActiveMQ;


public class ConectorActiveMQTest {
	ConectorActiveMQ conectorActiveMQ = new ConectorActiveMQ("tcp://localhost:61616/");
	@Test
	/*Envia y consume*/
	public void prueba1() throws JMSException, InterruptedException{
		final String TOPIC = "test";
		conectorActiveMQ.enviarMensaje(TOPIC, "Hola mundo");
		
		Thread.sleep(1000);
		
		Assert.assertEquals(conectorActiveMQ.hayNuevo(TOPIC), "Hola mundo");
		
	}
	
	@Test
	/*Retorna "" si no hay mensajes */
	public void prueba2() throws JMSException{
		final String TOPIC = "test";
		Assert.assertEquals(conectorActiveMQ.hayNuevo(TOPIC), "");		
	}

}
