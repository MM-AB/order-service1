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


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private static final String PATH_URL = "http://20.120.124.86"; //http://localhost:8083 //http://20.120.124.86
    private final OrderService orderService;
    //private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody  OrderRequest orderRequest){
        //System.out.println(orderRequest);
        orderService.createOrder(orderRequest);
    }



    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {

        List<OrderResponse> listOrders = orderService.getAllOrders();
        //System.out.println(listOrders);
        return listOrders;
    }


    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductRes> listProducts(){

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<ProductRes[]> resResponseEntity = restTemplate.getForEntity(PATH_URL + "/product", ProductRes[].class);
        List<ProductRes> productRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<ProductRes>>() {});
        //System.out.println(productRes);

        return productRes;

    }
}
