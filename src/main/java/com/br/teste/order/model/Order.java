package com.br.teste.order.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
