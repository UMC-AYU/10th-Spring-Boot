# Pre-Mission 보고서

**Name:** 최형석  
**Mission:** Pre-Mission

---

# 1. Spring Boot 핵심 개념 정리

강의를 듣고 핵심 키워드를 찾아 내용을 정리했습니다.

---

## 1. MVC (Model–View–Controller)

애플리케이션을 **Model, View, Controller** 역할로 분리하는 아키텍처 패턴이다.

| 구성 요소 | 역할 |
|---|---|
| Controller | HTTP 요청 처리 |
| Model | 데이터 및 비즈니스 로직 |
| View | 사용자에게 보여지는 화면 |

### Spring MVC 요청 흐름

1. 클라이언트 → HTTP 요청
2. `DispatcherServlet`이 요청 수신
3. 적절한 `Controller`로 요청 전달
4. 비즈니스 로직 수행
5. View 또는 API 응답 반환

---

## 2. API

### API

API(Application Programming Interface)는 **소프트웨어 간 통신을 위한 인터페이스**이다.  
웹에서는 주로 **HTTP 기반 Web API** 형태로 사용된다.

### RESTful API

REST는 **자원을 URI로 표현하고 HTTP Method로 동작을 정의하는 API 설계 방식**이다.

### 주요 특징

- Resource 기반 URI
- HTTP Method 사용
- Stateless 구조
- 주로 JSON 형식 사용

| Method | 역할 |
|---|---|
| GET | 데이터 조회 |
| POST | 데이터 생성 |
| PUT | 데이터 수정 |
| DELETE | 데이터 삭제 |

### 예시

```
GET /users
POST /users
GET /users/{id}
DELETE /users/{id}
```

Spring Boot에서는 `@RestController`를 사용하여 REST API를 구현한다.

---

## 3. 스프링 빈 (Spring Bean)

Spring Bean은 **Spring IoC 컨테이너가 관리하는 객체**이다.  
객체 생성과 의존성 관리를 Spring이 담당한다.

### 주요 특징

- 객체 생명주기 관리
- Dependency Injection 지원
- 애플리케이션 전반에서 재사용 가능

### Bean 등록 어노테이션

| Annotation | 역할 |
|---|---|
| `@Component` | 일반 Bean |
| `@Service` | 서비스 계층 |
| `@Repository` | 데이터 접근 계층 |
| `@Controller` | MVC 컨트롤러 |

### 예시

```java
@Service
public class MemberService {
}
```

---

## 4. Spring DB 접근 기술

Spring은 다양한 데이터 접근 기술을 지원한다.

### JDBC

JDBC(Java Database Connectivity)는 **Java에서 데이터베이스에 접근하기 위한 표준 API**이다.

특징

- SQL 직접 작성
- 데이터베이스 직접 연결
- 반복 코드가 많음

예시

```java
Connection connection = dataSource.getConnection();
PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
```

---

### JPA

JPA(Java Persistence API)는 **객체와 데이터베이스 테이블을 매핑하는 ORM 기술**이다.

특징

- 객체 중심 데이터 접근
- SQL 작성 최소화
- 데이터베이스 독립성 향상

예시

```java
@Entity
public class User {

    @Id
    private Long id;

    private String name;
}
```

Spring Boot에서는 보통 **Spring Data JPA**를 사용하여 Repository 기반 데이터 접근을 구현한다.

---

## 5. AOP (Aspect Oriented Programming)

AOP는 **공통 관심사(Cross-Cutting Concern)를 분리하기 위한 프로그래밍 패러다임**이다.

대표적인 공통 기능

- 로깅
- 트랜잭션 관리
- 보안 처리
- 성능 측정

### AOP 구성 요소

| 요소 | 설명 |
|---|---|
| Aspect | 공통 기능 모듈 |
| Advice | 실제 실행되는 로직 |
| Pointcut | Advice 적용 대상 |
| Join Point | Advice 실행 시점 |

