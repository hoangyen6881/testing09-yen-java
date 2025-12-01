import java.sql.SQLOutput;
import java.util.Scanner;

public class ArrayLoop {
    //    Viết hàm tính tổng 2 số nguyên
    public static int sum2num(int num1, int num2) {
        return num1 + num2;
    }

    //  Tính tổng các số nguyên trong mảng
//        Hàm này có bao nhiêu tham số
//        => array các số nguyên
//        Hàm này có kiểu dữ liệu nào?
//        => int
    public static int sumArray(int[] array) {
        int sum = 0;
        for (int number : array) {
            sum += number;
        }
        return sum;
    }


    public static void main(String[] args) {
//       b1: tinh tong các so trong mang
//--------------------------------- hàm nhập
//        Scanner scanner = new Scanner(System.in);
//        //Nhập so luong phan tu cua mang
//        // Array chứa phần tử
        int number1 = 10;
        int number2 = 20;
        int sum = sum2num(number1, number2);
        System.out.println(sum);

        int[] array = {2, 6, 7, 4, 7};
        int sumArray = sumArray(array);
        System.out.println(sumArray);


////        System.out.println("Nhập số lượng phần tử: ");
////        int n = scanner.nextInt();
////        int[] listInt = new int[n];
//// --------------------------------- hàm tính tổng
////        xác định tham số truyền vào
////        tham số: 2 số interger
////        datype hàm: int
//
////        int sum = 0;
//////        nhập số nguyên vào listInt
////        for (int i=0; i<n; i++){
////            System.out.println("Nhap phan tu thu "+(i+1));
//////            ListInt[i]: phần tử thu i trong array
//////            ListInt[i] = scanner.nextInt();
//////            nhập so nguyen tu ban phim va gan vao phan tu thu i của array
////            listInt[i] = scanner.nextInt();
////            sum+= listInt[i];
////        }
////        -------------------------- hàm xuất
//////        in list integer
////        for (int i = 0; i < n; i++) {
////            System.out.println(listInt[i]);
////
////        }
////        System.out.println("Tong = "+sum);
////  --------------------------------------------
//        //b2: tìm so maximun và minimun trong mang
//        System.out.println("Nhập số lượng phần tử: ");
//        int n = scanner.nextInt();
//        int[] listInt = new int[n];
//        for (int i=0; i<n; i++){
//            System.out.println("Nhap phan tu thu "+(i+1));
//            listInt[i] = scanner.nextInt();
//        }
////        int min = listInt[0];
////        int max = listInt[0];
////        for (int i = 1; i < n; i++) {
////            if(min>listInt[i]){
////                min = listInt[i];
////            }
////            if(max<listInt[i]){
////                max = listInt[i];
////            }
////        }
////        System.out.println("Min: "+min);
////        System.out.println("Max: "+max);
//
////        B3: Đếm số lượng chẵn lẻ trong mang
////        C1
//        int chan = 0;
//        int le =0;
////        for (int i=0; i<n; i++){
////            if(listInt[i]%2==0){
////                chan++;
////            }else{
////                le++;
////            }
////        }
////        C2:
//        for(int item : listInt){
//            if(listInt[item]%2==0){
//                chan++;
//            }else{
//                le++;
//            }
//        }
//        System.out.println("Số phan tu chan: "+chan);
//        System.out.println("Số phan tu le: "+le);


    }

}
