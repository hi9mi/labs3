import java.util.Scanner;

public class Main {
  static boolean isPalindrome(String s) {
    return s.equals(new StringBuilder(s).reverse().toString());
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("Введите четырёхзначное число (или ноль для выхода): ");
      String s = scanner.nextLine().trim();

      if (s.equals("0")) {
        break;
      }

      if (!s.matches("\\d{4}")) {
        System.out.println("Число не является четырёхзначным");
        continue;
      }

      System.out.println(isPalindrome(s) ? "Число является палиндромом" : "Число не является палиндромом");
    }
    scanner.close();
  }
}
