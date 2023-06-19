package com.bikkadit.elcetronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "USERS")
public class User extends CustomFields {

    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "USERNAME")
    private String name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IMAGE_NAME")
    private String imageName;
}
