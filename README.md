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

서론 : 이 프로젝트는 강의목적으로 실무에서는 권장하지 않는 방법이 사용된 케이스가 있음  
(엔티티 N:N 매핑, 엔티티에 @Setter 사용등 - )

1. 스프링부트 + JPA를 기반으로 회원가입, 상품등록, 상품주문/취소 가 가능한 간단한 웹애플리케이션 개발
2. 기본적인 타임리프 템플릿 사용법. 타임리프 view html 파일 매핑 경로 확인.
3. application.yml 세팅 - db커넥션 설정. JPA 엔티티구조로 테이블생성, jpa 쿼리 로그에 표시하기.
4. test 패키지는 별도의 application.yml 세팅가능. 테스트구동환경은 테스트 패키지의 application.yml 참고함. 
5. h2 url 을 인메모리모드로 하면 실제 db를 사용하지않고 JVM안에서 가상db를 사용함. (테스트 db 환경구축용)
6. 그러나 사실 아래 datasource 설정을 따로 안해줘도 된다.
7. test 패키지에 application.yml 파일을 만들어주면 스프링부트에서 기본적으로 인메모리 DB모드로 테스트를 실행시키기 때문.
8. 도메인 엔티티 설계방법. (PK, @Embedded, 1:1, 1:N, N:N 연관관계 매핑등) 

- 도메인 모델 설계   

![크기변환 크기변환 001](https://user-images.githubusercontent.com/48856906/213454636-141f822a-2d35-4ecc-acc2-4ed2b78ee918.png)

- 엔티티 설계  

![스크린샷 2023-01-17 오후 12 33 24](https://user-images.githubusercontent.com/48856906/212805568-42c51bcd-7723-4e83-bc3f-5e75fdd30bc3.png)

- 테이블 설계  

![스크린샷 2023-01-17 오후 12 33 41](https://user-images.githubusercontent.com/48856906/212805574-c7d92ab3-16e8-4a2f-8064-058792bc2685.png)

- 애플리케이션 아키텍처 구성  

![크기변환 002](https://user-images.githubusercontent.com/48856906/213454649-8bec543b-fbd1-4406-9ecb-9a740eb5424e.png)


-------------
### 1. 프로젝트 환경설정

1. 스프링부트 스타터로 시작 : https://start.spring.io/
dependency 선택 : web, thymeleaf, jpa, h2, lombok, validation 등

2. 롬복 적용
- Preferences plugin lombok 검색 실행 (재시작)
- Preferences Annotation Processors 검색 Enable annotation processing 체크 (재시작)

최근 IntelliJ 버전은 Gradle로 실행을 하는 것이 기본 설정이다.  
이렇게 하면 실행속도가 느리다. 다음과 같이 변경하면 자바로 바로 실행해서 실행속도가 더 빠르다.  
- Preferences Build, Execution, Deployment Build Tools Gradle
- Build and run using: Gradle IntelliJ IDEA
- Run tests using: Gradle IntelliJ IDEA

3. 로컬에 h2 DB설치
h2 DB 실행경로 : /h2/bin/h2.sh   
최초 접속 시 url : ```jdbc:h2:~/jpashop```  접속.  
최초접속 후 홈 경로에 ```jpashop.mv.db``` 파일이 생성된것을 확인한다.   
이후 접속 시 url : jdbc:h2:tcp://localhost/~/jpashop   

### 2.도메인 분석 설계
src/main/java/com/leesh/domains 경로에 도메인 클래스들 생성.   
이후 서버 구동 시 h2 DB에 테이블이 정상적으로 생성된 것 확인 할 수 있다.

![스크린샷 2023-01-27 오전 9 10 52](https://user-images.githubusercontent.com/48856906/214979019-82633210-48ef-4d4f-a3d0-064e765bc60e.png)

## 3. 엔티티 설계 시 주의점
1. 엔티티에는 가급적 Setter를 사용하지 말자 
  
* Setter가 모두 열려있으면 변경 포인트가 너무 많아서, 유지보수가 어렵다.    
나중에 리펙토링으로 Setter 제거

2. 모든 연관관계는 지연로딩으로 설정!  
  
* 즉시로딩( EAGER )은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다.   
특히 JPQL을 실행할 때 N+1 문제가 자주 발생한다.    
(N+1 문제. 아래 @ManyToOne 을 EAGER 로 해놓으면.jpql을 사용해서   
order를 100건 조회하면 member도 100건 조회쿼리가 날아감.)    
실무에서 모든 연관관계는 지연로딩( LAZY )으로 설정해야 한다.   
연관된 엔티티를 함께 DB에서 조회해야 하면, fetch join 또는     
엔티티 그래프 기능을 사용한다. @XToOne(OneToOne, ManyToOne)   
관계는 default 가 EAGER 이므로 직접 LAZY로 설정해야 한다.    

내용추가하기
