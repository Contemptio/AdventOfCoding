package mmxvii.dec01;

import java.util.ArrayList;
import java.util.List;

public class CaptchaMatching {

    public static void main(String[] args) {
        System.out.println(new CaptchaMatching().matchSum(args[0],
                args.length > 1 ? args[0].length() / 2 : 1));
    }

    public int matchSum(String input, int distance) {

        List<Integer> matches = new ArrayList<Integer>();

        for (int i = 0; i < input.length(); ++i) {
            if (matchesHalfway(input, i, distance)) {
                matches.add(((int) input.charAt(i)) - 48);
            }
        }
        return matches.stream().mapToInt(i -> i).sum();
    }

    private boolean matchesHalfway(String string, int pos, int distance) {
        return string.charAt(pos) == string
                .charAt((pos + distance) % string.length());
    }

}
