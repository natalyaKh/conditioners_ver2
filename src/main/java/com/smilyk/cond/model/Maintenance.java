package com.smilyk.cond.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="maint")
public class Maintenance extends BaseEntity{
    @Column(nullable = false, length = 50)
    private String uuidTypeMaintenance;

    @Column(nullable = false, length = 100)
    private String nameMaintenance;

    @Column(nullable = false)
    private Integer peopleHours;

    @Column(nullable = false)
    private Boolean deleted = false;
    /**
     *  сколько часов до следующего ТО
     */
    @Column(nullable = false)
    private Integer hoursBeforeNextMaint;

    @Column
    @ManyToMany(mappedBy = "maintenance", fetch = FetchType.EAGER)
    private Set<Conditioner> conditioners = new HashSet<>();
}
