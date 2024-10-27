package com.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
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
