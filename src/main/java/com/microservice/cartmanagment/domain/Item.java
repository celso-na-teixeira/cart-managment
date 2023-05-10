package com.microservice.cartmanagment.domain;

import java.util.Objects;

public class Item {

  private Long id;
  private String name;
  private String description;
  private Double price;
  private Integer quantity;

  public Item() {
  }

  public Item(String name, String description, Double price, Integer quantity) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Item item = (Item) o;

    if (!id.equals(item.id)) {
      return false;
    }
    if (!name.equals(item.name)) {
      return false;
    }
    if (!Objects.equals(description, item.description)) {
      return false;
    }
    if (!Objects.equals(price, item.price)) {
      return false;
    }
    return Objects.equals(quantity, item.quantity);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
    return result;
  }
}
