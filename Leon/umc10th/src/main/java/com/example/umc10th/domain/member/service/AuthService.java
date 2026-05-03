package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;

public interface AuthService {

    MemberResponseDTO.GetMyInfo signup(MemberRequestDTO.Signup dto);

    MemberResponseDTO.Login login(MemberRequestDTO.Login dto);
}