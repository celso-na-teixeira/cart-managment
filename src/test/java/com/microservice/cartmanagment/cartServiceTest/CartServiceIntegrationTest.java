package com.microservice.cartmanagment.cartServiceTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.microservice.cartmanagment.dao.ItemEntity;
import com.microservice.cartmanagment.domain.Item;
import com.microservice.cartmanagment.repository.CartRepository;
import com.microservice.cartmanagment.repository.ItemMapper;
import com.microservice.cartmanagment.service.CartServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


//@RunWith(MockitoJUnitRunner.class) // junit 4
@ExtendWith(MockitoExtension.class) // junit 5
public class CartServiceIntegrationTest {
  @Mock
  private CartRepository cartRepository;
  @Mock
  private ItemMapper itemMapper;
  @InjectMocks
  private CartServiceImpl cartService;

  @BeforeEach
  public void setUp(){
    MockitoAnnotations.openMocks(this);
  }
  @Test
  public void testAddItem(){
    Item item = new Item("Vinho", "Vinho do Douro", 3.99, 2);
    item.setId(1L);
    ItemEntity itemEntity = new ItemEntity("Vinho", "Vinho do Douro", 3.99, 2);
    itemEntity.setId(1L);

    when(cartRepository.findById(item.getId())).thenReturn(Optional.empty());
    when(itemMapper.toEntity(item)).thenReturn(itemEntity);
    when(cartRepository.save(itemEntity)).thenReturn(itemEntity);
    when(itemMapper.toDomain(itemEntity)).thenReturn(item);

    Item createdItem = cartService.addItem(item);

    assertNotNull(createdItem);
    assertEquals(item.getId(), createdItem.getId());
    assertEquals(item.getName(), createdItem.getName());
    verify(cartRepository, times(1)).findById(item.getId());
    verify(itemMapper, times(1)).toEntity(item);
    verify(cartRepository, times(1)).save(itemEntity);
    verify(itemMapper, times(1)).toDomain(itemEntity);
  }
  @Test
  public void testRemoveItem(){
    Long itemId = 1L;
    ItemEntity item = new ItemEntity();
    item.setId(itemId);
    when(cartRepository.findById(itemId)).thenReturn(Optional.of(item));

    cartService.removeItem(itemId);

    verify(cartRepository, times(1)).findById(itemId);
    verify(cartRepository, times(1)).delete(item);
  }
  @Test
  public void testGetTotal(){
    Double expectedTotal = 10d;
    when(cartRepository.getTotal()).thenReturn(expectedTotal);
    Double total = cartService.getTotal();

    assertEquals(expectedTotal, total);
    verify(cartRepository, times(1)).getTotal();
  }
  @Test
  public void testGetItems(){
    ItemEntity itemEntity = new ItemEntity("Vinho", "Vinho do Douro", 3.99, 2);
    itemEntity.setId(1L);
    Item item = new Item("Vinho", "Vinho do Douro", 3.99, 2);
    item.setId(1L);

    List<ItemEntity> entities = new ArrayList<>();
    entities.add(itemEntity);

    when(cartRepository.findAll()).thenReturn(entities);
    when(itemMapper.toDomain(itemEntity)).thenReturn(item);

    List<Item> items = cartService.getItems();

    assertNotNull(items);
    assertTrue(!items.isEmpty());
    verify(cartRepository, times(1)).findAll();
  }

}
