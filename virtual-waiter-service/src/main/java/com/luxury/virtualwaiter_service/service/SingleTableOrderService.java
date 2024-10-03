package com.luxury.virtualwaiter_service.service;

import com.luxury.virtualwaiter_service.dto.OrderItemDTO;
import com.luxury.virtualwaiter_service.dto.OrderRequestDTO;
import com.luxury.virtualwaiter_service.model.*;
import com.luxury.virtualwaiter_service.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SingleTableOrderService {

    private final SingleTableOrderRepository singleTableOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final SelectedAddOnRepository selectedAddOnRepository;
    private final AddOnRepository addOnRepository;
    private final StatusRepository statusRepository;

    public static final Logger LOG = LoggerFactory.getLogger(SingleTableOrderService.class);

    @Autowired
    public SingleTableOrderService(SingleTableOrderRepository singleTableOrderRepository,
                                   OrderItemRepository orderItemRepository,
                                   SelectedAddOnRepository selectedAddOnRepository,
                                   AddOnRepository addOnRepository,
                                   StatusRepository statusRepository) {
        this.singleTableOrderRepository = singleTableOrderRepository;
        this.orderItemRepository = orderItemRepository;
        this.selectedAddOnRepository = selectedAddOnRepository;
        this.addOnRepository = addOnRepository;
        this.statusRepository = statusRepository;
    }

    public ResponseEntity<List<SingleTableOrder>> getAllSingleTableOrders() {
        List<SingleTableOrder> singleTableOrders = singleTableOrderRepository.findAll();
        return ResponseEntity.ok(singleTableOrders);
    }

    @Transactional
    public ResponseEntity<SingleTableOrder> addSingleTableOrder(OrderRequestDTO orderRequestDTO) {

        // Create a new SingleTableOrder entity
        SingleTableOrder newOrder = SingleTableOrder.builder()
                .tableId(Integer.parseInt(orderRequestDTO.getTableId()))
                .dateTime(LocalDateTime.now())
                .status(statusRepository.findById(1L).get())
                .totalPrice(Double.parseDouble(orderRequestDTO.getOrderTotal()))// Assuming status always exists
                .build();

        // Save the new order
        SingleTableOrder savedOrder = singleTableOrderRepository.save(newOrder);

        //Create a list to hold OrderItem entities
        List<OrderItem> orderItems = new ArrayList<>();

        //Create OrderItem entities from the orderItemList in the DTO
        for (OrderItemDTO itemDTO : orderRequestDTO.getOrderItemList()) {
            OrderItem orderItem = OrderItem.builder()
                    .menuItem(
                            MenuItem.builder()
                                    .menuItemId(Long.parseLong(itemDTO.getMenuItemId()))
                                    .build()
                    )
                    .specialNote(itemDTO.getSpecialNote())
                    .quantity(Integer.parseInt(itemDTO.getQuantity()))
                    .totalPrice(Double.parseDouble(itemDTO.getTotalPrice()))
                    .singleTableOrder(savedOrder)
                    .build();

            //Save the OrderItem
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem); // Add to the list of saved items

            //Save each selected add-on
            for (String addonId : itemDTO.getAddonList()) {
                SelectedAddOn selectedAddOn = SelectedAddOn.builder()
                        .addOn(
                                AddOn.builder()
                                        .addOnId(Long.parseLong(addonId))
                                        .build()
                        )
                        .orderItem(savedOrderItem) // Associate with the saved order item
                        .build();

                selectedAddOnRepository.save(selectedAddOn); // Save the selected add-on
            }
        }

        savedOrder.setOrderItems(orderItems);

        SingleTableOrder newOrderSaved = singleTableOrderRepository.findById(savedOrder.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        LOG.info(newOrderSaved.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderSaved);
    }
}
