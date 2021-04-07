package com.nodey.ips.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity for postgreSQL database, using Hibernate
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ips")
public class IP {

    //ID of every object
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //IP for proxy settings
    @Column(name = "IP")
    private String ip;

    //PORT for proxy settings
    @Column(name = "PORT")
    private String port;
}
