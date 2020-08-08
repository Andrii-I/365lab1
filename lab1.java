import java.util.Arrays;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

class lab1 {
    public static void generateInput() throws IOException {
        File output = new File("input.txt");
        FileWriter filewriter = null;
        filewriter = new FileWriter(output);
        Random rand = new Random();
        for (int i = 0; i < 450; i++) {
            if (i == 449) {
                filewriter.write("99");
                break;
            } 
            //filewriter.write(i + "\n");
            filewriter.write(rand.nextInt(100) + "\n");
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
        String temp_output_names[] = {"temp0.txt",
                                      "temp1.txt",
                                      "temp2.txt",
                                      "temp3.txt",
                                      "temp4.txt",
                                      "temp5.txt",
                                      "temp6.txt",
                                      "temp7.txt"};
        
        try{
            File file = new File(filename);
            Scanner scan = new Scanner(file);

            int memory[] = new int[100];
            int mem_p = 0;
            int total_int_in = 0;
            int temp_f = 0;
            while (scan.hasNextLine()) {
                memory[mem_p] = scan.nextInt();
                mem_p++;
                total_int_in++;
                if (mem_p == 100) {
                    mem_p = 0;
                    File output = new File(temp_output_names[temp_f]);
                    temp_f++;
                    FileWriter filewriter = null;
                    filewriter = new FileWriter(output);
                    mergeSort(memory);
                    for (int j = 0; j < 100; j++) {
                        filewriter.write(memory[j] + "\n");
                    }
                    filewriter.close();
                }
            }
            File output = new File(temp_output_names[temp_f]);
            temp_f++;
            FileWriter filewriter = null;
            filewriter = new FileWriter(output);
            mergeSort(memory, mem_p);
            for (int j = 0; j < mem_p; j++) {
                filewriter.write(memory[j] + "\n");
            }
            filewriter.close();
            scan.close();

            //split memory into parts 
            int full_files = total_int_in / 100;
            if (total_int_in % 100 != 0) full_files++;
            //System.out.println(full_files);
            int mem_per_file = 100/full_files;
            int mem_clusters = 100/mem_per_file;
            int mem_split[][] = new int[mem_clusters][3]; //0 would store start and 1 finish of each memory cluster and 3 status (done or not)
            //System.out.println("full files:" + full_files);
            //System.out.println("mem_per_file:" + mem_per_file);
            //System.out.println("mem_clusters:" + mem_clusters);
            
            //iterate from 0 to # of mem clusters 
                //fill in mem_split with start and finish of each cluster based on mem_per_file
            mem_p = 0;
            for (int i = 0; i < mem_clusters; i++) {
                mem_split[i][0] = mem_p;
                mem_p += mem_per_file;
                mem_split[i][1] = mem_p;
                mem_split[i][2] = 0;
            }

            //create a writer for final output
            filename = "output.txt";
            output = new File(filename);
            filewriter = null;
            filewriter = new FileWriter(output);
            
            // set all elements of memory to -1 (= null)
            for (int i = 0; i < 100; i++) {
                memory[i] = -1;
            }

            //create array of readers[mem_clusters] to store pointers to readers
            Scanner[] temp_scan_p = new Scanner[mem_clusters];
            for (int i = 0; i < mem_clusters; i++) {
                filename = temp_output_names[i];
                file = new File(filename);
                temp_scan_p[i] = new Scanner(file); 
                //scan.hasNextLine();
                //scan.nextInt();
                scan.close();
            }

            //fill in mem clusters from temp files using array of readers
            int cluster_p = 0;
            mem_p = 0;
            for (int i = 0; i < mem_clusters; i++) {
                for (int j = mem_split[i][0]; j < mem_split[i][1]; j++) {
                    if (temp_scan_p[i].hasNextLine()) {
                        memory[j] = temp_scan_p[i].nextInt();
                    }
                }
            }
            //System.out.println(Arrays.toString(memory));

            //find smallest num in memory, write it into output file, replace it w next number from the same file, repeat the step until done
            int smallest_num = memory[0];
            int smallest_p = 0;
            for (int i = 0; i < 100; i++) {
                if (smallest_num > memory[i]) {
                    smallest_num = memory[i];
                    smallest_p = i;
                } 
            }

            //System.out.println(Arrays.toString(memory));
            //System.out.println(smallest_num + ", pos " + smallest_p);

            //put smallest num into output
            filewriter.write(smallest_num + "\n");

            // identify from which temp file the smallest num came and pull another checking if it's not done

            filewriter.close();

        } catch (FileNotFoundException e){
            System.err.println("File not found!");
        }

    }

}