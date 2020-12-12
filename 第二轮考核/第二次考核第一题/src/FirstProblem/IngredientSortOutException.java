package FirstProblem;

public class IngredientSortOutException extends RuntimeException{
    protected Drink drink;
    IngredientSortOutException(Drink drink){
        this.drink=drink;
    }
    public void printWrong(){
        System.out.println("很抱歉" + drink.name + "已售空");
    }
}
