package com.alicjawozniak.ticketbooker.domain.room;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String number;

}
