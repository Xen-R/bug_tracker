package Controller;

import Model.TicketList;
import Model.Users;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class CommentServlet extends HttpServlet {

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
        String comment = request.getParameter("comment");
        System.out.println("EDITING DATA:");
        System.out.println(name);
        System.out.println("hz cto");
        System.out.println("hz chto");
        tickets.addComment(admin, name, comment);
        response.sendRedirect("/bug_tracker_war_exploded/TicketServlet?name=" +name +"+" );
        //request.getRequestDispatcher("/bug_tracker_war_exploded/TicketServlet?name=" +name +"+" );
        out.close();

    }




}