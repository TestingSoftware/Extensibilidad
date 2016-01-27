package com.ncubo.extensibilidad.cliente.librerias;

import java.util.List;

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

}
