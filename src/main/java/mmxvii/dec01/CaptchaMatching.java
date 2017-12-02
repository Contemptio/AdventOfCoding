package mmxvii.dec01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CaptchaMatching {

    public static void main(String[] args) {
        System.out.println(new CaptchaMatching().matchSum(args[0]));
    }

    public int matchSum(String input) {

        char[] array = input.toCharArray();
        List<Integer> matches = new ArrayList<Integer>();
        for (int i = 0; i < input.length(); ++i) {
            if (matchesNext(array, i)) {
                matches.add(((int) array[i]) - 48);
            }
        }
        return sum(matches);
    }

    private int sum(Collection<Integer> ints) {
        int sum = 0;
        for (Integer i : ints) {
            sum += i;
        }
        return sum;
    }

    private boolean matchesNext(char[] string, int pos) {
        return matchesNext(string[pos],
                string[pos >= string.length - 1 ? 0 : pos + 1]);
    }

    private boolean matchesNext(char a, char b) {
        return a == b;
    }
}
