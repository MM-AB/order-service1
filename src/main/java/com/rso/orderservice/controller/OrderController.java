package com.rso.orderservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rso.orderservice.dto.OrderRequest;
import com.rso.orderservice.dto.OrderResponse;
import com.rso.orderservice.dto.ProductRes;
import com.rso.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    //private final ProductService productService;

    @PostMapping("/post-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createOrder(OrderRequest orderRequest, @RequestParam String name, @RequestParam String address, @RequestParam BigDecimal price){
        orderRequest.setName(name);
        orderRequest.setPrice(price);
        orderRequest.setUserId("1");
        orderRequest.setAddress(address);
        orderService.createOrder(orderRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("post-order");
        return modelAndView;
    }



    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView getAllOrders() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orders");
        List<OrderResponse> listOrders = orderService.getAllOrders();
        modelAndView.getModelMap().addAttribute("listOrders",listOrders);
        return modelAndView;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    //public ResponseEntity<ProductRes> getProducts(){
    //    ProductRes productRes = productService.getProducts();
    //    return ResponseEntity.ok(productRes);
    public ModelAndView listProd(){

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView modelAndView = new ModelAndView();

        ResponseEntity<ProductRes[]> resResponseEntity = restTemplate.getForEntity("http://localhost:8080/product/products", ProductRes[].class);
        List<ProductRes> productRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<ProductRes>>() {});
        //System.out.println(productRes);

        modelAndView.setViewName("index");
        modelAndView.getModelMap().addAttribute("productRes",productRes);
        return modelAndView;

    }
}
