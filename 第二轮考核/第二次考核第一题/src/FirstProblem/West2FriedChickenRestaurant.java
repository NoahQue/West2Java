package FirstProblem;

import java.time.LocalDate;
import java.util.*;

public class West2FriedChickenRestaurant implements FriedChickenRestaurant{

    private double balance;
    private LinkedList<Beer> beerList;
    private LinkedList<Juice> juiceList;
    protected static ArrayList<SetMeal> mealList;

    //linkedList是由链表实现的，可以在o(1)时间内实现插入和删除，由于该题需要经常进行插入和删除操作，使用linkedList更加高效
    //ArrayList是由数组实现，插入和删除都需要o(N)时间，在该题中效率较低
    //原本打算使用PriorityQueue，对时间升序可以在o(1)时间内删除过期饮料，但是查询特定元素仍需要o(N)时间，插入和删除大致为o(logN)，所以还是linkedList更加合适

    West2FriedChickenRestaurant(double balance, LinkedList<Beer> beerList, LinkedList<Juice> juiceList){
        this.balance = balance;
        this.beerList = beerList;
        this.juiceList = juiceList;
    }

    static {
        mealList = new ArrayList<SetMeal>();
        mealList.add(new SetMeal("儿童套餐", 12, "快乐炸鸡", new Juice("橙汁",4)));
        mealList.add(new SetMeal("特惠套餐", 9, "小炸鸡", new Juice("雪碧",3)));
        mealList.add(new SetMeal("经典套餐", 16, "蜜汁炸鸡", new Beer("青岛啤酒",4)));
        mealList.add(new SetMeal("欢乐全家享", 30,"超满足炸鸡", new Juice("可乐（乐享装）",10)));
    }

    private void use(Beer beer, LocalDate today) throws IngredientSortOutException{
        boolean flag = false;
        Iterator<Beer> iter = beerList.iterator();
        while(iter.hasNext()){
            Beer temp = iter.next();
            if(temp.isExpired(today)){
                iter.remove();
                continue;
            }
            if(!flag&&temp.name.equals(beer.name)){
                iter.remove();
                flag = true;
            }
        }
        if(!flag) throw new IngredientSortOutException(beer);
    }

    private void use(Juice juice, LocalDate today) throws IngredientSortOutException{
        boolean flag = false;
        Iterator<Juice> iter = juiceList.iterator();
        while(iter.hasNext()){
            Juice temp = iter.next();
            if(temp.isExpired(today)){
                System.out.println("过期" + temp.name +"已清除");
                iter.remove();
                continue;
            }
            if(!flag&&temp.name.equals(juice.name)){
                iter.remove();
                flag = true;
            }
        }
        if(!flag) throw new IngredientSortOutException(juice);
    }

    public void bulkPurchase(SetMeal type, int amount)throws OverdraftBalanceException{
        boolean temp = true;
        double totalPrice = 0;
        if(type.drink instanceof Beer){
            Beer beer = (Beer)type.drink;
            totalPrice = beer.cost*amount;
            if(balance > totalPrice){
                balance -= totalPrice;
                System.out.println("进货成功：成功采购" + type.setMealName + amount +"份");
                System.out.println("当前账户余额：" + balance + "元");
                for(int index = 0; index < amount; index++){
                    beerList.add((Beer)type.drink);
                }
            }
            else throw new OverdraftBalanceException(totalPrice, balance);
        }
        else{
            Juice juice = (Juice) type.drink;
            totalPrice = juice.cost*amount;
            if(balance > totalPrice){
                balance -= totalPrice;
                System.out.println("进货成功：成功采购" + type.setMealName + amount +"份");
                System.out.println("当前账户余额：" + balance + "元");
                for(int index = 0; index < amount; index++){
                    juiceList.add((Juice)type.drink);
                }
            }
            else throw new OverdraftBalanceException(totalPrice, balance);
        }

    }

    public void sellSetMeal(SetMeal type, LocalDate today){
        boolean temp = true;
        if(type.drink instanceof Beer){
            try{
                use((Beer)type.drink,today);
            }
            catch (IngredientSortOutException e) {
                e.printWrong();
                temp = false;
            }
        }
        else{
            try{
                use((Juice) type.drink,today);
            }
            catch (IngredientSortOutException e) {
                e.printWrong();
                temp = false;
            }
        }
        if(temp) {
            System.out.println("成功卖出"+type.setMealName+"一份");
            balance = balance + type.setMealPrice;
        }
    }


}
 