package com.microservices.clientlistener

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test

class SummarizeRequest {
    private var modelName: String = "default"
    private var textToSummarize: String = ""
    override fun toString(): String{
        return "$modelName\n$textToSummarize"
    }
    @Test
    fun `test toString`() {
        val req = SummarizeRequest()
        req.modelName = "bert"
        req.textToSummarize = "Hello world \n Hello"
        val expectedResult = "bert\nHello world \n Hello"
        AssertionsForClassTypes.assertThat(req.toString()).isEqualTo(expectedResult)
    }
}