package Controller;

import Model.Ticket;
import Model.TicketList;
import Model.Users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class TicketServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        String name = request.getParameter("name");
        name = name.trim();
        PrintWriter out = response.getWriter();
        Users us = new Users();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><link rel=\"stylesheet\" href=\"ticket.css\"></head><body>");
        us.getUsers();;
        TicketList tl= new TicketList(us);
        tl.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt");
        Ticket tmp = tl.getTicket(name);

        out.println("<div class = \"block-1\">");
        out.println("<p>");

        out.println("<form method=\"GET\" action=\"/bug_tracker_war_exploded/MainPage\">\n");
        out.println("<input type=\"submit\" value=\" return \">" );
        out.println("</form>");

        out.println("<form method=\"GET\" action=\"/bug_tracker_war_exploded/EditServlet\">\n");
        out.println("<button type=\"submit\" name = \"name\" value=\""+name+"\">\n" + "edit it" + "</button>");
        out.println("</form>");

        out.println("<form method=\"GET\" action=\"/bug_tracker_war_exploded/DeleteServlet\">\n");
        out.println("<button type=\"submit\" name = \"name\" value=\""+name+"\">\n" + "delete it" + "</button>");
        out.println("</form>");

        out.println("</p>");
        out.println("<h1>ticket info:</h1>");
        ArrayList<String> curt = tmp.getDisplay();
        for (String s : curt) {
            out.println(s);
        }
        out.println("</div>\n");

        out.println("<div class = \"block-3\">");
        out.println("<h1>ticket history:</h1>");
        ArrayList<String> hist = tmp.getDisplayHistory();;
        for (String s : hist) {
            out.println(s);
        }
        out.println("</div>");

        out.println("<div class = \"block-2\">");
        out.println("<h1>comments:</h1>");

        out.println("</form>");
        out.println("<form method=\"GET\" action=\"/bug_tracker_war_exploded/CommentServlet\">\n");
        out.println("<input type = \"text\" name=\"comment\" placeholder=\"your comment\"><br>");
        out.println("<button type=\"submit\" name = \"name\" value=\""+name+"\">\n" + "add comment" + "</button>");
        out.println("</form>");

        ArrayList<String> comms = tmp.getDisplayComments();;
        for (String comm : comms) {
            out.println(comm);
        }
        out.println("</div>");

        out.println("</body></html>");
        out.close();

    }

}