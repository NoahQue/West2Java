package FirstProblem;

import java.time.LocalDate;

public class Juice extends Drink{

    Juice(String name, double cost, LocalDate productDate){
        super(name, cost, productDate, 2);
    }

    Juice(String name, double cost){
        super(name, cost, LocalDate.now(), 2);
    }

    public String toString(){
        return "果汁名：" + name + "/n果汁价格：" + cost +
                "/n生产日期：" + productDate.toString() + "/n保质期: 2 天/n";}
}
