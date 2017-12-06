package mmxvii.dec03;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import util.RunUtil;
import util.StringUtil;

public class SpiralMemory {

    public static void main(String[] args) throws IOException {
        if (args.length <= 2) {
            RunUtil.error(
                    "Usage: java -cp ZKAdvent.jar mmxvii.dec01.SpiralMemory"
                            + " <number1> <number2> <function> [--printGrid]");
        }

        int n1 = 0, n2 = 0;
        try {
            n1 = Integer.parseInt(args[0]);
            n2 = Integer.parseInt(args[1]);
        } catch (NumberFormatException error) {
            RunUtil.error("Only integers are allowed...");
        }

        String flag = args[2];
        ToIntFunction<? super Integer> func = null;
        if (flag == null || flag.equals("") || !flag.equals("fibonacci")) {
            func = (number -> number + 1);
        } else {
            func = null;
        }

        Grid problem = new Grid(n1, n2, func);
        if (args.length > 3 && args[3].equals("--printGrid")) {
            System.out.println(problem);
        }

        Coords target = problem.find(n2);
        System.out.println("Manhattan distance between " + n1 + " and " + n2
                + ": " + problem.find(n1).distance(target));

        int max = problem.get(target.x, target.y + 1);

        System.out.println(
                "First number after max(" + n1 + ", " + n2 + "): " + max + ".");
    }

    private static final class Coords {
        public int x;
        public int y;

        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distance(Coords other) {
            return Math.abs(other.x - x) + Math.abs(other.y - y);
        }
    }

    private static class Grid {
        private int dim;
        private int[][] grid;
        private ToIntFunction<? super Integer> process;

        public Grid(int n1, int n2, ToIntFunction<? super Integer> func) {
            this.process = func;
            this.dim = getDim(n1, n2);
            this.grid = new int[dim][dim];
            this.process = func;
            fill();
        }

        public int get(int x, int y) {
            return grid[x][y];
        }

        private static int getDim(int n1, int n2) {
            int dim = ((int) Math.ceil((Math.sqrt(Math.max(n1, n2)))));
            return dim % 2 == 0 ? dim + 1 : dim;
        }

        public Coords find(int num) {

            for (int i = 0; i < dim; ++i) {
                for (int j = 0; j < dim; ++j) {
                    if (grid[i][j] == num) {
                        return new Coords(i, j);
                    }
                }
            }
            return new Coords(-1, -1);
        }

        private enum Direction {
            DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1), UP(-1, 0);

            public int dx;
            public int dy;

            private Direction(int dx, int dy) {
                this.dx = dx;
                this.dy = dy;
            }
        }

        private static class Arrow {

            protected Direction dir;
            protected int[][] grid;
            public Coords pos;
            protected int dim;
            private int numPos;
            private List<Integer> numbers;

            public Arrow(int[][] grid,
                    ToIntFunction<? super Integer> numberGenerator) {

                this.grid = grid;
                this.dim = grid.length;

                if (numberGenerator != null) {
                    this.numbers = IntStream.rangeClosed(0, dim * dim - 1)
                            .boxed().collect(Collectors.toList()).stream()
                            .mapToInt(numberGenerator).boxed()
                            .collect(Collectors.toList());
                }
                this.numPos = 0;

                pos = new Coords(dim / 2, dim / 2 + 1);
                dir = Direction.LEFT;
            }

            public boolean canAdvance() {
                int x = pos.x + dir.dx;
                int y = pos.y + dir.dy;

                return x >= 0 && y >= 0 && x < dim && y < dim
                        && grid[x][y] == 0;
            }

            public void advance() {
                grid[pos.x][pos.y] = nextNum();
                pos.x += dir.dx;
                pos.y += dir.dy;
            }

            public void rotate() {
                switch (dir) {
                    case DOWN:
                        dir = Direction.LEFT;
                        break;
                    case LEFT:
                        dir = Direction.UP;
                        break;
                    case RIGHT:
                        dir = Direction.DOWN;
                        break;
                    case UP:
                        dir = Direction.RIGHT;
                        break;

                }
            }

            public boolean hasNumbers() {
                return numbers.size() > 1;
            }

            private int nextNum() {
                return numbers.remove(numPos--);
            }

        }

        private static class NeighbourArrow extends Arrow {

            public NeighbourArrow(int[][] grid) {
                super(grid, null);
                fill();
            }

            public boolean canAdvance() {
                return false;
            }

            private void fill() {
                int stepsToTake = 1;
                int steps = 0;
                dir = Direction.RIGHT;
                grid[pos.x][pos.y] = 1;

                while (true) {
                    while (steps++ < stepsToTake) {
                        advance();
                    }

                    steps = 0;
                    if (dir == Direction.RIGHT) {
                        stepsToTake++;
                    }

                    rotate();
                }
            }

            public void advance() {
                pos.x += dir.dx;
                pos.y += dir.dy;

                grid[pos.x][pos.y] = neighbourSum(pos);
            }

            private int neighbourSum(Coords pos) {
                return get(pos.x + 1, pos.y) + get(pos.x, pos.y + 1)
                        + get(pos.x - 1, pos.y) + get(pos.x, pos.y - 1);
            }

            private int get(int x, int y) {
                if (x < 0 || y < 0 || x >= dim || y >= dim) {
                    return 0;
                }
                return grid[x][y];
            }

            public void rotate() {
                switch (dir) {
                    case DOWN:
                        dir = Direction.LEFT;
                        break;
                    case LEFT:
                        dir = Direction.UP;
                        break;
                    case RIGHT:
                        dir = Direction.DOWN;
                        break;
                    case UP:
                        dir = Direction.RIGHT;
                        break;

                }
            }

            public boolean hasNumbers() {
                return false;
            }

            @Override
            public String toString() {
                return super.toString();
            }
        }

        private void fill() {

            Arrow arrow = this.process == null ? new NeighbourArrow(grid)
                    : new Arrow(grid, this.process);

            while (arrow.hasNumbers()) {

                while (arrow.canAdvance()) {
                    arrow.advance();
                }
                arrow.rotate();
            }
            grid[dim / 2][dim / 2] = 1;
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            int maxLength = Integer.toString(dim * dim).length();

            for (int i = 0; i < dim; ++i) {
                string.append("|");
                String prefix = "";
                for (int j = 0; j < dim; ++j) {
                    string.append(prefix + StringUtil
                            .format(Integer.toString(grid[i][j]), maxLength));
                    prefix = " ";
                }
                string.append("|\n");
            }

            return string.toString();
        }
    }
}
