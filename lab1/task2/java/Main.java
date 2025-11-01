import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
      System.out.print("x (< -1): ");
      double x = scanner.nextDouble();
      System.out.print("epsilon (> 0): ");
      double eps = scanner.nextDouble();

      if (!(x < -1) || !(eps > 0)) {
        System.out.println(
          "Некорректные данные: требуется x < -1 и epsilon > 0"
        );
        return;
      }

      double sum = -Math.PI / 2.0;
      double term = -1.0 / x;
      long n = 0;
      long maxIter = 1_000_000;

      while (Math.abs(term) >= eps && n < maxIter) {
        sum += term;
        term = term * (-(2.0 * n + 1.0) / ((2.0 * n + 3.0) * x * x));
        n++;
      }

      System.out.printf("arctan(x) по ряду = %.12f%n", sum);
      System.out.printf("Math.atan(x)       = %.12f%n", Math.atan(x));
      System.out.printf(
        "Членов суммировано = %d, |последний член| < %.3g%n",
        n,
        eps
      );
    }
  }
}
