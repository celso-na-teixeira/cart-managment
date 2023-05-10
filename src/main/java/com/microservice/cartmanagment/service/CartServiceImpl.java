package com.microservice.cartmanagment.service;

import com.microservice.cartmanagment.dao.ItemEntity;
import com.microservice.cartmanagment.domain.Item;
import com.microservice.cartmanagment.exception.CartException;
import com.microservice.cartmanagment.repository.CartRepository;
import com.microservice.cartmanagment.repository.ItemMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private ItemMapper itemMapper;

  /**
   * For testing purpose only
   * @param cartRepository
   * @param itemMapper
   */
  public CartServiceImpl(CartRepository cartRepository, ItemMapper itemMapper) {
    this.cartRepository = cartRepository;
    this.itemMapper = itemMapper;
  }

  @Override
  public void addItem(final Item item) throws CartException {
    logger.info("Adding item.");
    try{
      ItemEntity itemEntity = itemMapper.toEntity(item);
      cartRepository.save(itemEntity);
    }catch (DataAccessException exception){
      logger.error("Failed to add item to cart.", exception);
      throw new CartException("Unable to add item to cart. Please try again later.");
    }catch (Exception exception){
      logger.error("Unexpected error while adding item to cart.", exception);
      throw new CartException("An unexpected error occurred. Please try again later.");
    }
  }

  @Override
  public void removeItem(final Long itemId) {
    logger.info("Deleting item.");
    try{
      Optional<ItemEntity> item = cartRepository.findById(itemId);
      if (item != null && Objects.isNull(item.get())){
        logger.error("Failed to delete item. Item with the Id provided not found.");
        throw new CartException("Failed to find the item with the Id provided.");
      }
      cartRepository.delete(item.get());
    }catch (DataAccessException exception){
      logger.error("Failed to delete the item. Please try again later.", exception);
      throw new CartException("Unable to delete item from the cart. Please try again later.");
    }catch (CartException exception){
      logger.error("Unexpected error while deleting the item.", exception);
      throw new CartException("An unexpected error occurred. Please try again later.");
    }
  }

  @Override
  public Double getTotal() {
    logger.info("Getting item total.");
    try{
      return cartRepository.getTotal();
    } catch (CartException exception){
      logger.error("Unexpected error while getting the item total.");
      throw new CartException("An unexpected error occurred. Please try again later.");
    }
  }

  @Override
  public List<Item> getItems() {
    logger.info("Getting items.");
    try{
      return cartRepository.findAll().stream().map(itemMapper::doDomain).collect(Collectors.toList());
    }catch (CartException exception){
      logger.error("Unexpected error while getting items.");
      throw new CartException("An unexpected error occurred. Please try again later.");
    }
  }
}
