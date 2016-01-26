package com.ncubo.extensibilidad.server;

import javax.jms.JMSException;

import org.testng.Assert;
import org.testng.annotations.Test;


public class ConectorActiveMQTest {

	@Test
	public void prueba1() throws JMSException{
		ConectorActiveMQ conectorActiveMQ = new ConectorActiveMQ("vm://localhost");//"tcp://10.1.3.48:61616/");
		conectorActiveMQ.enviarMensaje("test", "Hola mundo");
		Assert.assertEquals("Hola mundo", "Hola mundo");
	}

}
