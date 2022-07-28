package com.example.backend.dto.Cart;


import java.util.List;

public class CartDto {

    List<CartItemDto> cartItens;

    private double totalCusto;

    public List<CartItemDto> getCartItens() {
        return cartItens;
    }

    public void setCartItens(List<CartItemDto> cartItens) {
        this.cartItens = cartItens;
    }

    public double getTotalCusto() {
        return totalCusto;
    }

    public void setTotalCusto(double totalCusto) {
        this.totalCusto = totalCusto;
    }
}
