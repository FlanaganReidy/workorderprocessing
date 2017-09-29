package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Creator {

    public void createWorkOrders() throws IOException {
        // read input, create work orders and write as json files
        WorkOrder workOrder = new WorkOrder();
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");;
        System.out.println("Enter id: (int)");
        workOrder.setId(Integer.parseInt(scanner.next()));
        System.out.println("Enter Description: ");
        workOrder.setDescription(scanner.next());
        System.out.println("Enter Sender Name: ");
        workOrder.setSenderName(scanner.next());
        workOrder.setStatus(Status.INITIAL);

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(workOrder.getId()+".json");
        FileWriter fileWriter = new FileWriter(file);
        String json = mapper.writeValueAsString(workOrder);
        fileWriter.write(json);
        fileWriter.close();

    }

    public static void main(String args[]) {
        Creator creator = new Creator();
        while(true) {
            try {
                creator.createWorkOrders();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
