package com.example.backend.controller;

import com.example.backend.common.ApiResponse;
import com.example.backend.dto.Cart.AddToCartDto;
import com.example.backend.dto.Cart.CartDto;
import com.example.backend.model.User;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class cartController {

    @Autowired
    private CartService cartService;

    @Autowired

    private AuthenticationService authenticationService;

    // postar o carrinho api

    @PostMapping("/add")
    // @PathVariable é utilizado quando o valor da variável é passada diretamente na URL
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) {
        // autenticar o token
        authenticationService.authenticate(token);


        User user = authenticationService.getUser(token);


        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Adicionar no carrinho"), HttpStatus.CREATED) ;
    }


    @GetMapping("/")
   public ResponseEntity<CartDto> getCartItens(@RequestParam("token")String token){

       // autenticar o token
       authenticationService.authenticate(token);


       User user = authenticationService.getUser(token);

         // retorna o carrinho do itens
        CartDto cartDto = cartService.listCartItens(user);
       return new ResponseEntity<>(cartDto, HttpStatus.OK);

   }
   // delete um carrinho do item do usuário
   @DeleteMapping("/delete/{cartItemId}")

    public ResponseEntity<ApiResponse> deleteCartItem (@PathVariable("cartItemId")Integer itemId, @RequestParam("token") String token){

       authenticationService.authenticate(token);
       User user = authenticationService.getUser(token);


       cartService.deleteCartItem(itemId, user);

       return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item do Produto excluído com sucesso"), HttpStatus.OK) ;

   }
    }

