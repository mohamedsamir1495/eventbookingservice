package com.mohamedsamir1495.eventbookingsystem.domain.user;

import com.mohamedsamir1495.eventbookingsystem.domain.BaseEntity;
import com.mohamedsamir1495.eventbookingsystem.domain.event.Reservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations = new HashSet<>();
}
