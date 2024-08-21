
public class FCFS_Scheduler {
    public static void main(String[] args) {
        // Sample process data: Process ID, Arrival Time, Burst Time
        Process[] processes = {
            new Process('A', 0, 5),
            new Process('B', 1, 3),
            new Process('C', 2, 8),
            new Process('D', 3, 6)
        };

        fcfsScheduling(processes);
    }

    public static void fcfsScheduling(Process[] processes) {
        int n = processes.length;
        int currentTime = 0;

        // Calculate completion time, turnaround time, and waiting time for each process
        for (int i = 0; i < n; i++) {
            Process process = processes[i];
            if (process.arrivalTime > currentTime) {
                currentTime = process.arrivalTime;
            }

            process.completionTime = currentTime + process.burstTime;
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;

            // Update current time for the next process
            currentTime = process.completionTime;
        }

        // Print Gantt Chart
        System.out.println("Gantt Chart (FCFS):");
        System.out.println("  Time        Process");
        int startTime = 0;
        for (int i = 0; i < n; i++) {
            Process process = processes[i];
            System.out.printf("  %d-%d         %c%n", startTime, process.completionTime, process.id);
            startTime = process.completionTime;
        }

        // Calculate average times
        float totalTurnaroundTime = 0;
        float totalWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            Process process = processes[i];
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
        }
        float avgTurnaroundTime = totalTurnaroundTime / n;
        float avgWaitingTime = totalWaitingTime / n;

        // Print average times
        System.out.printf("\nAverage Turnaround Time: %.2f ms%n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f ms%n", avgWaitingTime);
    }
}