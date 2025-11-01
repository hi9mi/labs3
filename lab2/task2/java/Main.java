import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    int K = readPositiveInt(scanner, "Введите K (кол-во вагонов): ");
    int M = readPositiveInt(scanner, "Введите M (кол-во мест в вагоне): ");
    int[][] sold = new int[K][M];

    int N = readNonNegativeInt(
      scanner,
      "Введите N (кол-во проданных билетов): "
    );
    readSoldPairs(scanner, sold, N);

    printSummary(sold);
    queryLoop(scanner, sold);
  }

  private static int readPositiveInt(Scanner sc, String prompt) {
    while (true) {
      System.out.print(prompt);
      if (sc.hasNextInt()) {
        int v = sc.nextInt();
        if (v > 0) return v;
      } else sc.next();
      System.out.println("Введите положительное целое число");
    }
  }

  private static int readNonNegativeInt(Scanner sc, String prompt) {
    while (true) {
      System.out.print(prompt);
      if (sc.hasNextInt()) {
        int v = sc.nextInt();
        if (v >= 0) return v;
      } else sc.next();
      System.out.println("Введите неотрицательное целое число");
    }
  }

  private static void readSoldPairs(Scanner sc, int[][] sold, int N) {
    int K = sold.length,
      M = sold[0].length;
    System.out.println("Введите пары <вагон место> (нумерация с 1):");
    for (int i = 0; i < N; i++) {
      int car = sc.nextInt(),
        seat = sc.nextInt();
      if (car < 1 || car > K || seat < 1 || seat > M) {
        System.out.printf(
          "Диапазоны: вагон 1..%d, место 1..%d. Повторите\n",
          K,
          M
        );
        i--;
        continue;
      }
      sold[car - 1][seat - 1] = 1;
    }
  }

  private static int soldInCar(int[][] sold, int carIdx) {
    int sum = 0;
    for (int x : sold[carIdx]) sum += x;
    return sum;
  }

  private static int freeInCar(int[][] sold, int carIdx) {
    return sold[carIdx].length - soldInCar(sold, carIdx);
  }

  private static void printSummary(int[][] sold) {
    int K = sold.length;
    int M = sold[0].length;
    int totalFree = 0;
    System.out.println("\nСводка по вагонам:");
    for (int car = 0; car < K; car++) {
      int free = freeInCar(sold, car);
      totalFree += free;
      System.out.printf(
        "Вагон %d: свободных %d из %d — %s%n",
        car + 1,
        free,
        M,
        free > 0 ? "ЕСТЬ" : "НЕТ"
      );
    }
    System.out.println("Итого свободных мест по поезду: " + totalFree + "\n");
  }

  private static void queryLoop(Scanner scanner, int[][] sold) {
    int K = sold.length;
    System.out.println("Проверка вагона (введите номер вагона, 0 — выход):");
    while (true) {
      System.out.print("Вагон #: ");
      if (!scanner.hasNextInt()) {
        scanner.next();
        continue;
      }
      int q = scanner.nextInt();
      if (q == 0) break;
      if (q < 1 || q > K) {
        System.out.printf("Номер вагона должен быть 1..%d%n", K);
        continue;
      }
      int free = freeInCar(sold, q - 1);
      System.out.printf(
        "Вагон %d: свободных мест %d — %s%n",
        q,
        free,
        free > 0 ? "ЕСТЬ" : "НЕТ"
      );
    }
  }
}
