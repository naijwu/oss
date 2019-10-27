package com.mjtoolbox.oss.studentcontact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjtoolbox.oss.student.Student;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable; // from 'implements Serializable', anything that needs to go through a network
import java.util.Date;

// Object of student from database
@Entity // Tells Spring framework the class is an object that needs to be mapped to db
@Data // Lombok specific; makes it create getters and setters
@Table(name="student_contact", schema="public")
public class StudentContact implements Serializable {

    // for primary key
    @Id
    @Column(name="student_contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long student_contact_id;

    // for foreign key (from student table)
    @JoinColumn(name = "membership_id") // basically what's the foreign key of table student
    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @Column(name = "membership_id", insertable=false, updatable=false) // prevents a circular relationship thing (that may result in creating another student object)
    private long membership_id;

    @Column(name="cell_phone")
    private String cell_phone;

    @Column(name="email")
    private String email;

    @Column(name="home_phone")
    private String home_phone;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="province")
    private String province;

    @Column(name="postal_code")
    private String postal_code;

    @CreationTimestamp
    @Column(name="last_update")
    @Setter(AccessLevel.NONE)
    private Date last_update;
}
