# Memory Allocation Simulator 💾

## 📌 Project Overview
This project simulates **Contiguous Memory Allocation** techniques used in Operating Systems. It allows users to define memory block sizes and test different allocation strategies to understand how processes are assigned to memory and how **Internal Fragmentation** occurs.

## 🎯 Objectives
* Simulate core OS memory management algorithms.
* Analyze the efficiency of different allocation strategies.
* Visualize the state of memory blocks (Allocated vs. Free) and track fragmentation.

## 💡 Key Features
* **Allocation Algorithms:**
    * **First-Fit:** Allocates the first block that is large enough.
    * **Best-Fit:** Allocates the smallest block that fits the process to minimize wasted space.
    * **Worst-Fit:** Allocates the largest available block.
* **Memory Management:**
    * Dynamic setup of memory blocks and sizes.
    * Process allocation and de-allocation by ID.
* **Reporting System:** Generates a detailed status report showing Block ID, Size, Address Range, Status, and Internal Fragmentation.

## 🛠 Tech Stack
* **Language:** Java.
* **Concepts:** Operating Systems, Memory Management, Fragmentation.
* **Data Structures:** ArrayList (for dynamic block management).

---
*Developed for CSC227: Operating Systems course at King Saud University.*
