package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Processor {
    static Map<Status, HashSet<WorkOrder>> workOrderMap = new HashMap();

    public void processWorkOrders() throws IOException {

            moveIt();
            readIt();

    }

    private void moveIt() {

        for (WorkOrder order: workOrderMap.get(Status.IN_PROGRESS)) {
            order.setStatus(Status.DONE);
            workOrderMap.get(Status.DONE).add(order);

        }
        workOrderMap.get(Status.IN_PROGRESS).clear();

        for (WorkOrder order: workOrderMap.get(Status.ASSIGNED)) {
            order.setStatus(Status.IN_PROGRESS);
            workOrderMap.get(Status.IN_PROGRESS).add(order);

        }
        workOrderMap.get(Status.ASSIGNED).clear();

        for (WorkOrder order: workOrderMap.get(Status.INITIAL)) {
            order.setStatus(Status.ASSIGNED);
            workOrderMap.get(Status.ASSIGNED).add(order);

        }

        workOrderMap.get(Status.INITIAL).clear();


    }

   private void readIt() throws IOException {
        File file = new File(".");
        for (File f : file.listFiles()) {
            if (f.getName().endsWith(".json")) {
                // Now you have a File object named "f".
                // You can use this to create a new instance of Scanner
                ObjectMapper mapper = new ObjectMapper();
                WorkOrder order = mapper.readValue(new File(String.valueOf(f)), WorkOrder.class);
                workOrderMap.get(Status.INITIAL).add(order);


                f.delete();

                System.out.println(workOrderMap.toString());
            }
        }

    }
    public static void initializeSets(){
        HashSet<WorkOrder> initialSet = new HashSet<WorkOrder>();
        HashSet<WorkOrder> assignedSet = new HashSet<WorkOrder>();
        HashSet<WorkOrder> inProgressSet = new HashSet<WorkOrder>();
        HashSet<WorkOrder> doneSet = new HashSet<WorkOrder>();
        workOrderMap.put(Status.INITIAL, initialSet);
        workOrderMap.put(Status.ASSIGNED, assignedSet);
        workOrderMap.put(Status.IN_PROGRESS, inProgressSet);
        workOrderMap.put(Status.DONE, doneSet);
    }



    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.initializeSets();

        //call the read it function

        try {
            processor.readIt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){

            processor.moveIt();
            try {
                Thread.sleep(4000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(workOrderMap.toString());
        }



    }
}
