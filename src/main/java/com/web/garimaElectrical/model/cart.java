package com.web.garimaElectrical.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class cart {
	    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

		public user getUser() {
		return user;
	}

	public void setUser(user user) {
		this.user = user;
	}

	public Set<cartItems> getItems() {
		return items;
	}

	public void setItems(Set<cartItems> items) {
		this.items = items;
	}

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @OneToOne
	    @JoinColumn(name = "user_id")
	    private user user;

	    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private Set<cartItems> items = new HashSet<>();
}
