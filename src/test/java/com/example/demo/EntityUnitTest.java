package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

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
    private Appointment a4_rightOverlap_a1;
    private Appointment a5_leftOverlap_a1;
    private Appointment a6_innerOverlap_a1;
    private Appointment a7_noOverlap_a1;

    @BeforeAll
    void init() {
        d1 = new Doctor("John", "Doe", 35, "johndoe@example.com");
        p1 = new Patient("Jane", "Doe", 35, "janedoe@example.com");
        r1 = new Room("Room 1");
        a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T10:00:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTime.parse("2020-01-01T10:30:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        a2 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T10:00:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTime.parse("2020-01-01T11:30:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        a3 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T09:00:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTime.parse("2020-01-01T10:30:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        a4_rightOverlap_a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T10:15:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTime.parse("2020-01-01T11:00:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        a5_leftOverlap_a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T09:00:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTime.parse("2020-01-01T10:15:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        a6_innerOverlap_a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T10:10:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTime.parse("2020-01-01T10:20:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        a7_noOverlap_a1 = new Appointment(p1, d1, r1, LocalDateTime.parse("2020-01-01T12:00:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME), LocalDateTime.parse("2020-01-01T13:00:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
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
    void testSaveDoctor() {
        Doctor doctor = new Doctor("John", "Doe", 35, "johndoe@example.com");
        Doctor savedDoctor = entityManager.persistAndFlush(doctor);

        assertNotNull(savedDoctor);
        assertEquals("John", savedDoctor.getFirstName());
        assertEquals("Doe", savedDoctor.getLastName());
        assertEquals(35, savedDoctor.getAge());
        assertEquals("johndoe@example.com", savedDoctor.getEmail());
    }

    @Test
    void testGetDoctorById() {
        Doctor doctor = new Doctor("Jane", "Smith", 40, "janesmith@example.com");

        Doctor savedDoctor = entityManager.persistAndFlush(doctor);

        Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());

        assertNotNull(retrievedDoctor);

        assertEquals("Jane", retrievedDoctor.getFirstName());
        assertEquals("Smith", retrievedDoctor.getLastName());
        assertEquals(40, retrievedDoctor.getAge());
        assertEquals("janesmith@example.com", retrievedDoctor.getEmail());
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
    void testSavePatient() {
        Patient patient = new Patient("Helen", "Ill", 25, "helen@example.com");
        Patient savedPatient = entityManager.persistAndFlush(patient);

        assertNotNull(savedPatient);
        assertEquals("Helen", savedPatient.getFirstName());
        assertEquals("Ill", savedPatient.getLastName());
        assertEquals(25, savedPatient.getAge());
        assertEquals("helen@example.com", savedPatient.getEmail());
    }

    @Test
    void testGetPatientById() {
        Patient patient = new Patient("Helen", "Ill", 25, "helen@example.com");
        Patient savedPatient = entityManager.persistAndFlush(patient);
        Patient retrievePatient = entityManager.find(Patient.class, savedPatient.getId());

        assertNotNull(retrievePatient);
        assertEquals("Helen", retrievePatient.getFirstName());
        assertEquals("Ill", retrievePatient.getLastName());
        assertEquals(25, retrievePatient.getAge());
        assertEquals("helen@example.com", retrievePatient.getEmail());
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
        Appointment appointment = new Appointment();
        appointment.setId(500);
        appointment.setPatient(p1);
        appointment.setDoctor(d1);
        appointment.setRoom(r1);
        LocalDateTime startsAt = LocalDateTime.of(2023, 5, 13, 10, 0);
        appointment.setStartsAt(startsAt);
        LocalDateTime finishesAt = LocalDateTime.of(2023, 5, 13, 11, 0);
        appointment.setFinishesAt(finishesAt);

        assertEquals(500, appointment.getId());
        assertEquals(d1, appointment.getDoctor());
        assertEquals(r1, appointment.getRoom());
        assertEquals(startsAt, appointment.getStartsAt());
        assertEquals(finishesAt, appointment.getFinishesAt());
    }

    @Test
    void testAppointmentNullAndEmpty() {
        Appointment appointment = new Appointment();

        assertEquals(0, appointment.getId());
        assertNull(appointment.getDoctor());
        assertNull(appointment.getPatient());
        assertNull(appointment.getRoom());
        assertNull(appointment.getStartsAt());
        assertNull(appointment.getFinishesAt());
    }

    @Test
    void testValidDatesAppointment() {
        Appointment appointment = new Appointment(p1, d1, r1, null, null);
        assertFalse(appointment.areValidDates());
        appointment.setStartsAt(LocalDateTime.parse("2020-01-01T10:30:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        assertFalse(appointment.areValidDates());
        appointment.setStartsAt(null);
        appointment.setFinishesAt(LocalDateTime.parse("2020-01-01T11:30:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        assertFalse(appointment.areValidDates());
        appointment.setStartsAt(LocalDateTime.parse("2020-01-01T12:30:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        assertFalse(appointment.areValidDates());
        appointment.setStartsAt(LocalDateTime.parse("2020-01-01T10:30:00",DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        assertTrue(appointment.areValidDates());
    }
    @Test
    void testOverlappingAppointments() {
        assertTrue(a1.overlaps(a2));
        assertTrue(a1.overlaps(a3));
        assertTrue(a1.overlaps(a4_rightOverlap_a1));
        assertTrue(a1.overlaps(a5_leftOverlap_a1));
        assertTrue(a1.overlaps(a6_innerOverlap_a1));
        assertFalse(a1.overlaps(a7_noOverlap_a1));
    }

}
