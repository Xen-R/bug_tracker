package Model;

import Model.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    @Test
    void checkUser() {
        Users us = new Users();
        us.getUsers();
        boolean actual = us.checkUser("Triss", "666");
        assertEquals(true, actual);
    }

    @Test
    void exists() {
        Users us = new Users();
        us.getUsers();
        boolean actual = us.exists("no such user here forever");
        assertEquals(false, actual);
    }
}