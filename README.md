# SpringBoot-jpa-hello-shop

### 강의명
실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발(김영한)

### 프로젝트 구성
1. 언어 : java 12
2. 프레임워크 : springBoot 2.6.7   
3. 빌드도구 : gradle 7.1
4. ORM : jpa
5. 데이터베이스 : h2
6. 테스트 : Junit5
7. 뷰템플릿 : thymeleaf

### 이 프로젝트에 구현 되어 있는것 + 이 프로젝트를 통해 배운것

서론 : 이 프로젝트는 강의목적으로 실무에서는 권장하지 않는 방법이 많이 사용됨.  
(엔티티 N:N 매핑, 엔티티, dto 에 @Setter 사용등 - 리팩토링이 필요함.)

1. 스프링부트 + JPA를 기반으로 회원가입, 상품등록, 상품주문/취소 가 가능한 간단한 웹애플리케이션 개발
2. 기본적인 타임리프 템플릿 사용법. 타임리프 view html 파일 매핑 경로 확인.
3. application.yml 세팅 - db커넥션 설정. JPA 엔티티구조로 테이블생성, jpa 쿼리 로그에 표시하기.
4. test 패키지는 별도의 application.yml 세팅가능. 테스트구동환경은 테스트 패키지의 application.yml 을 참고함. 
5. test 패키지에 application.yml 파일을 만들어주면 기본적으로 인메모리 DB모드로 테스트를 실행함. (실DB없이 테스트 가능)
5. 도메인 엔티티 설계방법.
6. @Id, @GenerateValue 를 이용해 PK 설정
7. @Embedded 를 이용해 내장타입 속성 설정
8. @OneToOne @ManyToOne, @OneToMany 를 이용해 1:1, 1:N, N:N 관계 설정, 연관관계의 주인과 하인
9. 연관관계를 모두 (fetch = FetchType.LAZY) 로 설정해야 하는 이유. (N+1 문제)
10. 엔티티의 필드값 관련 비즈니스 로직은 엔티티 내부에 설정해놓는게 응집도가 좋다. (서비스단에서 하는것은 객체지향관점에서 좋지않음.)
11. enum 타입 DB저장방식 2가지 (EnumType.ORDINAL, EnumType.STRING) - ORDINAL로 쓸경우 위험성.
12. category 엔티티 parent-child 계층 구조 설계
13. RuntimeException 을 상속받아서 신규 Exception 클래스 생성.
14. @Test 어노테이션에서 실행된 쿼리들은 모두 자동 롤백처리되는 속성

---
모든 연관관계는 지연로딩으로 설정!  
  
* 즉시로딩( EAGER )은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다.   
특히 JPQL을 실행할 때 N+1 문제가 자주 발생한다.    
(N+1 문제. 아래 @ManyToOne 을 EAGER 로 해놓으면.jpql을 사용해서   
order를 100건 조회하면 member도 100건 조회쿼리가 날아감.)    
실무에서 모든 연관관계는 지연로딩( LAZY )으로 설정해야 한다.   
연관된 엔티티를 함께 DB에서 조회해야 하면, fetch join 또는     
엔티티 그래프 기능을 사용한다. @XToOne(OneToOne, ManyToOne)   
관계는 default 가 EAGER 이므로 직접 LAZY로 설정해야 한다.  
---

- 도메인 모델 설계   

![크기변환 크기변환 001](https://user-images.githubusercontent.com/48856906/213454636-141f822a-2d35-4ecc-acc2-4ed2b78ee918.png)

- 엔티티 설계  

![스크린샷 2023-01-17 오후 12 33 24](https://user-images.githubusercontent.com/48856906/212805568-42c51bcd-7723-4e83-bc3f-5e75fdd30bc3.png)

- 테이블 설계  

![스크린샷 2023-01-17 오후 12 33 41](https://user-images.githubusercontent.com/48856906/212805574-c7d92ab3-16e8-4a2f-8064-058792bc2685.png)

- 애플리케이션 아키텍처 구성  

![크기변환 002](https://user-images.githubusercontent.com/48856906/213454649-8bec543b-fbd1-4406-9ecb-9a740eb5424e.png)


-------------
### * 프로젝트 환경설정

1. 스프링부트 스타터로 시작 : https://start.spring.io/  
dependency 선택 : spring web, thymeleaf, jpa, h2, lombok, validation 등
기본 프로젝트 파일 다운로드 받음.

1-1. ide > open > 다운받은 프로젝트의 build.gradle 파일 오픈

2. 롬복 적용
- Preferences plugin lombok 검색 실행 (재시작)
- Preferences Annotation Processors 검색 Enable annotation processing 체크 (재시작)

최근 IntelliJ 버전은 Gradle로 실행을 하는 것이 기본 설정이다.  
이렇게 하면 실행속도가 느리다. 다음과 같이 변경하면 자바로 바로 실행해서 실행속도가 더 빠르다.  
- Preferences Build, Execution, Deployment Build Tools Gradle
- Build and run using: Gradle -> IntelliJ IDEA
- Run tests using: Gradle -> IntelliJ IDEA

3. 로컬에 h2 DB설치
h2 DB 실행경로 : /h2/bin/h2.sh   
최초 접속 시 url : ```jdbc:h2:~/jpashop```  접속.  
최초접속 후 홈 경로에 ```jpashop.mv.db``` 파일이 생성된것을 확인한다.   
이후 접속 시 url : jdbc:h2:tcp://localhost/~/jpashop   

### * 도메인 분석 설계
src/main/java/com/leesh/domains 경로에 도메인 클래스들 생성.   
이후 서버 구동 시 h2 DB에 테이블이 정상적으로 생성된 것 확인 할 수 있다.

* 도메인을 기준으로 생성된 테이블 구조  
![테이블구조1](https://user-images.githubusercontent.com/48856906/221398534-d5738d8e-3788-4c7a-bcc7-35b2c5797960.PNG) ![테이블구조2](https://user-images.githubusercontent.com/48856906/221398537-808fa3fc-c1a3-4812-8544-6e161af8f27b.PNG)

### 3.VIEW

![뷰01](https://user-images.githubusercontent.com/48856906/221398589-0d72bb52-7200-4de8-8d02-ee7607761681.PNG)
![뷰02](https://user-images.githubusercontent.com/48856906/221398590-cdf0781e-fab0-44f0-b05e-66ad4388b091.PNG)
![뷰03](https://user-images.githubusercontent.com/48856906/221398591-d8dcbcc1-9b36-4194-9cae-5e0030c6d4ea.PNG)
![뷰04](https://user-images.githubusercontent.com/48856906/221398592-ed0f5d0b-3b1c-4590-a6b5-af5d67317af0.PNG)
![뷰05](https://user-images.githubusercontent.com/48856906/221398594-fe072c0c-b2dd-4b54-b972-f2754f7fdcd9.PNG)
![뷰06](https://user-images.githubusercontent.com/48856906/221398595-e0b236b1-1f50-4afd-bb4c-b2b5b0a0bdf8.PNG)
![뷰07](https://user-images.githubusercontent.com/48856906/221398596-61f58827-5909-4129-8ab9-b59b7cd0f1ae.PNG)



