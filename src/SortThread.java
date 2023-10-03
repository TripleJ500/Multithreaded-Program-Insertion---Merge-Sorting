import java.util.List;

public class SortThread implements Runnable
{
   char[] arr;

   //Constructor for setting passed array to global array variable.
   public SortThread(char[] arr)
    {
        this.arr = arr;
    }

    // Run method that executes when Thread.start() is called. This run
    // method executes the insertion sort algorithm on the global character
    // array in this class.
    @Override
    public void run()
    {
        for(int i = 1; i < arr.length; i++)
        {
            char temp = arr[i];
            int j = i - 1;

            while(j >= 0 && arr[j] > temp)
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }
}
