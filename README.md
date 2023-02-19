# Car-Factory
Car factory simulation. An interesting university assignment written with help from a [friend](https://github.com/badlocale) in one night :)<br>
Multithreaded Java application using JavaFX that simulates car factory workflow.

# Assignment details
The car consists of 3 parts: body, engine and accessories. The car must
be assembled and taken to the warehouse, from where it comes to dealers.
The factory operation process is shown in the picture:
![image](https://user-images.githubusercontent.com/45130182/219959069-e9455cd4-1ded-4f7f-9398-d78dab8ae94d.png)

# Requirements
1. All warehouses have a certain size, which cannot be exceeded
2. Each assembler, supplier and dealer must work in a separate thread
3. Threads that represent suppliers deliver one part
every N milliseconds. If some parts warehouse is full, then the supplier expects
to free up space for parts
4. The threads that represent dealers request car from the finished product warehouse every M milliseconds

# Screenshots
![image](https://user-images.githubusercontent.com/45130182/219958523-28170e2b-9ceb-4552-8e82-8352534ec2dc.png)
