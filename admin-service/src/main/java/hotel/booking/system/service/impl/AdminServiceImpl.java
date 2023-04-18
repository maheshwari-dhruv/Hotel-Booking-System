package hotel.booking.system.service.impl;

import hotel.booking.system.enums.Role;
import hotel.booking.system.modal.Admin;
import hotel.booking.system.modal.dto.AdminDTO;
import hotel.booking.system.modal.response.ResAdminDTO;
import hotel.booking.system.repository.AdminRepository;
import hotel.booking.system.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public String addAdmin(AdminDTO adminDTO) {
        Admin admin = getADMINFromADMINDTO(adminDTO);
        adminRepository.save(admin);
        return "Data successfully";
    }

    private Admin getADMINFromADMINDTO(AdminDTO adminDTO) {
        return Admin.builder()
                .id(UUID.randomUUID())
                .firstname(adminDTO.getFirstname())
                .lastname(adminDTO.getLastname())
                .username(adminDTO.getUsername())
                .email(adminDTO.getEmail())
                .password(adminDTO.getPassword())
                .role(Role.ADMIN)
                .active(false)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();
    }

    @Override
    public List<ResAdminDTO> getAdminDetails() {
        List<Admin> allAdmin = adminRepository.findAll();
        List<ResAdminDTO> allResAdminDTO = new ArrayList<>();

        for (Admin admin: allAdmin) {
            allResAdminDTO.add(getResAdminDTO(admin));
        }

        return allResAdminDTO;
    }

    private ResAdminDTO getResAdminDTO(Admin admin) {
        return ResAdminDTO.builder()
                .id(admin.getId())
                .fullName(admin.getFirstname() + " " + admin.getLastname())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole())
                .active(admin.isActive())
                .createdOn(admin.getCreatedOn())
                .updatedOn(admin.getUpdatedOn())
                .lastLogin(admin.getLastLogin())
                .build();
    }

    @Override
    public String updateAdminDetails(String username, AdminDTO adminDTO) throws Exception {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Username not found: " + username));

        admin.setId(admin.getId());
        admin.setFirstname(adminDTO.getFirstname());
        admin.setLastname(adminDTO.getLastname());
        admin.setUsername(adminDTO.getUsername());
        admin.setUpdatedOn(LocalDateTime.now());

        adminRepository.save(admin);
        return "Details Updated Successfully";
    }

    @Override
    public String deleteAdmin(String username) throws Exception {
        UUID id = adminRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Username not found: " + username)).getId();
        adminRepository.deleteById(id);
        return "Admin Deleted";
    }
}

