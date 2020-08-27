package com.company;

import java.lang.*;
import java.io.*;

public class QuickSort {


    public static void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

    public static void PrintArr(int arr[], int n) {                                     //  Printing an array
        for (int i = 0; i < n; i++)
            System.out.println(arr[i]);

    }


    public static void partition(int[] arr, int start, int end, int i, int j) {         // partition in three parts i.e arr[start to i] contains elements smaller than pivot
        if (end - start <= 1) {                                                         // arr[i+1 to j-1] contains all ocuurence of pivot
            if (arr[end] < arr[start])                                                  // arr[j to end ] contains element greater than pivot
                swap(arr[end], arr[start]);
            i = start;
            j = end;
            return;
        }
        int partitionIndex = start;
        int pivot = arr[end];

        while (partitionIndex <= end) {
            if (arr[partitionIndex] < pivot)
                swap(arr[start++], arr[partitionIndex++]);
            else if (arr[partitionIndex] == pivot)
                partitionIndex++;

            else if (arr[partitionIndex] > pivot)
                swap(arr[partitionIndex], arr[end--]);
        }

            i = start - 1;                        // update i & j
            j = partitionIndex;


    }


    public static void Sort(int arr[], int start, int end) {

        if (start >= end)                                                             // for 1 or 0 element
            return;

        int i, j;
         i = start ;
         j = end;

        partition(arr, start, end, i, j);

        Sort(arr, start, i);
        Sort(arr, j, end);



    }

    public static void main(String[] args) {

        int arr[] = {4, 9, 4, 4, 1, 9, 4, 4, 9, 4, 4, 1, 4};

     // int arr[] = {2, 4,2,2,3,7,6,4,2,1,5,1,3,1,1,,5,1};
      //  int arr[] = {4, 9, 4, 4, 9, 1, 1, 1};
        PrintArr(arr, arr.length);
        Sort(arr, 0, arr.length - 1);
        PrintArr(arr, arr.length);


    }
}














