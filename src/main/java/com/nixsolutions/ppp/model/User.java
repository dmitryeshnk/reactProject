package com.nixsolutions.ppp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity(name = "users")
@JsonIgnoreProperties("captcha")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 50, unique = true)
    @Size(min = 2, max = 50, message = "username must contain at least 2 characters")
    @NotNull(message = "this field must not be empty")
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 50)
    @NotNull(message = "this field must not be empty")
    @Size(min = 2, max = 50, message = "password must contain at least 2 characters")
    private String password;

    @Column(name = "EMAIL", nullable = false, length = 50, unique = true)
    @Email(message = "enter a real email")
    @NotNull(message = "this field must not be empty")
    private String email;

    @Column(name = "BIRTHDAY", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "this field must not be empty")
    @Past(message = "must be past time")
    private Date birthday;

    @NotNull
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userrole", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @Transient
    private String captcha;
}

