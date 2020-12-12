package FirstProblem;

public class SetMeal {

    protected String setMealName;
    protected double setMealPrice;
    protected String friedChicken;
    protected Drink drink;

    SetMeal(String setMealName, double setMealPrice, String friedChicken, Drink drink){
        this.setMealName = setMealName;
        this.setMealPrice = setMealPrice;
        this.friedChicken = friedChicken;
        this.drink = drink;
    }



    public String toString(){
        return "套餐名：" + setMealName + "/n套餐价格：" + setMealPrice +
                "/n炸鸡种类：" + friedChicken + "/n饮品种类：" + drink + "/n";
    }
}
