import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Btvn {
    //    bai 1: xóa nguyên âm trong chuỗi
    public static String deleteVowels (String input){
        if(input==null || input == ""){
            return input;
        }
        String result="";
        String vowels = "aiueo";
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (!vowels.contains(String.valueOf(character))) {
                result += character;
            }
        }
        return result;
    }
//    bai 2: tìm giá trị lớn thứ 2 trong mảng
//    có mấy tham số: => int[]
//    datatype => int
    public static int findSecondLargest(int[] array) {
//        với case array null hoặc array có 1 phần tử
//        => Integer.MIN_VALUE
        int minVal = Integer.MIN_VALUE; //âm vô cùng
        if (array == null || array.length < 2) {
            return minVal;
        }

//        c1: duyệt từ cuối mảng
//        mảng da sort sẵn
        Arrays.sort(array);

//        lấy phần tử cuối làm maximum
        int largest = array[array.length - 1];

        for (int i = array.length - 2; i >= 0; i--) {
            if (array[i] != largest) {
                return array[i];
            }
        }
        return minVal;
    }

//    bai 3: Tính tổng số chẵn từ 0 -> n
    public static int sumEvenNumber(int n){
        int sum=0;
        for (int i = 0; i <= n; i++) {
            if(i%2==0){
                sum +=i;
            }
        }
        return sum;
    }
//    bai 4 : Đếm số từ trong chuỗi (dùng hàm split() => .length để đếm)
    public static int countWord(String input){
        String[] arrayWords = input.split(" ");
        return arrayWords.length;
    }
//    bai 5: In chữ cái đầu của mỗi từ
//    có mấy tham số: => 1 tham số có kiểu String
//    datatype => String
    public static String printFirstCharacter(String input) {
        String[] arrayWords = input.split(" ");
        String result = "";
        for (String word : arrayWords) {
//            String <=> char[] : la mang cac ky tu
            result = result + word.toUpperCase().charAt(0) + " ";
        }
        return result;
    }

//     bai 6: Tính thuế
    public static double findTax(double thuNhap){
        double thue =0;
        if (thuNhap <= 5) {
            thue = thuNhap * 0.05;
        } else if (thuNhap <= 10) {
            thue = (5 * 0.05) + (thuNhap - 5) * 0.10;
        } else if (thuNhap <= 18) {
            thue = (5 * 0.05) + (5 * 0.10) + (thuNhap - 10) * 0.15;
        } else if (thuNhap <= 32) {
            thue = (5 * 0.05) + (5 * 0.10) + (8 * 0.15) + (thuNhap - 18) * 0.20;
        } else if (thuNhap <= 52) {
            thue = (5 * 0.05) + (5 * 0.10) + (8 * 0.15) + (14 * 0.20) + (thuNhap - 32) * 0.25;
        } else if (thuNhap <= 80) {
            thue = (5 * 0.05) + (5 * 0.10) + (8 * 0.15) + (14 * 0.20) + (20 * 0.25) + (thuNhap - 52) * 0.30;
        } else {
            thue = (5 * 0.05) + (5 * 0.10) + (8 * 0.15) + (14 * 0.20)
                    + (20 * 0.25) + (28 * 0.30) + (thuNhap - 80) * 0.35;
        }
        return thue;
    }

//    bai 7: Kiểm Tra Chuỗi Đối Xứng
    public static boolean checkPalindrome(String word){
        String reverseWord = new StringBuilder(word).reverse().toString();
        if (reverseWord.equals(word)){
           return true;
        }else{
            return false;
        }
    }

//    bai 8: Xóa ký tự lặp lại trong chuỗi
    public static String removeDuplicates(String input) {
        if(input==null || input==""){
            return input;
        }
//        tạo 1 mảng ký tự String chứa kết quả là những ký tự không trùng nhau

        String uniqueCharacter = "";
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (!uniqueCharacter.contains(String.valueOf(character))) {
                uniqueCharacter += character;
            }
        }
        return uniqueCharacter;
    }


