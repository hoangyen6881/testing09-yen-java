import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class ArrayListEx {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập so lương phan tu: ");
        int n = scanner.nextInt();
        ArrayList<Integer> numbers= new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("nhập so thứ " +(i+1));
            numbers.add((scanner.nextInt()));
        }
//       for(int number:numbers){
//           System.out.println(number);
//       }


       //B1: đếm số lần xuất hiện của 1 phan tử trong mảng
//    input:
//        +n: số luong phan tu của mảng
//        +Arraylist
//        +number: tìm số lần xuất hiện của number
//    output:
//        + so lan xuat hiện của number
//     process:
//        +count = 0 => đếm số lần
//        +duyệt mảng
//        nếu phần tử thứ i = number => count++
//        if(count==0) =>"không có số number trong mảng"
//        else in số lần xuất hiện phần tử

//        int count=0;
//        System.out.println("Nhap so can tìm: ");
//        int number = scanner.nextInt();
//        for(int num:numbers ){
//            if(num==number){
//                count++;
//            }
//        }
//        if(count==0){
//            System.out.println("Không có số "+number+" trong mảng");
//        }else{
//            System.out.println("Số lần xuất hiện là: "+ count);
//        }

//        B2: Tìm min max trong mảng
//        int maximum = Collections.max(numbers);
//        int minimum = Collections.min(numbers);
//        System.out.println("max: "+ maximum);
//        System.out.println("Min: "+minimum);

// B3: sắp xếp array tăng dần, giảm dần
        Collections.sort(numbers);
        Collections.sort(numbers, Collections.reverseOrder());

        //đảo mảng
        Collections.reverse(numbers);

//        Remove phần tử trong mảng
//        remove theo index
        numbers.remove(1);
//        Remove theo value
        numbers.remove(Integer.valueOf(2));
    }

}
