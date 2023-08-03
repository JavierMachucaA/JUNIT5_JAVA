package com.programming.techie;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
    ContactManager contactManager;

    @BeforeAll
    public void setupAll() {
        System.out.println("Before All Testing case for ContactManager start");
    }

    @BeforeEach
    public void setup() {
        contactManager = new ContactManager();
    }

    @Test
    public void addContact() {
        contactManager.addContact("Jhon","Doe", "0994114742");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts()
                        .stream().anyMatch(c -> c.getFirstName().equals("Jhon") &&
                        c.getLastName().equals("Doe") &&
                        c.getPhoneNumber().equals("0994114742")));
    }

    @Test
    @DisplayName("Should Not Create Contact When Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () ->
                contactManager.addContact(null,"Doe", "0994114742"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Lastname is Null")
    public void shouldThrowRuntimeExceptionWhenLastnameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () ->
                contactManager.addContact("Jhon",null, "0994114742"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Phonenumber is Null")
    public void shouldThrowRuntimeExceptionWhenPhonenumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () ->
                contactManager.addContact("Jhon","Dow", null));
    }

    @AfterEach
    public void tearDown(){
        System.out.println("Should Excecute After Each Test");
    }

    @AfterAll
    public void tearDownAll(){
        System.out.println("Should Excecute After All Test");
    }

    @Test
    @DisplayName("Should Create Contact Only on MAC OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on MAC OS")
    public void shouldCreateContactOnlyOnMac(){
        contactManager.addContact("Jhon","Doe", "0994114742");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts()
                .stream().anyMatch(c -> c.getFirstName().equals("Jhon") &&
                        c.getLastName().equals("Doe") &&
                        c.getPhoneNumber().equals("0994114742")));
    }

    @Test
    @DisplayName("Should Create Contact Only on Windows OS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    public void shouldCreateContactOnlyOnWindows(){
        contactManager.addContact("Jhon","Doe", "0994114742");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts()
                .stream().anyMatch(c -> c.getFirstName().equals("Jhon") &&
                        c.getLastName().equals("Doe") &&
                        c.getPhoneNumber().equals("0994114742")));
    }

    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shoudTestContactCreationOnDEV(){
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("Jhon","Doe", "0994114742");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @RepeatedTest(value = 5)
    @DisplayName("Test Contact Creation on Repeateadly")
    public void shoudTestContactCreationRepeatedly(){
        contactManager.addContact("Jhon","Doe", "0994114742");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Group Test by Logic")
    @Nested
    class ParameterizedTests {
        @DisplayName("Phone Number should match the required Format")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormatUsingValueSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("CSV File Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

    }
    @DisplayName("Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456798", "0123456897");
    }


}