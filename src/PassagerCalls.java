import java.util.*;

public class ElevatorCalls implements Runnable {

    public void Calls(int Count){
        Scanner scanner = new Scanner(System.in);
        for (int i=0;i<Count;i++){
            System.out.println("Enter number of floor");
            int number = scanner.nextInt();
            System.out.println("Enter direction: 1 (if up) or -1 (if down)");
            int dir = scanner.nextInt();


        }

    }


    @Override
    public void run() {

    }
}
