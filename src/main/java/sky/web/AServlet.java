package sky.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: ExUpload
 * @description: baseServlet
 * @author: Zhaoziqi
 * @create: 2018-07-30 11:15
 **/
public class AServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.getWriter().println("hello fffffffff");

    }


}
