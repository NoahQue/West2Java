import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


public class ThreadTest {

    private static final Integer PART = 100000;
    private static final Integer MAX = 1000000000;
    private static final Integer MIN = 1;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("请输入数字x:");
        Integer x = scan.nextInt();
        long time1 = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> taskFuture = pool.submit(new MyForkJoinTask(MIN, MAX, x));
        try {
            long ans = taskFuture.get();
            System.out.println("ans = " + ans);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("CostTime = " + (time2-time1) + "ms");
    }

    private static boolean contain(Integer num, Integer x) {
        return String.valueOf(num).contains(String.valueOf(x));
    }

    static class MyForkJoinTask extends RecursiveTask<Long> {

        private final Integer start;//开始值
        private final Integer end;//结束值
        private final Integer x;//出现值

        public MyForkJoinTask(Integer start, Integer end, Integer x) {
            this.start = start;
            this.end = end;
            this.x=x;
        }

        @Override
        protected Long compute() {
            if (end - start < PART) { //如果区间长度满足长度限制进行判断累加
                long total = 0L;
                for (int index = this.start; index <= this.end; index++) {
                    if(contain(index,x)) total += index;
                }
                return total;
            }
            else { //如果不满足继续二分
                MyForkJoinTask subTask1 = new MyForkJoinTask(start, (start + end) / 2, x);
                subTask1.fork();
                MyForkJoinTask subTask2 = new MyForkJoinTask((start + end) / 2 + 1, end, x);
                subTask2.fork();
                return subTask1.join() + subTask2.join();//合并
            }
        }
    }

}

