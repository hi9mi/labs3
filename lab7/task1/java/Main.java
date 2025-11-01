import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    System.out.print("Введите строку текста: ");
    String input = scanner.nextLine();

    try (FileWriter fw = new FileWriter("input.txt")) {
      fw.write(input);
    } catch (IOException e) {
      System.out.println("Ошибка записи в файл");
      scanner.close();
      return;
    }

    String line = "";
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      line = br.readLine();
    } catch (IOException e) {
      System.out.println("Ошибка чтения из файла");
      scanner.close();
      return;
    }

    System.out.println("\nСчитанная строка из файла:");
    System.out.println(line);

    System.out.println(
      "\nСлова, начинающиеся и заканчивающиеся на одну букву:"
    );
    boolean found = false;

    for (String word : line.split("\\s+")) {
      word = word.replaceAll("^[^\\p{L}]+|[^\\p{L}]+$", ""); // remove punctuation
      if (word.length() > 1) {
        char first = Character.toLowerCase(word.charAt(0));
        char last = Character.toLowerCase(word.charAt(word.length() - 1));
        if (first == last) {
          System.out.println(word);
          found = true;
        }
      }
    }

    if (!found) System.out.println("Таких слов не найдено.");

    scanner.close();
  }
}
