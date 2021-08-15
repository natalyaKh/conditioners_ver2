package com.smilyk.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * BaseEntity class extends BaseEntity
 */
@MappedSuperclass
public class BaseEntity {
    /**
     * The unique id of model.
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
