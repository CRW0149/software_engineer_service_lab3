package org.example.controller;

import org.example.entity.Order;
import org.example.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/getOrderByUserId")
    public Result<Order>getOrderByUserId(@RequestParam(name = "userId")Integer userId) throws InterruptedException {
        Thread.sleep(3000);
        Order order=new Order();
        order.setUserId(userId);
        order.setId(10086);
        order.setUpdateTime(LocalDateTime.now());
        return Result.success(order);
    }
}
