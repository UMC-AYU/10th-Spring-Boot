# Chapter03 미션 제출

**Name:** 리온/최형석  
**Mission:** Chapter03

---

# 1. 3주차 워크북 학습 후기

> 원래 어렴풋이 알았거나 잘못 이해하고 있던 스프링의 핵심 개념들을 명확하게 이해할 수 있었습니다. 특히 서블릿에 대한 부분을 자세히 알고나니 스프링 전체적인 요청/응답 흐름도 훨씬 명확하게 이해할 수 있어서 좋았습니다. SOLID 원칙에 대한 부분도 최대한 알맞은 예시를 찾으려 노력했습니다. 그러다 보니 각 원칙들을 더 잘 이해할 수 있었던 것 같습니다.

---

# 2. 핵심 키워드 정리

## SOLID 원칙

좋은 객체지향 코드를 만들기 위한 5가지 원칙

### **단일 책임 원칙 (SRP: Single Responsibility Principle)**

> 클래스는 하나의 책임만 가져야 한다

```java
// 책임 1: 사용자 DB 저장
@Repository
public class UserRepository {
    public void save(User user) {
        System.out.println("DB 저장");
    }
}

// 책임 2: 사용자 비즈니스 로직
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user) {
        userRepository.save(user);
    }
}
```

- 클래스는 하나의 책임만 가져야 유지보수가 쉬워짐
- DB 저장과 비즈니스 로직을 분리
- 하나의 클래스는 하나의 역할만 담당
- 변경 발생 시 서로 영향 없이 수정 가능
- 변경 이유가 서로 다르면 클래스를 분리해야 함

---

### **개방/폐쇄 원칙  (OCP: Open/Closed Principle)**

> 확장에는 열려 있고, 수정에는 닫혀 있어야 한다
>

```java
public interface PaymentService {
    void pay(int amount);
}

@Component
public class CardPayment implements PaymentService {
    public void pay(int amount) {
        System.out.println("카드 결제");
    }
}

@Component
public class KakaoPayPayment implements PaymentService {
    public void pay(int amount) {
        System.out.println("카카오페이 결제");
    }
}
```

- 인터페이스 기반으로 확장 가능하게 설계
- 새로운 결제 수단 추가 시 클래스만 추가하면 됨
- 새로운 결제 수단 추가 시 기존 코드 수정 없음
- 스프링에서는 DI로 구현체 교체 가능

---

### **리스코프 치환 원칙 (LSP: Liskov Substitution Principle)**

> 부모 타입을 자식 타입으로 바꿔도 정상 동작해야 한다
>

```java
public interface Bird {
    void move();
}

@Component
public class Sparrow implements Bird {
    public void move() {
        System.out.println("날아다님");
    }
}

@Component
public class Penguin implements Bird {
    public void move() {
        System.out.println("헤엄침");
    }
}
```

- 공통 동작(move)을 기준으로 설계됨
- 부모 타입(Bird)으로 자식 객체(Sparrow, Penguin)를 동일하게 사용할 수 있어야 함
- 사용하는 쪽은 구현체의 내부 동작을 몰라도 정상 동작해야 함
- 구현 내용이 달라도 호출 방식과 결과는 유지
- 예외 발생, 기능 미지원 등으로 기존 코드를 깨뜨리면 안 됨
- 어떤 자식으로 바꿔도 기존 코드를 수정할 필요가 없어야 함

---

### **인터페이스 분리 원칙 (ISP: Interface Segregation Principle)**

> 사용하지 않는 인터페이스는 강제하면 안 된다


```java
public interface Printer {
    void print();
}

public interface Scanner {
    void scan();
}

@Component
public class SimplePrinter implements Printer {
    public void print() {
        System.out.println("출력");
    }
}
```

- 필요한 기능만 인터페이스로 분리
- 필요 없는 기능(scan)을 강제로 구현하지 않음
- 인터페이스를 작게 나누는 것이 핵심
- 클래스가 자신의 역할에만 집중 가능

---

### **의존 역전 원칙 (DIP: Dependency Inversion Principle)**

> 구체 클래스가 아니라 추상화(인터페이스)에 의존해야 한다


```java
public interface MessageService {
    void sendMessage(String msg);
}

@Component
public class EmailService implements MessageService {
    public void sendMessage(String msg) {
        System.out.println("이메일: " + msg);
    }
}

@Service
public class NotificationService {
    private final MessageService messageService;

    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void notify(String msg) {
        messageService.sendMessage(msg);
    }
}
```

