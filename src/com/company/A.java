package com.company;

public class A {

    public static void swap(int a,int b){
        int temp=a;
        a= b;
        b = temp;
    }


    public static void PrintArr(int arr[],int n){
        for(int i=0;i<n;i++){
            System.out.print(arr[i]);
        }
    }
    public static void Partition(int arr[],int start, int end,int i,int j ) {
        i = start - 1;
        j = end;
        int p = start - 1;
        int q = end;

        while (true) {
            while (arr[i++] < arr[end]) ;
            while (arr[end] < arr[j--])
                if (j == end)
                    break;

            if (i >= j)
                break;

            swap(arr[i], arr[j]);

            if (arr[i] == arr[end]) {
                p++;
                swap(arr[p], arr[i]);


            }

            if (arr[j] == arr[end]) {
                q--;
                swap(arr[j], arr[q]);


            }


        }
        swap(arr[i], arr[end]);
        j = i - 1;
        for (int k = start; k < p; k++, j--)
            swap(arr[k], arr[j]);
        i = i + 1;
        for (int k = end - 1; k > q; k--, i++)
            swap(arr[i], arr[k]);

    }

    public static void Sort(int arr[],int start, int end){
        if (end <= start)
            return;
        int i=start -1,j = end;
        Partition(arr,start,end,i,j);
        Sort(arr,start,j);
        Sort(arr, i , end);

}

    public static void main(String[] args) {
        int arr[] = {4, 9, 4, 4, 1, 9, 4, 4, 9, 4, 4, 1, 4};

        int n= arr.length;


        PrintArr(arr, args.length);
        Sort(arr,0, args.length - 1);
        PrintArr(arr, arr.length);

    }
}

