/* *****************************************************************************
 *
 *  Description: Check if output from encode.toy or decode.toy matches the
 *               expected output.
 *
 *  Compilation:    javac-introcs TOY.java
 *                  javac-introcs HammingChecker.java
 *
 *  TOY Execution:  java-introcs TOY encode.toy < encode16.txt > encode16-output.txt
 *  Java Execution: java-introcs HammingChecker encode encode16-answer.txt encode16-output.txt
 *
 *  TOY Execution:  java-introcs TOY decode.toy < decode128.txt > decode128-output.txt
 *  Java Execution: java-introcs HammingChecker decode decode128-answer.txt decode128-output.txt
 *
 *  Command-line arguments:
 *    - args[0] (String): "encode" or "decode" (denote if encode.toy or
 *              decode.toy produced the output file)
 *    - args[1] (String): path to answer file (e.g., encode16-answer.txt or
 *              decode128-answer.txt)
 *    - args[2] (String): Path to output from TOY program
 *              (e.g., encode16-output.txt, decode128-output.txt)
 *
 *  Output:
 *    - If actual output matches expected output, the following is printed:
 *          SUCCESS: Actual output is identical to the expected output.
 *
 *    - If the length of the actual output doesn't match that of the expected
 *      output, the following is printed:
 *          ERROR: Expected output size = ___. Actual output size = ___.
 *          They should be the same size; double check your files and
 *              command-line arguments.
 *
 *    - If there is a difference between the actual output and expected output,
 *      the following is printed:
 *          ERROR: Actual output differs from the expected output.
 *          Expected Output (line ___):     ____________
 *          Actual Output (line ___):       ____________
 *          ...
 *
 * Example: Assume encode16-output.txt is correct. Then, if the last line
 *          (line 112) in encode16-output.txt was "0000" instead of "0001" and
 *          the first line was "0001" instead of "0000", the output would be
 *          as follows:
 *
 * > java-introcs HammingChecker encode encode16-answer.txt encode16-output.txt
 *
 * ERROR: Actual output differs from the expected output.
 *
 * Expected Output (line 003):     0 0 0 0 0 0 0
 * Actual Output (line 001):       1 0 0 0 0 0 0
 *
 * Expected Output (line 018):     1 1 1 1 1 1 1
 * Actual Output (line 112):       1 1 1 1 1 1 0
 *
 * Programming constructs used that you'll learn about later in the course:
 *   - String .equals() method
 *   - In objects
 *
 * Programming constructs used that are out of scope for COS126:
 *   - assert statements (e.g., "assert(____)")
 *   - ArrayList objects
 *   - ArrayList .size() and .equals() method
 *   - static constants (e.g., "private static final...")
 *   - Java's ternary conditional operator (e.g.,"___ ? ___ : ___")
 *
 * Written by: Ruth Fong (ruthfong@cs.princeton.edu) on October 4, 2021.
 * ************************************************************************** */

import java.util.ArrayList;

public class HammingChecker {
    private static final String ENCODE = "encode";  // denotes encode.toy used
    private static final String DECODE = "decode";  // denotes decode.toy used
    private static final String END = "FFFF";       // denotes end of input
    private static final int N_ENCODE = 7;          // # output bits after encoding
    private static final int N_DECODE = 4;          // # output bits after decoding

    public static void main(String[] args) {
        // Parse command-line arguments.
        String mode = args[0];
        if (!(mode.equals(ENCODE) || mode.equals(DECODE))) {
            StdOut.printf("ERROR: The first command line argument should be "
                                  + "either \"encode\" or \"decode\". "
                                  + "Currently, it's \"%s\".\n",
                          mode);
            return;
        }
        boolean isEncode = mode.equals(ENCODE);

        ArrayList<Integer> expectedOutput = getExpectedOutput(args[1], isEncode);
        ArrayList<Integer> actualOutput = getActualOutput(args[2]);

        // Check if size of expected output doesn't match that of actual output.
        if (expectedOutput.size() != actualOutput.size()) {
            StdOut.printf("ERROR: Expected output size = %d. Actual output size = %d\n",
                          expectedOutput.size(), actualOutput.size());
            StdOut.println("They should have the same size; double check your "
                                   + "files and command-line arguments.");
            return;
        }

        // Check if expected output matches actual output.
        if (expectedOutput.equals(actualOutput)) {
            StdOut.println("SUCCESS: Actual output is identical to the expected output.");
            return;
        }

        // Handle case when expected output and actual output differ.
        StdOut.println("ERROR: Actual output differs from the expected output.");
        int numOutputBits = isEncode ? N_ENCODE : N_DECODE;

        // Print info for each line where expected and actual output differs.
        for (int i = 0; i < expectedOutput.size() / numOutputBits; i++) {
            // Check if this line in expected output differs from actual output.
            boolean differ = false;
            int differIndex = -1;
            for (int j = 0; j < numOutputBits; j++) {
                if (actualOutput.get(i * numOutputBits + j)
                        != expectedOutput.get(i * numOutputBits + j)) {
                    differ = true;
                    differIndex = i * numOutputBits + j;
                    break;
                }
            }

            // Print line and line number where expected and actual output differ.
            if (differ) {
                StdOut.printf("\nExpected Output (line %03d):\t", i + 3);
                for (int j = 0; j < numOutputBits; j++)
                    StdOut.printf("%d ", expectedOutput.get(i * numOutputBits + j));
                StdOut.printf("\nActual Output (line %03d):\t", differIndex + 1);
                for (int j = 0; j < numOutputBits; j++)
                    StdOut.printf("%x ", actualOutput.get(i * numOutputBits + j));
                StdOut.println();
            }
        }
    }

    // Read expected output from file and return it as ArrayList<Boolean>.
    // Example: getExpectedOutput("encode16-answer.txt", true)
    // Example: getExpectedOutput("decode128-answer.txt", false)
    public static ArrayList<Integer> getExpectedOutput(String path, boolean isEncode) {
        In answer = new In(path);
        ArrayList<Integer> expectedOutput = new ArrayList<Integer>();

        // Skip first two lines
        answer.readLine();
        answer.readLine();

        // Calculate number of input and output bits.
        int numInputBits = isEncode ? N_DECODE : N_ENCODE;
        int numOutputBits = isEncode ? N_ENCODE : N_DECODE;

        while (!answer.isEmpty()) {
            // Check if end of sequence.
            String m1 = answer.readString();
            if (m1.equals(END))
                break;

            // Skip rest of input bits (3 for encode; 6 for decode).
            for (int i = 0; i < numInputBits - 1; i++)
                answer.readString();

            for (int i = 0; i < numOutputBits; i++)
                expectedOutput.add(answer.readInt());
        }

        return expectedOutput;
    }

    // Read actual output from file and return it as ArrayList<Boolean>.
    // Example: getActualOutput("encode16-output.txt")
    public static ArrayList<Integer> getActualOutput(String path) {
        In output = new In(path);
        ArrayList<Integer> actualOutput = new ArrayList<Integer>();

        // Read in hex numbers.
        while (!output.isEmpty())
            actualOutput.add(Integer.parseInt(output.readString(), 16));

        return actualOutput;
    }
}
