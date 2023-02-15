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
    private int id;

    @Column(name = "`DATE`")
    private String name;

    @Column(name = "`COUNT`")
    private String currency;

    @Column(name = "`TEMPCOLUMN`")
    private String tempColumn;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getTempColumn() {
        return tempColumn;
    }

    public void setTempColumn(String tempColumn) {
        this.tempColumn = tempColumn;
    }

    @Override
    public String toString() {
        return "Holiday [currency=" + currency + ", id=" + id + ", name=" + name + "]";
    }
    
}
