package com.ncubo.extensibilidad;

import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ncubo.extensibilidad.server.ConectorActiveMQ;


public class ConectorActiveMQTest {

	@Test
	public void prueba1() throws JMSException{
		final String TOPIC = "test";
		ConectorActiveMQ conectorActiveMQ = new ConectorActiveMQ("tcp://localhost:61616/");
		conectorActiveMQ.enviarMensaje(TOPIC, "Hola mundo");
		Assert.assertEquals(conectorActiveMQ.hayNuevo(TOPIC), "Hola mundo1");
		
	}

}
