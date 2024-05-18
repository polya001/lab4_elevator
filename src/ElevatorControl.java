import java.util.*;

/**

 * Elevator distribution class according to requests to minimize waiting time and elevator mileage
 */
public class ElevatorControl {

    Elevator el1;
    Elevator el2;
    List<Map<Integer,ArrayDeque<Integer>>> CallsOnFlours;
    static List<CallData> DequeCalls = new ArrayList<>();
    int count_floors;

    static boolean CallsOver = false;

    /**

     * A list of data (destination points) is created for all floors
     * to find out in which direction the elevator is called from which floor
     *
     * @param count_f a number of floors
     */
    ElevatorControl(int count_f){
        count_floors=count_f;
        CallsOnFlours = new ArrayList<>(count_floors+1);
        el1 = new Elevator();
        el2 = new Elevator();
        CallsOver = false;
        Map<Integer,ArrayDeque<Integer>> DequeCalls =new HashMap<>();
        for (int i=0;i<count_floors+1;i++){
            DequeCalls.put(1,new ArrayDeque<>());
            DequeCalls.put(-1,new ArrayDeque<>());
            CallsOnFlours.add(DequeCalls);
        }

    }
    /**

     * Organization of elevator traffic
     */
    public synchronized void Movement(){

        System.out.println("Elevator 1 on " +el1.CurrentFloor+ " floor");
        System.out.println("Elevator 2 on " +el2.CurrentFloor+ " floor");
        ElevatorActions(el1,1);
        ElevatorActions(el2,2);



    }
    public void ElevatorActions(Elevator el,int n){
        while ((el.FloorsExit.contains(el.CurrentFloor))){
            System.out.println("A person on " +el1.CurrentFloor+ " floor exited elevator " + n);
            el.SetStops.remove(el.CurrentFloor);
            el.FloorsExit.remove((el.CurrentFloor));

        }
        if (el.FloorsExit.isEmpty()){
            el.Direction=0;
        }
        if (el.Direction!=0){
            ArrayDeque<Integer> WhereToGo = CallsOnFlours.get(el.CurrentFloor).get(el.Direction);
            while (!(WhereToGo.isEmpty())){
                el.SetStops.remove(el.CurrentFloor);
                System.out.println("A person on " +el1.CurrentFloor+ " go to elevator " + n);
                int NextPersonStop=WhereToGo.poll();

                for (int i = 0;i < DequeCalls.size(); i++) {
                    CallData call = DequeCalls.get(i);
                    if (call.StartFloor == el.CurrentFloor && NextPersonStop ==  call.FinalFloor ) {
                        DequeCalls.remove(i);
                    }
                }
                el.SetStops.add(NextPersonStop);
                el.FloorsExit.add(NextPersonStop);

            }
        }
        else {
            ArrayDeque<Integer> WhereToGo = new ArrayDeque<>();
            if (CallsOnFlours.get(el.CurrentFloor).get(1).size() >= CallsOnFlours.get(el.CurrentFloor).get(-1).size()) {
                el.Direction = 1;
                WhereToGo = CallsOnFlours.get(el.CurrentFloor).get(1);
            }
            else {
                el.Direction = -1;
                WhereToGo = CallsOnFlours.get(el.CurrentFloor).get(-1);
            }
            while ((!WhereToGo.isEmpty())){
                el.SetStops.remove(el.CurrentFloor);
                System.out.println("A person on " +el1.CurrentFloor+ " go to elevator " + n);

                int NextPersonStop=WhereToGo.poll();

                for (int i = 0;i < DequeCalls.size(); i++) {
                    CallData call = DequeCalls.get(i);
                    if (call.StartFloor == el.CurrentFloor && NextPersonStop ==  call.FinalFloor ) {
                        DequeCalls.remove(i);
                    }
                }
                el.SetStops.add(NextPersonStop);
                el.FloorsExit.add(NextPersonStop);

            }
        }


        if (el.SetStops.isEmpty()){
            if (DequeCalls.isEmpty()){
                System.out.println("The elevator is empty on floor " + el.CurrentFloor);
            }
            else {
                CallData call = DequeCalls.get(0);
                DequeCalls.remove(0);
                el.SetStops.add(call.StartFloor);
                el.Direction = el.CurrentFloor < call.StartFloor ? 1 : -1;
                System.out.println("Accepted call on " + call.StartFloor +  " floor by " + n);
            }
        }
        else if (el.CurrentFloor == el.SetStops.peek()) {
            el.SetStops.remove(el.CurrentFloor);
        }
        else if (el.SetStops.peek() > el.CurrentFloor) {
            el.Direction = 1;
        }
        else {
            el.Direction = -1;
        }
        if (el.Direction == -1) {
            if (el.CurrentFloor > 0) {
                el.CurrentFloor-=1;
            }
            else {
                el.Direction = 0;
            }
            System.out.println("Elevator " +n + " go down from floor " + el.CurrentFloor);
        } else if (el.Direction == 1){
            if (el.CurrentFloor < count_floors) {
                el.CurrentFloor+=1;
            } else {
                el.Direction = 0;
            }
            System.out.println("Elevator " +n + " go up from floor " + el.CurrentFloor);
        }


    }
    /**

     * Analyzing elevator calls to determine which elevator should arrive to make it faster
     * @param newcalls CallData class object (request)
     */
    public synchronized void FixCalls(CallData newcalls){


        if (el1.SetStops.isEmpty() && el2.SetStops.isEmpty()){
            if (Math.abs(newcalls.StartFloor - el1.CurrentFloor) >= Math.abs(newcalls.StartFloor - el2.CurrentFloor)){
                el2.SetStops.add(newcalls.StartFloor);
                CallsOnFlours.get(newcalls.StartFloor).get(newcalls.Direction).add(newcalls.FinalFloor);
                el2.Direction=newcalls.Direction;
                System.out.println("Accepted call on " + newcalls.StartFloor +  " floor by " + "2 elevator");
            }
            else {
                el1.SetStops.add(newcalls.StartFloor);
                CallsOnFlours.get(newcalls.StartFloor).get(newcalls.Direction).add(newcalls.FinalFloor);
                el1.Direction=newcalls.Direction;
                System.out.println("Accepted call on " + newcalls.StartFloor +  " floor by " + "1 elevator");
            }

        }
        else if((el1.Direction==newcalls.Direction && ((el1.Direction==-1 && el1.CurrentFloor>=newcalls.StartFloor) || (el1.Direction==1 && el1.CurrentFloor<=newcalls.StartFloor))) || el1.SetStops.isEmpty() ){
            if ((el2.Direction==newcalls.Direction && ((el2.Direction==-1 && el2.CurrentFloor>=newcalls.StartFloor) || (el2.Direction==1 && el2.CurrentFloor<=newcalls.StartFloor))) || el2.SetStops.isEmpty()  ){
                if (Math.abs(newcalls.StartFloor - el1.CurrentFloor) >= Math.abs(newcalls.StartFloor - el2.CurrentFloor)){
                    System.out.println("Accepted call on " + newcalls.StartFloor +  " floor by " + "2 elevator");
                    el2.SetStops.add(newcalls.StartFloor);
                    CallsOnFlours.get(newcalls.StartFloor).get(newcalls.Direction).add(newcalls.FinalFloor);
                    el2.Direction=newcalls.Direction;

                }
                else {
                    System.out.println("Accepted call on " + newcalls.StartFloor +  " floor by " + "1 elevator");
                    el1.SetStops.add(newcalls.StartFloor);
                    CallsOnFlours.get(newcalls.StartFloor).get(newcalls.Direction).add(newcalls.FinalFloor);
                    el1.Direction=newcalls.Direction;

                }
            }
            else{
                System.out.println("Accepted call on " + newcalls.StartFloor +  " floor by " + "1 elevator");
                el1.SetStops.add(newcalls.StartFloor);
                CallsOnFlours.get(newcalls.StartFloor).get(newcalls.Direction).add(newcalls.FinalFloor);
                el1.Direction=newcalls.Direction;

            }

        }
        else if(el2.SetStops.isEmpty() || (el2.Direction==newcalls.Direction && ((el2.Direction==-1 && el2.CurrentFloor>=newcalls.StartFloor) || (el2.Direction==1 && el2.CurrentFloor<=newcalls.StartFloor)))){
            el2.SetStops.add(newcalls.StartFloor);
            CallsOnFlours.get(newcalls.StartFloor).get(newcalls.Direction).add(newcalls.FinalFloor);
            el2.Direction=newcalls.Direction;
            System.out.println("Accepted call on " + newcalls.StartFloor +  " floor by " + "2 elevator");

        }
        else {
            DequeCalls.add(newcalls);
            System.out.println("The elevators are busy, please wait");
        }

    }

}
