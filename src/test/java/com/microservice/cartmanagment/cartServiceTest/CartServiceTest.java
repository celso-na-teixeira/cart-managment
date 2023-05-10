package com.microservice.cartmanagment.cartServiceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import com.microservice.cartmanagment.dao.ItemEntity;
import com.microservice.cartmanagment.domain.Item;
import com.microservice.cartmanagment.repository.CartRepository;
import com.microservice.cartmanagment.repository.ItemMapper;
import com.microservice.cartmanagment.repository.ItemMapperImpl;
import com.microservice.cartmanagment.service.CartService;
import com.microservice.cartmanagment.service.CartServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  testing the behavior of the CartService
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {
  private CartService cartService;

  @Mock
  private CartRepository cartRepository;

  @Mock
  private ItemMapper itemMapper;

  @Before
  public void setUp(){
    MockitoAnnotations.openMocks(this);
    cartService = new CartServiceImpl(cartRepository, itemMapper);
    itemMapper = new ItemMapperImpl();
  }



  @Test
  public void testAddItem(){
    Item item = new Item("Vinho", "Vinho do Douro", 3.99, 2);
    ItemEntity itemEntity = itemMapper.toEntity(item);
    when(cartRepository.findById(1L)).thenReturn(Optional.empty());
    when(cartRepository.save(itemEntity)).thenReturn(itemEntity);
    cartService.addItem(item);
    verify(cartRepository).save(itemEntity);
  }
  @Test
  public void testRemoveItem(){
    ItemEntity itemEntity = new ItemEntity("Queijo", "Queijo dos Açores", 10.5, 1);
    cartRepository.save(itemEntity);
    List<Item> items = cartService.getItems();
    cartService.removeItem(1L);
    ItemEntity deletedItemEntity = cartRepository.findById(1L).orElse(null);
    assertNull(deletedItemEntity);
  }

  @Test
  public void testGetTotal(){
    ItemEntity itemEntity = new ItemEntity("Vinho", "Vinho do Douro", 3.99, 2);
    ItemEntity itemEntity2 = new ItemEntity("Queijo", "Queijo dos Açores", 10.5, 1);
    cartRepository.saveAll(Arrays.asList(itemEntity, itemEntity2));
    List<Item> items = cartService.getItems();
    Double total = cartService.getTotal();
    assertEquals(Double.valueOf(14.49), total);
  }

  @After
  public void cleanUp(){
    cartRepository.deleteAll();
  }

  @Test
  public void testGetItems(){
    ItemEntity itemEntity = new ItemEntity("Vinho", "Vinho do Douro", 3.99, 2);
    ItemEntity itemEntity2 = new ItemEntity("Queijo", "Queijo dos Açores", 10.5, 1);
    cartRepository.saveAll(Arrays.asList(itemEntity, itemEntity2));
    List<Item> items =  cartService.getItems();
    assertNotNull(items);
    assertEquals(items.size(), 2);
  }

}
