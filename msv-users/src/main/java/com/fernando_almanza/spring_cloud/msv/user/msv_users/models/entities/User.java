package com.fernando_almanza.spring_cloud.msv.user.msv_users.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "tb_users")
public class User {

    private static Logger log = LoggerFactory.getLogger(User.class);

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "student_name", nullable = false, length = 50)
    @NotBlank()
    private String name;

    @Column(unique = true, nullable = false, length = 150, name = "student_email")
    @NotEmpty()
    @Email()
    private String email;

    @NotBlank()
    @Length(min = 8)
    private String password;

    @Transient
    private Integer age;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void cleanAndTransform(){
        this.email = this.cleanAndTransformText(this.getEmail());
        this.password = this.cleanAndTransformText(this.getPassword());
        this.name = this.cleanAndTransformText(this.getName());
    }

    private String cleanAndTransformText(String text){
        if (text == null){
            return null;
        }
        return text.toLowerCase().trim();
    }

    @PrePersist
    public void logNewUserAttempt() {
        if (gender == null){
            gender = Gender.OTHERS;
        }
        log.info("Attempting to add new user with username: {}", name);
    }

    @PostPersist
    public void logNewUserAdded() {
        log.info("Added user '{}' with ID: {}", name, id);
    }

    @PreRemove
    public void logUserRemovalAttempt() {
        log.info("Attempting to delete user: {}", name);
    }

    @PostRemove
    public void logUserRemoval() {
        log.info("Deleted user: {}", name);
    }

    @PreUpdate
    public void logUserUpdateAttempt() {
        log.info("Attempting to update user: {}", name);
    }

    @PostUpdate
    public void logUserUpdate() {
        log.info("Updated user: {}", name);
    }

    @PostLoad
    public void logUserLoad() {
        age = Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears();
    }

}
