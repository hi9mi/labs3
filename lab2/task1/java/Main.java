import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    final int DAYS = 30;
    final int DEFAULT_SWIMMING_TEMP = 22;
    int[] temps = new int[DAYS];

    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    System.out.print(
      "Введите температуру воды, пригодную для купания (по умолчанию 22°C): "
    );
    String inputTemp = scanner.nextLine().trim();
    int swimmingTemp = inputTemp.isEmpty()
      ? DEFAULT_SWIMMING_TEMP
      : Integer.parseInt(inputTemp);

    System.out.println("\nВведите температуру воды за 30 дней сентября:");
    int filled = 0;
    while (filled < DAYS) {
      System.out.print("День " + (filled + 1) + ": ");
      String inputDayTemp = scanner.nextLine().trim();
      if (inputDayTemp.isEmpty()) continue;
      temps[filled] = Integer.parseInt(inputDayTemp);
      filled++;
    }

    int suitableDays = 0;
    int[] decadeCount = new int[3]; // 0:1–10, 1:11–20, 2:21–30

    for (int i = 0; i < DAYS; i++) {
      if (temps[i] >= swimmingTemp) {
        suitableDays++;
        if (i < 10) decadeCount[0]++;
        else if (i < 20) decadeCount[1]++;
        else decadeCount[2]++;
      }
    }

    System.out.println("\nВсего дней, пригодных для купания: " + suitableDays);

    int maxIndex = 0;
    for (int i = 1; i < 3; i++) {
      if (decadeCount[i] > decadeCount[maxIndex]) maxIndex = i;
    }

    System.out.println(
      "Больше всего тёплых дней было в " + (maxIndex + 1) + " декаде сентября"
    );

    scanner.close();
  }
}
