package hotel.booking.system.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
}