//    bai 9: Two sum II
    public static int[] twoSum(int[] numbers, int target) {
//       c1: dung 2 vong for
//        for1: duyet index1
//        for2: duyet index2
//        for(int i=0; i<numbers.length-1; i++){
//            for (int j = i+1; j <numbers.length ; j++) {
//                if(numbers[i]+numbers[j]==target){
//                    return new int[]{i,j};
//                }
//            }
//        }return new int[]{-1, -1};

//        cách2: while
        int index1 = 0;
        int index2 = numbers.length - 1;
        while (index1 < index2) {
            if (numbers[index1] + numbers[index2] == target) {
                return new int[]{index1+1, index2+1};
            } else if (numbers[index1] + numbers[index2] < target) {
                index1++;
            } else {
                index2--;
            }

        }
        return new int[]{-1, -1};
    }
//    bai 10
//    bai 11

    public static void main(String[] args) {
//    bai 1
        System.out.println("Bai1: xóa nguyên âm trong chuỗi");
        String input1 = "Cybersoft";
        System.out.println("Kết quả chuỗi '"+input1+"' sau khi xóa tất cả các nguyên âm là: ");
        System.out.println(deleteVowels(input1));
//    bai 2
        System.out.println("Bai2: tìm giá trị lớn thứ 2 trong mảng");
//        int[] arrBai2 = {3,6,8,4}; //happy case 1
        int[] arrBai2 = {3,6,8,8,8,4}; //happy case 2
//        int[] arrBai2 = {3}; //case 3
//        int[] arrBai2 = null; //case 4
        int secondLargest = findSecondLargest(arrBai2);
        System.out.println(secondLargest);
//    bai 3
        System.out.println("Bai3: Tính tổng số chẵn từ 0 -> n");
        int n=10;
        System.out.println("Tổng số chẵn từ 0 đến "+n+" là: ");
        System.out.println(sumEvenNumber(n));
//    bai 4
        System.out.println("Bai4: Đếm số từ trong chuỗi");
        String input4 = "xin chào các bạn";
        System.out.println("Số từ trong chuỗi '"+input4+"' là: ");
        System.out.println(countWord(input4));
//    bai 5
        System.out.println("Bai5: In Chữ Cái Đầu Của Mỗi Từ");
        String input5 = "Xin chao cac ban";
        String result = printFirstCharacter(input5);
        System.out.println("Chữ cái đầu của mỗi từ trong chuỗi '"+input5+"' là: ");
        System.out.println(result);
//    bai 6
        Scanner sc = new Scanner(System.in);
        System.out.println("Bai6: Tính thuế thu nhập cá nhân");
        System.out.println("Nhập số tiền thu nhập hàng năm (triệu đồng)");
        double thuNhap = sc.nextDouble();
        System.out.println("Số thuế phải trả: " + findTax(thuNhap));

//    bai 7
        System.out.println("Bai7: Kiểm tra chuỗi đối xứng");
        String input7 = "helleh";
        if(checkPalindrome(input7)){
            System.out.println(input7+": là chuỗi đối xứng");
        }else {
            System.out.println(input7+": không phải là chuỗi đối xứng");
        }
//    bai 8
        System.out.println("Bai8: Xóa ký tự lặp lại trong chuỗi");
        String input8 = "Programming";
        String result8 = removeDuplicates(input8);
        System.out.println("Kết quả sau khi xóa kí tự lặp lại của chuỗi '"+input8+"' là: ");
        System.out.println(result8);
//    bai 9
        System.out.println("Bai9: Tìm vị trí của 2 số trong chuỗi có tổng target");
        int[] numbers = {2, 7, 8, 11, 16};
        int target = 9;
        int[] result9 = twoSum(numbers, target);
        System.out.println("["+result9[0]+","+result9[1]+"]");
//    bai 10
//    bai 11
    }
}
