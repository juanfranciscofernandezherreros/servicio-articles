package com.fernandez.api.articles.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "username" , length = 20)
    @NotNull
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String fistName;

    @Column(name = "second_surname")
    private String lastName;

    @Column(name = "email" , unique = true, length = 100)
    @NotNull
    private String email;

    @Column(name = "image_profile", length = 500000)
    private String imageProfile;

    @Column(name = "password" , length = 60)
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "rol_id") })
    private Set<Rol> roles = new HashSet<>();

}
