# 싱글톤 패턴

* 하나의 클래스에 오직 하나의 인스턴스만 가지는 패턴이다.
* 보통 데이터베이스 연결 모듈에 사용

## 자바의 싱글톤 패턴

#### [HelloWorld](./ex/src/HelloWorld.java)

```java
class Singleton {
    private static class SingleInstanceHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    private Singleton() {}

    public static synchronized Singleton getInstance() {
        return singleInstanceHolder.INSTANCE;
    }
}

public class HelloWorld {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        Singleton a = Singleton.getInstance();
        Singleton b = Singleton.getInstance();
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(a == b);
    }
}
```

* 해당 예시에서 정의한 `Singleton`은 정적 클래스인 `SingleInstanceHolder`에서 `Singleton` 인스턴스인 `INSTANCE`를 가지고 있다.
* `Singleton`의 정적 동기화 메서드 `getInstance()`는 `INSTANCE`에 대한 참조를 제공한다.

#### 실행결과

```text
1067040082
1067040082
true
```

### getInstance()가 동기화 되어야 하는 이유

```java
public static synchronized Singleton getInstance(){
    return singleInstanceHolder.INSTANCE;
}
```

* 해당 메서드를 보면 `synchronized` 키워드가 붙어 동기화 메서드임을 알 수 있다.
* 사실 해당 `Singleton`은 클래스 로딩 시점에 하나의 인스턴스만 생성되어 이를 반환하기 때문에 동기화 순서에 따라
  인스턴스가 여러개 생성될 가능성은 적다.

#### SingletonCustom

```java
class SingletonCustom{
    private static SingletonCustom singleton = null;
    
    private SingletonCustom() {};
    
    public static SingletonCustom getInstance(){
        if(singleton == null){
            singleton = new SingletonCustom();
        }
        return singleton;
    }
}
```

* 해당 `SingletonCustom`과 같은 경우 멀티스레딩 과정에서 `if(singleton == null)`을 통과한 여러 스레드에서
  인스턴스가 생성되는 동시성 이슈가 발생할 수 있다. 때문에 `synchronized` 키워드를 사용하는 것이 안전하다.

  
