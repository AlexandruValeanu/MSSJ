import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream("test.in"))) {
            final int N = scanner.nextInt();
            Solution solution = new Solution(N);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int x = scanner.nextInt();

                    if (x != 0)
                        solution.set(i, j, x);
                }
            }

            Solver.solve(solution);
            System.out.println(solution);
        }
    }
}
