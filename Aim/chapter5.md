# Chapter04_프로젝트 세팅하기 - API 응답 통일, 에러 핸들러

## 핵심 키워드 정리

### 1. 빌더패턴이란?
#### 빌더 패턴이란?
복잡한 객체를 생성할 때, 생성자(Constructor)대신 메서드 체이닝(Method Chaining)방식을 사용하여 객체를 단계별로 구축하는 디자인 패턴이다.
롬복(Lombok)의 @Builder 어노테이션 덕분에 복잡한 코드 없이 편하게 사용할 수 있다.
그럼 왜 생성자 대신 빌더를 써야하는지 생각해볼 필요가 있다.
실습을 해보면 알겠지만 엔터티를 만들다 보면 id, name, email, point, gender 등등 필드가 점점 늘어난다. 
이때 생성자만 사용하면 문제점이 생긴다.

1) 가독성 저하
-생성자 방식-
ex. ```Member member  = new Member(1L, "에임", "test@gmail.com", 2500, "Male");```
이런식으로 생성자 방식을 사용하면 순서가 틀리는 순간 참사가 일어난다...

2) 유연성 부족
만약 id랑 name만 넣어서 객체를 만들고 싶다면, 또 다른 생성자를 계속 만들어야 한다.(생성자 오버로딩)
필드가 10개라면 생성자는 엄청 많이 만들어야 할지도 모른다...

#### 빌더 패턴의 장점
1) 이름을 명시적으로 사용한다.
```java
Member member = Member.builder()
        .name("에임")
        .email("test@gmail.com")
        .point(2500)
        .build();
```
이런식으로 "에임"이 name에 들어간다는게 명확하다.
2) 불변성 유지: 객체를 생성한 후에 setter로 값을 바꾸는게 아닌, 생성 시점에 값을 정해보리니, 데이터가 중간에 변할 위험이 줄어들어 안전하다.
3) 필여한 값만 설정이 가능: 넣고 싶은 데이터만 골라서 넣을 수 있다. 안 넣은 값은 자동으로 null이나 기본값이 들어간다.

실습 때 무지성으로 코드를 따라치기만 했다가 오류가 엄청 났다. 빌더 메서드의 이름은 엔터티의 필드명을 따라간다. 나의 필드명은 member_Id인데 
id를 입력했으니 오류가 났을 수밖에 없었다... 
또한 @Builder를 쓰려면 모든 필드를 인자로 받는 생성자가 필요하다. 그래서 보통 엔터티 위에 @AllArgsConstructor와 @NoArgsConstructor를 같이 붙인다.

### 2. record vs static class
#### record(데이터 전용 객체)
데이터 전달만을 목적으로 하는 객체를 위해 도입된 새로운 클래스 타입이다. '오직 데이터를 담기 위한 객체야.' 라고 선언하는 것과 같다.
특징으로는
1. 불변성: 모든 필드가 기본적으로 final이다. 한 번 생성되면 내부 값을 바굴 수 없어 데이터 신뢰도가 매우 높다.
2. 보일러플레이트(Boilerplate)제거: Getter, 생성자, toString, equals, hashCode를 컴파일러가 자동으로 생성해 줘서 코드가 매우 간결하다.
3. 목적이 명확: 오직 데이터를 전달하기 위한 그릇(DTO)으로 쓰겠다는 의도를 명확하게 전달한다.

#### static class(정적 내부 클래스)
DTO를 만들 때 하나의 큰 클래스(Outer Class)안에 여러 응답용 클래스를 묶어둘 때 사용하는 방식이다.
특징으로는
1. 구조적 그룹화: 외부 클래스의 인스턴스 생성 없이도 내부 클래스를 사용할 수 있다. 관련 DTO들을 하나의 부모 클래스 안에 묶어 관리할 때 필수적이다.
2. 가변성 선택 가능: 필드에 final을 붙이지 않으면 언제든 값을 수정할 수 있어 유연하다.
3. 완전한 커스텀(유연성): 일반 클래스이므로 비즈니스 로직을 추가하거나, 다른 클래스를 상속받는 등 자유로운 확장이 가능하다.

#### 공통점
- 둘 다 계층 간 데이터 전송을 위한 객체(DTO)를 설계할 때 핵심적으로 사용된다.
- static class는 외부 클래스에 대한 참조를 가지지 않기 때문에 메모리 누수 걱정이 없고, record 역시 가볍게 설계되어 성능상 이점이 있다.
- 관련 있는 데이터나 로직을 하나의 단위로 묶어서 관리할 수 있게 해준다.(캡슐화)

### record vs static class 예시 코드
관련된 DTO들을 구조화하기 위해 `static class`를 사용하고, 각 응답 데이터의 불변성과 간결함을 위해 `record`를 활용한다.

```java
// 1. static class를 이용한 DTO 구조화
public class MemberResponseDTO {

    // 2. record를 이용한 간결한 데이터 정의
    @Builder
    public record GetInfo(
            String name,
            String email,
            Integer point,
            String phoneNumber
    ) {}

    @Builder
    public record CreateResult(
            Long memberId,
            LocalDateTime createdAt
    ) {}
}
```

