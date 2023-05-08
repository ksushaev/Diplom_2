package all_for_orders;

import java.util.List;

public class DifferentIngredients {

    public static OrderWithIngredients getNormalOrder() {
        return new OrderWithIngredients(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6e", "61c0c5a71d1f82001bdaaa79"));
    }

    public static OrderWithIngredients getOrderWithoutIngredients() {
        return new OrderWithIngredients();
    }

    public static OrderWithIngredients getOrderWithWrongHash() {
        return new OrderWithIngredients(List.of("1234"));
    }
}
