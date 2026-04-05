
### 핵심 키워드

#### 아키텍처 구조란?
개념 : 소프트웨어 아키텍처는 시스템을 구성하는 요소들과 그 요소들 간의 관계, 그리고 이것들을 따르는 원칙 전체입니다.
- 랄프 존슨(Ralph Johnson)은 **아키텍처에 대해서 전문가 개발자들이 시스템 설계에 대해 공유하는 이해, 그리고 아키텍처는 중요한 것들에 관한 것이다. 그게 무엇이던간에**라고 했습니다.
- 마틴 파울러(martin fowler)는 부실한 아키텍처는 개발자의 이해를 방해하는 불필요한 요소(크러프트)가 쌓이는 원인이 된다고 했습니다.
- 제 개인적인 생각으로는 아키텍처에 대해 여러 글들을 찾아보았지만 공통적으로 설계방식, 구조, 구성요소 간의 관계, 상호작용의 설계, 기본 구조(뼈대), 개발자의 상호 이해, 소프트웨어의 여러 규칙 등의 키워드가 반복적으로 나왔습니다 그래서 저의 결론으로는 아키텍처는 소프트웨어를 설계하는 기본 틀이자 그 요소들의 구성 및 관계, 개발자들의 상호 이해를 위한 기초 설계도(틀) 이라고 이해했습니다.

서버 입장에서의 아키텍처의 종류
1) 시스템 전체의 구조적 방식
    - 모놀리식 아키텍처 : 모든 기능을 단일 코드베이스에서 하나의 애플리케이션으로 빌드하고 배포하는 아키텍처로 프론트, 비즈니스로직, 데이터베이스가 모두 하나의 애플리케이션으로 구성됩니다.
    - 마이크로서비스 아키텍처 : 시스템을 여러 개의 작은 서비스로 나누어 구성하고 이 서비스들이 모여 하나의 시스템을 이루는 방식입니다.
    - 서비스 지향 아키텍처(SOA) : 백엔드에서 사용되는 아키텍처 중 하나로 애플리케이션의 기능을 비즈니스적인 의미를 가지는 기능 단위로 묶어서 서비스 인터페이스를 구축해 소프트웨어 구성 요소를 재사용 및 상호 운용 가능하게 만드는 방식입니다.
2) 애플리케이션 내부의 코드 구성 방식
    - 계층형 아키텍처 : 애플리케이션을 기능별로 계층화하여 설계하는 아키텍처입니다.
    - 클린 아키텍처 : 비즈니스 로직이 외부 구현 사항에 의존하지 않도록 독립적으로 만들어 독립적인 테스트가 용이하고 비즈니스 로직이 외부의 영향을 받지 않습니다
    - 헥사고날 아키텍처 : 비즈니스 로직을 중심에 두고 외부 시스템을 어댑터를 통해 연결하여 외부 시스템과의 의존성을 최소화합니다. 포트와 어댑터 아키텍처라고도 하며, 포트는 어댑터에 대한 명세만을 제공하여 인터페이스 정의만 존재하며 DI를 위해 사용됩니다. 어댑터는 실제로 포트를 통해 인프라와 연결하는 부분을 담당하는 구현체입니다.
그 외에도 정말 많은 종류가 있지만 전부 정리하기엔 너무 길어질 것 같아서 제가 참고한 글에 있는 구조를 정리해봤습니다.

#### Swagger란?
- 개발자가 RestAPI를 개발 시 웹 서비스의 설계, 빌드, 문서화 등을 편리하게 할 수 있도록 도와주는 오픈소스 소프트웨어 프레임워크입니다.
- 협업 시에 API문서화를 도와주므로 불필요한 작업을 하지 않도록 도와줍니다.

