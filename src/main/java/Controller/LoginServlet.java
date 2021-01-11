package Controller;

import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = name.trim();
        System.out.println(name);
        password = password.trim();
        PrintWriter out = response.getWriter();
        Users us = new Users();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><link rel=\"stylesheet\" href=\"../login.css\"></head><body>");
        us.getUsers();

        if(us.exists(name)) {
            if(us.checkUser(name, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("name", name);
                response.sendRedirect("/bug_tracker_war_exploded/MainPage");
            } else {
                out.print("<h1>Sorry, password is wrong</h1>");
                request.getRequestDispatcher("login.html").include(request, response);
            }
        } else {
            out.print("<h1>Sorry, name is wrong</h1>");
            request.getRequestDispatcher("login.html").include(request, response);
        }

        out.println("</body></html>");
        out.close();

    }

}