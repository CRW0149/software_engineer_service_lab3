package org.example.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.example.entity.Result;
import org.example.entity.UserOrderDTO;
import org.example.feign.ProviderService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "控制器：测试Swagger3", description = "描述：测试Swagger3")
public class UserController {
    @Resource
    private ProviderService providerService;

//    @Operation(summary = "测试Swagger3注解方法Get")
//    调用断路器A
//    @CircuitBreaker(name = "circuitBreakerA",fallbackMethod = "getOrderByUserIdDown")
//    调用断路器B
//    @CircuitBreaker(name = "circuitBreakerB",fallbackMethod = "getOrderByUserIdDown")
//    @Bulkhead(name = "bulkA",type = Bulkhead.Type.SEMAPHORE,fallbackMethod = "getOrderByUserIdDown")
    @RateLimiter(name = "rate1",fallbackMethod ="getOrderByUserIdDown" )
    @GetMapping("/getOrderByUserId")
    public Result<UserOrderDTO>getOrderByUserId(@RequestParam("userId")Integer userId)  {
        //远程调用
        Result<Order>orderResult=providerService.getOrderByUserId(userId);

        log.info("正常调用,用户id{}",userId);
        UserOrderDTO userOrderDTO=new UserOrderDTO();

        BeanUtils.copyProperties(orderResult.getData(),userOrderDTO);

        userOrderDTO.setName("小王");
        userOrderDTO.setAge(18);
        userOrderDTO.setOrderId(orderResult.getData().getId());

        return Result.success(userOrderDTO,orderResult.getMessage());
    }
//限流回调方法
    public Result<UserOrderDTO>getOrderByUserIdDown(Integer userId,Throwable e){
        log.info("失败调用,用户id{}",userId);
        UserOrderDTO userOrderDTO=new UserOrderDTO();
        return Result.success(userOrderDTO,"限流触发"+e.getMessage());
    }
    @Operation(summary = "测试2")
    @PostMapping("/sd")
    public Integer test(@RequestParam(name = "id") Integer id){
        return id;
    }
}
