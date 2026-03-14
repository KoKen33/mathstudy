package officialInternalAssessment;

import java.util.Random;

public class Questions {
    private static Random rand = new Random();

    public static String generateQuestion(String selectedTopic) {
        if ("Algebra".equals(selectedTopic)) {
            return generateAlgebraQuestion();
        } else if ("Areas & Perimeters".equals(selectedTopic)) {
            return generateAreaPerimeterQuestion();
        } else {
            return "No question available";
        }
    }

    private static String generateAlgebraQuestion() {
        int a = rand.nextInt(5) + 1;   // 1–5
        int b = rand.nextInt(10) + 1;  // 1–10
        int x = rand.nextInt(10) + 1;  // solution
        int c = a * x + b;
        return a + "x + " + b + " = " + c;
    }

    private static String generateAreaPerimeterQuestion() {
        int length = rand.nextInt(10) + 1;
        int width = rand.nextInt(10) + 1;
        if (rand.nextBoolean()) {
            return "Find area of rectangle with length " + length + " and width " + width;
        } else {
            return "Find perimeter of rectangle with length " + length + " and width " + width;
        }
    }

    public static String getAnswer(String question) {
        try {
            if (question.contains("x")) {
                // Algebra: ax + b = c
                String[] parts = question.split("x \\+ ");
                int a = Integer.parseInt(parts[0].trim());
                String[] rightSide = parts[1].split("=");
                int b = Integer.parseInt(rightSide[0].trim());
                int c = Integer.parseInt(rightSide[1].trim());
                return String.valueOf((c - b) / a);
            } else if (question.contains("area")) {
                String[] nums = question.replaceAll("[^0-9 ]", "").trim().split("\\s+");
                int l = Integer.parseInt(nums[0]);
                int w = Integer.parseInt(nums[1]);
                return String.valueOf(l * w);
            } else if (question.contains("perimeter")) {
                String[] nums = question.replaceAll("[^0-9 ]", "").trim().split("\\s+");
                int l = Integer.parseInt(nums[0]);
                int w = Integer.parseInt(nums[1]);
                return String.valueOf(2 * (l + w));
            }
        } catch (Exception e) {
            return "?";
        }
        return "?";
    }
}

