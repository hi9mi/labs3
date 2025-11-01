import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

  public static double areaReturn(double a, double b) {
    return a * b;
  }

  public static void areaWithHolder(double a, double b, AreaHolder out) {
    if (out == null) throw new IllegalArgumentException("out == null");
    out.setSo(a * b);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    System.out.print("a = ");
    double a = scanner.nextDouble();
    System.out.print("b = ");
    double b = scanner.nextDouble();

    double s1 = areaReturn(a, b);
    System.out.println("A) return: " + s1);

    AreaHolder holder = new AreaHolder();
    areaWithHolder(a, b, holder);
    System.out.println("B) holder: " + holder.getSo());

    scanner.close();
  }
}

class AreaHolder {

  private double so;

  public double getSo() {
    return so;
  }

  public void setSo(double so) {
    this.so = so;
  }
}
