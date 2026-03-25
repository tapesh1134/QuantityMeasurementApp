package org.quantitymeasurement.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 20)
    private String firstName;
    @Column(nullable = true,length = 20)
    private String lastName;
    @Column(nullable = false,unique = true,length = 150)
    private String email;
    @Column(nullable = true)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    private String provider;
    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;
    @JsonIgnore
    private LocalDateTime updateAt;
    @PrePersist
    protected void onCreate(){
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
    @PreUpdate
    protected  void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }
}
