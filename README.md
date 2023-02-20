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
1. 스프링부트 + JPA를 기반으로 회원가입, 상품등록, 상품주문/취소 가 가능한 간단한 웹애플리케이션 개발
2. 


- 도메인 모델 설계   

![크기변환 크기변환 001](https://user-images.githubusercontent.com/48856906/213454636-141f822a-2d35-4ecc-acc2-4ed2b78ee918.png)

- 엔티티 설계  

![스크린샷 2023-01-17 오후 12 33 24](https://user-images.githubusercontent.com/48856906/212805568-42c51bcd-7723-4e83-bc3f-5e75fdd30bc3.png)

- 테이블 설계  

![스크린샷 2023-01-17 오후 12 33 41](https://user-images.githubusercontent.com/48856906/212805574-c7d92ab3-16e8-4a2f-8064-058792bc2685.png)

- 애플리케이션 아키텍처 구성  

![크기변환 002](https://user-images.githubusercontent.com/48856906/213454649-8bec543b-fbd1-4406-9ecb-9a740eb5424e.png)


-------------
## 강의 진행

### 1. 프로젝트 환경설정


h2 DB 실행경로 : /h2/bin/h2.sh   
최초 접속 시 url :    
그리고 홈 경로에 ```jpashop.mv.db``` 파일이 생성된것을 확인한다.   
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
