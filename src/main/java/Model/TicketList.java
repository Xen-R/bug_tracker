package Model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TicketList {

    String file_name = "C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\current_tickets.txt";
    String bug_storage_name = "C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_bugs.txt";
    String feature_storage_name = "C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\archieved_features.txt";
    Users users;
    private ArrayList<Ticket> default_list;
    private HashMap<String, Ticket> sorted_by_name;

    public TicketList(Users us) {
        default_list = new ArrayList<>();
        sorted_by_name = new HashMap<>();
        users = us;
    }

    public synchronized void getTickets(String file) {
        try (FileReader reader = new FileReader(file))
        {
            Scanner scan = new Scanner(reader);
            while (scan.hasNext())
            {
                String user_data = scan.nextLine();
                String[] tokens = user_data.split(" ");
                String name = tokens[0];
                String date = tokens[1];
                String val = tokens[2];
                String prior = tokens[3];
                String resp = tokens[4];
                String descr = "";
                String sol = "";
                int idx = 0;
                for(int i = 5; i < tokens.length; i++) {
                     descr+=tokens[i];
                     descr += " ";
                     Character c = tokens[5].charAt(tokens[5].length()-1);
                     Character etal = '^';
                     if (tokens[i].contains("^") && (i != 5 || c == etal)) {
                             idx = i + 1;
                             break;
                     }
                }
                descr = descr.substring(descr.indexOf("^")+1);
                descr = descr.substring(0, descr.indexOf("^"));

                if(idx != 0) {
                    for(int i = idx; i < tokens.length; i++) {
                        sol+=tokens[i];
                        sol += " ";
                    }
                }
                sol = sol.substring(sol.indexOf("~")+1);
                sol = sol.substring(0,sol.indexOf("~"));

                Value value;
                if (val.equals("feature")) {
                    value = Value.FEATURE;
                } else {
                    value = Value.BUG;
                }

                Ticket.Priority priority;

                if (prior.equals("low")) {
                    priority = Ticket.Priority.LOW;
                } else {
                    if (prior.equals("medium")) {
                        priority = Ticket.Priority.MEDIUM;
                    } else{
                        priority = Ticket.Priority.HIGH;
                    }
                }

                if (!users.exists(resp)) {
                    resp = "";
                }

                Ticket new_ticket = new Ticket(name, date, value, priority,resp, descr, sol);
                default_list.add(new_ticket);
                sorted_by_name.put(name, new_ticket);

                if(resp.equals("")) {
                    resp = "no_resp";
                }
                if(sol.equals("")) {
                }
                else {
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public synchronized void addTicket(String creator, String name, String val, String prior, String resp, String descr, String sol) {

        Value value;
        if(val.equals("bug")) {
            value = Value.BUG;
        }
        else {
            value = Value.FEATURE;
        }
        Ticket.Priority priority;
        if(prior.equals("low")) {
            priority = Ticket.Priority.LOW;
        }
        else {
            if(prior.equals("medium")) {
                priority = Ticket.Priority.MEDIUM;
            }
            else{
                priority = Ticket.Priority.HIGH;
            }
        }
        if(!users.exists(resp)) {
            resp = "";
        }
        Ticket new_ticket = new Ticket(creator, name, val, prior, resp, descr, sol);
        for(int i = 0; i< default_list.size(); i++){
            Ticket tmp = default_list.get(i);
            String text = "";
            text += tmp.getName();
            text +=  " " + tmp.getDate();
            text +=  " " + tmp.getStringValue();
            text +=  " " + tmp.getStringPriority();
            text +=  " " + tmp.getResp();
            text +=  " " + tmp.getDescription();
            text +=  " " + tmp.getSolutions();
        }
        default_list.add(new_ticket);
        for(int i = 0; i< default_list.size(); i++){
            Ticket tmp = default_list.get(i);
            String text = "";
            text += tmp.getName();
            text +=  " " + tmp.getDate();
            text +=  " " + tmp.getStringValue();
            text +=  " " + tmp.getStringPriority();
            text +=  " " + tmp.getResp();
            text +=  " " + tmp.getDescription();
            text +=  " " + tmp.getSolutions();
        }
        sorted_by_name.put(name, new_ticket);
        updateFile();
    }

    public synchronized void addTicket(String creator, Ticket ticket) {

        String name = ticket.getName();
        Value value = ticket.getValue();
        ticket.addHistory(creator +": moved ticket to the archiee");
        default_list.add(ticket);
        sorted_by_name.put(name, ticket);
        updateArchive(value);

    }

    public synchronized void editTicket(String editor, String old_name, String new_name, String val, String prior, String resp, String d_c_mode, String descr, String s_c_mode, String sol) {

        Ticket tmp = sorted_by_name.get(old_name);
        String date = tmp.getDate();
        String value = tmp.getStringValue();
        String priority = tmp.getStringPriority();
        String responsible = tmp.getResp();
        String description = tmp.getDescription();
        String solutions = tmp.getSolutions();
        ArrayList<String> hist = tmp.getHistory();
        boolean  existing_change = false;

        if (!sorted_by_name.containsKey(new_name) && !(new_name.length() == 0) && !new_name.equals(old_name)) {
            existing_change = true;
            hist.add(editor + ": changed name from "+ old_name + " to " + new_name);
            File old_h = new File("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\history\\" + old_name + "_history.txt");
            File old_c = new File("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\comments\\" + old_name + "_comments.txt");
            old_h.delete();
            old_c.delete();
            old_name = new_name;


        }

        if (!val.equals("leave_unchanged") && !val.equals(value)) {
            hist.add(editor + ": changed value from "+ value + " to " + val);
            value = val;
            existing_change = true;

        }

        if (!prior.equals("leave_unchanged") && !prior.equals(priority)) {
            hist.add(editor + ": changed priority from "+ priority + " to " + prior);
            priority = prior;
            existing_change = true;
        }

        if (!(resp.length() == 0)) {
            if (users.exists(resp) && !resp.equals(responsible)) {
                hist.add(editor + ": changed responsible from "+ responsible + " to " +resp);
                responsible = resp;
                existing_change = true;
            } else {
                hist.add(editor + ": changed responsible from "+ responsible + " to no one");
                responsible = "";
                existing_change = true;
            }
        }

        if (!(descr.length() == 0)) {
            if(d_c_mode!=null && d_c_mode.equals("overwrite")){
                hist.add(editor + ": changed description from "+ description + " to " + descr);
                description = descr;
                existing_change = true;
            } else {
                hist.add(editor + ": added "+ descr + " to description");
                description += descr;
                existing_change = true;
            }
        }

        if((sol.length() != 0)) {
            if(s_c_mode != null && s_c_mode.equals("overwrite")){
                hist.add(editor + ": changed solutions from "+ solutions + " to " + sol);
                solutions = sol;
                existing_change = true;
            } else {
                hist.add(editor + ": added "+ sol + " to solutions");
                solutions += sol;
                existing_change = true;
            }
        }

        ArrayList<String> coms = tmp.getComments();
        if (existing_change) {
            Ticket new_ticket = new Ticket(old_name, date, value, priority, responsible, description, solutions, hist, coms);
            default_list.remove(tmp);
            default_list.add(new_ticket);
            sorted_by_name.remove(tmp);
            sorted_by_name.put(old_name, new_ticket);
            updateFile();
        }

    }

    public synchronized void deleteTicket(String name) {

        Ticket tmp = sorted_by_name.get(name);
        default_list.remove(tmp);
        sorted_by_name.remove(name);
        updateFile();

    }
    public synchronized void addComment(String creator, String name, String comment) {

        Ticket tmp = sorted_by_name.get(name);
        tmp.addComment(creator +": "+comment);
        sorted_by_name.remove(name);
        updateFile();

    }

    public synchronized void updateFile() {

        try (FileWriter writer = new FileWriter(file_name, false)) {
            for (int i = 0; i< default_list.size(); i++) {
                Ticket tmp = default_list.get(i);
                String text = "";
                text += tmp.getName();
                text +=  " " + tmp.getDate();
                text +=  " " + tmp.getStringValue();
                text +=  " " + tmp.getStringPriority();
                text +=  " " + tmp.getResp();
                text +=  " ^" + tmp.getDescription()+"^";
                text +=  " ~" + tmp.getSolutions()+"~";
                writer.write(text);
                writer.append("\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void updateArchive(Value val) {

        String cur_file;
        if (val == Value.BUG) {
            cur_file = bug_storage_name;
        } else{
            cur_file = feature_storage_name;
        }

        try (FileWriter writer = new FileWriter(cur_file, false)) {
            for (int i = 0; i< default_list.size(); i++){
                Ticket tmp = default_list.get(i);
                String text = "";
                text += tmp.getName();
                text +=  " " + tmp.getDate();
                text +=  " " + tmp.getStringValue();
                text +=  " " + tmp.getStringPriority();
                text +=  " " + tmp.getResp();
                text +=  " ^" + tmp.getDescription()+"^";
                text +=  " ~" + tmp.getSolutions()+"~";
                System.out.println("text " + text + " " + i);
                writer.write(text);
                writer.append("\n");
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized ArrayList<String> getDisplay() {

        ArrayList<String> newl = new ArrayList<>();
        newl.add("<table>");
        newl.add("<tr><th>name</th><th>date</th><th>value</th><th>priority</th><th>responsible</th><th>existing solutions</th></tr>");
        for(int i = 0; i< default_list.size(); i++) {
            String text = "<tr><th>";
            text+= "<form method=\"GET\" action=\"/bug_tracker_war_exploded/TicketServlet\">\n";
            text+="<input type=\"submit\" name = \"name\" value=\"" + default_list.get(i).getName()+" \">\n";
            text+="</form></th> <th>";
            //text += default_list.get(i).getName() + "</th> <th>";
            text += default_list.get(i).getDate() + "</th> <th>";
            text += default_list.get(i).getStringValue() + "</th> <th>";
            text += default_list.get(i).getStringPriority()+ "</th> <th>";
            text += default_list.get(i).getResp()+ "</th> <th>";
            //text +=  " " + default_list.get(i).getDescription();
            if (default_list.get(i).getSol()) {
                text += " + </th> ";
            } else{
                text += " - </th> ";
            }
            text+="</tr>";
            newl.add(text);
        }
        return newl;

    }

    public synchronized ArrayList<String> getSortedDisplay(String mode, String name) {

        ArrayList<String> newl = new ArrayList<>();
        newl.add("<table>");
        newl.add("<tr><th>name</th><th>date</th><th>value</th><th>priority</th><th>responsible</th><th>existing solutions</th></tr>");

        if(mode.contains("resp")) {
            for(int i = 0; i< default_list.size(); i++) {
                String text = "<tr><th>";
                text+= "<form method=\"GET\" action=\"/bug_tracker_war_exploded/TicketServlet\">\n";
                text+="<input type=\"submit\" name = \"name\" value=\"" + default_list.get(i).getName()+" \">\n";
                text+="</form></th> <th>";
                //text += default_list.get(i).getName() + "</th> <th>";
                text += default_list.get(i).getDate() + "</th> <th>";
                text += default_list.get(i).getStringValue() + "</th> <th>";
                text += default_list.get(i).getStringPriority()+ "</th> <th>";
                text += default_list.get(i).getResp()+ "</th> <th>";
                //text +=  " " + default_list.get(i).getDescription();
                if (default_list.get(i).getSol()) {
                    text += " + </th> ";
                }
                else{
                    text += " - </th> ";
                }
                text+="</tr>";
                if(default_list.get(i).getResp().equals(name)){
                    newl.add(2, text);
                }
                else{
                    newl.add(text);
                }
            }
        }

        if (mode.contains("value")) {
            for(int i = 0; i< default_list.size(); i++) {
                String text = "<tr><th>";
                text+= "<form method=\"GET\" action=\"/bug_tracker_war_exploded/TicketServlet\">\n";
                text+="<input type=\"submit\" name = \"name\" value=\"" + default_list.get(i).getName()+" \">\n";
                text+="</form></th> <th>";
                //text += default_list.get(i).getName() + "</th> <th>";
                text += default_list.get(i).getDate() + "</th> <th>";
                text += default_list.get(i).getStringValue() + "</th> <th>";
                text += default_list.get(i).getStringPriority()+ "</th> <th>";
                text += default_list.get(i).getResp()+ "</th> <th>";
                //text +=  " " + default_list.get(i).getDescription();
                if (default_list.get(i).getSol()) {
                    text += " + </th> ";
                }
                else{
                    text += " - </th> ";
                }
                text+="</tr>";
                if(default_list.get(i).getStringValue().equals("bug")){
                    if(mode.equals("value_a")) {
                        newl.add(2, text);
                    }
                    else
                    {
                        newl.add(text);
                    }
                }
                else{
                    if(mode.equals("value_a")) {
                        newl.add(text);
                    }
                    else
                    {
                        newl.add(2, text);
                    }
                }
            }
        }

        if (mode.contains("prior")) {
            int end_f = 2;
            for(int i = 0; i< default_list.size(); i++) {
                String text = "<tr><th>";
                text+= "<form method=\"GET\" action=\"/bug_tracker_war_exploded/TicketServlet\">\n";
                text+="<input type=\"submit\" name = \"name\" value=\"" + default_list.get(i).getName()+" \">\n";
                text+="</form></th> <th>";
                //text += default_list.get(i).getName() + "</th> <th>";
                text += default_list.get(i).getDate() + "</th> <th>";
                text += default_list.get(i).getStringValue() + "</th> <th>";
                text += default_list.get(i).getStringPriority()+ "</th> <th>";
                text += default_list.get(i).getResp()+ "</th> <th>";
                //text +=  " " + default_list.get(i).getDescription();
                if (default_list.get(i).getSol()) {
                    text += " + </th> ";
                }
                else{
                    text += " - </th> ";
                }
                text+="</tr>";
                if(default_list.get(i).getStringPriority().equals("high")){
                    if(mode.equals("prior_a")) {
                        newl.add(2, text);
                        end_f++;
                    } else {
                        newl.add(text);
                    }
                } else {
                    if (default_list.get(i).getStringPriority().equals("low")) {
                        if (mode.equals("prior_a")) {
                            newl.add(text);
                        } else {
                            newl.add(2, text);
                            end_f++;
                        }
                    } else {
                        newl.add(end_f, text);
                    }
                }
            }
        }

        newl.add("</table>");
        return newl;

    }


    public synchronized ArrayList<String> getArchivedDisplay() {

        ArrayList<String> newl = new ArrayList<>();
        newl.add("<table>");
        newl.add("<tr><th>name</th><th>date</th><th>priority</th><th>responsible</th><th>existing solutions</th></tr>");

        for (Ticket ticket : default_list) {
            String text = "<tr><th>";

            text += "<form method=\"GET\" action=\"/bug_tracker_war_exploded/TicketArchiveServlet/" + ticket.getStringValue() + "\">\n";
            text += "<input type=\"submit\" name = \"name\" value=\"" + ticket.getName() + " \">\n";
            text += "</form></th> <th>";

            text += ticket.getDate() + "</th> <th>";
            text += ticket.getStringPriority() + "</th> <th>";
            text += ticket.getResp() + "</th> <th>";

            if (ticket.getSol()) {
                text += " + </th> ";
            } else {
                text += " - </th> ";
            }
            text += "</tr>";
            newl.add(text);
        }
        return newl;

    }

    public synchronized Ticket getTicket(String name) {
        Ticket tmp = sorted_by_name.get(name);
        return tmp;
    }

    public synchronized int getSize() {
        return default_list.size();
    }

}
