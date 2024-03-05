package com.microservices.clientlistener

import com.fasterxml.jackson.core.JsonProcessingException
import org.slf4j.*
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
    var logger: Logger = LoggerFactory.getLogger(PluginController::class.java)
    @PostMapping("/text")
    @Throws(JsonProcessingException::class)
    fun summarize(@RequestBody text: SummarizeRequest): ResponseEntity<String?> {
        logger.info(restTemplate.toString())

//        val result = restTemplate!!.postForObject(
//            "http://client-listener-1/text",
//            text,
//            SummarizeRequest::class.java
//        )
        return ResponseEntity.status(HttpStatus.OK).body("result?.textToSummarize")
    }
}