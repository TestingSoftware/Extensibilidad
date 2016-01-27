package com.ncubo.extensibilidad.cliente.librerias;

public class Mapeo 
{
	private String idSAP;
	private String idNimbus;
	private String descSAP;
	private String descNimbus;
	
	public Mapeo(String idSAP, String idNimbus, String descSAP, String descNimbus) 
	{
		super();
		this.idSAP = idSAP;
		this.idNimbus = idNimbus;
		this.descSAP = descSAP;
		this.descNimbus = descNimbus;
	}
	
	public static Mapeo nimbus(String idNimbus, String descNimbus)
	{
		return new Mapeo(null, idNimbus, null, descNimbus);
	}

	public static Mapeo erp(String idSAP, String descSAP)
	{
		return new Mapeo(idSAP, null, descSAP, null);
	}

}
