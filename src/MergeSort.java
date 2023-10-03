import java.util.Scanner;

public class MergeSort
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int j = 0;

        // Input the integers, from 0-9 that you want to sort.
        // ex. '540994235209 654 54576 265'
        System.out.println("Enter the integers you want to sort:");
        String userInput = scanner.nextLine().strip();
        char[] arr = new char[userInput.length()];

        // Split the array of characters (integers) input by the users into two
        // sub arrays to be insertion sorted by child processes. The size of the first
        // sub array is the size of half of the input length from the user. The size
        // of the second sub array is the difference between the input length and the
        // sub array 1
        char[] subArr1 = new char[userInput.length() / 2];
        char[] subArr2 = new char[userInput.length() - subArr1.length];

        // Assign the characters in the user input string to the array
        for (int i = 0; i < userInput.length(); i++)
        {
            arr[i] = (userInput.charAt(i));
        }

        // Split the user input array into 2 sub arrays to be insertion
        // sorted by the child processes.
        subArr1 = addToArray(subArr1, arr);
        for(int i = arr.length / 2; i < arr.length; i++)
        {
            subArr2[j] = arr[i];
            j++;
        }

        //Initialize 2 SortThread objects with both sub arrays
        SortThread sortThread1 = new SortThread(subArr1);
        SortThread sortThread2 = new SortThread(subArr2);

        // Create 2 child processes and initialize them with the SortThread
        // objects.
        Thread thread1 = new Thread(sortThread1);
        Thread thread2 = new Thread(sortThread2);

        // Begin the child processes
        thread1.start();
        thread2.start();

        // Wait until child processes finish before moving on
        try
        {
            thread1.join();
            thread2.join();
        }
        catch (Exception e){}

        // Recombine the 2 sub arrays into 1 array to be passed into the merge sort
        // method
        char[] newArr = new char[userInput.length()];
        addToArray(newArr, subArr1);
        j = 0;
        for(int i = subArr1.length; i < newArr.length; i++)
        {
            newArr[i] = subArr2[j];
            j++;
        }

        //Merge sort the new array and then print out the results
        mergeSort(newArr);

        printArrayElements(newArr);
    }

    public static void merge(char[] leftArray, char[] rightArray, char[] arr)
    {
        //Initialize size of left and right array as well as indexes for each array
        int leftSize = arr.length / 2;
        int rightSize = arr.length - leftSize;
        int i = 0, l = 0, r = 0;

        //check the conditions for merging
        while(l < leftSize && r < rightSize)
        {
            // If the element in the left array at index l is less than the element in
            // the right array at index r, element l in the left array is added to the main
            // array at index i. Else, the element in the right array at index r is added
            // to the main array at index i.
            if(leftArray[l] < rightArray[r])
            {
                arr[i] = leftArray[l];
                i++;
                l++;
            }
            else
            {
                arr[i] = rightArray[r];
                i++;
                r++;
            }
        }

        while(l < leftSize)
        {
            arr[i] = leftArray[l];
            i++;
            l++;
        }
        while(r < rightSize)
        {
            arr[i] = rightArray[r];
            i++;
            r++;
        }
    }

    private static void mergeSort(char[] arr)
    {
        int length = arr.length;
        if (length <= 1)
        {   return;    }

        int middle = length / 2;

        char[] leftArray = new char[middle];
        char[] rightArray = new char[length - middle];

        int i = 0; //left array
        int j = 0; //right array

        for (; i < length; i++)
        {
            if (i < middle)
            {
                leftArray[i] = arr[i];
            } else
            {
                rightArray[j] = arr[i];
                j++;
            }
        }
        // Recursive method call on the left and right array to continue splitting
        // them until the size of the array is less than or equal to 1.
        mergeSort(leftArray);
        mergeSort(rightArray);
        // Pass the left, right, and original arrays to the merge method to be sorted
        // and merged into one array.
        merge(leftArray, rightArray, arr);
    }

    // Used simply for adding elements to an array in order to cut down
    // on the repetitive use of for loops.
    public static char[] addToArray(char[] arr1, char[] arr2)
    {
        if(arr1.length < arr2.length)
        {
            for(int i = 0; i < arr1.length; i++)
            {
                arr1[i] = arr2[i];
            }
        } else
        {
            for(int i = 0; i < arr2.length; i++)
            {
                arr1[i] = arr2[i];
            }
        }


        return arr1;
    }

    // Used for printing out each element in the array.
    // I also used this method for debugging at multiple points
    // throughout the program.
    public static void printArrayElements(char[] arr)
    {
        for (char character : arr)
        {
            System.out.println(character);
        }
    }
}
