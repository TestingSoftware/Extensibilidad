package com.ncubo.extensibilidad;

import javax.jms.JMSException;
import com.ncubo.extensibilidad.server.ConectorActiveMQ;

public class Run {

	public static void main(String[] args) throws JMSException {
		ConectorActiveMQ conectorActiveMQ = new ConectorActiveMQ("tcp://10.1.3.48:61616");
		for(int i = 0; i < 10; i++)
			conectorActiveMQ.enviarMensaje("PRODUCTO", "Hola");
		System.out.println(conectorActiveMQ.hayNuevo("PRODUCTO"));
	}

}
