package pizza.store;

import pizza.pizza.CheesePizza;
import pizza.pizza.PepperoniPizza;
import pizza.pizza.Pizza;
import pizza.pizza.PotatoPizza;
import pizza.pizza.factory.SimplePizzaFactory;

public class PizzaStoreV1 {
    public Pizza orderPizza(){
        Pizza pizza = new Pizza();
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    /**
     * 1단계<br/>
     * 추상화 해야함
     * @param type
     * @return
     */
    public Pizza orderPizzaV1(String type){
        Pizza pizza = switch (type) {
            // 변할 수 있음 추상화 해야 함
            case "cheese" -> new CheesePizza();
            case "potato" -> new PotatoPizza();
            case "pepperoni" -> new PepperoniPizza();
            default -> null;
        };
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    /**
     * 2단계<br/>
     * Pizza와 SimplePizzaFactory만 의존함<br/>
     * 장점: 피자 생성이 필요한 곳이면 어디든 사용 가능<br/>
     * 다른 종류의 피자 생성이 필요하면 기존 코드 수정 없이 createPizza 메서드만 수정<br/>
     * 단점: 유연하지 못함(static 메서드)
     *
     * @param type
     * @return
     */
    public Pizza orderPizzaV2(String type){
        // 구체적인 피자를 의존하지 않음
        // Pizza와 SimplePizzaFactory 클래스만 의존함
        Pizza pizza = SimplePizzaFactory.createPizzaV1(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    private SimplePizzaFactory factory;

    public PizzaStoreV1(SimplePizzaFactory factory) {
        this.factory = factory;
    }

    /**
     * 3단계<br/>
     * static메서드를 사용하지 않고 있다. 이 경우 상속을 통해 확장이 가능하다.<br/>
     *  다양한 Factory를 만들 수 있다.<br/>
     *  전략 패턴<br/>
     *  Factory 인스턴스를 전략 패턴으로 가져갈 필요가 없을 수도 있다.
     * @param type
     * @return
     */
    public Pizza orderPizza(String type){
        Pizza pizza = factory.createPizzaV2(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
