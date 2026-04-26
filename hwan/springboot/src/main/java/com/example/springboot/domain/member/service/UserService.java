package com.example.springboot.domain.member.service;

import com.example.springboot.domain.member.dto.UserReqDTO;
import com.example.springboot.domain.member.dto.UserResDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // 다음 주차에 구현 예정 (Repository 연동)
    public UserResDTO.SignUp signUp(UserReqDTO.SignUp dto) {
        return null;
    }
}