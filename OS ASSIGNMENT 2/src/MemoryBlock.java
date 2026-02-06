public class MemoryBlock {
    int blockNum; //The number of block
    int blockSize; //The size of block in KB
    int startAddr; //The start of memory address
    int endAddr; //The end of memory address
    String blockStatus; //The status of block can be Free or Allocated
    String processID; // The id of the process in the block
    int inFragmentation; //The unused space by the process in the block

    public MemoryBlock(int bNum, int bSize, int sAddr) {
        blockNum = bNum;
        blockSize = bSize;
        startAddr = sAddr;
        endAddr = sAddr + bSize - 1; //Calculating the end address
        blockStatus = "free"; //Since it is at the begging there is no process in the block
        processID = "Null"; //Since it is at the begging there is no process yet
        inFragmentation = 0; //Since it is at the begging there is no internal fragmentation yet
    }
}