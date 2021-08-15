package com.smilyk.cond.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cond")
public class Conditioner extends BaseEntity{
    @Column(nullable = false)
    private String uuidCond;
    @Column(nullable = false, length = 100)
    String nameCond;
    @Column(nullable = false, unique = true, length = 100)
    private String inventoryNumber;
    @Column(nullable = false, length = 100)
    String place;
    @Column(nullable = false)
    LocalDate startDate;

    @Column
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
    }, fetch=FetchType.EAGER)
    @JoinTable(name = "conditioner_maintenance",
        joinColumns = @JoinColumn(name = "conditioner_id"),
        inverseJoinColumns = @JoinColumn(name = "maintenance_id")
    )
    Set<Maintenance> maintenance = new HashSet<>();
    Boolean start;
    /**
     * в случае остановки кондиционера, сохраняется количество часов,
     * которые он успел отработать с момента последнего ТО
     */
    Integer workedHours;
    @Column(nullable = false)
    Boolean deleted = false;
}
