package FirstProblem;

public class  OverdraftBalanceException extends RuntimeException{
    private double fee;
    private double balance;
    OverdraftBalanceException(double fee, double balance){
        this.balance = balance;
        this.fee = fee;
    }
    public void printWrong(){
        System.out.println("采购失败：余额不足，还需" + (fee-balance) + "元");
    }
}
