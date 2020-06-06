package pl.sda.jpa.starter.basic;

import javax.persistence.*;

@Entity
@Table(name = "student")

public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String fullName;
    private int yearOfStudy;

    public StudentEntity() {
    }

    public StudentEntity(String fullName, int yearOfStudy) {
        this.fullName = fullName;
        this.yearOfStudy = yearOfStudy;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    @Override
    public String toString() {
        return "\nStudentEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", year='" + yearOfStudy + '\'' +
                '}'+"\n";
    }
}
