package hotel.booking.system.modal.response;

import hotel.booking.system.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResAdminDTO {
    private UUID id;
    private String fullName;
    private String username;
    private String email;
    private Role role;
    private boolean active;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime lastLogin;
}
