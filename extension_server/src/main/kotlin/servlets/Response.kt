package servlets


import java.io.File
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "ResponseServlet", urlPatterns = ["/response"])
class Response: HttpServlet() {
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html"
        File("E:\\projects\\vs_code_ext\\logs.txt").writeText("ща отправим ответ")
        if (request.getAttribute("summary") != null) {
            print(request.getAttribute("summary"))
            File("E:\\projects\\vs_code_ext\\logs.txt").writeText(request.getAttribute("summary") as String)
            response.writer.print(request.getAttribute("summary"))
        }
    }

}