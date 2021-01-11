package Controller;

import Model.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class SignUpServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        name = name.trim();
        password = password.trim();
        PrintWriter out = response.getWriter();
        Users us = new Users();
        us.getUsers();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><link rel=\"stylesheet\" href=\"../login.css\"></head><body>");

        if (us.exists(name)) {
            out.print("<h1>Sorry, name is already use</h1>");
            out.println("</body></html>");
            request.getRequestDispatcher("login.html").include(request, response);
        } else {
            if (name.length() != 0 && password.length() != 0) {
                us.addUser(name, password);
                out.print("<h1>successfully added </h1>");
                out.println("</body></html>");
                HttpSession session = request.getSession();
                session.setAttribute("name", name);
                response.sendRedirect("/bug_tracker_war_exploded/MainPage");
            } else {
                out.print("<h1>No name or password found</h1>");
                out.println("</body></html>");
                request.getRequestDispatcher("signup.html").include(request, response);
            }
        }

        out.close();

    }

}