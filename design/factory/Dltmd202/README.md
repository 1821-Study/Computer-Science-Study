# 팩토리 패턴

* 팩토리 패턴은 객체 생성 부분을 떼어내 추상화한 패턴 
* 상속 관계에 있는 클래스에서 상위 클래스가 중요한 뼈대를 결정
* 하위클래스에서 객체 생성에 관한 구체적인 내용을 결정
* 구체 클래스의 인스턴스가 생성되는지 캡슐화해주며, 언제, 누가 어떻게 생성하는지 숨겨줄 수 있다.

#### [PizzaStoreV2.java](./demo/src/pizza/store/PizzaStoreV2.java)

```java
package pizza.store;

import pizza.pizza.Pizza;

public abstract class PizzaStoreV2 {
    /**
     * 이 메서드는는 고정 prepare, bake, cut, box 등의 순서가<br/>
     * 유지되기를 바라기 때문에
     * @param type
     * @return
     */
    public final Pizza orderPizza(String type){
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    /**
     * 하위 클래스가 수정하도록 위임함
     * @param type
     * @return
     */
    public abstract Pizza createPizza(String type);
}
```


#### [SimplePizzaFactory.java](./demo/src/pizza/pizza/factory/SimplePizzaFactory.java)

```java
package pizza.pizza.factory;


import pizza.pizza.CheesePizza;
import pizza.pizza.PepperoniPizza;
import pizza.pizza.Pizza;
import pizza.pizza.PotatoPizza;

public class SimplePizzaFactory {
  public static Pizza createPizzaV1(String type) {
    switch (type) {
      // 변할 수 있음 추상화 해야 함
      case "cheese":
        return new CheesePizza();
      case "potato":
        return new PotatoPizza();
      case "pepperoni":
        return new PepperoniPizza();
      default:
        return null;
    }
  }

  public Pizza createPizzaV2(String type) {
    switch (type) {
      case "cheese":
        return new CheesePizza();
      case "potato":
        return new PotatoPizza();
      case "pepperoni":
        return new PepperoniPizza();
      default:
        return null;
    }
  }
}
```


### `new` 대신에 객체 생성 메서드의 사용

#### 객체 생성과 관련된 복잡한 과정을 추상화할 수 있음

* 필요한 여러 곳에서 사용할 수 있음
* 복잡한 생성 과정에 변화가 필요하면 이 메서드만 수정하면 된다.
* 객체 생성에 필요한 과정이 복잡하여 생성자에 해당 과정을 포함하기 힘들 수 있음

#### 객체 생성과 관련된 다양한 최적화와 필요한 제한을 할 수 있음

* Singleton 패턴을 유지 한다던가, 객체 풀(Thread 풀, jdbcConnection 풀 등)
  * 기존에 만든 객체 풀에서 객체를 제공할 수 있다.

#### 생성 메서드의 이름을 통해 가동성이 향상됨

* `new SoccerStriker` -> `createStriker`
* `new SoccerMidfielder` -> `createMidfielder`


#### 한 종류의 객체가 아니라 인자에 따라 여러 종류의 객체 중 하나를 생성할 수 있음 

* 새로운 종류의 객체가 추가되더라도 기존 코드에 영향을 주지 않고 생성 메서드만 수정하면 동작함