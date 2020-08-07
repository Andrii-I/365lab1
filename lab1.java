import java.util.Arrays;
import java.io.*; 
import java.util.Scanner;

class lab1 {
    public static void generateInput() {
        File output = new File("input.txt");
        FileWriter filewriter = null;
        filewriter = new FileWriter(output);
        for (int i = 0; i < 450; i++){
            if (i != 449) filewriter.write(i + "\n");
            else filewriter.write(i + "\n");
        }
        filewriter.close();
    }

    public static void mergeSort(int[] arr) {
        mergeSort(arr, arr.length);
    }

    public static void mergeSort(int[] arr, int N) {
        mergeSort(arr, 0, N - 1);
    }

    private static void mergeSort(int[] list, int first, int last) {
        if (first < last) {
            int middle = (first + last) / 2;
            mergeSort(list, first, middle);
            mergeSort(list, middle + 1, last);
            mergeSortedHalves(list, first, middle, last);
        }
    }

    private static void mergeSortedHalves(int[] arr, int left, int middle, int right) {
        int[] temp = new int[right - left + 1];
        int index1 = left;
        int index2 = middle + 1;
        int index = 0;

        while (index1 <= middle && index2 <= right) {
            if (arr[index1] <= arr[index2]) {
                // System.out.println(arr[index1]);
                temp[index] = arr[index1];
                index1++;
            } else {
                // System.out.println(arr[index2]);
                temp[index] = arr[index2];
                index2++;
            }
            index++;
        }

        for (int i = index1; i <= middle; i++) {
            temp[index] = arr[i];
            index++;
        }

        if (index < arr.length) {
            for (int j = index2; j <= right; j++) {
                temp[index] = arr[j];
                index++;
            }
        }

        for (int i = left; i <= right; i++) {
            arr[i] = temp[i - left];
        }
    }

    public static void main(String[] args) throws IOException {
        generateInput();
        String filename = "input.txt";  
        
        try{
            File file = new File(filename);
            Scanner scan = new Scanner(file);

            int memory[] = new int[100];
            int mem_p = 0;
            int total_in = 0;
            while (scan.hasNextLine()) {
                memory[mem_p] = scan.nextInt();
                mem_p++;
                total_in++;
                //if (mem_p == 100) {
                    mem_p = 0;
                    File output = new File("output.txt");
                    FileWriter filewriter = null;
                    filewriter = new FileWriter(output);
                    filewriter.write("1 jojoba\n");
                    filewriter.write("2 jojoba\n");
                    filewriter.close();
                //}
                
            }
            scan.close();
            //System.out.println(Arrays.toString(memory));

/*             int in_arr[] = new int[i];
            for (int j = 0; j < i; j++) {
                in_arr[j] = arr[j];
            }

            System.out.println("Input array: " + Arrays.toString(in_arr));
            mergeSort(in_arr);
            System.out.println("Sorted array: " + Arrays.toString(in_arr)); */

        } catch (FileNotFoundException e){
            System.err.println("File not found!");
        }

    }

}