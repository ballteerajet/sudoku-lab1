import java.util.Arrays;
import java.util.Scanner;

class Main {

    static int[][] myNumbers = {
        {5, 3, 4, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, 4, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, 8, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, 4, 1, 9, 6, 3, 5},
        {3, 4, 5, 2, 8, 6, 1, 7, 9},
    };
    static int[] checkNums = {1,2,3,4,5,6,7,8,9};
                        
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        boolean running = true;
        while(running){
        
        System.out.print("1.Win check\n2.Print row (1-9)\n3.Print col(1-9)\n4.Print region\n5.Exit\nEnter an integer: ");
        int func = scanner.nextInt();
        switch (func){
            case 1:
                boolean win = checkWin(myNumbers);
                System.out.println(win ? "win" : "lose");
                break;
            case 2:
                System.out.print("Enter row number: ");
                int r = scanner.nextInt();
                printRow(myNumbers, (r-1));
                break;
            case 3:
                System.out.print("Enter row number: ");
                int c = scanner.nextInt();
                printCol(myNumbers, (c-1));
                break;
            case 4:
                System.out.print("Enter axis-x (1-3): ");
                int x = scanner.nextInt();
                System.out.print("Enter axis-y (1-3): ");
                int y = scanner.nextInt();
                System.out.println(Arrays.toString(separateBlock(myNumbers, x-1, y-1)));
                break;
            case 5:
                running = false;
                break;
        }
        System.out.print("\n");
        }

        scanner.close();
    }
    
    /**
     * แยกค่าจากบล็อก 3x3 ภายใน Sudoku ขนาด 9x9 ออกมาเป็นอาร์เรย์ 1 มิติ
     * โดยใช้ตำแหน่งบล็อกแนวแกน X และ Y (ค่าตั้งแต่ 0 ถึง 2)
     *
     * @param array    บอร์ด Sudoku ขนาด 9x9
     * @param x_block  ตำแหน่งแนวนอนของบล็อก (ค่าตั้งแต่ 0 ถึง 2)
     * @param y_block  ตำแหน่งแนวตั้งของบล็อก (ค่าตั้งแต่ 0 ถึง 2)
     * @return         อาร์เรย์ 1 มิติที่มี 9 ตัวเลขจากบล็อก 3x3 ที่ระบุ
     */
    static int[] separateBlock(int array[][], int x_block, int y_block) {
        int[] block = new int[9];
        int index = 0;

        y_block = y_block * 3;
        x_block = x_block * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                block[index++] = array[x_block + i][y_block + j];
            }
        }

        return block;
    }
    
    /**
     * ตรวจสอบว่าอาร์เรย์สองชุดมีสมาชิกเหมือนกันหรือไม่ โดยไม่สนลำดับ
     *
     * @param arr1 อาร์เรย์ชุดที่หนึ่ง
     * @param arr2 อาร์เรย์ชุดที่สอง
     * @return true หากมีสมาชิกเหมือนกันทั้งหมด (ไม่สนลำดับ), false หากไม่ตรงกัน
     */
    public static boolean arraysMatch(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) return false;
    
        // คัดลอกข้อมูลเพื่อไม่แก้ไขต้นฉบับ
        int[] copy1 = Arrays.copyOf(arr1, arr1.length);
        int[] copy2 = Arrays.copyOf(arr2, arr2.length);
    
        // เรียงลำดับก่อนเปรียบเทียบ
        Arrays.sort(copy1);
        Arrays.sort(copy2);
    
        return Arrays.equals(copy1, copy2);
    }
    
    /**
     * ตรวจสอบว่า Sudoku บอร์ดที่ส่งเข้ามานั้นเป็นคำตอบที่ถูกต้องครบถ้วนหรือไม่
     * โดยตรวจเฉพาะแถว (row) และกลุ่มย่อย 3x3 (block) เท่านั้น
     * 
     * @param array บอร์ด Sudoku ขนาด 9x9
     * @return true หาก Sudoku ถูกต้องครบทุกแถวและกลุ่มย่อย, false หากผิด
     */
    public static boolean checkWin(int[][] array) {
        // ตรวจสอบกลุ่มย่อยขนาด 3x3 (บล็อก)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boolean state = arraysMatch(separateBlock(array, i, j), checkNums);
                if (!state) {
                    return false;
                }
            }
        }
    
        // ตรวจสอบแถวทั้งหมดว่าครบและถูกต้อง
        for (int i = 0; i < 9; i++) {
            boolean state = arraysMatch(array[i], checkNums);
            if (!state) {
                return false;
            }
        }
    
        // ไม่จำเป็นต้องตรวจคอลัมน์ เพราะแถวและกลุ่มครอบคลุมทุกกรณีแล้ว
        return true;
    }
    
    /**
     * พิมพ์ค่าในแถวที่กำหนดของ Sudoku
     *
     * @param array บอร์ด Sudoku ขนาด 9x9
     * @param r แถวที่ต้องการพิมพ์ (0 ถึง 8)
     */
    public static void printRow(int[][] array, int r) {
        for (int i = 0; i < 9; i++) {
            System.out.println(array[r][i]);
        }
    }
    
    /**
     * พิมพ์ค่าในคอลัมน์ที่กำหนดของ Sudoku
     *
     * @param array บอร์ด Sudoku ขนาด 9x9
     * @param c คอลัมน์ที่ต้องการพิมพ์ (0 ถึง 8)
     */
    public static void printCol(int[][] array, int c) {
        for (int i = 0; i < 9; i++) {
            System.out.println(array[i][c]);
        }
    }

}



