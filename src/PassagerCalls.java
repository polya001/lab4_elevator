import java.time.Instant;
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

        Random random = new Random(Instant.now().getEpochSecond());
        Scanner scanner = new Scanner(System.in);
        for (int i=0;i<CountCalls;i++){
            int start = random.nextInt(0, control.count_floors + 1);
            int end = random.nextInt(0, control.count_floors + 1);
            while (start == end) {
                end = random.nextInt(0, control.count_floors + 1);
            }

            int dir = start>end ? -1:1;
            control.FixCalls(new CallData(start,end,dir));


            try {
                Thread.sleep(1000);        //время между запросами
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
        ElevatorControl.CallsOver = true;

    }
}
