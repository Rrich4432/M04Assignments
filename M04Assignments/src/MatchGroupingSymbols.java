import java.io.*;
import java.util.LinkedList;
import java.nio.file.Files;
import java.nio.file.FileSystems;

public class MatchGroupingSymbols {
    public static void main(String[] args) throws IOException {
        validateArgs(args);
        String fileName = args[0];
        String fileData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(fileName)));
        String result = isGroupedCorrectly(fileData) ? "has" : "does not have";
        System.out.println("The file " + result + " the correct pairs of grouping symbols");
    }

    private static boolean isCloser(char ch) {
        return ch == ')' || ch == ']' || ch == '}';
    }

    private static boolean isGroupedCorrectly(String fileData) {
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < fileData.length(); i++) {
            char character = fileData.charAt(i);
            if (isOpener(character)) {
                stack.push(character);
            } else if (isCloser(character)) {
                if (stack.isEmpty()) return false;
                if (isMatch(stack.peek(), character)) {
                    stack.pop();
                } else return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatch(char opener, char closer) {
        return (opener == '(' && closer == ')') ||
               (opener == '[' && closer == ']') ||
               (opener == '{' && closer == '}');
    }

    private static boolean isOpener(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    private static void validateArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments");
            System.exit(0);
        }
    }
}
