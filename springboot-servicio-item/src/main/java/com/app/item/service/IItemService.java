package com.app.item.service;

import java.util.List;

import com.app.commons.models.entity.Producto;
import com.app.item.models.Item;

public interface IItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	public Producto save(Producto producto);
	public Producto update(Producto producto, Long id);
	public void delete(Long id);
}
