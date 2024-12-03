package com.example.eams.users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RegisterUserTest {

    private RegisterUser registerUser;

    @Before
    public void setUp() throws Exception {
        registerUser = new Attendee();
    }

    @Test
    public void validateFirstNameTrue() {
        registerUser.setFirstName("Steven");
        assertTrue(registerUser.firstNameIsValid());

        registerUser.setFirstName("Ed");
        assertTrue(registerUser.firstNameIsValid());

        registerUser.setFirstName("Bartholomew");
        assertTrue(registerUser.firstNameIsValid());
    }

    @Test
    public void validateFirstNameFalse() {
        registerUser.setFirstName("");
        assertFalse(registerUser.firstNameIsValid());

        registerUser.setFirstName("Unicorn12345678910");
        assertFalse(registerUser.firstNameIsValid());

        registerUser.setFirstName("^%&%$*^((##$)#@$$#");
        assertFalse(registerUser.firstNameIsValid());
    }

    @Test
    public void validateLastNameTrue() {
        registerUser.setLastName("Wu");
        assertTrue(registerUser.lastNameIsValid());

        registerUser.setLastName("Wolfeschlegelsteinhausenbergerdorff");
        assertTrue(registerUser.lastNameIsValid());

        registerUser.setLastName("Fitzgerald");
        assertTrue(registerUser.lastNameIsValid());
    }

    @Test
    public void validateLastNameFalse() {
        registerUser.setLastName("                                   ");
        assertFalse(registerUser.lastNameIsValid());

        registerUser.setLastName("11516874987651");
        assertFalse(registerUser.lastNameIsValid());

        registerUser.setLastName("<(￣︶￣)>");
        assertFalse(registerUser.lastNameIsValid());
    }

    @Test
    public void validatePhoneNumberTrue() {
        registerUser.setPhoneNumber("123-456-7890");
        assertTrue(registerUser.phoneNumberIsValid());

        registerUser.setPhoneNumber("1234567890");
        assertTrue(registerUser.phoneNumberIsValid());

        registerUser.setPhoneNumber("0000000000");
        assertTrue(registerUser.phoneNumberIsValid());
    }

    @Test
    public void validatePhoneNumberFalse() {
        registerUser.setPhoneNumber("123$456-7890");
        assertFalse(registerUser.phoneNumberIsValid());

        registerUser.setPhoneNumber("MY PIAOLIANG TELEFONNUMMER");
        assertFalse(registerUser.phoneNumberIsValid());

        registerUser.setPhoneNumber("3741913284720398471290");
        assertFalse(registerUser.phoneNumberIsValid());
    }

    @Test
    public void validateAddressTrue() {
        registerUser.setAddress("12 Diamond Rd.", "Ottawa", "Ontario", "A1A1A1");
        assertTrue(registerUser.addressIsValid());

        registerUser.setAddress("50 diamond Road", "Toronto", "ON", "D1A3A1");
        assertTrue(registerUser.addressIsValid());

        registerUser.setAddress("520 applewood street", "johnsville", "NewBrunswick", "A1A1A1");
        assertTrue(registerUser.addressIsValid());
    }

    @Test
    public void validateAddressFalse() {
        registerUser.setAddress("12 Diamond", "Ottawa", "Ontario", "A1A1A1");
        assertFalse(registerUser.addressIsValid());

        registerUser.setAddress("50 diamond Road", "Toro#@!$nto", "ON", "D1A3A1");
        assertFalse(registerUser.addressIsValid());

        registerUser.setAddress("520 applewood street", "johnsville", "New Brunswick", "A1A 1A1");
        assertFalse(registerUser.addressIsValid());
    }

}