package org.bonn.se2.model.objects.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */


public class Student extends User implements Serializable {

    private String vorname;
    private String nachname;
    private String vollstName;
    private String studienfach;
    private String job;
    private String arbeitgeber;

    private LocalDate geburtstag;
    private Integer fachsemester;
    private Integer studentID;

    public Student() {
    }

    public Student(User user) {
        super(user.getUsername(), user.getEmail(), user.getPasswort());
    }

    public Student(String vorname, String nachname, String studienfach,
                   String job, String arbeitgeber, LocalDate bday,
                   Integer fachsemester) {

        this.vorname = vorname;
        this.nachname = nachname;
        this.studienfach = studienfach;
        this.job = job;
        this.arbeitgeber = arbeitgeber;
        this.geburtstag = bday;
        this.fachsemester = fachsemester;

    }

    public Student(String vorname, String nachname, String studienfach,
                   String job, String arbeitgeber, LocalDate bday,
                   Integer fachsemester, Integer id) {

        this.vorname = vorname;
        this.nachname = nachname;
        this.studienfach = studienfach;
        this.job = job;
        this.arbeitgeber = arbeitgeber;
        this.geburtstag = bday;
        this.fachsemester = fachsemester;
        this.studentID = id;

    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVollstName() {
        return getVorname() + " " + getNachname();
    }

    public String getStudienfach() {
        return studienfach;
    }

    public void setStudienfach(String studienfach) {
        this.studienfach = studienfach;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getArbeitgeber() {
        return arbeitgeber;
    }

    public void setArbeitgeber(String arbeitgeber) {
        this.arbeitgeber = arbeitgeber;
    }

    public LocalDate getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(LocalDate geburtstag) {
        this.geburtstag = LocalDate.from(geburtstag);
    }

    public Integer getFachsemester() {
        return fachsemester;
    }

    public void setFachsemester(Integer fachsemester) {
        this.fachsemester = fachsemester;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(getStudentID(), student.getStudentID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", studienfach='" + studienfach + '\'' +
                ", job='" + job + '\'' +
                ", arbeitgeber='" + arbeitgeber + '\'' +
                ", geburtstag=" + geburtstag +
                ", fachsemester=" + fachsemester +
                ", studentID=" + studentID +
                ", username=" + getUsername() +
                ", email=" + getEmail() +
                ", userId=" + getUserID() +
                '}';
    }
}