- 구체 클래스가 아닌 인터페이스에 의존
- Email → SMS 등 구현체 교체가 쉬움
- 스프링 DI로 자동 주입되어 유연한 구조
- 실제 실행은 인터페이스가 아니라 스프링이 주입한 구현체가 수행
- 어떤 구현체를 사용할지는 스프링 컨테이너가 결정

---

## DI (Dependency Injection)

의존성을 직접 생성하지 않고 외부에서 주입받는 것

```java
@Service
class OrderService {
    private final Repository repo;

    public OrderService(Repository repo) {
        this.repo = repo;
    }
}
```

### 작동 원리

- Spring 컨테이너가 Bean을 생성할 때 필요한 의존성을 찾아서 자동으로 주입
- 이 과정에서 `@Component`, `@Service`, `@Repository` 등을 기반으로 Bean 등록

---

### 쓰는 이유

- 객체 간 결합도 감소 → 유지보수 쉬움
- 구현체 변경이 쉬움
- 테스트 용이 (Mock 객체 주입 가능)

---

### 특징

- IoC를 구현하는 대표적인 방법
- 런타임 시점에 의존성 연결됨
- 인터페이스 기반 설계와 궁합이 좋음

---

## IoC (Inversion of Control)

객체 생성과 생명주기의 제어권이 개발자가 아닌 Spring에게 넘어간 것

---

### 작동 원리

Spring 컨테이너가

- 객체 생성
- 의존성 주입
- 생명주기 관리

개발자는 사용만 하고 생성은 하지 않음

---

### 쓰는 이유

- 객체 관리 일원화 → 코드 단순화
- 라이프사이클 자동 관리
- 프레임워크 중심 개발 가능

---

### 특징

- DI를 포함하는 상위 개념
- Bean이라는 단위로 객체 관리
- 설정 기반 or 어노테이션 기반 구성 가능

---

## 생성자 주입 (Constructor Injection)

생성자를 통해 의존성을 주입하는 방식

```java
@Service
public class OrderService {

    private final Repository repo;

    public OrderService(Repository repo) {
        this.repo = repo;
    }
}
```

---

### 작동 원리

- Spring이 Bean 생성 시 생성자를 호출하면서 의존성 주입
- 의존성이 없으면 객체 생성 자체가 실패

---

### 쓰는 이유

- 필수 의존성 보장 (안 넣으면 컴파일/런타임 에러)
- `final` 키워드 사용 가능 → 불변성 유지
- 순환 참조 문제를 조기에 발견 가능

---

### 특징

- Spring에서 가장 권장되는 방식
- 테스트 코드 작성에 유리
- Lombok의 `@RequiredArgsConstructor`와 자주 사용됨

---

## 수정자 주입 (Setter Injection)

setter 메서드를 통해 의존성을 주입하는 방식

```java
@Service
public class OrderService {

    private Repository repo;

    @Autowired
    public void setRepo(Repository repo) {
        this.repo = repo;
    }
}
```

---

### 작동 원리

- 객체 생성 이후 setter 메서드를 호출하여 주입
- 선택적으로 의존성을 넣을 수 있음

---

### 쓰는 이유

- 선택적 의존성 처리 가능
- 런타임에 의존성 변경 가능

---

### 특징

- 객체가 완전하지 않은 상태로 생성될 수 있음
- Null 가능성 존재 → 안정성 낮음
- 특정 상황에만 사용

---

## 필드 주입 (Field Injection)

필드에 직접 의존성을 주입하는 방식

```java
@Service
public class OrderService {

    @Autowired
    private Repository repo;
}
```

---

### 작동 원리

- Reflection을 이용해 필드에 직접 값을 주입
- 생성자나 setter 호출 없이 바로 주입됨

---

### 쓰는 이유

- 코드가 매우 간단하고 빠르게 작성 가능

---

### 특징

- 테스트 어려움 (Mock 주입 힘듦)
- final 사용 불가 → 불변성 깨짐
- 순환 참조 발견 어려움

→ 실무에서는 거의 사용하지 않음

---

## AOP (Aspect-Oriented Programming)

공통 관심사를 핵심 로직과 분리하는 프로그래밍 방식

```java
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.example..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        // 메서드 실행 전 공통 로직 (시간 측정 시작)

        Object result = joinPoint.proceed(); // 실제 대상 메서드 실행

        // 메서드 실행 후 공통 로직 (실행 시간 출력)

        return result;
    }
}
```

