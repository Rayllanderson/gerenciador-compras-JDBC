package com.rayllanderson.gerenciadordecompras.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1, max = 50)
    private String name;

    @DecimalMax("999999")
    private BigDecimal budget;

    @Transient
    private BigDecimal completedPercentage;

    @JsonIgnore
    @ManyToOne
    private User user;

    public Category(Long id) {
        this.id = id;
    }
}
