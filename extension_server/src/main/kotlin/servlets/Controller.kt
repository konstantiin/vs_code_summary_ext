package servlets

import mu.KotlinLogging
import summarization.SummaryGetter
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
@WebServlet(name = "ControllerServlet", urlPatterns = ["/control"])
class Controller: HttpServlet() {
    private val logger = KotlinLogging.logger {}
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            val userDirectory = System.getProperty("user.dir")
            logger.debug(userDirectory)
            request.setAttribute("error", false)
            logger.info("HTTP Post received")
            val sb = StringBuilder()
            val reader = request.reader
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }

            val input = sb.toString()
            val summaryGetter = SummaryGetter()
            request.setAttribute("summary", summaryGetter.getSummary(input))
            logger.info("Redirecting to send response..")
            servletContext.getRequestDispatcher("/response").forward(request, response)
        } catch ( e: Exception){
            logger.error(e.toString())
            request.setAttribute("error", true)
            servletContext.getRequestDispatcher("/response").forward(request, response)
        }
    }

}