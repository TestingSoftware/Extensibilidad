package com.ncubo.extensibilidad.server;

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

public class ConectorActiveMQ 
{
	final protected static String NOMBRE_COLA_PRODUCTO = "PRODUCTO";
	final protected static String NOMBRE_COLA_PEDIDO = "PEDIDO";
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
	
	public void escucharProducto(final String topic) throws JMSException
	{
		Thread hilo = new Thread()
				{
			public void run()
			{
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(cadenaConexion);
				 
				Connection connection;
				try {
					connection = connectionFactory.createConnection();
				
				connection.start();

				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		        Destination destination = session.createQueue(topic);
		        MessageConsumer consumer = session.createConsumer(destination);

		        while(true)
		        {
		        	Message message = consumer.receive();
		            if (message instanceof TextMessage) 
		            {
		                TextMessage textMessage = (TextMessage) message;
		                String text = textMessage.getText();
		                System.out.println("Received: " + text);
		            } 
		            else 
		            {
		                System.out.println("Received: " + message);
		            }
		        }
				} catch (JMSException e) {
					throw new RuntimeException(e);
				}
			}
				};
				hilo.start();
		
	}

	public void escuchar() throws JMSException
	{
		escucharProducto(NOMBRE_COLA_PRODUCTO);
		escucharProducto(NOMBRE_COLA_PEDIDO);
	}

       
}
