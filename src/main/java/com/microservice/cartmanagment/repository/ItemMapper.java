package com.microservice.cartmanagment.repository;

import com.microservice.cartmanagment.dao.ItemEntity;
import com.microservice.cartmanagment.domain.Item;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ItemMapper {
  Item doDomain(ItemEntity itemEntity);
  ItemEntity toEntity(Item item);
}
