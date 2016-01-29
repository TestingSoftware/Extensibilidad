package com.ncubo.extensibilidad.cliente.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.ncubo.extensibilidad.cliente.librerias.Configuracion;


public class ConectorActiveMQ 
{
	private static final Logger logger = Logger.getLogger(ConectorActiveMQ.class);

	private String cadenaConexion;
	
	public ConectorActiveMQ()
	{
		this.cadenaConexion = new Configuracion().conexionCola();
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
		
		logger.info(String.format("A la cola %s se envia %s.", cola, message));
		
		session.close();
		connection.close();

	}

	public String hayNuevo(String cola) throws JMSException
	{
		String nuevo = "";
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(cadenaConexion);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(cola);
		MessageConsumer consumer = session.createConsumer(destination);
		
		Message message = consumer.receive(1000);
		if(message != null){
			TextMessage textMessage = (TextMessage) message;
			nuevo = textMessage.getText();
		}
		
		session.close();
		connection.close();
		return nuevo;
	}

}
