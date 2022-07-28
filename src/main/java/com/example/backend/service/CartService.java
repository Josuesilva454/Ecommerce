package com.example.backend.service;


import com.example.backend.dto.Cart.AddToCartDto;
import com.example.backend.dto.Cart.CartDto;
import com.example.backend.dto.Cart.CartItemDto;
import com.example.backend.exceptions.CustomException;
import com.example.backend.model.Cart;
import com.example.backend.model.Product;
import com.example.backend.model.User;
import com.example.backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;


    public void addToCart(AddToCartDto addToCartDto, User user) {

        // vilidate se o produto for id inválido

        Product product = productService.findById(addToCartDto.getProductId());


        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        // salvar no carrinho
        cartRepository.save(cart);
    }

    public CartDto listCartItens(User user) {

     final List<Cart> cartList =  cartRepository.findAllByUserOrderByCreatedDateDesc(user);

     List<CartItemDto> cartItens = new ArrayList<>();

     double totalCusto = 0;
      for (Cart cart: cartList){
          CartItemDto cartItemDto = new CartItemDto(cart);
          totalCusto += cartItemDto.getQuantity() * cart.getProduct().getPrice();
          cartItens.add(cartItemDto);
      }

      CartDto cartDto = new CartDto();
      cartDto.setTotalCusto(totalCusto);
      cartDto.setCartItens(cartItens);

      return cartDto;
    }


    public void deleteCartItem(Integer cartItemId, User user) {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()){
            throw new CustomException("o ID do item do carrinho é inválido" + cartItemId);

        }
        Cart cart = optionalCart.get();

        if (cart.getUser() != user){
            throw  new CustomException("Item do carrinho não pertence ao usuário" +cartItemId);
        }
        cartRepository.delete(cart);
    }
}