### 3. 제네릭이란?
#### 제네릭이란?
클래스나 메서드에서 사용할 데이터 타입을 외부에서 지정하는 기법이다. 조금 더 쉽게 생각해보자면 어떤 타입을 담을지 미리 정하지 않고, 실제로 쓸 때 결정을 하기위해 
비워두는 타입 파라미터 라고 생각하면 된다.

#### 제네릭을 사용하는 이유는?
1. 타입 안정성: 의도하지 않은 타입의 객체가 들어오는 걸 컴파일 시점에 막아준다. (런타임 에러 방지)
2. 재사용성 극대화: 하나의 클래스로 String, Integer, MemberDTO 등 다양한 타입을 다룰 수 있다.
3. 불필요한 형변환 제거: 데이터를 꺼낼 때마다 강제로 타입을 바꿔줄 필요가 없어 코드가 깔끔해진다.

```java
// 제네릭 클래스 선언: 어떤 데이터(result)가 올지 모르니 T로 비워둠
public class ApiResponse<T> {
    private Boolean isSuccess;
    private String code;
    private String message;
    private T result; // 실제 응답 데이터가 들어갈 자리!
}
```
만약 유저 정보를 주면 ApiResponse<MemberResponseDTO.GetInfo>가 되는거고, 게시글을 주면 ApiResponse<PostDTO>가 되는 형식이다.
상자(ApiResponse)는 그대로인데 내용물(T)만 바뀌는 것이다.

### 4. @RestControllerAdvice이란?
#### @RestControllerAdvice란?
@ControllerAdvice + @ResponseBody가 합쳐진 어노테이션이다. 애플리케이션 내의 모든 @RestController에서 발생하는 예외를 한곳에서 잡아 처리할 수 있게 해준다.
중앙 통제를 하는 역할이라고 생각하면 될 것 같다.

@RestControllerAdvice를 사용하는 이유를 생각해 보았다.
1. 코드 중복 제거: 모든 컨트롤러 메소드마다 try-catch를 넣는다면 코드가 매우 지저분해질 것이다. 이걸 한 곳으로 몰아서 해결해준다.
2. 응답 규칙 통일: 에러가 나도라도 실습에서 한 것처럼 ApiResponse 객체에 담아서 보낼 수 있다. 클라이언트(프론트엔드) 입장에서는 에러를 대응하기 훨씬 쉬워진다.
3. 관심사 분리: 비즈니스 로직(Service)은 자기 할 일만 하고, 에러 처리는 Advice가 담당하기에 코드가 깔끔해진다.

#### 작동방식
@RestControllerAdvice 클래스 내부에서 @ExceptionHandler를 붙인 메서드를 만들면, 특정 에러가 터졌을 때 그 메서드가 자동으로 실행된다.
```java
@RestControllerAdvice
public class ExceptionAdvice {

    // MemberException이라는 에러가 터지면 이 메서드가 출동한다.
    @ExceptionHandler(value = MemberException.class)
    public ApiResponse<String> handleMemberException(MemberException e) {
        // 에러 정보를 가져와서 우리가 만든 응답 틀에 담아 반환한다.
        return ApiResponse.onFailure(e.getCode(), e.getMessage(), null);
    }
}
```
### 5. Optional이란?
#### Optional이란?
Optional<T>는 값이 있을 수도 있고, 없을 수도 있는 객체를 감싸는 wrapper 클래스이다.
객체가 직접 null을 반환하게 두는 대신, Optional 이라는 상자에 담아서 반환함으로써 이 결과는 비어있을 수도 있으니 조심하라고 명시적으로 알려주는 것이다.
조금 더 쉽게 정리를 해보자면 "Optional은 null이라는 폭탄을 안전하게 담아두는 보관함이며, 값이 없을 때 어떻게 할지 미리 정해두게 만드는 안전장치이다."
Optional을 왜 사용하는지 생각을 해보자.
1. NullPointerException 방지: 직접 null을 다루지 않아도 되기 때문에 런타임에 갑자기 서버가 터지는 걸 막아준다.
2. 가독성이 좋고, 의도 표현이 확실하다: 반환 타입이 만약 Optional<Member>라면, 코드를 읽는 사람은 "유저를 못 찾을 수 있겠네?" 라고 바로 짐작하고 대비할 수 있다.
3. 메서드 체이닝: if 문을 길게 작성하는 대신, 함수형 인터페이스를 활용해 한 줄로 깔끔하게 처리할 수 있다.

실습을 하면서 작성한 주요 메소드를 작성해보았다.
- orElseThrow(): 값이 없으면 내가 지정한 예외를 던져라.
- isPresent(): 값이 있을 때만 이 로직을 실행해라.
- orElse(): 값이 없으면 이 기본값을 대신 줘라.

```java
memberRepository.findById(memberId) // Optional<Member> 반환
    .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)); 
    // 값이 없으면 바로 에러 중앙 통제(@RestControllerAdvice)로 보낸다.
    ```
