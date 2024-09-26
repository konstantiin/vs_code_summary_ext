package com.microservices.clientlistener

import com.fasterxml.jackson.core.JsonProcessingException
import org.slf4j.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient

/**
 * lkkjkjkrbjvkb
 */
@RestController
class PluginController {
    var logger: Logger = LoggerFactory.getLogger(PluginController::class.java)
    val modelURL = "http://model:8050/get-summary"
    fun getTextFromHFModel(text: SummarizeRequest): String? {
        logger.info("Sending data to Flask:")
        logger.info(text.textToSummarize)
        val summary = RestClient.create()
            .post()
            .uri(modelURL)
            .contentType(MediaType.APPLICATION_JSON)
            .body(text)
            .retrieve()
            .toEntity(String::class.java).body
        logger.info("Result: ")
        logger.info(summary)
        return summary
    }
    fun clearTextFromTags(text: String): String {
        val result = Regex("""(<[a-zA-Z]+>)|(</[a-zA-Z]+>)""").replace(text, "")
        logger.info("Cleared tags:")
        logger.info(result)
        return result
    }
    fun removeCommentsSymbols(text: String): String{
        var result = Regex("""(\n//)|(\n#)|(/\*)|(\s*\*/)|(\s*\*)""").replace(text, "\n")
        if (result[0] == '/' && result[1] == '/'){
            result = result.drop(2)
        }
        logger.info("Cleared comment symbols:")
        logger.info(result)
        return result
    }
    @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
    @PostMapping("/manage-summarization")
    @Throws(JsonProcessingException::class)
    fun summarize(@RequestBody text: SummarizeRequest): ResponseEntity<String?> {
        // clear text
        val clearedData = SummarizeRequest()
        clearedData.modelName = text.modelName
        clearedData.textToSummarize = removeCommentsSymbols(clearTextFromTags(text.textToSummarize))


        val summary: String? = getTextFromHFModel(clearedData)
        return ResponseEntity.status(HttpStatus.OK).body(summary)

    }
}