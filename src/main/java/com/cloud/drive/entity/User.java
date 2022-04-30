package com.cloud.drive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    private char[] password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private Image image;

    @OneToMany(mappedBy = "user")
    private List<Cloud> cloud;

    public User(Long id) {
        this.id = id;
    }

    public User(String username, char[] password) {
        this.username = username;
        this.password = password;
    }
}
