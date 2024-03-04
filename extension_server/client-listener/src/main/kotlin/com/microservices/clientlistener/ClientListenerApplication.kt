package com.microservices.clientlistener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate


@SpringBootApplication
class ClientListenerApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ClientListenerApplication>(*args)
        }
    }
    @LoadBalanced
    @Bean
    fun restTemplate(): RestTemplate? {
        return RestTemplate()
    }
}