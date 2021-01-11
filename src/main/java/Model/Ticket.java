package Model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Ticket {
    private String name = "no_specific_name";
    private String date;
    private Value value = Value.BUG;
    private Priority priority = Priority.LOW;
    private String responsible = "no_resp";
    private String description = "sample text.";
    private String solutions = "-";
    private ArrayList<String> comments;
    private ArrayList<String> history;

    public Ticket(String creator, String new_name, String new_val, String new_priority, String resp, String descript, String sol) {

        if (new_name.length() != 0) {
            name = new_name;
        }
        if (new_val.equals("feature")) {
            value = Value.FEATURE;
        }
        if (new_priority.equals("medium")) {
            priority = Priority.MEDIUM;
        }
        if (new_priority.equals("high")) {
            priority = Priority.HIGH;
        }
        if (resp.length() != 0) {
            responsible = resp;
        }
        if (descript.length() != 0) {
            description = descript;
        }
        if (sol.length() != 0) {
            solutions = sol;
        }
        history = new ArrayList<>();
        comments = new ArrayList<>();

        SimpleDateFormat formatForDateNow = new SimpleDateFormat(" dd.MM.yyyy ");
        Date dateNow = new Date();
        date = formatForDateNow.format(dateNow);
        date = date.substring(1, date.length()-1);

        String file_name ="C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\history\\"+ name + "_history.txt";
        try (FileWriter writer = new FileWriter(file_name, false)) {
            String text = creator + ": created ticket.\n";
            history.add(text);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
;
        String file_name2 ="C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\comments\\"+ name + "_comments.txt";
        try (FileWriter writer2 = new FileWriter(file_name2, false)) {
            String text = "";
            writer2.write(text);
            writer2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Ticket(String new_name, String new_date, Value new_val, Priority new_priority, String resp, String descript, String sol) {

        if (new_name.length() != 0) {
            name = new_name;
        }
        date = new_date;
        value = new_val;
        priority = new_priority;
        if (resp.length() != 0) {
            responsible = resp;
        }
        if (descript.length() != 0) {
            description = descript;
        }
        if (sol.length() != 0) {
            solutions = sol;
        }

        history = new ArrayList<>();
        String file = "C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\history\\"+ new_name + "_history.txt";
        try(FileReader reader = new FileReader(file))
        {
            Scanner scan = new Scanner(reader);
            while (scan.hasNext())
            {
                history.add(scan.nextLine());
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        comments = new ArrayList<>();
        String file2 = "C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\comments\\"+ new_name + "_comments.txt";
        try(FileReader reader2 = new FileReader(file2))
        {
            Scanner scan2 = new Scanner(reader2);
            while (scan2.hasNext())
            {
                comments.add(scan2.nextLine());
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public Ticket(String new_name, String new_date, String new_val, String new_priority, String resp, String descript, String sol, ArrayList<String> hist, ArrayList<String>coms) {

        if (new_name.length() != 0) {
            name = new_name;
        }
        date = new_date;
        if (new_val.equals("feature")) {
            value = Value.FEATURE;
        }
        if (new_priority.equals("medium")) {
            priority = Priority.MEDIUM;
        }
        if (new_priority.equals("high")) {
            priority = Priority.HIGH;
        }
        if (resp.length() != 0) {
            responsible = resp;
        }
        if (descript.length() != 0) {
            description = descript;
        }
        if (sol.length() != 0) {
            solutions = sol;
        }
        history = hist;
        writeHistory();
        comments = coms;
        writeComments();

    }

    public synchronized String getName() {
        return name;
    }

    public synchronized String getDate() {
        return date;
    }

    public synchronized Value getValue() {
        return value;
    }

    public synchronized Priority getPriority() {
        return priority;
    }

    public synchronized String getResp() {
        return responsible;
    }

    public synchronized boolean getSol() {
        if(solutions.equals("-")) {
            return false;
        }
        return true;
    }

    public synchronized String getSolutions() {
        return solutions;
    }

    public synchronized String getDescription() {
        return description;
    }

    public synchronized String getStringValue() {
        if(value == Value.BUG) {
            return "bug";
        }
        return "feature";
    }

    public synchronized String getStringPriority() {
        if(priority == Priority.LOW) {
            return "low";
        }
        if(priority == Priority.MEDIUM) {
            return "medium";
        }
        if(priority == Priority.HIGH) {
            return "high";
        }
       return "error";
    }

    public synchronized ArrayList<String> getDisplayHistory(){

        ArrayList<String> display = new ArrayList<>();
        for (String s : history) {
            String text = "<p>";
            text += s;
            text += "</p>";
            display.add(text);
        }
        return display;

    }

    public synchronized ArrayList<String> getDisplayComments(){

        ArrayList<String> display = new ArrayList<>();
        for (String comment : comments) {
            String text = "<p>";
            text += comment;
            text += "</p>";
            display.add(text);
        }
        return display;

    }

    public synchronized ArrayList<String> getDisplay(){
        ArrayList<String> display = new ArrayList<>();

        display.add("<table align = \"left\">");
        String text = "<tr><th align = \"left\">name</th><th align = \"left\">";
        text+=name + " </th>";
        text+="</tr>";

        display.add(text);
        text = "<tr><th align = \"left\">date</th><th align = \"left\">";
        text+=date + " </th>";
        text+="</tr>";

        display.add(text);
        text = "<tr><th align = \"left\">value</th><th align = \"left\">";
        text+=getStringValue() + " </th>";
        text+="</tr>";

        display.add(text);
        text = "<tr><th align = \"left\">priority</th><th align = \"left\">";
        text+=getStringPriority() + " </th>";
        text+="</tr>";

        text = "<tr><th align = \"left\">responsible</th><th align = \"left\">";
        text+=responsible+ " </th>";
        text+="</tr>";

        display.add(text);
        text = "<tr><th align = \"left\">description</th><th align = \"left\">";
        text+=description + " </th>";
        text+="</tr>";

        display.add(text);
        text = "<tr><th align = \"left\">solution</th><th align = \"left\">";
        text+=solutions + " </th>";

        text+="</tr>";
        display.add(text);
        display.add("</table>");
        return display;

    }

    public synchronized ArrayList<String> getArchiveDisplay(){

        ArrayList<String> display = new ArrayList<>();
        display.add("<table align = \"left\">");
        String text = "<tr><th align = \"left\">name</th><th align = \"left\">";
        text+=name + " </th>";
        text+="</tr>";
        display.add(text);

        text = "<tr><th align = \"left\">date</th><th align = \"left\">";
        text+=date + " </th>";
        text+="</tr>";
        display.add(text);

        text = "<tr><th align = \"left\">priority</th><th align = \"left\">";
        text+=getStringPriority() + " </th>";
        text+="</tr>";
        text = "<tr><th align = \"left\">responsible</th><th align = \"left\">";
        text+=responsible+ " </th>";
        text+="</tr>";
        display.add(text);

        text = "<tr><th align = \"left\">description</th><th align = \"left\">";
        text+=description + " </th>";
        text+="</tr>";
        display.add(text);

        text = "<tr><th align = \"left\">solution</th><th align = \"left\">";
        text+=solutions + " </th>";
        text+="</tr>";
        display.add(text);

        display.add("</table>");
        return display;

    }

    public synchronized ArrayList<String> getHistory() {
            return history;
    }

    public synchronized ArrayList<String> getComments() {
        return comments;
    }

    public synchronized void writeHistory() {

        String file ="C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\history\\"+ name + "_history.txt";

        try (FileWriter writer = new FileWriter(file, false)) {
            System.out.println("IN CREATING");
            for (String s : history) {
                writer.write(s + "\n");
            }
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void writeComments() {

        String file ="C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\comments\\"+ name + "_comments.txt";
        try(FileWriter writer = new FileWriter(file, false)) {
            for(int i = 0; i < comments.size(); i++) {
                writer.write(comments.get(i) + "\n");
            }
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void addComment(String comm) {

        comments.add(comm);
        writeComments();

    }

    public synchronized void addHistory(String event) {

        history.add(event);
        writeHistory();

    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

}
