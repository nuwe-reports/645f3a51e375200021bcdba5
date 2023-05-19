package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;


    @Test
    public void testDoctorConstructor() {
        Doctor doctor = new Doctor("John", "Doe", 35, "johndoe@example.com");
        assertEquals("John", doctor.getFirstName());
        assertEquals("Doe", doctor.getLastName());
        assertEquals(35, doctor.getAge());
        assertEquals("johndoe@example.com", doctor.getEmail());
    }

    @Test
    public void testDoctorSetterAndGetter() {
        Doctor doctor = new Doctor();
        doctor.setId(123);
        assertEquals(123, doctor.getId());
    }

    @Test
    public void testDoctorNullAndEmpty() {
        Doctor doctor = new Doctor();
        doctor.setFirstName(null);
        doctor.setLastName("");
        assertNull(doctor.getFirstName());
        assertEquals("", doctor.getLastName());
    }

    @Test
    public void testDoctorBoundaryValue() {
        Doctor doctor = new Doctor("John", "Doe", 200, "johndoe@example.com");
        assertEquals(200, doctor.getAge());
    }

    @Test
    public void testRoomConstructor() {
        Room room = new Room("Room 1");
        assertEquals("Room 1", room.getRoomName());
    }

    @Test
    public void testRoomDefaultConstructor() {
        Room room = new Room();
        assertNull(room.getRoomName());
    }



}
