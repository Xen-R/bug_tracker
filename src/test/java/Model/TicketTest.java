package Model;

import Model.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TicketTest {

    @Test
    void getName() {
        Ticket tck = new Ticket("cr", "ticket", "bug", "medium", "no_one", "","" );
        boolean actual = tck.getName().equals("ticket");
        assertEquals(true, actual);

    }


    @Test
    void getResp() {
        Ticket tck = new Ticket("cr", "ticket", "bug", "medium", "no_resp", "","" );
        boolean actual = tck.getResp().equals("no_resp");
        assertEquals(true, actual);
    }

    @Test
    void getSol() {
        Ticket tck = new Ticket("cr", "ticket", "bug", "medium", "no_resp", "","" );
        boolean actual = tck.getSol();
        assertEquals(false, actual);
    }


    @Test
    void getStringValue() {
        Ticket tck = new Ticket("cr", "ticket", "bug", "medium", "no_resp", "","" );
        boolean actual = tck.getStringValue().equals("bug");
        assertEquals(true, actual);
    }

    @Test
    void getStringPriority() {
        Ticket tck = new Ticket("cr", "ticket", "bug", "medium", "no_resp", "","" );
        boolean actual = tck.getStringPriority().equals("medium");
        assertEquals(true, actual);
    }
}