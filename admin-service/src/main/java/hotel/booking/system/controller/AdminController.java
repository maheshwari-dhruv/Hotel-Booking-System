package hotel.booking.system.controller;

import hotel.booking.system.modal.dto.AdminDTO;
import hotel.booking.system.modal.response.ResAdminDTO;
import hotel.booking.system.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add")
    public String addAdminInDB(@RequestBody AdminDTO adminDTO) {
        return adminService.addAdmin(adminDTO);
    }

    @GetMapping("/view")
    public List<ResAdminDTO> viewAllAdmin() {
        return adminService.getAdminDetails();
    }

    @PutMapping("/update/{username}")
    public String updateAdminDetails(@PathVariable String username, @RequestBody AdminDTO adminDTO) throws Exception {
        return adminService.updateAdminDetails(username, adminDTO);
    }

    @DeleteMapping("/delete/{username}")
    public String deleteAdminFromDB(@PathVariable String username) throws Exception {
        return adminService.deleteAdmin(username);
    }
}
