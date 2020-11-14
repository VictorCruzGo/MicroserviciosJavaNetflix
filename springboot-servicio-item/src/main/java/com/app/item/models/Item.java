package com.app.item.models;

import com.app.commons.models.entity.Producto;

//Para generar el Item atraves del producto
public class Item {

	private Producto producto;
	private Integer cantidad;
	
	//Total del item (De esta forma el objeto item tendra un atributo con el total)	
	public Double getTotal() {
		return producto.getPrecio() * cantidad.doubleValue();
	}

	//Constructores
	public Item() {

	}

	public Item(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	//Propiedades
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}	
}
