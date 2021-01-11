package Model;

import Model.Ticket;
import Model.TicketList;
import Model.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketListTest {

    @Test
    void getTickets() {
        Users us =new Users();
        TicketList tl = new TicketList(us);
        tl.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\test\\resources\\tck.txt");
        int size = tl.getSize();
        boolean actual = true;
        if(size != 2) {
            actual = false;
        }
        System.out.println(size);
        assertEquals(true, actual);
    }

    @Test
    void getTicket() {
        Users us =new Users();
        TicketList tl = new TicketList(us);
        tl.getTickets("C:\\Users\\varangian\\IdeaProjects\\bug_tracker\\src\\test\\resources\\tck.txt");
        Ticket tck = tl.getTicket("tyyyyy");
        boolean actual = true;
        if(!tck.getDate().equals("09.01.2021")) {
            System.out.println(tck.getDate());
            actual = false;
        }
        if(!tck.getStringValue().equals("bug")) {
            actual = false;
        }
        if(!tck.getStringPriority().equals("medium")) {
            actual = false;
        }
        if(!tck.getResp().equals("no_resp")) {
            actual = false;
        }
        if(!tck.getDescription().equals("ryyhtrhrhtr")) {
            actual = false;
        }
        if(!tck.getSolutions().equals("-")) {
            actual = false;
        }
        assertEquals(true, actual);
    }
}