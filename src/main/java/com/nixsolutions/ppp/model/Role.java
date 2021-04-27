package com.nixsolutions.ppp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "roles")
@Builder
@Data
@JsonIgnoreProperties({"users", "authority"})
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable, GrantedAuthority {
    @Id
    @Column(name = "ID")
    @NonNull
    private Long id;

    @Column(name = "NAME", nullable = false, length = 128)
    @NonNull
    private String name;
    @ManyToMany
    @JoinTable(name = "userrole", joinColumns = {
            @JoinColumn(name = "role_id")}, inverseJoinColumns = {
            @JoinColumn(name = "user_id")})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}
