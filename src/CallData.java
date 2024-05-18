public class CallData {
    int StartFloor,FinalFloor;
    int Direction;  //-1 - down, 1 - up
    CallData(int startFloor,int finalFloor, int direction){
        StartFloor = startFloor;
        FinalFloor = finalFloor;
        Direction=direction;
    }
}
