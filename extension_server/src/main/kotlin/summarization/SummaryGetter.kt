package summarization

import mu.KotlinLogging
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.random.Random


class SummaryGetter {
    private val logger = KotlinLogging.logger {}
    class PythonScriptError(message: String) : Exception(message)

    fun getSummary(text: String) : String{
        try {
            var path = "E:/projects/vs_code_ext/extension_server/"
            path += "input_data_${Random.nextLong()}"
            File("$path.txt").writeText(text)
            val processBuilder = ProcessBuilder(
                "python",
                "E:/projects/vs_code_ext/extension_server/run.py",
                "$path.txt"
            )
            processBuilder.redirectErrorStream(false)
            processBuilder.redirectError(File("E:\\projects\\vs_code_ext\\extension_server\\server.log"))

            val process: Process = processBuilder.start()
            logger.info("Python script running")
            val exitCode = process.waitFor()
            if (exitCode != 0) throw PythonScriptError("Check logs for Python error")
            val bufferedReader = BufferedReader(InputStreamReader(FileInputStream(path + "_out.txt")), 1)

            val out = bufferedReader.readLines()
            var result = ""
            for (line in out) result += line
            return result
        } catch (e: Exception){
            logger.error(e.toString())

            return ""
        }
    }
}