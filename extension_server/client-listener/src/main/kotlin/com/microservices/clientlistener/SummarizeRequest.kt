package com.microservices.clientlistener

class SummarizeRequest {
    var modelName: String = "default"
    var textToSummarize: String = ""
    override fun toString(): String{
        return "$modelName\n$textToSummarize"
    }
}