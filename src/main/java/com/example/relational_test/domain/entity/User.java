package com.example.relational_test.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name="tb_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Integer age;

    @Column
    @CreatedDate
    private LocalDateTime created_at;

    public User(String name, String lastName, Integer age) {

        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public User(){

    }

    @PrePersist
    public void prePersist(){
        if(getCreated_at() == null){
            setCreated_at(LocalDateTime.now());
        } else {
            setCreated_at(getCreated_at());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
