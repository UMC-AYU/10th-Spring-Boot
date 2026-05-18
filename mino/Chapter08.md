
# Chapter08_Spring Security - Security 구조, 폼 로그인


## 학습 후기


## 핵심 키워드 정리

### Spring Security가 무엇인가?
### 인증(Authentication)vs 인가(Authorization)
### Stateful vs Stateless



## 미션

### Spring Security를 적용하고 회원가입 API를 구현해주세요 
(폼 로그인을 위한 email, password를 추가로 받고 비밀번호는 BCrypt로 솔트처리해주세요)

### 회원가입 API는 Public API, 그 이외의 API는 Private API로 설정해주세요
(Public API: 로그인 불필요 / Private API: 로그인 필요)
(exceptionHandling을 구현해 인증, 인가 실패 시 응답이 통일되야 함)