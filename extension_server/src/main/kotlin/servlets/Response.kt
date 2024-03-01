package servlets


import mu.KotlinLogging
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "ResponseServlet", urlPatterns = ["/response"])
class Response: HttpServlet() {
    private val logger = KotlinLogging.logger {}
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html"
        logger.info("Sending answer")
        if (!(request.getAttribute("error") as Boolean)
            && request.getAttribute("summary") != null) {

            response.writer.print(request.getAttribute("summary"))
        }
        else{
            logger.info("Error message send")
            response.sendError(500)
        }
    }

}