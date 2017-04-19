public class Solver {
    private static boolean stopped;

    private Solver(){
    }

    private static void backtracking(int step, Solution solution){
        if (stopped)
            return;

        int nn = solution.N * solution.N;

        if (solution.isFinished()){
            stopped = true;
            solution.frozeSolution();
            return;
        }

        for (int k: solution.getAllValues()) {
            for (int pos = 0; pos < nn; pos++) {
                int i = solution.getRowPosition(pos);
                int j = solution.getColumnPosition(pos);

                if (solution.isValidMove(i, j, k)) {
                    solution.set(i, j, k);
                    backtracking(step + 1, solution);
                    solution.unset(i, j);
                }
            }
        }
    }

    public static void solve(Solution solution){
        stopped = false;
        backtracking(0, solution);
    }
}
