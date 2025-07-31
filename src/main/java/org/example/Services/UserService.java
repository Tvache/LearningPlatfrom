package org.example.Services;

import org.example.Entities.User;
import org.example.Repositories.UserRepository;
import org.example.DTOs.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);

        if (userDTO.getPassword() != null && !userDTO.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContaining(email, pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findTop3UsersByTotalPayment() {
        return userRepository.findTop3UsersByTotalPayment();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findUsersWithNoSuccessfulPayments() {
        return userRepository.findUsersWithNoSuccessfulPayments();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findMostRecentSuccessfulPaymentPerUser() {
        return userRepository.findMostRecentSuccessfulPaymentPerUser();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAverageProgressPerUser() {
        return userRepository.findAverageProgressPerUser();
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
