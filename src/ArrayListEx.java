import java.util.Scanner;

public class ArrayList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập so lương phan tu: ");
        int n = scanner.nextInt();
        java.util.ArrayList<Integer> numbers= new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            numbers.add((scanner.nextInt()));
        }
    }
}
