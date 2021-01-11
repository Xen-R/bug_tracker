package Controller;

import Model.TicketList;
import Model.Users;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class ArchiveServlet  extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><link rel=\"stylesheet\" href=\"archive.css\"></head><body>");
        HttpSession session = request.getSession();
        out.println("<div class = \"block-1\">");
        request.getRequestDispatcher("linksa.html").include(request, response);
        String admin = (String) session.getAttribute("name");
        Users us = new Users();
        us.getUsers();
        TicketList bugs = new TicketList(us);
        bugs.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_bugs.txt");
        TicketList features = new TicketList(us);
        features.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_features.txt");
        out.println("</div>\n");
        out.println("<div class = \"block-2\">");
        out.println("<h1>Archived bugs:</h1>" );
        ArrayList<String> bug = bugs.getArchivedDisplay();
        for (String s : bug) {
            out.println(s);
            System.out.println(s);
        }
        out.println("</table");
        out.println("</div>\n");
        out.println("<div class = \"block-3\">");
        out.println("<h1>Archived features:</h1>" );
        ArrayList<String> feat = features.getArchivedDisplay();
        for (String s : feat) {
            out.println(s);
            System.out.println(s);
        }
        out.println("</table");
        out.println("</div>\n");
        out.println("</body></html>");
        out.close();

    }

}
