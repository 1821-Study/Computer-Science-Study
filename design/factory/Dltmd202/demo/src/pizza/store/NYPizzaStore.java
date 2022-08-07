package pizza.store;

import pizza.pizza.Pizza;
import pizza.pizza.factory.SimplePizzaFactory;

public class NYPizzaStore extends PizzaStoreV2{
    private SimplePizzaFactory factory;

    public NYPizzaStore(SimplePizzaFactory factory) {
        this.factory = factory;
    }
    @Override
    public Pizza createPizza(String type) {
        Pizza pizza = factory.createPizzaV2(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
