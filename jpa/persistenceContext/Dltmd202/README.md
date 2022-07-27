# 영속성 관리

📅 2022.07.27.~

JPA가 제공하는 기능은 크게 엔티티와 테이블을 매핑하는 설계 부분과 매핑한 엔티티를 실제 사용하는 부분으로 나눌 수 있다.


## 엔티티 매니저 팩토리와 엔티티 매니저

#### persistence.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
  <persistence-unit name="hello">
    <properties>
      <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
      <property name="javax.persistence.jdbc.user" value="sa"/>
      <property name="javax.persistence.jdbc.password" value=""/>
      <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
      <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
      <property name="hibernate.show_sql" value="true"/>
      <property name="hibernate.format_sql" value="true"/>
      <property name="hibernate.use_sql_comments" value="true"/>
    </properties>
  </persistence-unit>
</persistence>
```

* `Persistence.createEntityManagerFactory("hello");`: `META-INF/persistence.xml`의 정보를 바탕으로
  `EntityManagerFactory`를 생성한다.
* `EntityManager em = emf.createEntityManager();` : 를 통해서 엔티티 매니저를 생성


* 엔티티 매니저는 엔티티를 저장하고, 수정하고, 삭제하고, 조회하는 등 엔티티와 관련된 모든 일을 처리한다. 
* 데이터베이스를 하나만 사용하는 애플리케이션은 일반적으로 `EntityManagerFactory`를 하나만 생성한다.
  * 엔티티 매니저 팩토리는 엔티티 매니저를 만드는 공장, 공장을 만드는 비용이 큼
  * 반면에 공장에서 엔티티 매니저를 생성하는 비용은 거이 들지 않음


> 주의
>
> 엔티티 매니저 팩토리는 여러 스레드가 동시에 접근해도 안전하므로 서로 다른 스레드 간에 공유해도 되지만, 엔티티 매니저는
> 여러 스레드가 동시에 접근하면 동시성 문제가 발생하므로 스레드 간에 공유하면 안된다.

#### 일반적인 웹 애플리케이션
  
![](res/img.png)


* `EntityManagerFactory`에서 다수의 엔티티 매니저를 생성한다.
* 그림에서 `EntityManager1`은 커넥션을 사용하지 않고있는데 엔티티 매니저는 데이터베이스 연결이 필요한 시점까지
  커넥션을 얻지 않는다.
  * 트랜잭션을 시작할 때 커넥션을 획득
  * `EntityManger2`는 커넥션을 사용중인데 보통 트랜잭션으 시작할 때 커넥션을 확득한다.
* 대부분의 JPA 구현체들은 `EntityManagerFactory`를 생성할 때 커넥션풀을 만든다.
  * J2SE 환경에서 사용하는 방법이다.

## 영속성 컨텐스트란(Persistence Context)

> 엔티티를 영구 저장하는 환경

* `em.persist(member);`
  * 해당 코드가 회원 엔티티를 저장한다고 표현하는것은 옳지 못함
  * 엔티티 매니저를 사용해서 회원 엔티티를 영속성 컨텍스트에 저장한다. 는 맥락이 정확한 표현이다.

## 엔티티의 생명주기

> 엔티티의 4가지 생명주기


![](res/img_1.png)


#### 비영속

* `new`/`trasient`
* 영속성 컨텍스트와 전혀 관계가 없는 상태
* 엔티티 객체를 생성하여 순수한 객체 상태


![](res/img_2.png)


```java
Member member = new Member();
member.setId(1L);
member.setName("helloA");
```



#### 영속


* `managed`
* 영속성 컨텍스트에 저장된 상태





```java
Member member = new Member();
member.setId(1L);
member.setName("helloA");

em.persist(member);
```

#### 준영속

* `detached`
* 영속성 컨텍스트에 저장되었다가 분리된 상태

#### 삭제

* `removed`
* 삭제된 상태


## 영속성 컨텍스트의 특징

#### 영속성 컨텍스트의 식별자 값
* 영속성 컨텍스트는 엔티티의 식별자 값으로 엔티티를 구분한다.
* 영속 상태는 식별자 값이 반드시 있어야 한다.

#### 영속성 컨텍스트와 데이터베이스 저장
* `JPA`는 보통 트랜잭션을 커밋하는 순산에 영속성 컨텍스트에 데이터를 반영함
  * `flush` 라고 함

#### 엔티티 관리에 대한 영속성 컨텍스트의 특징

* 1차 캐시
* 동일성 보장
* 트랜잭션을 지원하는 쓰기 지연
* 변경 감지
* 지연 로딩

### 엔티티 조회

* 영속성 컨텍스트 내부에 1차 캐시를 가지고 있다. 
  * 영속 상태의 엔티티는 모두 이곳에 저장된다.
* 영속성 컨텍스트 내부에 `Map`과 같은 자료구조가 있다
  * `@Id`로 매핑한 칼럼을 식별자로 한다.


![](res/img_3.png)


```java
Member memebr = new Member();
member.setId("member1");
member.setUsername("회원1");

em.persist(member);
```
