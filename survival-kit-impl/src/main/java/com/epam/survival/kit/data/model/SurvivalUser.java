package com.epam.survival.kit.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "survival_user")
@TypeDef(
        name = "user_role",
        typeClass = RolePostgresEnumType.class
)
@AllArgsConstructor
@NoArgsConstructor
public class SurvivalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
    private long id;
    private String name;
    private String username;
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    @Type(type = "user_role")
    private UserRole role;
}
