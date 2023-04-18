package hotel.booking.system.service;

import hotel.booking.system.modal.dto.AdminDTO;
import hotel.booking.system.modal.response.ResAdminDTO;

import java.util.List;

public interface AdminService {
    String addAdmin(AdminDTO adminDTO);

    List<ResAdminDTO> getAdminDetails();

    String updateAdminDetails(String username, AdminDTO adminDTO) throws Exception;

    String deleteAdmin(String username) throws Exception;
}
