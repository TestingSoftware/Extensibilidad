package com.ncubo.extensibilidad;

import javax.jms.JMSException;
import com.ncubo.extensibilidad.server.ConectorActiveMQ;

public class Run {

	public static void main(String[] args) throws JMSException {
		ConectorActiveMQ conectorActiveMQ = new ConectorActiveMQ("tcp://localhost:61616");
		for(int i = 0; i < 10; i++)
			conectorActiveMQ.enviarMensaje("PRODUCTO", i+"");
		System.out.println(conectorActiveMQ.hayNuevo("PRODUCTO"));
	}

}
