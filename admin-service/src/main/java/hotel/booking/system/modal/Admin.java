package hotel.booking.system.modal;

import hotel.booking.system.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "createdOn", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdOn;

    @Column(name = "updatedOn", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedOn;

    @Column(name = "lastLogin", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastLogin;
}

