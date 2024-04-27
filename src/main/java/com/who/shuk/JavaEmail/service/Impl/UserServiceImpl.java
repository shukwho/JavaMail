package com.who.shuk.JavaEmail.service.Impl;

import com.who.shuk.JavaEmail.model.Confirmation;
import com.who.shuk.JavaEmail.model.User;
import com.who.shuk.JavaEmail.repository.ConfirmationRepository;
import com.who.shuk.JavaEmail.repository.UserRepository;
import com.who.shuk.JavaEmail.service.EmailService;
import com.who.shuk.JavaEmail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    @Override
    public User saveUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        user.setEnabled(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
        emailService.sendSimpleMailMessage(user.getName(),user.getEmail(),confirmation.getToken());

        //Send email to user with token
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
       // confirmationRepository.delete(confirmation);
        return Boolean.TRUE;
    }
}
