package com.microservice.cartmanagment.controller;

import com.microservice.cartmanagment.domain.Item;
import com.microservice.cartmanagment.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @GetMapping("/items")
  public ResponseEntity<List<Item>> getItems(){
    return ResponseEntity.ok(cartService.getItems());
  }

  @PostMapping("/items")
  public ResponseEntity<String> addItem(final @RequestBody Item item){
    cartService.addItem(item);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<String> removeItem(final @PathVariable Long itemId){
    cartService.removeItem(itemId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
  @GetMapping("/total")
  public ResponseEntity<Double> getTotal(){
    return ResponseEntity.ok(cartService.getTotal());
  }



}
