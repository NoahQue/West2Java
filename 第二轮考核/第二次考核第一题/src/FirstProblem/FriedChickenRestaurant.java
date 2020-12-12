package FirstProblem;

import java.time.LocalDate;

public interface FriedChickenRestaurant{
    public void sellSetMeal(SetMeal type, LocalDate today);
    public void bulkPurchase(SetMeal type, int amount);
}
