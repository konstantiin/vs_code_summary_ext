package com.microservices.clientlistener


import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.*

import org.springframework.web.bind.annotation.RestController


@RestController
class PluginController {
    var logger: Logger = LoggerFactory.getLogger(PluginController::class.java)

    fun clearTextFromTags(text: String): String {
        //val result = Regex("""(<[a-zA-Z/].*>)|(\s\*\s)""").replace(text, " ")
        val result = Regex("""(<[a-zA-Z]+>)|(</[a-zA-Z]+>)""").replace(text, "")

        logger.info("Cleared tags:")
        logger.info(result)
        return result
    }
    @Test
    fun `test clearTextFromTags with text containing HTML tags`() {
        val inputText = "This is a <b>test</b> with <i>HTML</i> tags."
        val expectedResult = "This is a test with HTML tags."
        val controller = PluginController()
        val actualResult = controller.clearTextFromTags(inputText)
        assertThat(actualResult).isEqualTo(expectedResult)
    }
    @Test
    fun `test clearTextFromTags with russian text`() {
        val inputText = "бла бла жуомиуж <b>tмук</b> wiмукth <i>ккHTML</i> tags."
        val expectedResult = "бла бла жуомиуж tмук wiмукth ккHTML tags."
        val controller = PluginController()
        val actualResult = controller.clearTextFromTags(inputText)
        assertThat(actualResult).isEqualTo(expectedResult)
    }
    @Test
    fun `test clearTextFromTags with diff tags`() {
        val inputText = "привет <hello> hello </hello> HeLlO <HellE><HELLO> приVET</HellE> wvw"
        val expectedResult = "привет  hello  HeLlO  приVET wvw"
        val controller = PluginController()
        val actualResult = controller.clearTextFromTags(inputText)
        assertThat(actualResult).isEqualTo(expectedResult)
    }
    @Test
    fun `test clearTextFromTags with math`() {
        val inputText = "1 > 2 1>2 1<2 2 < ejv 1 jbkjerv"
        val expectedResult = "1 > 2 1>2 1<2 2 < ejv 1 jbkjerv"
        val controller = PluginController()
        val actualResult = controller.clearTextFromTags(inputText)
        assertThat(actualResult).isEqualTo(expectedResult)
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
    @Test
    fun `test removeCommentsSymbols`() {
        val inputText = """//
// comment
# cmnt
/*
/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */"""
        val expectedResult = """
 comment
 cmnt




 Copyright 2002-2023 the original author or authors.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
"""
        val controller = PluginController()
        val actualResult = controller.removeCommentsSymbols(inputText)
        assertThat(actualResult).isEqualTo(expectedResult)
    }

}