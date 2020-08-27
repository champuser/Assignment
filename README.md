# Assignment



TASK1:- Simulation of 'tail -f' command

 

A log file named 'logger.txt' is saved in a particular location on a server. This log file is continuously appended by some other process(es) which is outside the scope of this assignment. You have to build a client-server interaction that does the following

1. The server would be listening on a particular port number (eg 8080)

2. Your client would be a web page that connects to this server

3. After successful connection, the client would request for last 10 lines from the 'logger.txt' file from the server

4. Server would send the contents of the same file to the client

5. On receiving the contents of this log file, the client would display it on the browser

6. The same web page should receive new content from the log file as and when they are appended to the log file within the server without refreshing the browser window




 

TASK2:- 



Suppose we have an unsorted array A[1..n] of integers with possible duplicates.  Design a version of Quicksort that instead of portioning into two sets, one whose elements are less than or equal to the pivot and a second whose elements are greater than or equal to the pivot, the new algorithm partitions into three sets, one whose elements are strictly less than the pivot, a second whose elements are strictly more than the pivot, and a third whose elements are equal to the pivot. Your algorithm should be in-place. One idea is that in the partitioning phase that as we move the two pointers i and j toward each other we maintain the invariant that the array looks like;
                                                                            i                              j
[elements equal to pivot] [elements less than pivot] [unknown elements] [elements greater than pivot] [elements equal to pivot]

When there are no unknown elements left then the elements can be rearranged to be of this form:

[elements less than pivot][elements equal to pivot][elements greater than pivot]

Design the Quicksort and Partition algorithms that implements this idea.
Show that your Quicksort algorithm runs in worst case time O(dn) where d is the number of distinct keys in the array.
 
