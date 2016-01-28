package com.ncubo.extensibilidad.cliente.librerias;

import com.google.gson.Gson;

public class Mapeo 
{
	private String idSAP;
	private String idNimbus;
	private String descSAP;
	private String descNimbus;
	
	public String getIdSAP() {
		return idSAP;
	}

	public String getIdNimbus() {
		return idNimbus;
	}

	public String getDescSAP() {
		return descSAP;
	}

	public String getDescNimbus() {
		return descNimbus;
	}
	public Mapeo(String idSAP, String descSAP, String idNimbus, String descNimbus) 
	{
		super();
		this.idSAP = idSAP;
		this.idNimbus = idNimbus;
		this.descSAP = descSAP;
		this.descNimbus = descNimbus;
	}
	
	public static Mapeo nimbus(String idNimbus, String descNimbus)
	{
		return new Mapeo(null, null, idNimbus, descNimbus);
	}

	public static Mapeo erp(String idSAP, String descSAP)
	{
		return new Mapeo(idSAP, descSAP, null, null);
	}

	@Override
	public String toString() {
		return  new Gson().toJson(this);
	}
	
	
}
