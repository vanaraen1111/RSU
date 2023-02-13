package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`AZLM_TGE_CALENDAR`")
public class Holiday {
    @Id
    @Column(name = "`CALENDAR_ID`")
    private Integer id;

    @Column(name = "`DATE`")
    private String name;

    @Column(name = "`COUNT`")
    private String currency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Holiday [currency=" + currency + ", id=" + id + ", name=" + name + "]";
    }
    
}
