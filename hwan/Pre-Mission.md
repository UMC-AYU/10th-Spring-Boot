# 1. 스프링 입문 강의 요약
### MVC와 템플릿 엔진
MVC란 Controller, Model, View 의 앞 글자를 딴 용어이다.

Controller는 사용자의 URL 요청을 가장 먼저 받아서 특정 주소와 매핑된 메서드를 실행한다. 사용자가 보낸 파라미터를 변수로 받아 로직을 처리하고
처리된 데이터를 Model에 집어넣고 View의 이름을 문자열로 반환한다.

Model은 컨트롤러에서 생성되거나 가공된 데이터를 View로 전달하기 위한 전용 객체이다.

View는 Controller에서 전달받은 값을 받아서 사용자 화면에 띄워준다

Controller
```
@Controller
public class HelloController {
@GetMapping("hello-mvc")
public String helloMvc(@RequestParam("name") String name, Model model) {
model.addAttribute("name", name);
return "hello-template";
}
}
```

View
```
<html xmlns:th="http://www.thymeleaf.org">
<body>
<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
</html>
```

해당 코드 기준으로 사용자가 
http://localhost:8080/hello-mvc?name=spring 로 입력하여 들어갈 경우
Controller에서 spring이라는 객체를 받아 model에 전달 후 view Resolver에서 템플릿 엔진 처리 후 HTML로 변환하여 
hello spring이 반환된다.


### TDD
TDD는 테스트 코드를 먼저 작성 후 그 테스트를 통과하기 위한 코드를 나중에 짜는 방식이다.
TDD의 3단계 구성
RED : 아직 구현하지 않은 기능에 대해 실패하는 테스트 코드 먼저 작성
Green : 테스트를 통과하기 위한 최소한의 코드만 작성
Refactor : 기능을 유지한 채 코드를 다듬는 과정

테스트 코드 없이 개발은 불가능하고, 강사님께서 실제로 개발 과정에서 테스트 코드를 짜는 데에 시간이 더 많이 든다고 했었다
그만큼 테스트 코드 작성의 중요성이 매우 크다고 강조 하셨다.


### 스프링 빈과 의존관계
생성자에 @Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어주는데 이렇게 객체 의존 관계를 외부에서 넣어주는것을 DI (의존성 주입) 이라고 한다.
스프링은 이러한 객체들을 스프링 빈으로 등록하여 스프링 컨테이너라는 바구니에 담아 관리한다.

의존성 주입 (DI)의 3가지 방법
1. 필드 주입 : 클래스의 변수에 바로 @Autowired를 붙여서 주입하는 방식으로 코드가 짧고 간결하지만, 외부에서 변경하기가 거의 불가능해서 테스트 코드나 설정을 바꿀 때 유연성이 매우 떨어진다.
2. Setter 주입 : 별도의 수정자(Setter)메서드를 통해 주입하는 방식으로 언제든 의존관계를 바꿀 수 있지만, 주입받는 메서드를 public으로 열어둬야 하기 때문에 프로그램이 돌아가는 도중 의존관계를 바꿔버릴 위험이 있다.
3. 생성자 주입 : 객체가 생성될 때 딱 한 번 호출되는 생성자를 통해 주입하는 방식으로 누락될 일이 없고, 한번 정해진 의존관계는 앱이 종료될 때까지 변하지 않아 실무에서 가장 권장되는 방식이다.

**스프링 빈으로 등록해서 사용하는 이유**
1. 싱글톤 관리 : 하나의 객체만을 생성하여 여러 곳에서 요청해도 같은 객체를 사용해 메모리를 효율적으로 아낄 수 있다
2. 의존성 주입(DI) : 스프링 빈으로 등록돼있어야만 @Autowired를 통해 자동으로 필요한 곳에 객체를 넣어줄 수 있다.

#### 스프링 빈을 만드는 방법
1) 컴포넌트 스캔
 @Controller, @Service, @Repository 등의 애노테이션들은 내부적으로 @Component를 포함하여 자동으로 스캔 대상이 된다.  
2) 자바 코드로 직접 등록
설정 파일을 만들고 메서드에 @Bean을 붙여서 어떤 객체를 빈으로 등록할지 직접 적어 주는 방식으로
나중에 클래스를 변경 할 때 다른 코드를 변경하지 않고 설정 파일만 수정하면 되는 장점이 있다.

### AOP
AOP (관점 지향 프로그래밍)은 핵심 관심 사항과 공통 관심 사항을 분리하여 공통 기능을 분리한다.
핵심 관심 사항 : 회원가입 시 중복 회원 검증, 회원 조회 기능 등
공통 관심 사항 : 모든 메서드의 실행 시간 측정, 로그기록 등

예를 들어 시간 측정 로직 모든 메서드에 적용한다면 기존에는 모든 코드에 시간을 측정하는 코드를 추가하게 되는데
코드의 양이 많아질수록 유지보수가 어려워지고 코드가 복잡해지는 문제가 발생한다.
하지만 AOP를 적용한다면 한곳에만 측정 로직을 작성 후 원하는 대상을 적용하면 되기 때문에 기존 로직의 코드가 깔끔하게 되어 유지보수가 편해지기 떄문에 일일이 메서드를 찾아 수정 할 필요가 없다.

스프링에서는 AOP를 적용 할 때 프록시를 세워 호출이 들어오면 프록시가 먼저 공통 로직을 실행 후 실제 로직을 호출해준다


# 2. SQL 기초 DDL vs DML

**DDL (Data Definition Language)**
- 데이터 정의 언어로 DB에서 스키마를 정의, 수정하고 View, Index 등을 생성, 변경 하는데 사용한다
- 실행 즉시 DB에 물리적으로 반영되며(Auto-Commit) 한번 실행하면 되돌리기(Rollback)이 어렵다.
- CREATE, ALTER, DROP, RENAME, TRUNCATE 등

DDL의 예시
```
-- 테이블 생성 (CREATE)
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT
);

-- 테이블 구조 변경: 컬럼 추가 (ALTER)
ALTER TABLE member ADD COLUMN email VARCHAR(255);

-- 테이블 삭제 (DROP)
-- DROP TABLE member;
```

**DML (Data Manipulatio Language)**
- DB에 저장된 데이터를 조회, 삽입, 수정, 삭제하는 작업에 사용
- 실행 후 바로 반영되는 것이 아닌 메모리 버퍼에 임시 저장되고 COMMIT을 통해 저장할 수 있고 ROLLBACK이 가능하다.
- SELECT, INSERT, UPDATE, DELETE, MERGE, CALL, EXPLAIN PLAN, LOCK TABLE 등

DML의 예시
```
-- 데이터 삽입 (INSERT)
INSERT INTO member (name, age, email) 
VALUES ('YongHwan', 25, 'hwan@example.com');

-- 데이터 조회 (SELECT)
SELECT * FROM member WHERE name = 'YongHwan';

-- 데이터 수정 (UPDATE)
UPDATE member SET age = 26 WHERE name = 'YongHwan';

-- 데이터 삭제 (DELETE)
DELETE FROM member WHERE id = 1;
```
