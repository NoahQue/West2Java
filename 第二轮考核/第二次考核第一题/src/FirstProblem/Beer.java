package FirstProblem;

import java.time.LocalDate;

public class Beer extends Drink{

    protected float vol;

    Beer(String name,double cost, LocalDate productDate, float vol){
        super(name, cost, productDate,30);
        this.vol = vol;
    }

    Beer(String name,double cost){
        super(name, cost, LocalDate.now(),30);
        this.vol = 0;
    }
    public String toString(){
        return "啤酒名：" + name + "/n啤酒价格：" + cost +
                "/n啤酒度数：" + vol + "/n生产日期：" + productDate.toString() +
                "/n保质期：: 30 天/n";
    }
}
