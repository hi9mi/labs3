import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    final int DAYS = 30;
    final int DEFAULT_SWIMMING_TEMP = 22;

    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    int swimmingTemp = readSwimmingTemp(scanner, DEFAULT_SWIMMING_TEMP);
    int[] temps = readTemperatures(scanner, DAYS);

    int suitableDays = countSuitableDays(temps, swimmingTemp);
    int maxDecade = findMaxDecade(temps, swimmingTemp);

    printResults(suitableDays, maxDecade);

    scanner.close();
  }

  public static int readSwimmingTemp(Scanner scanner, int defaultTemp) {
    System.out.print(
      "Введите температуру воды, пригодную для купания (по умолчанию " +
        defaultTemp +
        "°C): "
    );
    String input = scanner.nextLine().trim();
    return input.isEmpty() ? defaultTemp : Integer.parseInt(input);
  }

  public static int[] readTemperatures(Scanner scanner, int days) {
    int[] temps = new int[days];
    System.out.println("\nВведите температуру воды за 30 дней сентября:");
    int filled = 0;
    while (filled < days) {
      System.out.print("День " + (filled + 1) + ": ");
      String inputDayTemp = scanner.nextLine().trim();
      if (inputDayTemp.isEmpty()) continue;
      temps[filled] = Integer.parseInt(inputDayTemp);
      filled++;
    }
    return temps;
  }

  public static int countSuitableDays(int[] temps, int swimmingTemp) {
    int count = 0;
    for (int temp : temps) {
      if (temp >= swimmingTemp) count++;
    }
    return count;
  }

  public static int findMaxDecade(int[] temps, int swimmingTemp) {
    int[] decadeCount = new int[3]; // 0:1–10, 1:11–20, 2:21–30
    for (int i = 0; i < temps.length; i++) {
      if (temps[i] >= swimmingTemp) {
        if (i < 10) decadeCount[0]++;
        else if (i < 20) decadeCount[1]++;
        else decadeCount[2]++;
      }
    }
    int maxIndex = 0;
    for (int i = 1; i < 3; i++) if (
      decadeCount[i] > decadeCount[maxIndex]
    ) maxIndex = i;
    return maxIndex + 1;
  }

  public static void printResults(int suitableDays, int maxDecade) {
    System.out.println("\nВсего дней, пригодных для купания: " + suitableDays);
    System.out.println(
      "Больше всего тёплых дней было в " + maxDecade + " декаде сентября"
    );
  }
}
