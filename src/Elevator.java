import java.util.ArrayDeque;


public class Elevator {
    int CurrentFloor,FinalFloor;

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
