import java.util.*;

public class Solution {
    public final int N;
    private final int[] rows, columns;
    private int mainDiagonal = 0, secondaryDiagonal = 0;
    public final int expectedSum;
    private final int[][] table;

    private final boolean[] values;
    private Set<Integer> set = new TreeSet<>();
    private int variablesSet = 0;
    private final int[] positionRow, positionColumn;

    private boolean frozen = false;

    public Solution(int n) {
        this.N = n;
        this.rows = new int[n];
        this.columns = new int[n];
        this.table = new int[n][n];

        this.expectedSum = n * (n * n + 1) / 2;

        this.values = new boolean[n * n];
        Arrays.fill(this.values, true);

        for (int i = 1; i <= n * n; i++) {
            set.add(i);
        }

        int p = 0;

        this.positionRow = new int[N * N];
        this.positionColumn = new int[N * N];

        for (int i = 1; i <= n; i++){
            this.positionRow[p] = i - 1;
            this.positionColumn[p] = i - 1;
            p++;
        }

        for (int i = 1; i <= n; i++) {
            if (i != n - i + 1){ // not on main diagonal
                this.positionRow[p] = i - 1;
                this.positionColumn[p] = n - i + 1 - 1;
                p++;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && j != n - i + 1){
                    this.positionRow[p] = i - 1;
                    this.positionColumn[p] = j - 1;
                    p++;
                }
            }
        }
    }

    public void frozeSolution(){
        frozen = true;
    }

    public boolean stillContains(int k){
        return values[k - 1];
    }

    public int getRowPosition(int k){
        return positionRow[k];
    }

    public int getColumnPosition(int k){
        return positionColumn[k];
    }

    public Set<Integer> getAllValues(){
        return new TreeSet<>(set);
    }

    private boolean isValidRow(int row, int value){
        return rows[row] + value <= expectedSum;
    }

    private boolean isValidColumn(int column, int value){
        return columns[column] + value <= expectedSum;
    }

    public boolean isValidMove(int i, int j, int k){
        if (frozen || table[i][j] != 0 || !stillContains(k))
            return false;

        if (!isValidRow(i, k) || !isValidColumn(j, k))
            return false;

        if (i == j)
            return mainDiagonal + k <= expectedSum;
        else if (j == N - 1 - i)
            return secondaryDiagonal + k <= expectedSum;

        return true;
    }

    public void set(int i, int j, int k){
        if (frozen)
            return;

        assert 1 <= k && k <= N * N;

        rows[i] += k;
        columns[j] += k;
        values[k - 1] = false;
        set.remove(k);
        table[i][j] = k;

        if (i == j)
            mainDiagonal += k;
        else if (j == N - 1 - i)
            secondaryDiagonal += k;

        variablesSet++;
    }

    public void unset(int i, int j){
        if (frozen)
            return;

        assert table[i][j] != 0;
        int k = table[i][j];

        rows[i] -= k;
        columns[j] -= k;
        values[k - 1] = true;
        set.add(k);
        table[i][j] = 0;

        if (i == j)
            mainDiagonal -= k;
        else if (j == N - 1 - i)
            secondaryDiagonal -= k;

        variablesSet--;
    }

    public int size(){
        return variablesSet;
    }

    public boolean isFinished(){
        return variablesSet == N * N;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(32);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(table[i][j]);

                if (j + 1 != N)
                    sb.append(' ');
            }

            sb.append('\n');
        }

        return sb.toString();
    }
}
