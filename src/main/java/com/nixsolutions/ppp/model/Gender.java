package com.nixsolutions.ppp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "genders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gender implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 128)
    private String name;

}
