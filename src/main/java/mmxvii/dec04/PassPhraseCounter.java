package mmxvii.dec04;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import util.FileUtil;
import util.RunUtil;
import util.StringUtil;

public class PassPhraseCounter {
    private List<BiFunction<List<String>, String, Boolean>> checks;
    private List<List<String>> invalid;
    private List<List<String>> valid;

    public PassPhraseCounter(List<List<String>> phrases, int part) {
        this.checks = new ArrayList<BiFunction<List<String>, String, Boolean>>();
        if (part >= 3 || part <= 0) {
            throw new IllegalArgumentException("There's only two parts, not " + part + "!");

        }

        if (part >= 1) {
            this.checks.add(PassPhraseCounter::correctFrequency);
            if (part >= 2) {
                this.checks.add(PassPhraseCounter::hasNoAnagrams);
            }
        }
        Map<Boolean, List<List<String>>> partition = phrases.stream()
                .collect(Collectors.groupingBy(list -> isValid(list)));
        this.invalid = partition.get(false);
        this.valid = partition.get(true);
    }

    private boolean isValid(List<String> phrase) {
        for (BiFunction<List<String>, String, Boolean> check : checks) {
            for (String word : phrase) {
                if (!check.apply(phrase, word)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean correctFrequency(List<String> phrase, String word) {
        int count = 0;
        for (String otherWord : phrase) {
            if (word.equals(otherWord)) {
                if (++count >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasNoAnagrams(List<String> phrase, String word) {
        for (String otherWord : phrase) {
            if (word.equals(otherWord)) {
                continue;
            }
            if (StringUtil.isAnagramOf(word, otherWord)) {
                return false;
            }
        }
        return true;
    }

    public int numberOfInvalid() {
        return invalid.size();
    }

    public int numberOfValid() {
        return valid.size();
    }

    public static void main(String[] args) throws IOException {
        if (args.length <= 1) {
            RunUtil.error("Missing arguments: <fileName> <part>");
        }

        int part = 0;
        try {
            part = Integer.parseInt(args[1]);
        } catch (NumberFormatException error) {
            RunUtil.error("Second argument must be an integer: " + args[1] + ".");
        }

        PassPhraseCounter counter = new PassPhraseCounter(FileUtil.fileAsMatrix(new File(args[0])), part);
        System.out.println(counter.numberOfValid());
    }
}
