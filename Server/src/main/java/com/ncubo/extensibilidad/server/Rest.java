package com.ncubo.extensibilidad.server;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class Rest {

	final private String NOMBRE_COLA_PRODUCTO = "PRODUCTO";
	final private String NOMBRE_COLA_PEDIDO = "PEDIDO";
	@Autowired
	private ConectorActiveMQ conectorActiveMQ;
	private static final Logger logger = Logger.getLogger(Rest.class);

	@RequestMapping(value="/producto",  method=RequestMethod.POST)
	public @ResponseBody void crearProducto(
		@RequestBody String body) throws JMSException {

		conectorActiveMQ.enviarMensaje(NOMBRE_COLA_PRODUCTO, body);

	}
	
	@RequestMapping(value="/pedido",  method=RequestMethod.POST)
	public @ResponseBody void crearPedido(
		@RequestBody String body) throws JMSException {

		conectorActiveMQ.enviarMensaje(NOMBRE_COLA_PEDIDO, body);
	
	}

	
}
