package com.fabrikam.orders.domain;

import com.microsoft.azure.spring.data.cosmosdb.repository.ReactiveCosmosRepository;

import org.springframework.stereotype.Repository;
// import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCosmosRepository<Order, String> {
    
    Mono<Order> findById(String id);

}