//package com.example.eams;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//import android.widget.TextView;
//
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//
//import com.example.eams.users.Attendee;
//import com.example.eams.users.RegisterUser;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class MainRegisterTest {
//    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);
//    private MainActivity mActivity = null;
//    private TextView text;
//    private RegisterUser user;
//
//    @Before
//    public void setUp() throws Exception {
//
//        user = new Attendee(
//                "test@example.com",
//                "password",
//                "John",
//                "Doe",
//                "123-456-7890",
//                "123 Main St",
//                "Anytown",
//                "Ontario",
//                "A1B 2C3"
//        );
//    }
//
//    @Test
//    public void checkFirstNameIsValid() {
//        assertNotNull(mActivity.findViewById(R.id.firstName));
//        user.setFirstName("John");
//        assertTrue("First name should be valid", user.firstNameIsValid());
//
//        // Test invalid first name with numbers
//        user.setFirstName("John123");
//        assertFalse("First name should be invalid with numbers", user.firstNameIsValid());
//
//        // Test invalid first name with special characters
//        user.setFirstName("John!");
//        assertFalse("First name should be invalid with special characters", user.firstNameIsValid());
//
//        // Test invalid empty first name
//        user.setFirstName("");
//        assertFalse("First name should be invalid if empty", user.firstNameIsValid());
//    }
//
//    @Test
//    public void testLastNameIsValid() {
//        // Test valid last name
//        user.setLastName("Doe");
//        assertTrue("Last name should be valid", user.lastNameIsValid());
//
//        // Test invalid last name with numbers
//        user.setLastName("Doe123");
//        assertFalse("Last name should be invalid with numbers", user.lastNameIsValid());
//
//        // Test invalid last name with special characters
//        user.setLastName("Doe@");
//        assertFalse("Last name should be invalid with special characters", user.lastNameIsValid());
//
//        // Test invalid empty last name
//        user.setLastName("");
//        assertFalse("Last name should be invalid if empty", user.lastNameIsValid());
//    }
//}
