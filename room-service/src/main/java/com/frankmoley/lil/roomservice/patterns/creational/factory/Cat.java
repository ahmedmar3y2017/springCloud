package com.frankmoley.lil.roomservice.patterns.creational.factory;

public class Cat implements pet {
    @Override
    public void sleep() {
        System.out.println("Cat Sleep");
    }

    @Override
    public void loud() {
        System.out.println("Cat loud");

    }

    @Override
    public void eat() {
        System.out.println("Cat eat");

    }
}
