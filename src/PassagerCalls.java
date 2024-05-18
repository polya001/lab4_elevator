import java.util.*;

/**

 * Flow class for managing elevator calls
 */
public class PassagerCalls implements Runnable {

    ElevatorControl control;
    int CountCalls;
    PassagerCalls(int count_calls,ElevatorControl elevatorControl){
        CountCalls=count_calls;
        control=elevatorControl;


    }


    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        for (int i=0;i<CountCalls;i++){
            System.out.println("Enter number of current floor");
            int start= scanner.nextInt();
            System.out.println("Enter number of final floor");
            int end = scanner.nextInt();

            int dir = start>end ? -1:1;
            control.FixCalls(new CallData(start,end,dir));


            try {
                Thread.sleep(1000);        //время между запросами
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
