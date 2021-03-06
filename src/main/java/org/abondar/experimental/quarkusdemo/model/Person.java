package org.abondar.experimental.quarkusdemo.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@RegisterForReflection
public class Person implements Serializable {

    private static long serialVersionUID = -21L;

    private long id;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 50)
    private String phoneNumber;

    public Person() {
    }

    public Person(@NotNull @Size(max = 50) String firstName, @NotNull @Size(max = 50) String lastName, @NotNull @Size(max = 50) String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Person(long id, @NotNull @Size(max = 50) String firstName, @NotNull @Size(max = 50) String lastName, @NotNull @Size(max = 50) String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
