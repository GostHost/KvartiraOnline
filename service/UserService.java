package com.example.KvartiraOnline.service;

import com.example.KvartiraOnline.dto.UserDto;
import com.example.KvartiraOnline.entity.User;
import com.example.KvartiraOnline.repository.IUserService;
import com.example.KvartiraOnline.repository.RoleRepository;
import com.example.KvartiraOnline.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        if (userExists(accountDto.getUsername())) {
            System.out.println("There is an account with username: " + accountDto.getUsername());
        }
        final User user = new User();

        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    public User registerNewAdminAccount(UserDto accountDto) {
        if (userExists(accountDto.getUsername())) {
            System.out.println("There is an account with username: " + accountDto.getUsername());
        }
        final User user = new User();

        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        return userRepository.save(user);
    }

    public User registerNewWorkerAccount(UserDto accountDto) {
        if (userExists(accountDto.getUsername())) {
            System.out.println("There is an account with username: " + accountDto.getUsername());
        }
        final User user = new User();

        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_STAFF")));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String verificationToken) {
        return null;
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {

    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {

    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByID(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String password) {
        return  passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
        return null;
    }

    @Override
    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public User updateUser2FA(boolean use2FA) {
        return null;
    }

    private boolean userExists(final String user) {
        return userRepository.findByUsername(user) != null;
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return null;
    }

    @Override
    public String isValidNewLocationToken(String token) {
        return null;
    }

    @Override
    public void addUserLocation(User user, String ip) {

    }
}
