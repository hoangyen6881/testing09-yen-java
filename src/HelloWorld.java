import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
//        Mac: cmd +?, Win: Ctrl+?
        System.out.println("Hello World");
        System.out.println("Hello "+ "Testing 09");

        //1. Khai báo biến
//        - So (so nguyen, so thưc)
//        - Chuoi (string)
//        - Cach khai bao bien
//        <kieu du lieu> <ten bien> = <dữ liệu>;
        int number1 = 10;
        int number2 = 20;
        float number3 = 3.5f;
        double number4 = 7.0f;

        String string1 =  "Hello world";
        String string2 = "Testing09";

        boolean bool1 = true;
        boolean bool2 = false;

        System.out.println(number3);
//        chuoi + so => println se tu động convert tu so -> chuoi
//        VD: 10 => "10"
        System.out.println(string1 + number1);
//        Nhập gia tri tu ban phim
//        => Scanner
//        Khai báo doi tuong Scanner
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
//        System.out.println("Please enter a number: ");
//        int number6 = scanner.nextInt();
//        System.out.println("So ban nhap la: "+ number6);

//        System.out.println("Moi ban nhap so thưc: ");
//        float number7 = scanner.nextFloat();
//        System.out.println("So thuc ban nhap la: "+ number7);

//        System.out.println("Moi ban nhap chuoi: ");
//        String string9 = scanner.nextLine();
//        System.out.println("Chuoi ban nhap la: " + string9);
//-Phép tính +, -, *, / (chia lấy nguyên), % (chia lấy dư)
//        đi chung với biến (variable)
//        +
        int number8 = 10;
        int number9 = 20;
        int result = number8 + number9+30;
        System.out.println("Ket qua cong là: " + result);

//        -
        int number10 = 30;
        int number11 = 20;
        int result1 = number10-number11-80;
        System.out.println("Ket qua trừ là: " + result1);

//        *
        int number12 = 68;
        int number13 = 79;
        int result2 = number12*number13;
        System.out.println("kết quả nhân 2 số là: "+result2);

//        chia lấy phần nguyên (/)
        int number14 = 80;
        int number15 = 7;
        int result3 = number14/number15;
        System.out.println("kết quả chia lấy nguyên là " + result3);

//      Chia lay du (%)
        int result4  =  number14%number15;
        System.out.println("Kết quả chia lấy dư " + result4);

//        result = number1 + number2
//        result++ => result = result +1
//      ++result => result = result +1
//result++ : cộng sau => gán xong mới cộng
//  ++result: cộng trước => cộng trước xong mới gán

        int number16 = 10;
        //number16++: gán giá trị hiện tại của num16(11)
//        và gán vào biến number 17 (11)
//        và tăng giá trị của number16 lên 1 đơn vị
        int number17 = number16++;
        System.out.println("num17: "+ number17);
        //++number16: tăng giá trị num16 lên 1 đơn vị (12)
//        và gán vào biến number 17 (12)
        number17 = ++number16;
        System.out.println("num17: "+ number17);

//        result +=2 => result = result +2

//        - true-false
//        (>, >=, <, <=, ==, !=)
//        phải đi kèm với keywork if/else
//        Kết quả của phép so sánh là boolean
        int number18 = 30;
        int number19 = 20;
        boolean check1 = number18 > number19;
        boolean check2 = number18 < number19;
        boolean check3 = number18 == number19;
        boolean check4 = number18 != number19;

        System.out.println(check1);
        System.out.println(check2);
        System.out.println(check3);
        System.out.println(check4);

    }
}