#### 도메인형 아키텍처란?
- 개념 : 비즈니스 로직에 중점을 둔 아키텍처로, 도메인(비즈니스 중심으로 코드를 작성합니다.)
- 장점 : 도메인별로 흐름을 알고 싶을때 파악하기 쉽고, 변경 범위가 적습니다.
- 단점 : 애플리케이션의 전반적인 흐름을 파악하기 힘들고 도메인 간 코드 중복이 발생할 수 있습니다.
```
com.nexus.marketplace
├── order (주문 도메인)
│   ├── controller    
│   ├── service       
│   ├── repository    
│   ├── domain        
│   ├── dto           
│   └── exception     
│
├── inventory (재고 도메인)
│   ├── controller
│   ├── service
│   ├── repository
│   ├── domain
│   ├── dto
│   └── exception
│
├── shipping (배송 도메인)
│   ├── controller
│   ├── service
│   ├── repository
│   ├── domain
│   ├── dto
│   └── exception
│
├── common (공통 모듈)
│   ├── utils          
│   ├── response       
│   └── auth           
│
└── config (시스템 설정)
    ├── database      
    ├── security      
    └── swagger       
```
#### DDD vs 도메인형 아키텍처

#### DDD
- 비즈니스 도메인에 깊이 집중하여 이를 기반으로 소프트웨어를 설계하는 방법론
- Eric Evans의 "Domain-Driven Design"에서 처음 정리됨
- 전략적 DDD : 대규모 소프트웨어 시스템을 도메인 영역에 따라 Bounded Context로 나누고, Context Map을 통해 컨텍스트 간 관계와 통합 방식을 정의합니다. 핵심 도메인을 식별하는것이 중요합니다.
- 전술적 DDD : 모델링 관점에서 구체적인 구현 기법을 다루며 Entity, Value Object, Agreegate, Domain Service, Application Service, Repository 등 DDD 구성요소를 실제 코드로 어떻게 옮길지 초점을 맞춥니다.

#### 경계 컨텍스트 (Bounded Context) 
- 도메인 관점에서 의미와 유효범위를 물리적·구조적으로 코드와 데이터를 분리하는 것

#### 콘텍스트 맵 (Context Map)
- 여러 Bounded Context간의 관계와 통합 방식을 시각화한 지도

#### 유비쿼터스 언어 (Ubiquitous Language)
- 개발자와 기획자가 동일한 용어로 소통하기 위한 공통 언어
- 코드의 클래스, 메서드, 변수명도 이 언어를 그대로 반영합니다.
- Bounded Context마다 유비쿼터스 언어가 다를 수 있습니다.
    ex) 주문(order)의 의미가 주문 도메인과 배송 도메인에서 다를 수 있음

#### 서브 도메인 (Sub Domain)
- Core Domain : 비즈니스 핵심 경쟁력, 차별화 요소
- Generic Domain : 범용적 기능 (인증, 결제, 알림 등)
- Supporting Domain : Core를 지원하는 보조 기능

| 구분 | DDD | 도메인형 아키텍처 |
|------|-----|-----------------|
| **성격** | 소프트웨어 설계 **방법론** | 코드 **패키지 구조 패턴** |
| **목적** | 복잡한 비즈니스 문제를 모델로 해결 | 코드 유지보수성·응집도 향상 |
| **범위** | 비즈니스 분석 → 설계 → 구현 전반 | 패키지/폴더 구조에만 해당 |
| **핵심 개념** | Bounded Context, Aggregate, Ubiquitous Language 등 | 도메인별 레이어(controller/service/...) 분리 |
| **팀 협업** | 도메인 전문가 + 개발자 협업 필수 | 개발자 중심의 구조화 |
| **복잡도** | 높음 (전략/전술 패턴 습득 필요) | 낮음 (구조 패턴만 적용) |
| **상호관계** | DDD 적용 시 도메인형 구조를 쓸 수 있음 | DDD 없이도 독립적으로 사용 가능 |

#### 왜 DTO를 사용하는가?
DTO : 프로세스 간 데이터를 전달하는 객체
- DTO대신 엔티티를 사용하면 엔티티 구조가 노출 될 수 있어 보안에 취약해 질 수 있습니다.
- 클라이언트로 넘겨줘야 할 데이터는 API마다 다를 수 있기 때문에 반환값으로 사용하면 유지보수가 힘듭니다.
- DTO 엔티티 사용 시 대신 클라이언트 요구사항에 엔티티가 영향을 받을 수 있습니다.
- 엔티티만 사용했을 경우 발생하는 버그와 유지보수의 난이도가 높습니다.
- Swagger등의 코드들과 엔티티 코드를 분리하여 코드가 깔끔해지고 관리가 용이해집니다.

