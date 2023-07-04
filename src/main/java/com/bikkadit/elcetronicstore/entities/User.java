package com.bikkadit.elcetronicstore.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity

@Table(name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends CustomFields {

    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IMAGE_NAME")
    private String imageName;
}
