import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintABC {
    private  final Object sinObj = new Object();
    private  volatile char whatString = 'A';

    public static void main(String[] args) {
        PrintABC printABC = new PrintABC();
        ExecutorService fixedThread =Executors.newFixedThreadPool(3);
        fixedThread.execute(()-> printABC.printA());
        fixedThread.execute(()-> printABC.printB());
        fixedThread.execute(()-> printABC.printC());
        fixedThread.shutdown();

    }

    public void printA() {
        synchronized (sinObj) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (whatString != 'A') {
                        sinObj.wait();
                    }
                    System.out.print("A");
                    whatString = 'B';
                    sinObj.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void printB() {
        synchronized (sinObj) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (whatString != 'B') {
                        sinObj.wait();
                    }
                    System.out.print("B");
                    whatString = 'C';
                    sinObj.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void printC() {
        synchronized (sinObj) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (whatString != 'C') {
                        sinObj.wait();
                    }
                    System.out.print("C");
                    whatString = 'A';
                    sinObj.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
