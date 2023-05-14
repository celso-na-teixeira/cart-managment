package com.microservice.cartmanagment.service;

import com.microservice.cartmanagment.dao.ItemEntity;
import com.microservice.cartmanagment.domain.Item;
import com.microservice.cartmanagment.exception.CartException;
import com.microservice.cartmanagment.repository.CartRepository;
import com.microservice.cartmanagment.repository.ItemMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private ItemMapper itemMapper;

  /**
   * For testing purpose only
   *
   * @param cartRepository
   * @param itemMapper
   */
  public CartServiceImpl(CartRepository cartRepository, ItemMapper itemMapper) {
    this.cartRepository = cartRepository;
    this.itemMapper = itemMapper;
  }

  @Override
  public Item addItem(final Item item) {
    logger.info("Adding item.");
    ItemEntity itemEntity = itemMapper.toEntity(item);

    if (cartRepository.findById(item.getId()).isPresent()) {
      throw new CartException("The item already exists.");
    }

    try {
      ItemEntity itemEntitySaved = cartRepository.save(itemEntity);
      return itemMapper.toDomain(itemEntitySaved);
    } catch (DataAccessException exception) {
      logger.error("Failed to add item to cart.", exception);
      throw new CartException("Unable to add item to cart. Please try again later.", exception);
    } catch (Exception exception) {
      logger.error("Unexpected error while adding item to cart.", exception);
      throw new CartException("An unexpected error occurred. Please try again later.", exception);
    }
  }

  @Override
  public void removeItem(final Long itemId) {
    logger.info("Deleting item.");
    ItemEntity item = cartRepository.findById(itemId)
        .orElseThrow(() -> new CartException("Item not found."));

    try {
      cartRepository.delete(item);
    } catch (DataAccessException exception) {
      logger.error("Failed to delete the item. Please try again later.", exception);
      throw new CartException("Unable to delete item from the cart. Please try again later.");
    } catch (Exception exception) {
      logger.error("Unexpected error while deleting the item.", exception);
      throw new CartException("An unexpected error occurred. Please try again later.", exception);
    }
  }

  /**
   * TODO change que query to calculate
   * @return
   */
  @Override
  public Double getTotal() {
    logger.info("Getting item total.");
    try {
      Double total = cartRepository.getTotal();
      return Objects.isNull(total) ? 0d : total;
    } catch (Exception exception) {
      logger.error("Unexpected error while getting the item total.", exception);
      throw new CartException("An unexpected error occurred. Please try again later.", exception);
    }
  }

  @Override
  public List<Item> getItems() {
    logger.info("Getting items.");
    try {
      return cartRepository.findAll().stream().map(itemMapper::toDomain).collect(Collectors.toList());
    } catch (Exception exception) {
      logger.error("Unexpected error while getting items.", exception);
      throw new CartException("An unexpected error occurred. Please try again later.", exception);
    }
  }
}
