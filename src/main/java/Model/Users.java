package Model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Users {
    private HashMap<String, String> userlist;

    public Users() {
        userlist = new HashMap<>();
    }

    public synchronized void getUsers() {

        try(FileReader reader = new FileReader("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\users.txt"))
        {
            Scanner scan = new Scanner(reader);
            while (scan.hasNext())
            {
                String user_data = scan.nextLine();
                String[] tokens = user_data.split(" ");
                String name = tokens[0];
                String password = tokens[1];
                userlist.put(name, password);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public synchronized void updateFile() {

        try(FileWriter writer = new FileWriter("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\main\\resources\\users.txt", false)) {
            for(Map.Entry<String, String> entry: userlist.entrySet()){
                String text = "";
                text += entry.getKey();
                text +=  " " + entry.getValue();
                writer.write(text);
                writer.append("\n");
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized boolean addUser(String name, String password) {

        if(name.length() == 0 || password.length() == 0) {
            return false;
        }
        if(userlist.containsKey(name)) {
            return false;
        }
        userlist.put(name, password);
        updateFile();
        return true;

    }

    public synchronized boolean checkUser(String name, String password) {

        if(name.length() == 0 || password.length() == 0) {
            return false;
        }
        if(userlist.containsKey(name)) {
            if (userlist.get(name).equals(password)) {
                return true;
            }
        }
        return false;

    }

    public synchronized boolean exists(String name){

        if(userlist.containsKey(name)) {
            return true;
        }
        return false;

    }

}
