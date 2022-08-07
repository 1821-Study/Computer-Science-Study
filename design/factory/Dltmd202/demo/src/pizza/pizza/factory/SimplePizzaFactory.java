package pizza.pizza.factory;


import pizza.pizza.CheesePizza;
import pizza.pizza.PepperoniPizza;
import pizza.pizza.Pizza;
import pizza.pizza.PotatoPizza;

public class SimplePizzaFactory {
    public static Pizza createPizzaV1(String type){
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

    public Pizza createPizzaV2(String type){
        switch (type){
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
