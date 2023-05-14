package com.microservice.cartmanagment.controller;

import com.microservice.cartmanagment.domain.Item;
import com.microservice.cartmanagment.exception.CartException;
import com.microservice.cartmanagment.service.CartService;
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
  public ResponseEntity<String> getItems(){
   try{
     return ResponseEntity.ok(cartService.getItems().toString());
   }catch (Exception e){
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
   }
  }


  @PostMapping("/items")
  public ResponseEntity<String> addItem(final @RequestBody Item item){
    try{
      Item itemCreated = cartService.addItem(item);
      return ResponseEntity.status(HttpStatus.CREATED).body(itemCreated.toString());
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<String> removeItem(final @PathVariable Long itemId){
    try {
      cartService.removeItem(itemId);
      return ResponseEntity.ok("Item deleted.");
    } catch (CartException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
  @GetMapping("/total")
  public ResponseEntity<String> getTotal(){
    try{
      return ResponseEntity.ok(cartService.getTotal().toString());
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }



}
