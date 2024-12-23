package com.frankmoley.lil.roomservice.patterns.creational.factory;

public class Dog implements pet {
    @Override
    public void sleep() {
        System.out.println("Dog Sleep");

    }

    @Override
    public void loud() {
        System.out.println("Dog loud");

    }

    @Override
    public void eat() {
        System.out.println("Dog eat");

    }
}
