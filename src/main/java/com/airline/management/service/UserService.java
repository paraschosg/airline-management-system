package com.airline.management.service;

import com.airline.management.dto.RegisterRequest;
import com.airline.management.dto.UpdateUserRequest;
import com.airline.management.model.Role;
import com.airline.management.model.User;
import com.airline.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        user.setAfm(request.getAfm());
        user.setAddress(request.getAddress());
        user.setEmployeeCode(request.getEmployeeCode());
        user.setIdentityNumber(request.getIdentityNumber());

        return userRepository.save(user);
    }

    public User login(String username, String password) {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findByAfm(String afm){
        return userRepository.findByAfm(afm).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User findByIdentityNumber(String identityNumber){
        return userRepository.findByIdentityNumber(identityNumber).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setAfm(request.getAfm());
        user.setEmployeeCode(request.getEmployeeCode());
        user.setIdentityNumber(request.getIdentityNumber());
        user.setRole(request.getRole());
        user.setActive(request.isActive());

        return userRepository.save(user);
    }

    public void deleteUser(Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    public String importUsers(MultipartFile file) {

        try{

            Reader reader = new InputStreamReader(file.getInputStream());

            CSVParser scvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            for(CSVRecord record : scvParser) {

                String username = record.get("username");
                if(userRepository.existsByUsername(username)){
                    continue;
                }

                User user = new User();

                user.setUsername(username);
                user.setPassword(record.get("password"));
                user.setEmail(record.get("email"));
                user.setFirstName(record.get("firstName"));
                user.setLastName(record.get("lastName"));
                user.setRole(Role.valueOf(record.get("role")));
                user.setAfm(record.get("afm"));
                user.setAddress(record.get("address"));
                user.setEmployeeCode(record.get("employeeCode"));
                user.setIdentityNumber(record.get("identityNumber"));
                user.setActive(true);
                userRepository.save(user);
            }

            return "User has been imported";
        }catch(Exception e){
            throw new RuntimeException("Error importing");
        }
    }

    public User deactivateUser(Long id) {

        User user =
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);

        return userRepository.save(user);
    }

    public User activateUser(Long id) {

        User user =
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(true);

        return userRepository.save(user);
    }
}