#### 컨버터는 왜 사용하는가?
 
- JPA Converter (엔티티와 데이터베이스에서 서로 변환 할 때 사용) 
```
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {

    // 1. 자바 엔티티 -> DB 컬럼으로 변환 (저장할 때)
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        if (attribute == null) return null;
        
        if (attribute == Gender.MALE) {
            return "M";
        } else if (attribute == Gender.FEMALE) {
            return "F";
        }
        
        throw new IllegalArgumentException("알 수 없는 성별입니다.");
    }

    // 2. DB 컬럼 -> 자바 엔티티로 변환 (읽어올 때)
    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        
        if (dbData.equals("M")) {
            return Gender.MALE;
        } else if (dbData.equals("F")) {
            return Gender.FEMALE;
        }
        
        throw new IllegalArgumentException("알 수 없는 DB 성별 코드입니다: " + dbData);
    }
}
```
- HTTP message converter (네트워크와 자바 객체 사이의 포맷 변환에 사용)
- HTTP message body를 사용한 요청과 응답 처리 시 사용, 스프링에서는 Jackson을 기본으로 사용하지만 다른 라이브러리로 교체가 가능하다.
1) 컨트롤러 파라미터에 @RequestBody가 있으면 루프를 돌아 canRead()를 호출해 HTTP 요청의 대상 클래스 타입과 Content-Type 미디어 타입을 지원하는지 체크하고 canread()를 만족하면 read()로 객체를 생성 후 반환합니다
2) 컨트롤러에서 @ResponseBody로 반환되면 메세지 컨버터가 메세지를 쓸 수 있는지 확인하기 위해 루프를 돌며 canWrite()를 호출해 HTTP 요청의 대상 클래스 타입과 HTTP 요청의 Accept미디어 타입을 지원하는지 체크 후 canWrite()조건을 만족하면 write()를 호출해 HTTP 응답 메세지 바디에 데이터를 생성합니다.
```
// AbstractHttpMessageConverter에 구현된 템플릿 메서드 패턴의 일부입니다.
@Override
public boolean canRead(Class<?> clazz, @Nullable MediaType mediaType) {
    return supports(clazz) && canRead(mediaType);
}

protected boolean canRead(@Nullable MediaType mediaType) {
    if (mediaType == null) {
        return true;
    }
    for (MediaType supportedMediaType : getSupportedMediaTypes()) {
        if (supportedMediaType.includes(mediaType)) {
            return true;
        }
    }
    return false;
}

@Override
public boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType) {
    return supports(clazz) && canWrite(mediaType);
}

protected boolean canWrite(@Nullable MediaType mediaType) {
    if (mediaType == null || MediaType.ALL.equalsTypeAndSubtype(mediaType)) {
        return true;
    }
    for (MediaType supportedMediaType : getSupportedMediaTypes()) {
        if (supportedMediaType.isCompatibleWith(mediaType)) {
            return true;
        }
    }
    return false;
}
```

- Converter<S,T> (단순 문자열을 의미 있는 객체로 바꾸는 타입 바인딩에 사용) 
- 스프링에서 제공하는 타입 변환 인터페이스로, 개발자가 원하는 대로 단순하게 1:1 변환을 하는 컨버터입니다. 
- 이 외에도 다른 컨버터들이 있고 각 컨버터는 ConversionService에 등록되어 사용됩니다.
```
// "127.0.0.1:8080" → IpPort 객체 (ip="127.0.0.1", port=8080)로 변환하는 코드
class StringToIpPortConverter implements Converter<String, IpPort> {
    
    @Override
    public IpPort convert(String source) {
        String[] split = source.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        
        return new IpPort(ip, port);
    }
}
```
위의 예시 말고도 계층 간 관심사 분리, 보안, 데이터 무결성, 확장성과 유연성 등의 이유도 있습니다.

---

