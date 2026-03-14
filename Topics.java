package officialInternalAssessment;

import java.util.HashMap;
import java.util.Map;

public class Topics {

    public static class Topic {
        private String explanation;
        private String examples;
        private String examples2;

        public Topic(String explanation, String examples) {
            this.explanation = explanation;
            this.examples = examples;
        }

        public String getExplanation() {
            return explanation;
        }

        public String getExamples() {
            return examples;
        }
    }

    private static Map<String, Topic> topics = new HashMap<>();

    static {
        topics.put("Algebra", new Topic(
                "Algebra is a part of mathematics that helps us work with numbers we do not know yet. Instead of always using exact numbers, algebra uses letters, called variables, to represent unknown values. The most common variable you will see is x, but any letter can be used. Algebra allows us to write and solve problems in a clear and organized way.\n"
                + "\n"
                + "An expression is a combination of numbers, variables, and operations, such as 2x + 3. An equation is a mathematical statement that shows two expressions are equal, such as 2x + 3 = 7. The goal of algebra is often to find the value of the variable that makes the equation true. This process is called solving the equation.\n"
                + "\n"
                + "To solve an equation, we use opposite operations to isolate the variable. For example, if an equation has +3, we subtract 3 from both sides. If a variable is multiplied by a number, we divide both sides by that number. Whatever you do to one side of the equation, you must do to the other side to keep the balance.\n"
                + "\n"
                + "Algebra is useful in everyday life. It helps us solve problems involving money, time, distance, and patterns. Learning algebra builds problem-solving skills and prepares you for more advanced math topics in the future.",
                "Example 1: Solve 2x + 3 = 7\n\nExample 2: Expand (x + 2)(x + 3)"
        ));

        topics.put("Areas & Perimeters", new Topic(
                "Area and perimeter are two important measurements used to describe shapes. Although they are related, they measure different things. Area tells us how much space is inside a shape, while perimeter tells us the total distance around the outside of a shape.\n"
                + "\n"
                + "The area of a shape is measured in square units, such as square centimeters or square meters. For a rectangle, the area can be found using the formula:\n"
                + "\n"
                + "Area = length × width\n"
                + "\n"
                + "For example, if a rectangle has a length of 5 units and a width of 4 units, its area is 20 square units. This means the rectangle covers 20 unit squares.\n"
                + "\n"
                + "Perimeter is measured in units, such as centimeters or meters. It is found by adding up all the sides of a shape. For a rectangle, the formula is:\n"
                + "\n"
                + "Perimeter = 2 × (length + width)\n"
                + "\n"
                + "Using the same rectangle, the perimeter would be 2 × (5 + 4) = 18 units.\n"
                + "\n"
                + "Understanding area and perimeter is useful in real life. We use area when calculating how much paint is needed for a wall or how much carpet is needed for a room. Perimeter is useful for fencing a garden or measuring the border of a field.",
                "Example 1: there is a rectangle with length 5 and width 4.\n– Area = 5 x 4 = 20\n– Perimeter = 5 + 4 + 5 + 4 = 18\n\n Example 2: There is another rectangle with length 9 and width 6.\n– Area = 9 x 6 = 54\n– Perimeter = 9 + 6 + 9 + 6 = 30"
                
        ));
    }

    public static Topic getTopic(String name) {
        return topics.get(name);
    }
    
    public static String[] topicNames() {
        return topics.keySet().toArray(new String[0]);
    }
}





//topics.put("Fractions", new Topic(
//"Fractions represent parts of a whole. They have numerators and denominators.",
//"Example 1: 1/2 + 1/3 = ?\nExample 2: Simplify 12/16"
//));

//topics.put("Percentages", new Topic(
//"Percentages are fractions with denominator 100. They are useful for comparison.",
//"Example 1: 25% of 200 = ?\nExample 2: Increase 80 by 15%"
//));
