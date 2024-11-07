package com.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;
    private String name;
    private Integer age;
    private Gender gender;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
