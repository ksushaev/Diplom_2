package all_for_orders;

import java.util.List;

public class OrderWithIngredients {
    private List<String> ingredients;

    public OrderWithIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderWithIngredients() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
