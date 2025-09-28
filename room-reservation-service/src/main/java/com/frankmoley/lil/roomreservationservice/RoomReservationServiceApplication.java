package com.frankmoley.lil.roomreservationservice;

//import com.frankmoley.lil.roomreservationservice.config.loadBalancer.RoomReservationLoadBalancer1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@LoadBalancerClient(name = "guest-service", configuration = RoomReservationLoadBalancer1.class) // only service id : room-reservation-service user random load balancer
public class RoomReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomReservationServiceApplication.class, args);
    }

    @LoadBalanced
    // intercept the request tell rest template hey -> please use sring load balance to forward request depends on load balance algo
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
