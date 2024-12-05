package com.example.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountDBTest {

    private AccountDB accountDB;

    @BeforeEach
    public void setUp() {
        // Khởi tạo đối tượng AccountDB
        accountDB = AccountDB.getInstance();
    }

    @Test
    public void testSingleton() {
        // Kiểm tra nếu cả hai đối tượng AccountDB đều trỏ đến một đối tượng duy nhất
        AccountDB anotherAccountDB = AccountDB.getInstance();
        assertSame(accountDB, anotherAccountDB, "AccountDB should be a Singleton");
    }

    @Test
    public void testGetConnection() {
        // Kiểm tra xem kết nối có hợp lệ không
        try (Connection con = accountDB.getConnection()) {
            assertNotNull(con, "Connection should not be null");
            assertTrue(con.isValid(2), "Connection should be valid");
        } catch (SQLException e) {
            fail("Connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testSetAndGetId() {
        // Kiểm tra getter và setter cho id
        AccountDB.setId(12345);
        assertEquals(12345, AccountDB.getId(), "ID should be correctly set and retrieved");
    }

    @Test
    public void testSetAndGetIsbn() {
        // Kiểm tra getter và setter cho ISBN
        AccountDB.setIsbn("978-3-16-148410-0");
        assertEquals("978-3-16-148410-0", AccountDB.getIsbn(), "ISBN should be correctly set and retrieved");
    }
}
