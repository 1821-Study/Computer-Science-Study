# 엔티티 매핑


### @Entity

* JPA를 사용해 테이블과 매핑할 클래스는 `@Entity` 어노테이션을 필수로 분여한다. 
* `@Entity`가 붙은 클래스는 JPA가 관리하는 것으로 엔티티라 부른다.


#### @Entity 속성
| 속성   | 기능                                                                                                    | 기본값                        |
|------|-------------------------------------------------------------------------------------------------------|----------------------------|
| name | JPA에서 사용할 엔티티 이름을 지정한다. 보통 기본값인 클래스 이름을 사용한다. 만약 다른 패키지에 이름이 같은 엔티티 클래스가 있다면 이름을 지정해서 충돌하지 않도록 해야 한다. | 설정하지 않으면 클래스 이름을 그대로 사용한다. |

* 기본 생성자는 필수다(파라미터가 없는 `public` 또는 `protected` 생성자)
* `final` 클래스, `enum`, `interface`, `inner` 클래스는 사용할 수 없다.
* 저장할 필드에 final을 사용하면 안 된다.

### @Table

`@Table`은 엔티티와 매핑할 테이블을 지정한다. 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용한다.

#### @Table 속성
| 속성                          | 기능                                                                                             | 기본값           |
|-----------------------------|------------------------------------------------------------------------------------------------|---------------|
| name                        | 매핑할 테이블 이름                                                                                     | 엔티티 이름을 사용한다. |
| catalog                     | catalog 기능이 있는 데이터베이스에서 catalog를 매핑한다.                                                         |               |
| schema                      | schema 기능이 있는 데이터베이스에서 schema를 매핑한다.                                                           |               |
| uniqueConstraints<br/>(DDL) | DDL 생성 시에 유니크 제약조건을 만든다. 2개 이상의 복합 유니크 제약조건도 만들 수 있다.<br/>스키마  자동 생성 기능을 사용해서 DDL을 만들 때만 사용된다. |               |


### 다양한 매핑 사용

#### 예시 매핑 
1. 회언은 일반 회원과 관리자로 구분해야 한다.
2. 회원 가입일과 수정일이 있어야 한다.
3. 회원을 설명할 수 있는 필드가 있어야 한다.


#### [Member.java](./ex1-hello-jpa/src/main/java/hellojpa/Member.java)

```java
package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}

```

#### [RoleType.java](./ex1-hello-jpa/src/main/java/hellojpa/RoleType.java)


```java
package hellojpa;

public enum RoleType {
    ADMIN, USER
}
```

1. `roleType`: 자바의 enum을 사용해서 회원의 타입을 구분했다, 일반 회원은 `USER`, 관리자는 `ADMIN`이다.
2. `createDate`, `lastModifiedDate`: 자바의 날짜 타입은 `@Temporal`을 사용해서 매핑한다. 
3. `description`: 회원을 설명하는 필드는 길이 제한이 없다. 따라서 `VARCHAR` 타입 대신에 `CLOB` 타입으로 저장해야 한다.
   `@Lob`을 사용하여 `CLOB`, `BLOB` 타입을 매핑할 수 있다.

