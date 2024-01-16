package summarization

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.*
import kotlin.random.Random


class SummaryGetter {
    fun getSummary(text: String) : String{
        try {
            var path = "E:/projects/vs_code_ext/extension_server/"
            path += "input_data_${Random.nextLong()}"
            File("$path.txt").writeText(text)
            val processBuilder = ProcessBuilder("python", "E:/projects/vs_code_ext/extension_server/run.py", "$path.txt")
            processBuilder.redirectError(File("E:\\projects\\vs_code_ext\\logs.txt"))
            File("E:\\projects\\vs_code_ext\\log.txt").writeText("Ща запустим питон")
            val process: Process = processBuilder.start()
            process.waitFor()
            val bufferedReader = BufferedReader(InputStreamReader(FileInputStream(path + "_out.txt")), 1)

            val out = bufferedReader.readLines()
            var result: String = ""
            for (line in out) result += line
            return result
        } catch (e: Exception){
            File("E:\\projects\\vs_code_ext\\logs.txt").writeText("ошибка:")

            File("E:\\projects\\vs_code_ext\\logs.txt").writeText(e.toString())
            return ""
        }
    }
}