# Chapter02 미션 기록

**Name:** 리온/최형석  
**Mission:** Chapter02

---

# 1. 2주차 워크북 학습 후기

> API와 RESTful API에 대한 근본적인 개념을 명확히 이해할 수 있었고 실제 API 설계 시에 고려해야될 요소들에 대해서도 배울 수 있어서 좋았습니다. 특히 멱등성을 배우면서 POST가 Idempotency Key가 있다면 멱등성이 보장되는 점, PATCH는 자체적으로 멱등성이 보장될 수도 있고 안될 수도 있다는 점이 흥미로웠습니다. 앞으로 API 설계 시에 RESTful 원칙과 멱등성 보장에 더욱 신경 써서 개발할 수 있을 것 같습니다.

---

# 2. 핵심 키워드 정리

## 1. RESTful API

REST(Representational State Transfer) 아키텍처 스타일을 준수하는 API 설계 방식

### 특징
- 자원(Resource)을 URI로 표현하고 HTTP 메서드로 행위를 표현
- Stateless 구조 (서버가 상태를 저장하지 않음)
- Client - Server 구조
- Uniform Interface (일관된 인터페이스)

### REST 설계 규칙

### 1. URI는 명사 기반
- ❌ /getUser
- ✅ /users
   
### 2. 자원은 기본적으로 복수형으로 표현
- ❌ /user
- ✅ /users

### 3. 동사 대신 HTTP 메서드로 행위 표현
- ❌ /users/delete/1
- ✅ DELETE /users/1

### 4. 단 하나의 자원을 명시적으로 표현하기 위해서는 식별 값을 추가로 사용
- GET /users/1 → 사용자 1 조회

### 5. 자원 간 연관 관계 표현
- GET /users/1/orders → 사용자 1의 주문 목록 조회

---

## 2. 멱등성 (Idempotency)

> 같은 요청을 여러 번 보내도 결과가 동일한 성질

### 예시
- GET /users/1 → 여러 번 호출해도 동일 결과
- DELETE /users/1 → 여러 번 호출해도 동일 상태

### 중요한 이유
- 네트워크 재시도 안정성 보장
- 중복 요청 방지

### HTTP 메서드와 관계
- 멱등성 O → GET, PUT, DELETE
- 멱등성 X → POST, PATCH (상황에 따라 다름)

### PUT vs PATCH
- PUT: 전체 수정 (덮어쓰기) → 멱등성 O
- PATCH: 부분 수정 → 멱등성 상황에 따라 다름(추가, 증가 같은 연산)

### Idempotency Key

> 같은 요청을 여러 번 보내도 서버에서 한 번만 처리되도록 보장하는 키

- REST API 요청에서 멱등성을 보장하기 위해 클라이언트가 고유한 키를 서버에 전달하여 중복 요청을 방지하는 방법
- 네트워크 지연 등으로 인해 클라이언트가 동일한 요청을 여러 번 보낼 수 있는 상황을 방지(결제 요청 등)

### 동작 방식 
클라이언트 
  - 요청 + Idempotency Key -> 서버

서버 
  1. Idempotency Key 확인
  2. 키가 존재하지 않으면 요청 처리 후 결과 저장
  3. 키가 존재하면 저장된 결과 반환

---

## 3. HTTP 메서드 정리

### HTTP 메서드 
> 자원(Resource)에 대해 수행할 행위를 정의하는 HTTP 요청 방식

---

### GET
- 데이터 조회
- 멱등성 O / Safe O
- HTTP Status Code: 200 OK

```http
GET /users  
GET /users/1
```

---

### POST
- 데이터 생성
- 멱등성 X
- HTTP Status Code: 201 Created

```http
POST /users  
Content-Type: application/json

{  
  "name": "Leon",  
  "age": 25  
}
```

---

### PUT
- 전체 수정 (덮어쓰기)
- 멱등성 O
- HTTP Status Code: 200 OK

```http
PUT /users/1  
Content-Type: application/json

{  
  "name": "Leon",  
  "age": 26  
}
```

---

### PATCH
- 부분 수정
- 멱등성 (상황에 따라 다름)
- HTTP Status Code: 200 OK

```http
PATCH /users/1  
Content-Type: application/json

{  
  "age": 27  
}
```

---

### DELETE
- 데이터 삭제
- 멱등성 O
- HTTP Status Code: 204 No Content

```http
DELETE /users/1
```

---

# 3. API 명세서

## API 설계 과정

주어진 요구사항과 기존에 설계한 ERD를 기반으로 설계 진행

