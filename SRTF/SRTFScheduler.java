package SRTF;
import java.util.*;
public class SRTFScheduler {
    public static void main(String[] args) {
        // Sample process data: Process ID, Arrival Time, Burst Time
        Process[] processes = {
            new Process('A', 0, 6),
            new Process('B', 1, 8),
            new Process('C', 2, 7),
            new Process('D', 3, 3)
        };

        srtfScheduling(processes);
    }

    public static void srtfScheduling(Process[] processes) {
        int n = processes.length;
        int currentTime = 0, completed = 0;
        Process currentProcess = null;

        List<Character> ganttChart = new ArrayList<>();

        while (completed < n) {
            Process nextProcess = null;

            // Find the process with the shortest remaining time
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && process.remainingTime > 0) {
                    if (nextProcess == null || process.remainingTime < nextProcess.remainingTime) {
                        nextProcess = process;
                    }
                }
            }

            if (nextProcess != null) {
                // Preempt the current process if the new process has a shorter remaining time
                if (currentProcess != nextProcess) {
                    currentProcess = nextProcess;
                    ganttChart.add(currentProcess.id);
                }

                // Process the current process
                currentProcess.remainingTime--;
                currentTime++;

                // If the current process is completed
                if (currentProcess.remainingTime == 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    completed++;
                }
            } else {
                currentTime++;
            }
        }

        // Print Gantt Chart
        System.out.println("Gantt Chart (SRTF):");
        for (Character processId : ganttChart) {
            System.out.print(processId + " ");
        }
        System.out.println();

        // Calculate and print average times
        float totalTurnaroundTime = 0;
        float totalWaitingTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
            totalWaitingTime += process.waitingTime;
        }
        float avgTurnaroundTime = totalTurnaroundTime / n;
        float avgWaitingTime = totalWaitingTime / n;

        System.out.printf("\nAverage Turnaround Time: %.2f ms%n", avgTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f ms%n", avgWaitingTime);
    }
}