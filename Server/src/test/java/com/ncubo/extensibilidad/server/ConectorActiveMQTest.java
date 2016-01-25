package com.ncubo.extensibilidad.server;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
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
