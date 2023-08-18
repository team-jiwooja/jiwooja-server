package com.jiwooja.jiwoojaserver.service;

import com.jiwooja.jiwoojaserver.domain.Authority;
import com.jiwooja.jiwoojaserver.domain.User;
import com.jiwooja.jiwoojaserver.dto.UserDto;
import com.jiwooja.jiwoojaserver.dto.UserViewDto;
import com.jiwooja.jiwoojaserver.exception.DuplicateUserException;
import com.jiwooja.jiwoojaserver.exception.NotFoundUserException;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

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

    /**
     * 아이디 중복 체크
     * @param username : 아이디
     * @return boolean : true-사용가능 / false - 사용불가
     */
    public boolean usernameChecker(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return !user.isPresent();
    }

    /**
     * 로그인 여부 판별
     */
    public Boolean loginTf(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * user 정보
     */
    public UserViewDto getUserInfo(){
        org.springframework.security.core.userdetails.User userSecurity = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userSecurity.getUsername())
                .orElseThrow(() -> new NotFoundUserException("가입되지 않은 유저입니다."));

        return UserViewDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .pointsTotal(user.getPointsTotal())
                .build();
    }
}
