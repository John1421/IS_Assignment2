package com.server;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private long id;
    private String name;
    private Integer age;
    private Gender gender;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
