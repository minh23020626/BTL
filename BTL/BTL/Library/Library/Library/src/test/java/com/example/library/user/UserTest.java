//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.library.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    public UserTest() {
    }

    @Test
    public void testUserConstructorWithClassname() {
        User user = new User("john_doe", "password123", "John Doe", "1234567890", "Class A");
        Assertions.assertEquals("john_doe", user.getUsername());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertEquals("John Doe", user.getName());
        Assertions.assertEquals("1234567890", user.getPhone());
        Assertions.assertEquals("Class A", user.getClassname());
    }

    @Test
    public void testUserConstructorWithRole() {
        User user = new User("john_doe", "password123", "John Doe", "admin", "1234567890", "Class A");
        Assertions.assertEquals("john_doe", user.getUsername());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertEquals("John Doe", user.getName());
        Assertions.assertEquals("admin", user.getRole());
        Assertions.assertEquals("1234567890", user.getPhone());
        Assertions.assertEquals("Class A", user.getClassname());
    }

    @Test
    public void testSetAndGetId() {
        User user = new User("john_doe", "password123", "John Doe", "1234567890", "Class A");
        user.setId(1);
        Assertions.assertEquals(1, user.getId());
    }

    @Test
    public void testDefaultValues() {
        User user = new User("jane_doe", "mypassword", "Jane Doe", "teacher", "0987654321", "Class B");
        Assertions.assertNotNull(user.getUsername());
        Assertions.assertNotNull(user.getPassword());
        Assertions.assertNotNull(user.getName());
        Assertions.assertNotNull(user.getRole());
        Assertions.assertNotNull(user.getPhone());
        Assertions.assertNotNull(user.getClassname());
    }
}
