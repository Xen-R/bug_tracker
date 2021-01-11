package Controller;

import Model.TicketList;
import Model.Users;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class MainPage  extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        request.getRequestDispatcher("switcher.html").include(request, response);
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("name");

        if(session.getAttribute("name") != null){
            request.getRequestDispatcher("links.html").include(request, response);
            request.getRequestDispatcher("sort.html").include(request, response);
            Users us = new Users();
            us.getUsers();
            TicketList current = new TicketList(us);
            current.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt");
            ArrayList<String> curt = current.getDisplay();
            for(int i = 0; i < curt.size(); i++) {
                out.println(curt.get(i));
                System.out.println(curt.get(i));
            }
        } else {
            request.getRequestDispatcher("login.html").include(request, response);
        }

        out.println("</body></html>");
        out.close();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><link rel=\"stylesheet\" href=\"mainpage.css\"></head><body>");
        HttpSession session = request.getSession();
        String sort = request.getParameter("sorting_mode");
        request.getRequestDispatcher("links.html").include(request, response);
        request.getRequestDispatcher("sort.html").include(request, response);
        String admin = (String) session.getAttribute("name");
        Users us = new Users();
        us.getUsers();
        TicketList current = new TicketList(us);
        current.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt");

        if(sort.equals("default")) {
            ArrayList<String> curt = current.getDisplay();
            for (String s : curt) {
                out.println(s);
            }
        } else {
            ArrayList<String> curt = current.getSortedDisplay(sort, admin);
            for (String s : curt) {
                out.println(s);
            }
        }

        out.println("</body></html>");
        out.close();

    }

}
