package com.microservice.cartmanagment.cartServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.microservice.cartmanagment.dao.ItemEntity;
import com.microservice.cartmanagment.domain.Item;
import com.microservice.cartmanagment.exception.CartException;
import com.microservice.cartmanagment.repository.CartRepository;
import com.microservice.cartmanagment.repository.ItemMapper;
import com.microservice.cartmanagment.service.CartServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServiceUnitTest {
  @Mock
  private CartRepository cartRepository;
  @Mock
  private ItemMapper itemMapper;
  @InjectMocks
  private CartServiceImpl cartService;

  @BeforeEach
  void setUp(){
    MockitoAnnotations.openMocks(this);
  }
  @Test
  public void addItem_WhenItemDoesNotExist_ShouldSaveItem(){
    Item item = new Item("Vinho", "Vinho do Douro", 3.99, 2);
    item.setId(1L);
    ItemEntity itemEntity = new ItemEntity("Vinho", "Vinho do Douro", 3.99, 2);
    itemEntity.setId(1L);
    when(itemMapper.toEntity(item)).thenReturn(itemEntity);
    when(cartRepository.findById(item.getId())).thenReturn(Optional.empty());
    when(cartRepository.save(itemEntity)).thenReturn(itemEntity);
    when(itemMapper.toDomain(itemEntity)).thenReturn(item);

    Item itemCreated = cartService.addItem(item);

    assertNotNull(itemCreated);
    assertEquals(item, itemCreated);
    verify(cartRepository, times(1)).findById(item.getId());
    verify(cartRepository, times(1)).save(itemEntity);

  }
  @Test
  void addItem_WhenItemExists_shouldThrowCartException(){
    Item item = new Item("Vinho", "Vinho do Douro", 3.99, 2);
    item.setId(1L);

    when(cartRepository.findById(item.getId())).thenReturn(Optional.of(new ItemEntity()));

    assertThrows(CartException.class, () -> cartService.addItem(item));
    verify(cartRepository, times(1)).findById(item.getId());
    verify(cartRepository, never()).save(any(ItemEntity.class));
  }

  @Test
  public void removeItem_WhenItemExists_ShouldDeleteItem(){
    Long itemId = 1L;
    ItemEntity itemEntity = new ItemEntity();
    when(cartRepository.findById(itemId)).thenReturn(Optional.of(itemEntity));

    cartService.removeItem(itemId);

    verify(cartRepository, times(1)).findById(itemId);
    verify(cartRepository, times(1)).delete(itemEntity);
  }

  @Test
  public void removeItem_WhenItemDoesNotExists_shouldThrowCartException(){
    Long itemId = 1L;
    when(cartRepository.findById(itemId)).thenReturn(Optional.empty());

    assertThrows(CartException.class, () -> cartService.removeItem(itemId));
    verify(cartRepository, times(1)).findById(itemId);
    verify(cartRepository, never()).delete(any(ItemEntity.class));
  }
  @Test
  public void getTotal_WhenCartRepositoryReturnsNull_ShouldReturnZero(){
    when(cartRepository.getTotal()).thenReturn(null);

    Double total = cartService.getTotal();

    assertEquals(0d, total);
    verify(cartRepository, times(1)).getTotal();
  }

  @Test
  public void getTotal_WhenCartRepository_ReturnsValue_ShouldReturnValue(){
    Double expectedTotal = 10.0;
    when(cartRepository.getTotal()).thenReturn(expectedTotal);

    Double total = cartService.getTotal();

    assertEquals(expectedTotal, total);
    verify(cartRepository, times(1)).getTotal();
  }
}
