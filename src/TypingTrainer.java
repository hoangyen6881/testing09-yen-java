import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;

/**
 * Simple console-based Typing Trainer in Java
 * Features:
 *  - Quick Practice: type one random prompt and get instant feedback (accuracy, WPM, CPM).
 *  - Timed Challenge: 60-second (or custom) challenge with multiple prompts.
 *  - Colored per-character diff (green = correct, red = wrong, gray = extra chars).
 *  - Tracks personal best (highest WPM) in a local file (typing_trainer.properties).
 *
 * Works on terminals that support ANSI colors.
 */
public class TestG {
    private static final String HIGHSCORE_FILE = "typing_trainer.properties";

    // ANSI colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String GRAY = "\u001B[90m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BOLD = "\u001B[1m";

    private static final Random RNG = new Random();

    private static final List<String> WORD_BANK = Arrays.asList(
            "The quick brown fox jumps over the lazy dog.",
            "Typing practice improves speed and accuracy.",
            "Focus on rhythm, not on rushing.",
            "Consistent effort beats occasional intensity.",
            "Java runs on a virtual machine across platforms.",
            "Stay relaxed and keep your hands light on the keys.",
            "Shortcuts save time; learn them well.",
            "Errors happen; correct, reset, and continue.",
            "Discipline is choosing what you want most.",
            "Refactor your habits like you refactor your code.",
            "Clean code reads like well-written prose.",
            "Measure progress by accuracy and consistency first.",
            "Practice makes permanent; perfect practice makes perfect.",
            "Start slow, then gradually increase your pace.",
            "Great developers test early and often.",
            "Whitespace can be as important as words.",
            "Small steps forward build big momentum.",
            "Keep calm and type on.",
            "Typing is a skill you can train every day.",
            "Breathe, focus, and trust your muscle memory."
    );

    public static void main(String[] args) throws IOException {
        new TypingTrainer().run();
    }

