
## 1. 내가 진행중인 미션 조회 API 추가

### 수정 전

- 기존 API는 `GET /member/{userid}/missions` 형태였다.
- 사용자 ID를 URL Path Variable로 받았다.
- 진행중/완료 상태는 `status` Query Parameter로 필터링했다.

### 수정 후

- `POST /member/missions/ongoing` API를 추가했다.
- 사용자 ID를 Request Body에서 받도록 구현했다.
- 하드코딩 없이 요청 Body의 `userId` 값을 사용한다.
- 진행중 미션만 조회하도록 `Status.CHALLENGING`으로 고정 조회한다.
- 오프셋 기반 페이지네이션을 사용한다.

### Request Body 예시

```json
{
  "userId": 1,
  "page": 0,
  "size": 10
}
```

### 주요 변경 파일

- `src/main/java/org/example/swaggerpr/mission/controller/MissionController.java`
- `src/main/java/org/example/swaggerpr/mission/service/MissionService.java`
- `src/main/java/org/example/swaggerpr/mission/dto/MissionReqDto.java`

## 2. 내가 생성한 리뷰 조회 API 추가

### 수정 전

- 리뷰 생성 API만 존재했다.
- 내가 작성한 리뷰 목록을 조회하는 API는 없었다.
- 리뷰 Repository에 커서 기반 조회 쿼리가 없었다.

### 수정 후

- `POST /users/reviews` API를 추가했다.
- 사용자 ID를 Request Body에서 받는다.
- 커서 기반 페이지네이션으로 응답한다.
- 사진 관련 필드는 응답 DTO에 포함하지 않았다.
- ID 순 조회와 별점 순 조회를 모두 구현했다.

### Request Body 예시: ID 순

```json
{
  "userId": 1,
  "cursorId": null,
  "size": 10,
  "sortBy": "ID"
}
```

### Request Body 예시: 별점 순

```json
{
  "userId": 1,
  "cursorId": null,
  "cursorScore": null,
  "size": 10,
  "sortBy": "SCORE"
}
```

### 응답 구조

- `reviews`: 리뷰 목록
- `nextCursorId`: 다음 요청에 사용할 마지막 리뷰 ID
- `nextCursorScore`: 별점 순 조회에서 다음 요청에 사용할 마지막 별점
- `size`: 현재 응답 개수
- `hasNext`: 다음 페이지 존재 여부

### 주요 변경 파일

- `src/main/java/org/example/swaggerpr/review/controller/ReviewController.java`
- `src/main/java/org/example/swaggerpr/review/service/ReviewService.java`
- `src/main/java/org/example/swaggerpr/review/repository/ReviewRepository.java`
- `src/main/java/org/example/swaggerpr/review/converter/ReviewConverter.java`
- `src/main/java/org/example/swaggerpr/review/dto/ReviewReqDto.java`
- `src/main/java/org/example/swaggerpr/review/dto/ReviewResDto.java`

## 3. Request Body 검증 추가

### 수정 전

- Request Body가 있는 API에 `@Valid`가 붙어 있지 않았다.
- DTO 필드에 검증 어노테이션이 없었다.
- 검증 실패 예외를 `GeneralExceptionAdvice`에서 별도로 처리하지 않았다.

### 수정 후

- Request Body를 사용하는 모든 API에 `@Valid`를 추가했다.
- DTO 필드에 검증 어노테이션을 추가했다.
- `GeneralExceptionAdvice`에 `MethodArgumentNotValidException` 처리 로직을 추가했다.
- 검증 실패 응답을 위한 `GeneralErrorCode.VALIDATION_FAILED`를 추가했다.
- Validation 의존성을 `build.gradle`에 추가했다.

### 적용한 주요 검증

- `@NotNull`
- `@NotBlank`
- `@Min`
- `@Max`
- `@Email`
- `@AssertTrue`

### 주요 변경 파일

- `build.gradle`
- `src/main/java/org/example/swaggerpr/global/apiPayload/code/GeneralErrorCode.java`
- `src/main/java/org/example/swaggerpr/global/apiPayload/handler/GeneralExceptionAdvice.java`
- `src/main/java/org/example/swaggerpr/member/controller/MemberController.java`
- `src/main/java/org/example/swaggerpr/member/dto/MemberReqDto.java`
- `src/main/java/org/example/swaggerpr/mission/controller/MissionController.java`
- `src/main/java/org/example/swaggerpr/mission/dto/MissionReqDto.java`
- `src/main/java/org/example/swaggerpr/review/controller/ReviewController.java`
- `src/main/java/org/example/swaggerpr/review/dto/ReviewReqDto.java`

## 4. 리뷰 생성 로직 수정

### 수정 전

- `ReviewService`의 리뷰 생성 코드에서 `.member(member)`가 깨진 주석에 묻혀 실제 Builder 호출로 동작하지 않는 문제가 있었다.
- 이 상태에서는 `Review.member_id`가 정상 저장되지 않아 리뷰 생성 시 문제가 발생할 수 있었다.

### 수정 후

- `.member(member)`를 정상 Builder 호출로 복구했다.
- 리뷰 생성 시 작성자 회원 정보가 `Review`에 저장된다.

### 주요 변경 파일

- `src/main/java/org/example/swaggerpr/review/service/ReviewService.java`