- **@Around** → 언제 실행되는지 (전/후)
- **execution(...)** → 어디에 적용되는지
- **proceed()** → 실제 메서드 실행 지점

---

### 작동 원리

- 프록시 객체를 생성해서 기존 객체를 감쌈
- 메서드 실행 전/후에 공통 로직 삽입

실제 실행 흐름

`클라이언트 → 프록시 → 대상 객체`

---

### 쓰는 이유

- 중복 코드 제거 (로깅, 트랜잭션 등)
- 핵심 로직과 부가 기능 분리
- 코드 가독성 및 유지보수성 향상

---

### 특징

- Spring은 Proxy 기반 AOP 사용
- 런타임에 동적으로 적용
- 대표 활용: `@Transactional`, 로깅, 보안

---

## 서블릿 (Servlet)

HTTP 요청/응답을 처리하는 자바 기반 기술

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.getWriter().write("hello");
    }
}
```

---

### 작동 원리

1. 클라이언트 요청 발생
2. 서블릿 컨테이너가 요청을 받음
3. HttpServletRequest / HttpServletResponse 객체 생성
4. URL에 맞는 서블릿을 찾음
5. 서블릿 객체가 없으면 생성 (최초 1회)
6. `service(request, response)` 호출
7. `service()` → `doGet()` / `doPost()` 호출
8. request 객체로 요청 데이터 조회 / response 객체로 응답 작성
9. 응답 생성 후 클라이언트 반환

- **HttpServletRequest:** 클라이언트 요청 정보 (파라미터, 헤더 등)
- **HttpServletResponse:** 클라이언트로 보낼 응답 데이터 작성

---

### 쓰는 이유

- 자바 기반 웹의 표준 기술
- 멀티 스레드 기반 요청 처리 → 성능 효율적
- 개발자는 비즈니스 로직에 집중 가능

---

### 특징

- 상태를 유지하지 않는 Stateless 구조
- 멀티 스레드 기반 처리 (사용자의 요청을 동시 처리)
- Spring MVC는 이 위에서 동작 (추상화 제공)
- 서블릿 생명주기 관리 (생성 / 초기화 / 소멸)

---

## DispatcherServlet

Spring MVC의 핵심 서블릿 (모든 요청의 진입점)

```java
@Controller
public class UserController {

    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }
}
```

위 요청은 DispatcherServlet이 먼저 받아서 Controller로 전달됨

---

### 작동 원리

1. 클라이언트 요청 발생
2. DispatcherServlet이 요청을 받음
3. HandlerMapping으로 Controller 탐색
4. HandlerAdapter로 Controller 실행
5. 결과를 받아 View 또는 JSON으로 변환
6. 응답 반환

---

### 쓰는 이유

- 모든 요청을 하나로 관리 (중앙 집중 처리)
- 공통 기능 처리 가능 (인증, 로깅 등)
- 개발자는 Controller 로직에만 집중 가능

---

### 특징

- Front Controller 패턴 적용
- Spring MVC의 핵심 구성 요소
- 다양한 컴포넌트와 유연하게 연동 (HandlerMapping 등)

---

# 3. Spring 요청 / 응답 흐름

## 1. 클라이언트 요청

- 브라우저 / 앱에서 HTTP 요청 발생

```
GET /users/1
```

---

## 2. 서블릿 컨테이너 (Tomcat)

- 요청을 가장 먼저 받는 곳
- **HttpServletRequest / HttpServletResponse 객체 생성**
- DispatcherServlet으로 요청 전달

---

## 3. DispatcherServlet

- 모든 요청을 받는 **Front Controller**
- 어떤 Controller가 처리할지 결정

---

## 4. Controller (요청 처리)

- 요청 데이터 받아서 처리
- Service 계층 호출

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userService.findById(id);
}
```

---

## 5. Service → Repository → DB

- **Service** → 비즈니스 로직 수행
- **Repository** → DB 접근
- **DB** → 데이터 조회/저장

→ 최종적으로 다시 Controller로 반환

---

## 6. 응답 생성 및 반환

- Controller가 결과 반환
- 객체는 Spring이 자동 처리

→ 최종적으로 HTTP Response 생성 후 클라이언트 반환

---

## 전체 흐름

*요청 → Tomcat → DispatcherServlet → Controller → Service → Repository → DB → 응답 반환*