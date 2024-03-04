package com.microservices.clientlistener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
@SpringBootApplication
class ClientListenerApplication

fun main(args: Array<String>) {
    runApplication<ClientListenerApplication>(*args)
}
