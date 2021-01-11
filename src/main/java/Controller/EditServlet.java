package Controller;

import Model.TicketList;
import Model.Users;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class EditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        //response.sendRedirect("/bug_tracker_war_exploded/edit.html");
        HttpSession session = request.getSession();
        Users us = new Users();
        us.getUsers();
        TicketList tickets = new TicketList(us);
        tickets.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt");
        String admin = (String) session.getAttribute("name");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><link rel=\"stylesheet\" href=\"add.css\"></head>");
        out.println("<body>");

        out.println("<form action=\"EditServlet\" method=\"post\">");
        out.println("<h1>Edit ticket</h1>");

        out.println("<p><b>change name(note: name must be unique, less than 200 characters and doesn't contain spaces):</b><Br>");
        out.println("<input type = \"text\" name=\"new_name\" placeholder=\"new_name\"><br>");

        out.println("<p><b>change value(use arrows to switch between variants):</b><Br>");

        out.println("<p><select size=\"1\" multiple name=\"new_value\">");
        out.println(" <option disabled>unchanged</option>");
        out.println("<option selected value=\"leave_unchanged\">don't change</option>");
        out.println("         <option value=\"bug\">bug</option>");
        out.println("<option value=\"feature\">feature</option>");
        out.println("</select></p>");

        out.println("<p><b>change priority(use arrows to switch between variants):</b><Br>");
        out.println("<p><select size=\"1\" multiple name=\"new_priority\">");
        out.println("<option disabled>unchanged</option>");
        out.println(" <option selected value=\"leave_unchanged\">don't change</option>");
        out.println(" <option value=\"low\">low</option>");
        out.println("<option value=\"medium\">medium</option>");
        out.println("<option value=\"high\">high</option>");
        out.println("</select></p>");

        out.println(" <p><b>change responsible(note: it must be registred user):</b><Br>");
        out.println("<input type=\"text\" name=\"responsible\"placeholder=\"responsible\"><br>");
        out.println("<p><b>change description:</b><Br>");
        out.println("<p><b>if you'll like to overwrite the description field, press here:</b><Br>");
        out.println(" <input type=\"radio\" name=\"change_descr_mode\" value=\"overwrite\"> overwrite<Br>");
        out.println(" </p>");

        out.println("<input type=\"text\" name=\"description\"placeholder=\"description\"><br>");
        out.println("<p><b>change solutions:</b><Br>");
        out.println("<p><b>if you'll like to overwrite the solution field, press here:</b><Br>");
        out.println("<input type=\"radio\" name=\"change_sol_mode\" value=\"overwrite\"> overwrite<Br>");
        out.println("</p>");

        out.println("<input type=\"text\" name=\"solution\"placeholder=\"solution\"><br>");
    //out.println("<input type=\"submit\" name = \"name\" value= \"" +request.getParameter("name")+"\">\\n\">");
        out.println("<button type=\"submit\" name = \"name\" value=\""+request.getParameter("name")+"\">\n" + "confirm changes" + "</button>");
        out.println("</form>");
        out.println("</body></html>");
        out.close();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Users us = new Users();
        us.getUsers();
        TicketList tickets = new TicketList(us);
        tickets.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt");
        String admin = (String) session.getAttribute("name");
        String name = request.getParameter("name");
        name = name.trim();

        String new_name = request.getParameter("new_name");
        if(new_name.length() != 0) {
            if(new_name.contains(" ")) {
                new_name = new_name.substring(0, name.indexOf(" "));
            }
            if (new_name.length() > 200) {
                new_name = new_name.substring(0, 199);
            }
        }

        String value = request.getParameter("new_value");
        String priority = request.getParameter("new_priority");
        String responsible = request.getParameter("responsible");
        String desc_change = request.getParameter("change_descr_mode");
        String description = request.getParameter("description");
        String sol_change = request.getParameter("change_sol_mode");
        String solution = request.getParameter("solution");
        tickets.editTicket(admin, name, new_name, value, priority, responsible, desc_change, description, sol_change, solution);
        if(new_name == "") {
            response.sendRedirect("/bug_tracker_war_exploded/TicketServlet?name=" + name + "+");
        } else {
            response.sendRedirect("/bug_tracker_war_exploded/MainPage");
        }
        out.close();

    }

}