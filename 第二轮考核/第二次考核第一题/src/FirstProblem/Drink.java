package FirstProblem;

import java.time.LocalDate;

public abstract class Drink {

    protected String name;
    protected double cost;
    protected LocalDate productDate;
    protected int qualityDate;

    Drink(String name, double cost, LocalDate productDate, int qualityDate){
        this.name = name;
        this.cost = cost;
        this.productDate = productDate;
        this.qualityDate = qualityDate;
    }
    public boolean isExpired(LocalDate now){
        LocalDate expiryDate = productDate.plusDays(qualityDate);
        if(now.isAfter(expiryDate)) return true;
        else return false;
    }
    public abstract String toString();
}
