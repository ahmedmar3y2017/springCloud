package com.frankmoley.lil.roomservice.patterns.creational.builder;

public class StudentBuilder {

    private String firstName;
    private String lastName;
    private int age;

    public static StudentBuilder getInstence() {

        return new StudentBuilder();
    }

    public StudentBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public StudentBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;

    }

    public StudentBuilder withAge(int age) {
        this.age = age;
        return this;


    }

    public Student build() {

        return new Student(firstName, lastName, age);
    }

}
