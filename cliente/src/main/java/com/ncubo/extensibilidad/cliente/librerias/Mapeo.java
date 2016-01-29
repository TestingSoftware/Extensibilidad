package com.ncubo.extensibilidad.cliente.librerias;

import com.google.gson.Gson;

public class Mapeo 
{
	private String idERP;
	private String idNimbus;
	private String descERP;
	private String descNimbus;
	
	public String getIdERP() {
		return idERP;
	}

	public String getIdNimbus() {
		return idNimbus;
	}

	public String getDescERP() {
		return descERP;
	}

	public String getDescNimbus() {
		return descNimbus;
	}
	public Mapeo(String idERP, String descERP, String idNimbus, String descNimbus) 
	{
		super();
		this.idERP = idERP;
		this.idNimbus = idNimbus;
		this.descERP = descERP;
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
