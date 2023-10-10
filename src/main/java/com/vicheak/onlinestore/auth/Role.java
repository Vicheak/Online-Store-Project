package com.vicheak.onlinestore.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    @ManyToMany
    @JoinTable(joinColumns =
    @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "auth_id", referencedColumnName = "id"))
    private List<Authority> authorities;

}
