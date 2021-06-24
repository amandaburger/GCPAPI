package com.csr.prac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Country {
    @Id
    @Column
    private int numericCode;
    @Column
    private String name;
    @Column
    private String region;
    @Column
    private String subregion;
    @Column
    private int population;

}