import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class Elevator {
    int CurrentFloor,FinalFloor;  //FinalFloor - макс. этаж по текущему напр.,
                                                  
    int Direction;  //-1 - down, 1 - up, 0 - stands still

    ArrayDeque<Integer> SetStops;
    ArrayDeque<Integer> FloorsExit;

    Elevator(){
        CurrentFloor=0;
        Direction=0;
        FinalFloor = 0;
        SetStops = new ArrayDeque<>();
        FloorsExit= new ArrayDeque<>();

    }

}
