package com.aim.umc10th.global.security.util;

import com.aim.umc10th.global.security.entity.AuthMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;


// JWT 토큰의 생성, 파싱, 검증을 전담하는 Utility 클래스
@Component
public class JwtUtil {
    private final SecretKey  secretKey; // 암호화 서명(Signature)에 사용할 비밀키 객체
    private final Duration accessExpiration; //토큰 유효기간 (30분)

    public JwtUtil(
            @Value("${jwt.token.secret-key}") String secret,               // 환경변수에서 읽어온 시크릿 키 문자열
            @Value("${jwt.token.expiration.access}") Long accessExpiration // 환경변수에서 읽어온 만료 시간 (밀리초)
    ){
        //문자열로 된 시크릿 키를 JJWT 라이브러리가 인식할 수 있는 HMAC-SHA 암호화 키 객체로 변환
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        // 밀리초 단위의 숫자를 Duration 객체로 변환하여 저장
        this.accessExpiration = Duration.ofMillis(accessExpiration);
    }

    //외부(MemberService 등)에서 호출하여 진짜 AccessToken을 발급받아갈 수 있는 통로를 열어준다.
    public String createAccessToken(AuthMember member){
        return createToken(member, accessExpiration);
    }

    /* 토큰에서 이메일 가져오기
     *
     * @param token 유저 정보를 추출할 토큰
     * @return 유저 이메일을 토큰에서 추출한다.
     * -> 로그인 성공 시, 유저 정보를 바탕으로 AccessToken을 생성한다.
     */
    public String getEmail(String token) {
        try {
            // 토큰을 파싱(getClaims)한 뒤, Payload에 적힌 Subject(이메일)를 꺼낸다.
            return getClaims(token).getPayload().getSubject(); // Parsing해서 Subject 가져오기
        } catch (JwtException e) {
            // 토큰이 변조되었으나 만료되었다면 예외가 터지므로 안전하게 null을 반환한다.
            return null;
        }
    }

    /* 토큰 유효성 확인
     *
     * @param token 유효한지 확인할 토큰
     * @return True, False 반환합니다
     * -> 토큰이 위변조되지 않았는지, 만료 기간이 지나지 않았는지 확인
     */
    public boolean isValid(String token){
        try{
            // 토큰 파싱 시도. 내부적으로 서명(Signature)검증이랑 만료 시간 체크를 동시에 한다.
            getClaims(token);
            return true; //에러 없이 파싱 성공하면 안전한 토큰
        } catch (JwtException e){
            return false; // 만료되었거나, 위변조되었거니, 형식이 깨졌다면 예외 발생
        }
    }

    //토큰 생성
    // JWT 라이브러리를 사용해 무상태(Stateless) 토큰을 굽는 메서드
    private String createToken(AuthMember member, Duration expiration){
        Instant now = Instant.now(); //현재 시간 기준

        // Spring Security 인가 정보
        String authorities = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(member.getUsername()) //User의 식별자(이메일)을 sub 필드에 넣기
                .claim("role", authorities) //커스텀 클레임으로 유저 권한 정보 넣기
                .claim("email", member.getUsername()) //명시적으로 이메일 필드도 한번 더 저장하기
                .issuedAt(Date.from(now)) //언제 발급한지, 토큰 발행 시간 기록
                .expiration(Date.from(now.plus(expiration))) // 언제까지 유효한지, 현재 시간 + 30분 더해 만료 시간 설정
                .signWith(secretKey) //sign할 Key (Signature 설정)
                .compact(); //최종적으로 문자열 형태로 압축하여 반환

    }

    //토큰 정보 가져오기
    // [수학적 검증 및 파싱] 비밀키를 이용해 토큰의 위변조 여부를 체크하고 내용을 연다.
    private Jws<Claims> getClaims(String token) throws JwtException{
        return Jwts.parser()
                .verifyWith(secretKey) //검증에 사ㅏ용할 비밀키 세팅
                .clockSkewSeconds(60) //서버 간의 시간 오차를 최대 60초까지는 인정
                .build() //파서 빌드 완료
                .parseSignedClaims(token); //토큰 파싱
    }

}
