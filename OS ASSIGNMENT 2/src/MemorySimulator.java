
import java.util.*;
public class MemorySimulator {
    private static ArrayList<MemoryBlock> memoryArray; //List of memory blocks, we chose arrayList because it uses contiguous memory allocation and has a dynamic length
    private static int allocationAlgorithm; //This indicates which allocation algorithm to use (First-fit, Best-fit, or Worst-fit)
    private static Scanner read = new Scanner(System.in);

    public static void main(String[] args) {
        try{
            memoryArray = new ArrayList<>();
            System.out.print("Enter the total number of blocks: ");
            int numOfBlocks = read.nextInt();
            System.out.print("Enter the size of each block in KB: ");
            read.nextLine(); //To read unwanted lines
           String sizes = read.nextLine();
            String[] sizesList = sizes.split(" "); //This splits the string by the space to create an array of sizes
            if (sizesList.length != numOfBlocks) { //Check if the sizes entered by users equals the number of blocks entered
                System.out.println("Error:The number of blocks (sizes) entered doesn't match number of blocks.");
                return;
            }
            int startAddress = 0; //The start address for the first block
            for (int i = 0; i < numOfBlocks; i++) { //For each block size in the array it will create a new memory block object and add it to the memoryArray List
                int bSize = Integer.parseInt(sizesList[i]);
                MemoryBlock block = new MemoryBlock(i, bSize, startAddress);
                memoryArray.add(block);
                startAddress += bSize; //It Adds the block size to it to re-use it as the start address for the next block
            }
            System.out.print("Enter allocation strategy (1 for first-fit, 2 for best-fit, 3 for worst-fit): ");
            allocationAlgorithm = read.nextInt();
            System.out.println("Memory blocks are created...");
            System.out.println("Memory blocks:");
            System.out.println("======================================================");
            System.out.printf("%-10s %-10s %-15s %-10s\n", "Block#", "size", "start-end", "status");
            System.out.println("======================================================");

            for (int i = 0; i < memoryArray.size(); i++) { //Printing an initial report of the memory
                MemoryBlock block = memoryArray.get(i);
                String startEND = block.startAddr + "-" + block.endAddr;
                System.out.printf("Block%-5d %-10d %-15s %-10s\n",
                        block.blockNum, block.blockSize, startEND, block.blockStatus);
            }
            System.out.println("======================================================");

        int selection;
        do {
            System.out.println("======================================================");
            System.out.println("1) Allocates memory blocks");
            System.out.println("2) De-allocates memory blocks");
            System.out.println("3) Print report about the current state of memory and internal Fragmentation");
            System.out.println("4) Exit");
            System.out.println("======================================================");
            System.out.print("Enter your choice: ");
            selection = read.nextInt();

            if (selection == 1) {
                allocatingMemory();
            } else if (selection == 2) {
                deallocatingMemory();
            } else if (selection == 3) {
                memoryReport();
            } else if (selection == 4) {
                System.out.println("Exiting the program");
            } else{
                System.out.println("Error:Try again, this choice is invalid.");
            }
        } while (selection != 4);
        }catch(InputMismatchException e) { //If the user enters an invalid data type
            System.out.println("Please enter a valid data type");
        }
    } //end of main

    public static void allocatingMemory() {
        try{
        System.out.print("Enter the process ID and size of process: ");
        String processID;
        int processSize;
        boolean flag;
        do{ //When the user enters the same process id twice , the user will be asked to re-enter the proceed id again
        processID = read.next();
        processSize = read.nextInt();
        flag = false;
        for(int i=0 ; i<memoryArray.size() ; i++) {
            if(memoryArray.get(i).processID.equals(processID)) {
                flag=true;
                System.out.println("Process id has been used before");
                System.out.print("Re-enter the process ID and size of process: ");
                break;
            }
        }}while(flag);

        MemoryBlock selectedBlock = null; //To store the block that suits the process
        switch (allocationAlgorithm) {

            case 1: // First-fit

                for (int i = 0; i < memoryArray.size(); i++) {
                    MemoryBlock block = memoryArray.get(i);
                    if (block.blockStatus.equals("free") && block.blockSize >= processSize) { //To find the first free block and its size is larger or equals process size
                        selectedBlock = block;
                        break;
                    }
                }

                break;

            case 2: // Best-fit
                MemoryBlock bestBlock = null;

                for (int i = 0; i < memoryArray.size(); i++) {
                    MemoryBlock block = memoryArray.get(i);
                    if (block.blockStatus.equals("free") && block.blockSize >= processSize) { //To find the first free block and its size is larger or equals process size
                        if (bestBlock == null || block.blockSize < bestBlock.blockSize) { //Checks if there is BEST BLOCK or if the block is smaller size than the BEST BLOCK
                            bestBlock = block;
                        }
                    }
                }
                selectedBlock = bestBlock;

                break;

            case 3: // Worst-fit
                MemoryBlock worstBlock = null;
                int maxSize = -1; //Initial size will be -1 , since there is no WORST BLOCK yet

                for (int i = 0; i < memoryArray.size(); i++) {
                    MemoryBlock block = memoryArray.get(i);
                    if (block.blockStatus.equals("free") && block.blockSize >= processSize && block.blockSize > maxSize) {  //To find the first free block AND its size is larger or equals process size AND larger than maxSize
                        worstBlock = block;
                        maxSize = block.blockSize;
                    }
                }
                selectedBlock = worstBlock;

                break;
        }

        if (selectedBlock != null) { //This is for updating the block attributes
            selectedBlock.blockStatus = "allocated";
            selectedBlock.processID = processID;
            selectedBlock.inFragmentation = selectedBlock.blockSize - processSize;
            System.out.printf("%s Allocated at address %d, and the internal fragmentation is %d\n",
                    processID, selectedBlock.startAddr, selectedBlock.inFragmentation);
        } else { //If there is no suitable block a message will be printed
            System.out.println("Error: We can't find an appropriate block to allocate.");
        }}catch(InputMismatchException e){ //If the user enters an invalid data type
            String G= read.nextLine(); //To read unwanted lines
            System.out.println("Please enter a valid data type");}
    }
    public static void deallocatingMemory() {
        System.out.print("Enter the process ID you want to de-allocate: ");
        String processID = read.next();

        boolean flag = false;

        for (int i = 0; i < memoryArray.size(); i++) {
            MemoryBlock block = memoryArray.get(i);
            if (block.processID.equals(processID)) { //If the process id was found we will release its block
                block.blockStatus = "free";
                block.processID = "Null";
                block.inFragmentation = 0;
                flag = true;
                System.out.printf("The process %s is deallocated from the block %d\n", processID, block.blockNum);
                break;
            }
        }
        if (!flag) { //If process wasn’t found an error message will be printed
            System.out.printf("Error: We can't find the process %s in memory\n", processID);
        }
    }
    public static void memoryReport() { //This prints all block details
            System.out.println("Memory blocks:");
            System.out.println("====================================================================================");
            System.out.printf("%-7s %-10s %-15s %-12s %-12s %-20s\n",
                    "Block#", "size", "start-end", "status", "ProcessID", "InternalFragmentation");
            System.out.println("====================================================================================");

            for (int i = 0; i < memoryArray.size(); i++) {
                MemoryBlock block = memoryArray.get(i);
                String range = block.startAddr + "-" + block.endAddr;
                System.out.printf("Block%-7d %-10d %-15s %-12s %-12s %-20d\n",
                        block.blockNum, block.blockSize, range, block.blockStatus, block.processID, block.inFragmentation);
            }
            System.out.println("====================================================================================");
        }
    }