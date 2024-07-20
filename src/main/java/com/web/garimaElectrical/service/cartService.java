package com.web.garimaElectrical.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.web.garimaElectrical.model.cart;
import com.web.garimaElectrical.model.cartItems;
import com.web.garimaElectrical.model.productModel;
import com.web.garimaElectrical.model.user;
import com.web.garimaElectrical.repository.CartItemRepository;
import com.web.garimaElectrical.repository.CartRepository;
import com.web.garimaElectrical.repository.productRepo;
import com.web.garimaElectrical.repository.userRepo;

@Service
public class cartService {

    @Autowired
    private userRepo userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private productRepo productRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    

    
    public void addItemToCart(String username, Integer productId, int quantity) {
        user user = userRepository.findByEmail(username).get();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        cart cart = user.getCart();
        if (cart == null) {
            cart = new cart();
            cart.setUser(user);
            user.setCart(cart);
        }

        productModel product =productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        cartItems cartItem = new cartItems();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getItems().add(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    public ModelAndView removeItemFromCart(String username, Integer itemId) {
        user user = userRepository.findByEmail(username).get();
        cart cart = user.getCart();

        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        cartItemRepository.deleteById(itemId);

        userRepository.save(user);  // Save the updated user entity
        RedirectView rd=new RedirectView();
		rd.setUrl("/cart");
		return new ModelAndView(rd);
    }
}
