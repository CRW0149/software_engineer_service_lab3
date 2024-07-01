package org.example.feign;

import org.example.entity.Order;
import org.example.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("provider")
public interface ProviderService {
    @GetMapping("/order/getOrderByUserId")
    public Result<Order> getOrderByUserId(@RequestParam(name = "userId")Integer userId);
}
