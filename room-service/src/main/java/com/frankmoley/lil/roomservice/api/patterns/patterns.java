package com.frankmoley.lil.roomservice.api.patterns;


import com.frankmoley.lil.roomservice.patterns.creational.builder.StudentBuilder;
import com.frankmoley.lil.roomservice.patterns.creational.factory.PetFactory;
import com.frankmoley.lil.roomservice.patterns.creational.factory.pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.frankmoley.lil.roomservice.patterns.creational.builder.Student;


@RestController("/patterns")
public class patterns {

    @Autowired
    PetFactory petFactory;


    @PostMapping("/factory/{type}")
    public pet factory(@PathVariable("type") String type) {

        return petFactory.getPet(type);


    }

    @PostMapping("/builder/{firstName}/{lastName}/{age}")
    public Student builder(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("age") int age) {

        Student build = StudentBuilder.getInstence().withFirstName(firstName).withLastName(lastName).withAge(age).build();

        return build;

    }

}
