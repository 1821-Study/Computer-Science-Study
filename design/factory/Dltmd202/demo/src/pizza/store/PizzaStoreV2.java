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
