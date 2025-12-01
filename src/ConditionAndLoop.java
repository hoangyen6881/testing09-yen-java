import java.util.Scanner;

public class ConditionAndLoop {
    public static void main(String[] args) {
//        B1: nhập số nguyên từ bàn phím,
//        kiểm tra số chẵn lẻ
//        input: số nguyên nhập từ bàn phím => Scanner
//        output: Số đó là số ... (chẵn hoặc lẻ)
//        process:
//        => dùng if-else để xu lý
//        Số chẵn chia hết cho 2 => number % 2 == 0
//       số lẻ => (number %2 == 1) hoặc (number % 2 !=0)
        Scanner scanner = new Scanner((System.in));
//        System.out.println("Nhập 1 số nguyên: ");
//        int num1 = scanner.nextInt();
//        if(num1%2==0){
//            System.out.println("Số "+ num1 +" là số chẵn");
//        }else{
//            System.out.println("Số "+ num1 +" là số lẻ");
//        }

//      Bài 2: Nhập số nguyên từ bàn phím
//      Kiểm tra số đó là số nguyên dương, số âm, hay là so 0
//        input: số nguyên nhập từ bàn phím => Scanner
//        output: Số đó là số ... (0, dương hoặc âm)
//        process:
//      if - else if - else
//        if - if - if

//        Scanner scanner = new Scanner((System.in));
//        System.out.println("Nhập 1 số nguyên: ");
//        int num1 = scanner.nextInt();
//        if(num1==0){
//            System.out.println("Số "+ num1 +" là số 0");
//        }else if(num1>0){
//            System.out.println("Số "+ num1 +" là số dương");
//        }else{
//            System.out.println("Số "+ num1 +" là số âm");
//        }

//    Luu y: neu if else qua nhiều => dùng switch-case

//        b3: Tìm số lớn nhất trong 3 số
//        input: 3 số nhập từ ban phím
//        output: số lớn nhât
//        process:


//        System.out.println("Nhập 3 số nguyên: ");
//        int num1 = scanner.nextInt();
//        int num2 = scanner.nextInt();
//        int num3 = scanner.nextInt();
//        int maximun = num1;
//        if(maximun<num2){
//            maximun=num2;
//        }
//        if(maximun<num3){
//            maximun=num3;
//        }
//        System.out.println("Số lớn nhất là: "+ maximun);


    //bài 4: Tính tiền taxi

//        input: Nhập số km (float, double)
//        output: số tiền phải trả (int)
//        process:
//        1km đầu tiên: 15000VND
//        từ km thứ 2 trở đi: 12000 VND
//        tính tiền cước taxi với số km nhập từ bàn phím
//        VD: 3km => 15000 + 2*12000
//        VD2: 1.2km => 15000 + 1*12000 (0.2 km làm tròn thành 1 km)
//      default: nhập số km>0

//        System.out.println("Nhập vào số km: ");
//        float soKm = scanner.nextFloat();
//        int taxiCost = 0;
////        0<soKm<1
//        if(0.0f<=soKm && soKm<=1.0f){
//            taxiCost=15000;
//        } else {
////            Trừ 1km đầu tiền, làm tròn lên (ceil) số km còn lại
//            int soKmConLai = (int)Math.ceil(soKm-1);
//            taxiCost = 15000+soKmConLai*12000;
//        }
//        System.out.println("Tiền taxi là: "+ taxiCost);

        //vòng lặp
//        b1: tính tổng các số từ 1 -> n
//        input: nhập so n để tính tổng từ 1 đến n
//        output: tổng các số từ 1 đến n
//        Process:
//        Dùng for để xử lý
//        for(<dk bắt đầu loop>, <dk dung loop>, <logic để dừng vòng lặp>)
//        System.out.println("Nhập số n: ");
//        int n = scanner.nextInt();
//        int sum=0;
//        for (int i = 1; i <= n; i++) {
//            sum =sum+i;
//        }
//        System.out.println("tổng là: "+sum);

// b2: tính tổng các số chẵn từ 1-> 100
    // cách 1:
//        System.out.println("Nhập số n: ");
//        int n = scanner.nextInt();
//        int sum=0;
//        for (int i = 1; i <= n; i++) {
//            if (i%2==0){
//                sum =sum+i;
//            }
//        }
//        System.out.println("tổng các số chẵn là: "+sum);

//        cách 2
//        dk bắt đầu vòng lặp: i=2
//        dk đi vào vòng lặp: i<=n
//        logic dung vong lặp: i=i+2 hoặc i+=2

//        System.out.println("Nhập số n: ");
//        int n = scanner.nextInt();
//        int sum=0;
//        for (int i = 2; i <= n; i+=2) {
//            sum += i;
//        }
//        System.out.println("tổng các số chẵn là: "+sum);

//    B3: In bảng cửu chương
//        input: Nhập n với n là bảng cửu chương thứ n
//        output: in ra bảng cửu chương thứ n
//        process:
//        for:
//        dk bắt đầu vòng lặp: i=1
//        dk đi vào vòng lặp: i<=10
//        logic: i++
        System.out.println("Nhập số bảng cửu chương: ");
        int n = scanner.nextInt();
        for (int i = 1; i <=10 ; i++) {
            System.out.println(n+"x"+i+" = "+n*i);
        }
    }
}




