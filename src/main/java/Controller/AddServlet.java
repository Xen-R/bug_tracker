package Controller;

import Model.TicketList;
import Model.Users;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><link rel=\"stylesheet\" href=\"add.css\"></head><body>");
        HttpSession session = request.getSession();
        Users us = new Users();
        us.getUsers();
        TicketList tickets = new TicketList(us);
        tickets.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt");
        String admin = (String) session.getAttribute("name");

        String name = request.getParameter("name_of");
        String priority = request.getParameter("priority");
        String value = request.getParameter("value");
        String responsible = request.getParameter("responsible");
        String description = request.getParameter("description");
        String solution = request.getParameter("solution");

        tickets.addTicket(admin, name, value, priority, responsible, description, solution);
        //response.sendRedirect("/bug_tracker_war_exploded/MainPage");
        response.sendRedirect("/bug_tracker_war_exploded/TicketServlet?name=" +name +"+" );
        out.println("</body></html>");
        out.close();

    }

}