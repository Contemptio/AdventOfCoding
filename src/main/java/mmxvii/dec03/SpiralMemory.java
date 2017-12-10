package mmxvii.dec03;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import util.Coords;
import util.RunUtil;
import util.StringUtil;

public class SpiralMemory {

    public static void main(String[] args) throws IOException {
        if (args.length <= 2) {
            RunUtil.error("Usage: java -cp ZKAdvent.jar mmxvii.dec01.SpiralMemory"
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

        Coords target = problem.find(265149);
        System.out.println(problem);
        System.out
                .println("Manhattan distance between " + n1 + " and " + n2 + ": " + problem.find(n1).distance(target));

        System.out.println("Coordinates of problem number " + 265149 + ": " + target);
        System.out.println("Next number: " + problem.get(target.x, target.y + 1));
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

            public Direction opposite() {
                switch (this) {
                    case DOWN:
                        return UP;
                    case LEFT:
                        return RIGHT;
                    case RIGHT:
                        return LEFT;
                    case UP:
                        return DOWN;
                    default:
                        throw new UnsupportedOperationException("No such direction.1");
                }
            }
        }

        private static class Arrow {

            protected Direction dir;
            protected int[][] grid;
            public Coords pos;
            protected int dim;
            private int numPos;
            private List<Integer> numbers;

            public Arrow(int[][] grid, ToIntFunction<? super Integer> numberGenerator) {

                this.grid = grid;
                this.dim = grid.length;

                if (numberGenerator != null) {
                    this.numbers = IntStream.rangeClosed(0, dim * dim - 1).boxed().collect(Collectors.toList()).stream()
                            .mapToInt(numberGenerator).boxed().collect(Collectors.toList());
                }
                this.numPos = 0;

                pos = new Coords(dim / 2 + 1, dim / 2 + 1);
                dir = Direction.DOWN;
            }

            public boolean canAdvance() {
                int x = pos.x;
                int y = pos.y;

                return x != dim - 1 || y != dim - 1;
            }

            public void advance() {
                pos.x += dir.dx;
                pos.y += dir.dy;
                grid[pos.x][pos.y] = nextNum();
            }

            public void rotate() {
                switch (dir) {
                    case DOWN:
                        dir = Direction.RIGHT;
                        break;
                    case LEFT:
                        dir = Direction.DOWN;
                        break;
                    case RIGHT:
                        dir = Direction.UP;
                        break;
                    case UP:
                        dir = Direction.LEFT;
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

        private class NeighbourArrow extends Arrow {

            public NeighbourArrow(int[][] grid) {
                super(grid, null);
                fill();
            }

            private void fill() {
                pos.x = dim / 2;
                pos.y = dim / 2;
                grid[pos.x][pos.y] = 1;

                while (canAdvance()) {
                    if (shouldRotate()) {
                        rotate();
                    }
                    advance();
                }
            }

            private boolean outOfBounds(int x, int y) {
                return x >= dim || y >= dim;
            }

            private boolean shouldRotate() {
                int x = pos.x;
                int y = pos.y;

                if (outOfBounds(pos.x + dir.dx, pos.y + dir.dy)) {
                    return true;
                }

                Set<Coords> coordsToCheck = new HashSet<Coords>();
                coordsToCheck.add(new Coords(x + 1, y));
                coordsToCheck.add(new Coords(x - 1, y));
                coordsToCheck.add(new Coords(x, y + 1));
                coordsToCheck.add(new Coords(x, y - 1));

                Direction opposite = dir.opposite();
                Coords anti = new Coords(x + opposite.dx, y + opposite.dy);

                for (Coords coords : coordsToCheck) {
                    if (coords.equals(anti)) {
                        continue;
                    }
                    if (get(coords) != 0) {
                        return false;
                    }
                }
                return true;
            }

            public void advance() {
                pos.x += dir.dx;
                pos.y += dir.dy;

                grid[pos.x][pos.y] = neighbourSum(pos);
            }

            private int neighbourSum(Coords pos) {
                int sum = 0;
                int x = pos.x;
                int y = pos.y;

                int[] ints = { -1, 0, 1 };
                for (int i = 0; i < ints.length; ++i) {
                    for (int j = 0; j < ints.length; ++j) {
                        sum += get(x + ints[i], y + ints[j]);
                    }
                }
                return sum;
            }

            private int get(int x, int y) {
                if (x < 0 || y < 0 || x >= dim || y >= dim) {
                    return 0;
                }
                return grid[x][y];
            }

            private int get(Coords coords) {
                return get(coords.x, coords.y);
            }

            public void rotate() {
                switch (dir) {
                    case DOWN:
                        dir = Direction.RIGHT;
                        break;
                    case LEFT:
                        dir = Direction.DOWN;
                        break;
                    case RIGHT:
                        dir = Direction.UP;
                        break;
                    case UP:
                        dir = Direction.LEFT;
                        break;

                }
            }

            public boolean hasNumbers() {
                return false;
            }

            @Override
            public String toString() {
                return Grid.this.toString();
            }
        }

        private void fill() {

            Arrow arrow = this.process == null ? new NeighbourArrow(grid) : new Arrow(grid, this.process);
            if (this.process == null) {
                return;
            }

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
                    string.append(prefix + StringUtil.format(Integer.toString(grid[i][j]), maxLength));
                    prefix = " ";
                }
                string.append("|\n");
            }

            return string.toString();
        }
    }
}
