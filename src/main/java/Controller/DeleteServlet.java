package Controller;

import Model.Ticket;
import Model.TicketList;
import Model.Users;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        HttpSession session = request.getSession();
        Users us = new Users();
        us.getUsers();
        TicketList tickets = new TicketList(us);
        tickets.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt");
        String admin = (String) session.getAttribute("name");
        Ticket tck = tickets.getTicket(name);

        if(tck.getStringValue().equals("bug")) {
            TicketList bugs = new TicketList(us);
            bugs.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_bugs.txt");
            bugs.addTicket(admin, tck);
        } else {
            TicketList features = new TicketList(us);
            features.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_features.txt");
            features.addTicket(admin, tck);
        }

        tickets.deleteTicket(name);
        response.sendRedirect("/bug_tracker_war_exploded/MainPage");
        out.close();

    }

}