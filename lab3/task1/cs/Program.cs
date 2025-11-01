public static class Program
{
    public static double AreaReturn(double a, double b) => a * b;

    public static void AreaOut(double a, double b, out double so) => so = a * b;

    public static void Main()
    {
        Console.Write("a = ");
        double a = double.Parse(Console.ReadLine() ?? "0");
        Console.Write("b = ");
        double b = double.Parse(Console.ReadLine() ?? "0");


        double s1 = AreaReturn(a, b);
        Console.WriteLine($"A) return: {s1}");

        AreaOut(a, b, out double s2);
        Console.WriteLine($"B) out: {s2}");
    }
}
