package servlets

import summarization.SummaryGetter
import java.io.File
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlinx.serialization.json.Json


@WebServlet(name = "ControllerServlet", urlPatterns = ["/control"])
class Controller: HttpServlet() {
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            File("E:\\projects\\vs_code_ext\\logs.txt").writeText("пришёл post")
            val sb = StringBuilder()
            val reader = request.reader
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }

            val input = sb.toString()
            val summaryGetter: SummaryGetter = SummaryGetter()
            request.setAttribute("summary", summaryGetter.getSummary(input))
            File("E:\\projects\\vs_code_ext\\logs.txt").writeText(input)
            File("E:\\projects\\vs_code_ext\\logs.txt").writeText("редирект...")
            servletContext.getRequestDispatcher("/response").forward(request, response)
        } catch ( e: Exception){
            File("E:\\projects\\vs_code_ext\\logs.txt").writeText(e.toString())

        }
    }

}