    private void run() throws IOException {
        clearScreen();
        printHeader();
        Properties props = loadOrCreateHighscore();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println();
                System.out.println(CYAN + "Menu" + RESET);
                System.out.println("1) Quick practice (one prompt)");
                System.out.println("2) 60-second challenge");
                System.out.println("3) Custom timed challenge");
                System.out.println("4) Reset high score");
                System.out.println("0) Exit");
                System.out.print(YELLOW + "Choose: " + RESET);
                String choice = br.readLine();
                if (choice == null) break;
                switch (choice.trim()) {
                    case "1":
                        quickPractice(br, props);
                        break;
                    case "2":
                        timedChallenge(br, props, Duration.ofSeconds(60));
                        break;
                    case "3":
                        customTimedChallenge(br, props);
                        break;
                    case "4":
                        resetHighScore(props);
                        break;
                    case "0":
                        System.out.println("Goodbye! Keep practicing ✨");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private void quickPractice(BufferedReader br, Properties props) throws IOException {
        clearScreen();
        printHeader();
        String prompt = randomPrompt();
        System.out.println(BOLD + "Prompt:" + RESET);
        System.out.println(prompt);
        System.out.println();
        System.out.println(GRAY + "Start typing and press Enter when done..." + RESET);

        long start = System.nanoTime();
        String typed = br.readLine();
        long end = System.nanoTime();

        if (typed == null) return;

        Result r = evaluate(prompt, typed, start, end);
        showResult(r, true);
        updateHighScoreIfNeeded(props, r.wpm);
        pause(br);
    }

    private void timedChallenge(BufferedReader br, Properties props, Duration duration) throws IOException {
        clearScreen();
        printHeader();
        System.out.println(CYAN + "Timed Challenge: " + duration.getSeconds() + " seconds" + RESET);
        System.out.println(GRAY + "Type as many prompts as you can. Press Enter after each line." + RESET);
        System.out.println();
        countdown(3);

        long deadline = System.nanoTime() + duration.toNanos();
        long totalChars = 0;
        long totalCorrect = 0;
        long totalTyped = 0;
        long sessionStart = System.nanoTime();
        int completed = 0;

        while (System.nanoTime() < deadline) {
            String prompt = randomPrompt();
            System.out.println(BOLD + "Prompt:" + RESET);
            System.out.println(prompt);
            System.out.print(YELLOW + "> " + RESET);

            long start = System.nanoTime();
            String typed = readLineWithTimeout(br, deadline);
            long end = System.nanoTime();

            if (typed == null) {
                // time ran out mid-line
                break;
            }

            Result r = evaluate(prompt, typed, start, end);
            totalChars += prompt.length();
            totalCorrect += r.correctChars;
            totalTyped += r.typedChars;
            completed++;

            System.out.println(compareColored(prompt, typed));
            System.out.printf("Accuracy: %.1f%% | WPM (this line): %.1f | CPM: %.1f\n", r.accuracy * 100, r.wpm, r.cpm);
            System.out.println();
        }

        long sessionEnd = System.nanoTime();
        double minutes = (sessionEnd - sessionStart) / 1_000_000_000.0 / 60.0;
        double wpm = (totalTyped / 5.0) / Math.max(1e-9, minutes);
        double cpm = (totalTyped) / Math.max(1e-9, minutes);
        double accuracy = totalTyped == 0 ? 0 : (double) totalCorrect / totalTyped;

        System.out.println(BOLD + "— Session Summary —" + RESET);
        System.out.printf("Prompts completed: %d\n", completed);
        System.out.printf("Total typed: %d chars\n", totalTyped);
        System.out.printf("WPM: %.1f | CPM: %.1f | Accuracy: %.1f%%\n", wpm, cpm, accuracy * 100);
        updateHighScoreIfNeeded(props, wpm);
        pause(br);
    }

    private void customTimedChallenge(BufferedReader br, Properties props) throws IOException {
        System.out.print("Enter duration in seconds (e.g., 90): ");
        String s = br.readLine();
        int secs = 60;
        try { secs = Math.max(15, Integer.parseInt(s.trim())); } catch (Exception ignored) {}
        timedChallenge(br, props, Duration.ofSeconds(secs));
    }

    private String readLineWithTimeout(BufferedReader br, long deadlineNanos) throws IOException {
        // Basic polling loop for console input without advanced terminal libs.
        // If time runs out before a line is entered, returns null.
        while (System.nanoTime() < deadlineNanos) {
            if (br.ready()) {
                return br.readLine();
            }
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
        }
        return null; // timeout
    }

    private void resetHighScore(Properties props) throws IOException {
        props.setProperty("best_wpm", "0.0");
        saveHighScore(props);
        System.out.println("High score reset to 0.0 WPM.");
    }

    private void pause(BufferedReader br) throws IOException {
        System.out.println(GRAY + "Press Enter to return to menu..." + RESET);
        br.readLine();
        clearScreen();
        printHeader();
    }

    private void countdown(int n) {
        for (int i = n; i >= 1; i--) {
            System.out.println(YELLOW + "Starting in " + i + "..." + RESET);
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
        }
    }

    private static class Result {
        final long elapsedNanos;
        final int correctChars;
        final int typedChars;
        final double accuracy;
        final double wpm;
        final double cpm;

        Result(long elapsedNanos, int correctChars, int typedChars) {
            this.elapsedNanos = elapsedNanos;
            this.correctChars = correctChars;
            this.typedChars = typedChars;
            double minutes = Math.max(1e-9, elapsedNanos / 1_000_000_000.0 / 60.0);
            this.cpm = typedChars / minutes;
            this.wpm = (typedChars / 5.0) / minutes;
            this.accuracy = typedChars == 0 ? 0 : (double) correctChars / typedChars;
        }
    }

    private Result evaluate(String expected, String actual, long startNanos, long endNanos) {
        int correct = 0;
        int typed = actual.length();
        int len = Math.min(expected.length(), actual.length());
        for (int i = 0; i < len; i++) {
            if (expected.charAt(i) == actual.charAt(i)) correct++;
        }
        return new Result(endNanos - startNanos, correct, typed);
    }

    private void showResult(Result r, boolean showDiffHeader) {
        if (showDiffHeader) {
            System.out.println();
            System.out.println(BOLD + "Your input vs prompt:" + RESET);
        }
        System.out.println(compareColoredSummary());
        System.out.printf("Accuracy: %.1f%% | WPM: %.1f | CPM: %.1f\n", r.accuracy * 100, r.wpm, r.cpm);
    }

    private String compareColored(String expected, String actual) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < expected.length() || i < actual.length()) {
            char e = i < expected.length() ? expected.charAt(i) : 0;
            char a = i < actual.length() ? actual.charAt(i) : 0;
            if (i < expected.length() && i < actual.length()) {
                if (e == a) sb.append(GREEN).append(a).append(RESET);
                else sb.append(RED).append(a).append(RESET);
            } else if (i < actual.length()) {
                // extra chars
                sb.append(GRAY).append(a).append(RESET);
            } else {
                // missing expected char -> show underscore placeholder
                sb.append(GRAY).append('_').append(RESET);
            }
            i++;
        }
        return sb.toString();
    }

    private String compareColoredSummary() {
        return GRAY + "Green: correct, Red: incorrect, Gray: extra/missing" + RESET;
    }

    private void updateHighScoreIfNeeded(Properties props, double wpm) throws IOException {
        double best = Double.parseDouble(props.getProperty("best_wpm", "0.0"));
        if (wpm > best) {
            props.setProperty("best_wpm", String.format(Locale.US, "%.1f", wpm));
            saveHighScore(props);
            System.out.println(CYAN + BOLD + "New personal best! " + String.format(Locale.US, "%.1f WPM", wpm) + RESET);
        } else {
            System.out.println("Personal best remains: " + best + " WPM");
        }
    }

    private Properties loadOrCreateHighscore() throws IOException {
        Properties props = new Properties();
        Path p = Paths.get(HIGHSCORE_FILE);
        if (Files.exists(p)) {
            try (InputStream in = Files.newInputStream(p)) {
                props.load(in);
            }
        } else {
            props.setProperty("best_wpm", "0.0");
            saveHighScore(props);
        }
        System.out.println("Your best WPM: " + props.getProperty("best_wpm"));
        return props;
    }

    private void saveHighScore(Properties props) throws IOException {
        try (OutputStream out = Files.newOutputStream(Paths.get(HIGHSCORE_FILE))) {
            props.store(out, "Typing Trainer High Score");
        }
    }

    private void printHeader() {
        System.out.println(BOLD + "=== Typing Trainer ===" + RESET);
        System.out.println(GRAY + "Improve speed and accuracy with short sessions." + RESET);
        System.out.println();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String randomPrompt() {
        return WORD_BANK.get(RNG.nextInt(WORD_BANK.size()));
    }
}
