package com.shadow.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends BaseEntity {
    private String name;
    private String address;
    private String phone;
    private String email;

    @ElementCollection
    private List<String> workingDays;

    private LocalTime openTime;
    private LocalTime closeTime;

    @ManyToOne
    private Store store;

    @OneToOne
    private User manager;
}
