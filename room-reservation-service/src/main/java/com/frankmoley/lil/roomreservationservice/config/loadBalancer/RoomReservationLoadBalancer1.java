//package com.frankmoley.lil.roomreservationservice.config.loadBalancer;
//
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
//import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
//import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//
//@Configuration
////@ConditionalOnProperty(
////        value = LoadBalancerClientFactory.PROPERTY_NAME,
////        havingValue = "guest-service"
////)
////
//public class RoomReservationLoadBalancer1 {
//
//
//    @Bean
//    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(
//                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
//        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider( "guest-service" , ServiceInstanceListSupplier.class), "guest-service");
//    }
//
//}