### 예시

```java
@Aspect
@Component
public class TimeTraceAop {
}
```

---

# 2. DDL vs DML

SQL은 데이터베이스를 관리하기 위한 언어이며 크게 **DDL**과 **DML**로 구분된다.

---

# 1. DDL (Data Definition Language)

DDL은 **데이터베이스 객체(Database Object)의 구조를 정의하거나 변경하는 명령어**이다.

## DDL 명령어

| Command | 설명            |
|---|---------------|
| CREATE | 데이터베이스 객체 생성  |
| ALTER | 데이터베이스 객체 구조 변경 |
| DROP | 데이터베이스 객체 삭제  |
| RENAME | 데이터베이스 객체 이름 변경 |
| TRUNCATE | 테이블 모든 데이터 삭제 |
| COMMENT | 데이터베이스 객체에 설명 추가 |

---

## MySQL 예시

```sql
-- CREATE
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    email VARCHAR(100)
);

-- COMMENT
ALTER TABLE users COMMENT = '사용자 정보를 저장하는 테이블';

-- ALTER
ALTER TABLE users ADD COLUMN age INT;

-- RENAME
RENAME TABLE users TO members;

-- TRUNCATE
TRUNCATE TABLE members;

-- DROP
DROP TABLE members;
```

---

# 2. DML (Data Manipulation Language)

DML은 **데이터베이스에 저장된 데이터를 조회하거나 수정하는 명령어**이다.

## DML 명령어

| Command | 설명 |
|---|---|
| SELECT | 데이터 조회 |
| INSERT | 데이터 삽입 |
| UPDATE | 데이터 수정 |
| DELETE | 데이터 삭제 |
| MERGE | 조건에 따라 INSERT 또는 UPDATE 수행 |
| CALL | 저장 프로시저 실행 |
| EXPLAIN PLAN | SQL 실행 계획 조회 |
| LOCK TABLE | 테이블 잠금 |

---

## MySQL 예시

```sql
-- INSERT
INSERT INTO users (name, email)
VALUES ('Leon', 'leon@email.com');

-- SELECT
SELECT * FROM users;

-- UPDATE
UPDATE users
SET name = 'Leon Choi'
WHERE id = 1;

-- DELETE
DELETE FROM users
WHERE id = 1;

-- MERGE (MySQL에서는 INSERT ... ON DUPLICATE KEY UPDATE 사용)
INSERT INTO users (id, name, email)
VALUES (1, 'Leon', 'leon@email.com')
ON DUPLICATE KEY UPDATE name = 'Leon Choi';

-- CALL (Stored Procedure 실행)
CALL update_user_name(1, 'Leon');

-- EXPLAIN PLAN
EXPLAIN SELECT * FROM users;

-- LOCK TABLE
LOCK TABLES users WRITE;
UNLOCK TABLES;
```

---

# 3. DDL vs DML 차이

| 구분 | DDL | DML |
|---|---|---|
| 대상 | 데이터베이스 구조 | 데이터 |
| 목적 | 스키마 정의 및 변경 | 데이터 조회 및 조작 |
| 대표 명령어 | CREATE, ALTER, DROP | SELECT, INSERT, UPDATE, DELETE |
| 영향 범위 | 테이블/객체 구조 변경 | 테이블 내부 데이터 변경 |

정리하면 **DDL은 데이터베이스 구조를 정의하는 언어이고, DML은 데이터를 조회하거나 조작하는 언어이다.**

---

# 3. 느낀 점

이번 사전 과제를 통해 Spring Boot의 기본 구조와 주요 특징들에 대해서 다시 정리할 수 있었습니다.

또한 SQL의 DDL과 DML을 구분하면서 단순히 사용만 하던 RDBMS 나 NoSQL 같은 DB의 기본적인 개념을 배울 수 있었습니다. 
관리에서 각 명령어가 어떤 역할을 하는지도 명확하게 정리할 수 있었습니다.