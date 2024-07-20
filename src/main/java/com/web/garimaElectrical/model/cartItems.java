package com.web.garimaElectrical.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class cartItems {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @ManyToOne
	    @JoinColumn(name = "cart_id")
	    private cart cart;

	    public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public cart getCart() {
			return cart;
		}

		public void setCart(cart cart) {
			this.cart = cart;
		}

		public productModel getProduct() {
			return product;
		}

		public void setProduct(productModel product) {
			this.product = product;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		@ManyToOne
	    @JoinColumn(name = "product_id")
	    private productModel product;

	    private int quantity;
}
