package com.frankmoley.lil.roomservice.patterns.creational.factory;

import org.springframework.stereotype.Component;

@Component
public class PetFactory {


    public pet getPet(String type) {

        if (type == null || type.equals("")) {
            throw new IllegalArgumentException("Invalid pet type");
        }

        switch (type) {

            case "cat":
                return new Cat();
            case "dog":
                return new Dog();

            default:
                throw new IllegalArgumentException("Invalid pet type");
        }

    }


}
