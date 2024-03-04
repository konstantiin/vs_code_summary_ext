package com.microservices.clientlistener

import com.fasterxml.jackson.core.JsonProcessingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class PluginController {
    @Autowired
    var restTemplate: RestTemplate? = null

    @PostMapping("/text")
    @Throws(JsonProcessingException::class)
    fun summarize(@RequestBody textToSummarize: String): ResponseEntity<String?> {
        val result = restTemplate!!.postForObject(
            "http://data-aggregation-service/calculateGrades", textToSummarize,
            String::class.java
        )

        return ResponseEntity.status(HttpStatus.OK).body(result)
    }
}