### 1. 요구사항 정리
- 홈 화면
- 마이 페이지
- 리뷰 작성
- 미션 목록 조회 (진행중, 완료)
- 미션 성공 처리
- 회원가입 (소셜 로그인 제외)

---

### 2. 기능(도메인) 기준으로 분류

기능 단위로 요구사항 분류

- 인증 (회원가입, 로그인)
- 홈 (지역 기반 미션 조회)
- 미션 (조회, 수행, 완료)
- 리뷰 (리뷰 작성)
- 마이페이지 (사용자 정보 및 활동)

---

### 3. ERD 기반으로 API 구조 설계

기존에 설계한 ERD의 테이블 구조를 기준으로 API를 설계

- `region → store → mission`  
  → 지역 선택 시 미션 목록이 조회되는 구조이기 때문에 `/regions/{regionId}/missions` 형태로 설계

- `member_mission`  
  → 사용자의 미션 진행 상태에 따라 조회하기 때문에 `/missions?status=ONGOING` 형태로 조회 API 설계

- `review`  
  → 특정 가게에 종속되기 때문에 `/stores/{storeId}/reviews` 형태로 설계

- `member`  
  → 사용자 정보 조회 및 마이페이지 기능을 `/users/me`로 통합

---

## API 구성 요소 설명

- **Header**  
  요청에 대한 메타 정보로, 인증 토큰(Authorization)이나 데이터 형식(Content-Type) 등을 전달

- **Request Body**  
  요청 시 서버로 전달하는 데이터로, 주로 생성이나 수정에 필요한 정보를 포함

- **Query Parameter**  
  URL 뒤에 붙는 값으로, 정렬, 필터링, 상태 조회 등 추가 조건을 전달

- **Path Variable**  
  URL 경로에 포함되는 변수로, 특정 자원(resource)을 식별하기 위해 사용

---

## 인증 (Auth)

| API Name | Method | Endpoint      | Header                          | Request Body                                                     | Query | Path | 설명           |
|----------|--------|---------------|---------------------------------|------------------------------------------------------------------|-------|------|--------------|
| 회원가입 | POST | /auth/signup  | Content-Type: application/json  | email, password, name, gender, birthDate, address, serviceAgree  | - | - | 일반 회원가입      |
| 로그인 | POST | /auth/login   | Content-Type: application/json  | email, password                                                  | - | - | 로그인 후 토큰 발급  |

---

## 홈 (지역 기반 조회)

| API Name | Method | Endpoint                      | Header         | Request Body  | Query | Path     | 설명                    |
|----------|--------|-------------------------------|----------------|---------------|-------|----------|-----------------------|
| 지역 미션 조회 | GET | /regions/{regionId}/missions  | Authorization  | - | - | regionId | 특정 지역의 가게 및 미션 목록 조회  |

---

## 미션

| API Name | Method | Endpoint                        | Header         | Request Body  | Query                        | Path       | 설명                         |
|----------|--------|---------------------------------|----------------|---------------|------------------------------|------------|----------------------------|
| 미션 목록 조회 | GET | /missions                       | Authorization  | - | status (ONGOING, COMPLETED)  | -          | 진행중 / 완료 미션 조회             |
| 미션 시작 | POST | /missions/{missionId}/start     | Authorization  | - | -                            | missionId  | 미션 시작 (member_mission 생성)  |
| 미션 완료 | POST | /missions/{missionId}/complete  | Authorization  | - | -                            | missionId  | 미션 성공 처리 및 포인트 지급          |

---

## 리뷰

| API Name | Method | Endpoint                   | Header                                         | Request Body     | Query | Path    | 설명               |
|----------|--------|----------------------------|------------------------------------------------|------------------|-------|---------|------------------|
| 리뷰 작성 | POST | /stores/{storeId}/reviews  | Authorization, Content-Type: application/json  | rating, content  | - | storeId | 특정 가게에 대한 리뷰 작성  |

---

## 마이페이지 (User)

| API Name | Method | Endpoint               | Header                                         | Request Body   | Query | Path | 설명               |
|----------|--------|------------------------|------------------------------------------------|----------------|-------|------|------------------|
| 내 정보 조회 | GET | /users/me              | Authorization                                  | -              | - | - | 로그인한 사용자 정보 조회   |
| 내 미션 조회 | GET | /users/me/missions     | Authorization                                  | -              | status | - | 내가 진행/완료한 미션 조회  |
| 내 리뷰 조회 | GET | /users/me/reviews      | Authorization                                  | -              | - | - | 내가 작성한 리뷰 목록 조회  |
| 선호 음식 설정 | POST | /users/me/preferences  | Authorization, Content-Type: application/json  | categoryIds[]  | - | - | 선호 음식 카테고리 등록    |

---