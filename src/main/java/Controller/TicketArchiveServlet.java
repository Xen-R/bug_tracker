package Controller;

import Model.Ticket;
import Model.TicketList;
import Model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class TicketArchiveServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        String name = request.getParameter("name");
        if (name != null) {
            name = name.trim();
        }
        PrintWriter out = response.getWriter();
        Users us = new Users();
        us.getUsers();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><link rel=\"stylesheet\" href=\"../ticket.css\"></head><body>");

        String uri = request.getRequestURI();
        TicketList tl= new TicketList(us);
        if (uri.equals("/bug_tracker_war_exploded/TicketArchiveServlet/bug")) {
            tl.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_bugs.txt");
        } else {
            tl.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_features.txt");
        }
        Ticket tmp = tl.getTicket(name);

        out.println("<div class = \"block-1\">");
        out.println("<form method=\"GET\" action=\"/bug_tracker_war_exploded/MainPage\">\n");
        out.println("<input type=\"submit\" value=\" return \">" );
        out.println("</form>");
        out.println("<h1>ticket info:</h1>");
        ArrayList<String> curt = tmp.getArchiveDisplay();
        for (String value : curt) {
            out.println(value);
        }
        out.println("</div>\n");

        out.println("<div class = \"block-3\">");
        out.println("<h1>ticket history:</h1>");
        ArrayList<String> hist = tmp.getDisplayHistory();
        for (String s : hist) {
            out.println(s);
        }
        out.println("</div>\n");

        out.println("<div class = \"block-2\">");
        out.println("<h1>comments:</h1>");
        ArrayList<String> comms = tmp.getDisplayComments();;
        for (String comm : comms) {
            out.println(comm);
        }
        out.println("</div>\n");

        out.println("</body></html>");
        out.close();

    }

}