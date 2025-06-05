package com.br.teste.order.service;

import com.br.teste.order.model.Order;

public class OrderService {

    public double calculateTotal(Order order) {
        return order.getItems().stream()
                .mapToDouble(item -> item.getTotal())
                .sum();
    }
}
