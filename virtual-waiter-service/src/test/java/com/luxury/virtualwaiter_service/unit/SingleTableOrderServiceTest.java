package com.luxury.virtualwaiter_service.service;

import com.luxury.virtualwaiter_service.dto.OrderItemDTO;
import com.luxury.virtualwaiter_service.dto.OrderRequestDTO;
import com.luxury.virtualwaiter_service.dto.StatusDTO;
import com.luxury.virtualwaiter_service.model.*;
import com.luxury.virtualwaiter_service.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SingleTableOrderServiceTest {

    @InjectMocks
    private SingleTableOrderService singleTableOrderService;

    @Mock
    private SingleTableOrderRepository singleTableOrderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private SelectedAddOnRepository selectedAddOnRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    private SingleTableOrder singleTableOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        singleTableOrder = SingleTableOrder.builder()
                .orderId(1L)
                .tableId(1)
                .dateTime(LocalDateTime.now())
                .status(new Status())
                .totalPrice(100.0)
                .build();
    }

    @Test
    void addSingleTableOrder_shouldCreateOrderSuccessfully() {
        // Prepare the test data
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setTableId("1");
        orderRequestDTO.setOrderTotal("100.0");
        orderRequestDTO.setOrderItemList(Collections.singletonList(new OrderItemDTO("1", "Note", "2000", "2", Collections.emptyList())));

        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status()));
        when(singleTableOrderRepository.save(any())).thenReturn(singleTableOrder);
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(new MenuItem()));

        // Call the method
        ResponseEntity<SingleTableOrder> response = singleTableOrderService.addSingleTableOrder(orderRequestDTO);

        // Verify the results
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(singleTableOrder, response.getBody());
        verify(singleTableOrderRepository, times(1)).save(any(SingleTableOrder.class));
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void updateStatus_shouldUpdateOrderStatusSuccessfully() {
        // Prepare the test data
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setOrderId(1L);
        statusDTO.setStatus("Completed");

        // Create a Status object with the correct status name
        Status completedStatus = new Status();
        completedStatus.setStatusName("Completed");

        when(singleTableOrderRepository.findById(1L)).thenReturn(Optional.of(singleTableOrder));
        when(statusRepository.findByStatusName("Completed")).thenReturn(completedStatus);

        // Call the method
        ResponseEntity<SingleTableOrder> response = singleTableOrderService.updateStatus(statusDTO);

        // Verify the results
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(singleTableOrder, response.getBody());
        assertEquals("Completed", response.getBody().getStatus().getStatusName()); // This should now pass
        verify(singleTableOrderRepository, times(1)).save(singleTableOrder);
    }

    @Test
    void updateStatus_orderNotFound() {
        // Prepare the test data
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setOrderId(999L);
        statusDTO.setStatus("Completed");

        when(singleTableOrderRepository.findById(999L)).thenReturn(Optional.empty());

        // Call the method
        ResponseEntity<SingleTableOrder> response = singleTableOrderService.updateStatus(statusDTO);

        // Verify the results
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void updateStatus_statusNotFound() {
        // Prepare the test data
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setOrderId(1L);
        statusDTO.setStatus("NonExistentStatus");

        when(singleTableOrderRepository.findById(1L)).thenReturn(Optional.of(singleTableOrder));
        when(statusRepository.findByStatusName("NonExistentStatus")).thenReturn(null);

        // Call the method
        ResponseEntity<SingleTableOrder> response = singleTableOrderService.updateStatus(statusDTO);

        // Verify the results
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
