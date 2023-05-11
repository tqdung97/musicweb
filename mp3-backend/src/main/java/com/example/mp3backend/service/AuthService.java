package com.example.mp3backend.service;

import com.example.mp3backend.entity.TokenConfirm;
import com.example.mp3backend.entity.User;
import com.example.mp3backend.exception.BadRequestException;
import com.example.mp3backend.exception.NotFoundException;
import com.example.mp3backend.reponse.LoginResponse;
import com.example.mp3backend.request.RegisterRequest;
import com.example.mp3backend.repository.TokenConfirmRepository;
import com.example.mp3backend.repository.UserRepository;
import com.example.mp3backend.request.LoginRequest;
import com.example.mp3backend.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {
    @Autowired
    TokenConfirmRepository tokenConfirmRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailService mailService;

    public LoginResponse login (LoginRequest request) {

        // Tạo đối tượng
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        // Xác thực từ đối tượng
        Authentication authentication = authenticationManager.authenticate(token);

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo token và trả về cho client
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String tokenJwt = jwtTokenUtil.generateToken(userDetails);

        // Thông tin trả về cho Client
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Not found user with email = " + request.getEmail());
                });
        return new LoginResponse(user, tokenJwt, true);
    }
    @Transactional
    public String register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            User user = userOptional.get();
                if(!user.getEnabled()
                        && user.getName().equals(request.getName())
                        && user.getEmail().equals(request.getEmail()) ){
                    // Generate ra token va send mail
                    // return link kích hoạt ở đây
                    return generateTokenAndSendMail(user);
                }
            throw new BadRequestException("Tài khoản đã được kích hoạt");
        }
        // Tạo user mới
        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                "USER",true);
        userRepository.save(user);
        return generateTokenAndSendMail(user);

    }

    public String generateTokenAndSendMail(User user) {
        // Tạo token
        String generateToken = UUID.randomUUID().toString();
        TokenConfirm tokenConfirm = new TokenConfirm(
                generateToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

        tokenConfirmRepository.save(tokenConfirm);

        // Tạo link và send email
        String link = "http://localhost:8081/api/auth/confirm?token=" + generateToken;
        mailService.send(user.getEmail(), "Kích hoạt tài khoản", link);

        return link;
    }
    @Transactional
    public String confirm(String token) {
        Optional<TokenConfirm> tokenConfirmOptional = tokenConfirmRepository.findByToken(token);
        if(tokenConfirmOptional.isEmpty()) {
            throw new NotFoundException("Token not found");
        }

        // Kiểm tra xem token đã được kích hoạt hay chưa
        TokenConfirm tokenConfirm = tokenConfirmOptional.get();
        if(tokenConfirm.getConfirmedAt() != null) {
            throw new BadRequestException("Token đã được kích hoạt");
        }

        // Kiểm tra còn thời gian hay không
        if(tokenConfirm.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token đã hết hạn");
        }

        // Set lại thời gian kích hoạt của token
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);

        // Kích hoạt user
        User user = tokenConfirm.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return "confirmed";
    }
}
