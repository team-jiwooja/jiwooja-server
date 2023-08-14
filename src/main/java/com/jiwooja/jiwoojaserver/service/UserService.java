package com.jiwooja.jiwoojaserver.service;

import com.jiwooja.jiwoojaserver.domain.Authority;
import com.jiwooja.jiwoojaserver.domain.User;
import com.jiwooja.jiwoojaserver.dto.UserDto;
import com.jiwooja.jiwoojaserver.exception.DuplicateUserException;
import com.jiwooja.jiwoojaserver.exception.NotFoundUserException;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserDto userDto) {

        if(userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateUserException("이미 가입되어 있는 사용자 입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .build();
        return userRepository.save(user);
    }


    /**
     * SecurityContextHolder로 user 구분자(USER_ID) 값 찾는 메서드
     * @return Long : user 구분자(USER_ID)
     */
    public Long findUserIdBySecurity(){
        org.springframework.security.core.userdetails.User userSecurity = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userSecurity.getUsername())
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));
        return user.getUserId();
    }

    /**
     * 로그인 아이디로 user 구분자(USER_ID) 값 찾는 메서드
     * @return Long : user 구분자(USER_ID)
     */
    public Long findUserIdByUserName(String userName){
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));
        return user.getUserId();
    }

}
