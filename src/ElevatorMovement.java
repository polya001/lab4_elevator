import java.util.*;

/**

 * Flow class for managing elevators movement
 */
public class ElevatorMovement implements Runnable{

    ElevatorControl Control;
    ElevatorMovement(ElevatorControl control){
        Control=control;
    }
    @Override
    public void run() {
        while (!(Control.el1.SetStops.isEmpty()) && !(Control.el2.SetStops.isEmpty())){

            Control.Movement();
            try {
                Thread.sleep(1100);            //передвижение на 1 этаж
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
