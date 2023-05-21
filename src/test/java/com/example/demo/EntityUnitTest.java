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



    @BeforeAll
    void init() {
        d1 = new Doctor("John", "Doe", 35, "johndoe@example.com");
        p1 = new Patient("Jane", "Doe", 35, "janedoe@example.com");
        r1 = new Room("Room 1");
        a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T10:00:00"), LocalDateTime.parse("2020-01-01T10:30:00"));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T10:00:00"), LocalDateTime.parse("2020-01-01T10:30:00"));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T10:00:00"), LocalDateTime.parse("2020-01-01T10:30:00"));
    }

    @Test
    void testDoctorConstructor() {
        assertEquals("John", d1.getFirstName());
        assertEquals("Doe", d1.getLastName());
        assertEquals(35, d1.getAge());
        assertEquals("johndoe@example.com", d1.getEmail());
    }

    @Test
    void testDoctorSetterAndGetter() {
        Doctor d = new Doctor("David", "Niven", 49, "david@example.com");
        d.setFirstName("John");
        d.setLastName("Doe");
        d.setId(123);
        d.setAge(61);
        d.setEmail("johndoe@mail.com");
        assertEquals("John", d.getFirstName());
        assertEquals("Doe", d.getLastName());
        assertEquals(123, d.getId());
        assertEquals(61, d.getAge());
        assertEquals("johndoe@mail.com", d.getEmail());
    }

    @Test
    void testDoctorNullAndEmpty() {
        Doctor doctor = new Doctor();
        doctor.setFirstName(null);
        doctor.setLastName("");
        assertNull(doctor.getFirstName());
        assertEquals("", doctor.getLastName());
        assertNull(doctor.getEmail());
        assertEquals(0,doctor.getAge());

    }

    @Test
    void testRoomConstructor() {
        assertEquals("Room 1", r1.getRoomName());
    }

    @Test
    void testRoomNullAndEmpty() {
        Room room = new Room("");
        assertEquals("", room.getRoomName());
        Room room2 =  new Room();
        assertNull(room2.getRoomName());
    }

    @Test
    void testPersonConstructor() {
        Person person = new Person("John", "Doe", 35, "testmail@mail.com");
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals(35, person.getAge());
        assertEquals("testmail@mail.com", person.getEmail());
    }

    @Test
    void testPersonNullAndEmpty() {
        Person person = new Person();
        person.setFirstName(null);
        person.setLastName("");
        assertNull(person.getFirstName());
        assertEquals("", person.getLastName());
        assertNull(person.getEmail());
        assertEquals(0,person.getAge());
    }

    @Test
    void testPatientConstructor() {
        assertEquals("Jane", p1.getFirstName());
        assertEquals("Doe", p1.getLastName());
        assertEquals(35, p1.getAge());
        assertEquals("janedoe@example.com", p1.getEmail());
    }

    @Test
    void testPatientSetterAndGetter() {
        Patient patient = new Patient();
        patient.setId(123);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setAge(61);
        patient.setEmail("johndoe@mail.com");
        assertEquals(123, patient.getId());
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals(123, patient.getId());
        assertEquals(61, patient.getAge());
        assertEquals("johndoe@mail.com", patient.getEmail());
    }

    @Test
    void testPatientNullAndEmpty() {
        Patient patient = new Patient();
        patient.setFirstName(null);
        patient.setLastName("");
        assertNull(patient.getFirstName());
        assertEquals("", patient.getLastName());
        assertNull(patient.getEmail());
        assertEquals(0,patient.getAge());
    }


    @Test
    void testAppointmentConstructor() {
        Patient patient = new Patient("John", "Doe", 35, "testmail@mail.com");
        Doctor doctor = new Doctor("Doctor", "Smith", 200, "drsmith@example.com");
        Room room = new Room("Room1");
        LocalDateTime startsAt = LocalDateTime.of(2023, 5, 13, 10, 0);
        LocalDateTime finishesAt = LocalDateTime.of(2023, 5, 13, 11, 0);

        Appointment appointment = new Appointment(patient, doctor, room, startsAt, finishesAt);

        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(room, appointment.getRoom());
        assertEquals(startsAt, appointment.getStartsAt());
        assertEquals(finishesAt, appointment.getFinishesAt());
    }

    @Test
    void testAppointmentGetterSetter() {
        Patient patient = new Patient("John", "Doe", 35, "testmail@mail.com");
        Doctor doctor = new Doctor("Doctor", "Smith", 200, "drsmith@example.com");
        Room room = new Room("Room1");
        LocalDateTime startsAt = LocalDateTime.of(2023, 5, 13, 10, 0);
        LocalDateTime finishesAt = LocalDateTime.of(2023, 5, 13, 11, 0);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setRoom(room);
        appointment.setStartsAt(startsAt);
        appointment.setFinishesAt(finishesAt);

        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(room, appointment.getRoom());
        assertEquals(startsAt, appointment.getStartsAt());
        assertEquals(finishesAt, appointment.getFinishesAt());
    }

    @Test
    void testOverlappingAppointments() {
        Patient patient1 = new Patient("John", "Doe", 35, "testmail@mail.com");
        Doctor doctor1 = new Doctor("Doctor", "Smith", 200, "drsmith@example.com");
        Room room1 = new Room("Room1");

        LocalDateTime startsAt1 = LocalDateTime.of(2023, 5, 13, 10, 0);
        LocalDateTime finishesAt1 = LocalDateTime.of(2023, 5, 13, 11, 0);
        Appointment appointment1 = new Appointment(patient1, doctor1, room1, startsAt1, finishesAt1);

        LocalDateTime startsAt2 = LocalDateTime.of(2023, 5, 13, 10, 30);
        LocalDateTime finishesAt2 = LocalDateTime.of(2023, 5, 13, 11, 30);
        Appointment appointment2 = new Appointment(patient1, doctor1, room1, startsAt2, finishesAt2);

        LocalDateTime startsAt3 = LocalDateTime.of(2023, 5, 13, 9, 30);
        LocalDateTime finishesAt3 = LocalDateTime.of(2023, 5, 13, 10, 30);
        Appointment appointment3 = new Appointment(patient1, doctor1, room1, startsAt3, finishesAt3);



        assertTrue(appointment1.overlaps(appointment2));
        assertTrue(appointment1.overlaps(appointment3));



    }


}
