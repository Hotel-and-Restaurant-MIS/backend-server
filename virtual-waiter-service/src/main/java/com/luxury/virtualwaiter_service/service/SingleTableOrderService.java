package com.luxury.virtualwaiter_service.service;

import com.luxury.virtualwaiter_service.dto.OrderItemDTO;
import com.luxury.virtualwaiter_service.dto.OrderRequestDTO;
import com.luxury.virtualwaiter_service.dto.StatusDTO;
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
    private final StatusRepository statusRepository;
    private final MenuItemRepository menuItemRepository;

    public static final Logger LOG = LoggerFactory.getLogger(SingleTableOrderService.class);

    @Autowired
    public SingleTableOrderService(SingleTableOrderRepository singleTableOrderRepository,
                                   OrderItemRepository orderItemRepository,
                                   SelectedAddOnRepository selectedAddOnRepository,
                                   StatusRepository statusRepository,
                                   MenuItemRepository menuItemRepository) {
        this.singleTableOrderRepository = singleTableOrderRepository;
        this.orderItemRepository = orderItemRepository;
        this.selectedAddOnRepository = selectedAddOnRepository;
        this.statusRepository = statusRepository;
        this.menuItemRepository = menuItemRepository;
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

            MenuItem menuItem = menuItemRepository.findById(Long.parseLong(itemDTO.getMenuItemId())).orElse(null);
            orderItem.setMenuItem(menuItem);


            // Create and save each selected add-on
            List<SelectedAddOn> selectedAddOns = new ArrayList<>();
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
                selectedAddOns.add(selectedAddOn);
            }

            // Assuming MenuItem has a method to set selected add-ons
            orderItem.setSelectedAddOns(selectedAddOns);
        }


        savedOrder.setOrderItems(orderItems);

              List<OrderItem> orderItemList = orderItemRepository.findAllBySingleTableOrder(savedOrder);
        savedOrder.setOrderItems(orderItemList);

        LOG.info(savedOrder.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    public ResponseEntity<SingleTableOrder> updateStatus(StatusDTO statusDTO) {

        SingleTableOrder singleTableOrder = singleTableOrderRepository.findById(statusDTO.getOrderId()).orElse(null);
        //Check order is exist
        if (singleTableOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Status status = statusRepository.findByStatusName(statusDTO.getStatus());
        //check status is exist
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //update status
        singleTableOrder.setStatus(status);
        singleTableOrderRepository.save(singleTableOrder);

        return ResponseEntity.ok(singleTableOrder);

    }

}
