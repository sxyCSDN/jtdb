package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;

import com.jt.pojo.Cart;
import com.jt.servive.DubboCartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout=3000,check=false)
	private DubboCartService cartService;
	
	@RequestMapping("/show")
	public String findCartListByUser(Model model) {
		Long userId = 7L;
		List<Cart> cartList = 
				cartService.findCartListByUser(userId);
		model.addAttribute("cartList", cartList);
		return "cart";	//转发到cart.jsp页面
	}
	
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart,Model model) {
		Long userId = 7L;
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show";
	}


}
