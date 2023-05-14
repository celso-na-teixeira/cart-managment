package com.microservice.cartmanagment.repository;

import com.microservice.cartmanagment.dao.ItemEntity;
import com.microservice.cartmanagment.domain.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
  Item toDomain(ItemEntity itemEntity);
  ItemEntity toEntity(Item item);
}
