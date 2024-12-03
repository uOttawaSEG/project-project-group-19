package com.example.eams.users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new Attendee();
    }

    @Test
    public void validateEmailTrue() {
        user.setEmail("hehe@gmail.com");
        assertTrue(user.emailIsValid());

        user.setEmail("a@a.ca");
        assertTrue(user.emailIsValid());

        user.setEmail("what1-_234@outlook.wiki");
        assertTrue(user.emailIsValid());
    }

    @Test
    public void validateEmailFalse() {
        user.setEmail("!mad@mail.com");
        assertFalse(user.emailIsValid());

        user.setEmail("WHAT.com");
        assertFalse(user.emailIsValid());

        user.setEmail("@.");
        assertFalse(user.emailIsValid());
    }

    @Test
    public void validatePasswordTrue() {
        user.setPassword("Pass1!");
        assertTrue(user.passwordIsValid());

        user.setPassword("dDdd1!");
        assertTrue(user.passwordIsValid());

        user.setPassword("Pa12!$");
        assertTrue(user.passwordIsValid());
    }

    @Test
    public void validatePasswordFalse() {
        user.setPassword("Pas1!");
        assertFalse(user.passwordIsValid());

        user.setPassword("pass1!");
        assertFalse(user.passwordIsValid());

        user.setPassword("PASS1!");
        assertFalse(user.passwordIsValid());

        user.setPassword("Pass!!");
        assertFalse(user.passwordIsValid());

        user.setPassword("Pass11");
        assertFalse(user.passwordIsValid());
    }
}