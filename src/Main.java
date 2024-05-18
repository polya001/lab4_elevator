import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enter count of requests");

        Scanner scanner = new Scanner(System.in);
        int count_calls = scanner.nextInt();
        System.out.println("Enter count of floors");
        int count_floors =scanner.nextInt();
        ElevatorControl newControl = new ElevatorControl(count_floors);
        Thread calls = new Thread(new PassagerCalls(count_calls,newControl));
        Thread elevatorMovement = new Thread(new ElevatorMovement(newControl));
        calls.start();
        elevatorMovement.start();
        calls.join();
        elevatorMovement.join();


    }
}
