package com.microservice.cartmanagment.repository;

import com.microservice.cartmanagment.dao.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JpaRepository
 */
public interface CartRepository extends JpaRepository<ItemEntity, Long> {

  @Query("SELECT SUM(price) FROM ItemEntity")
  Double getTotal();

}
