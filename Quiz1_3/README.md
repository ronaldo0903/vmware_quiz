JDK Version: 1.9

Basic Idea for ConcurrentQueue:
                           1) Maintain two pointer: head and tail indicating the current read/write index,
                              and use CAS instead of lock to maximize performance when multiple threads are contending.
                           2) The queue can ensure that the elements added to the queue will not be lost, 
                               and each of them will only be polled once. 
                           3) The way of using circular queue and a markable element indicating whether it has been 
                              consumed or not has been considered, but it is a bit complex that I could not figure it out
                              in a limited time frame.