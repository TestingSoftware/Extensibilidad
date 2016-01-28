package com.ncubo.extensibilidad.cliente.librerias;

import java.util.List;

import com.google.gson.Gson;

public class Producto {
	
	private String codigo;
	private String descripcion;
	private int cantidad;
	private String nombre;
	private double precio;
	private List<String> imagenes;
	
	public Producto(String codigo)
	{
		this.codigo = codigo;
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	public int getCantidad()
	{
		return cantidad;
	}
	public void setCantidad(int cantidad)
	{
		this.cantidad = cantidad;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public double getPrecio()
	{
		return precio;
	}
	public void setPrecio(double precio)
	{
		this.precio = precio;
	}
	public List<String> getImagenes()
	{
		return imagenes;
	}
	public void setImagenes(List<String> imagenes)
	{
		this.imagenes = imagenes;
	}
	public String getDescripcion() 
	{
		return descripcion;
	}
	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}
	@Override
	public String toString() 
	{
		return new Gson().toJson(this);
	}

}
