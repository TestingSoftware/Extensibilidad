package com.ncubo.extensibilidad.server;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConectorActiveMQ 
{
	
	private String cadenaConexion;
	
	public ConectorActiveMQ(String cadenaConexion)
	{
		this.cadenaConexion = cadenaConexion;
	}
	
	public void enviarMensaje(String cola, String mensaje) throws JMSException
	{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(cadenaConexion);
		Connection connection;
		connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(cola);

		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		TextMessage message = session.createTextMessage(mensaje);
		producer.send(message);
		
		session.close();
		connection.close();

	}
}
