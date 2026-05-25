package com.aim.umc10th.global.security.filter;

import com.aim.umc10th.global.apiPayload.ApiResponse;
import com.aim.umc10th.global.apiPayload.code.BaseErrorCode;
import com.aim.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.aim.umc10th.global.security.service.CustomUserDetailsService;
import com.aim.umc10th.global.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// [9주차 인증 게이트] 모든 HTTP 요청이 컨트롤러로 가기 전, OncePerRequest 통과를 하며
// 헤더의 JWT 토큰을 검증하고 사용자의 신원을 증명하는 커스텀 시큐리티 필터
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException{

        try{
            //토큰 가져오기. (Http 요청 헤더에서 Authorization 키를 가진 값을 꺼내온다.)
            String token = request.getHeader("Authorization");
            //token이 없거나 Bearer가 아니면 그냥 통과시킨다.
            if(token == null || !token.startsWith("Bearer ")){
                filterChain.doFilter(request, response); //다음 시큐리티 필터 그물망으로 요청을 넘긴다.
                return; // 현대 필터 로직은 여기서 종료
            }

            //Bearer이면 추출. 순수한 JWT 토큰 문자열만 추출한다.
            token = token.replace("Bearer ","");

            //AccessToken 검증하기. 올바른 토큰이면
            if(jwtUtil.isValid(token)){

                //[신원 추출]안전함이 증명된 토큰의 페이로드에서 유저의 고유 식별자를 꺼낸다.
                String email = jwtUtil.getEmail(token);

                //[DB 조회]인증 객체 생성: 이메일로 찾아온 뒤, 인증 객체 생성
                UserDetails user = customUserDetailsService.loadUserByUsername(email);

                //[스프링 시큐리티 전용 인증 객체 생성]
                //유저 정보(user)와 권한 정보(authorities)를 묶어서 시큐리티가 이해할 수 있는 증명서 만들기
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                //인증 완료 후 SecurityContextHolder에 방금 만든 증명서를 넣는다.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            //모든 검증 관문 통과, 다음 필터나 컨트롤러 목적지로 기분 좋게 넘겨줌
            filterChain.doFilter(request, response);
        }catch (Exception e){
            //[에러 핸들링] 토큰 파싱이나 검증 중 지뢰를 밟아 예외가 터졌을 때 일어남
            ObjectMapper mapper = new ObjectMapper();
            BaseErrorCode code = GeneralErrorCode.UNAUTHORIZED;

            //응답 형태를 브라우저가 읽을 수 있게 UTF-8 JSON 형태로 헤더를 구워준다.
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(code.getStatus().value());

            ApiResponse<Void> errorResponse = ApiResponse.onFailure(code.getCode(), code.getMessage(), null);

            mapper.writeValue(response.getOutputStream(), errorResponse);
        }
    }
}
