package FirstProblem;

import java.time.LocalDate;
import java.util.LinkedList;


public class Test {
    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2020,12,12);
        LocalDate productionDay1 = LocalDate.of(2020,12,11);
        LocalDate productionDay2 = LocalDate.of(2020,12,1);
        Beer beer = new Beer("青岛啤酒",4,productionDay1,11);
        Juice juice1 = new Juice("橙汁",4,productionDay1);
        Juice juice2 = new Juice("雪碧",3,productionDay2);

        LinkedList<Beer> beerList = new LinkedList<Beer>();

        beerList.add(beer);

        LinkedList<Juice> juiceList = new LinkedList<Juice>();

        juiceList.add(juice1);

        juiceList.add(juice2);

        West2FriedChickenRestaurant test = new West2FriedChickenRestaurant(100, beerList, juiceList);
        //sellSetMeal测试
        test.sellSetMeal(test.mealList.get(0), today);
        test.sellSetMeal(test.mealList.get(1), today);
        //bulkPurchase测试
        try{
        test.bulkPurchase(test.mealList.get(0),10);
        test.bulkPurchase(test.mealList.get(3),10);
        }catch (OverdraftBalanceException e){
            e.printWrong();
        }
    }

}
