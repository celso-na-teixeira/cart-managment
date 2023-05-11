package com.microservice.cartmanagment.service;


import com.microservice.cartmanagment.domain.Item;
import java.util.List;

public interface CartService {

  Item addItem(final Item item);

  void removeItem(final Long itemId) throws Exception;

  Double getTotal() throws Exception;

  List<Item> getItems() throws Exception;
}
