---
title: 9. Hamming Codes in Toy 
subtitle: 
summary:  Write a TOY program to encode data using Hamming codes. Then write a TOY program to correct encoded data that has been corrupted. <br>Partner assignment  {{< project "hamming" >}} |  {{< submit "Hamming" >}}
weight: 10
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false
---
{{<construction>}}

{{< project "hamming" >}} |  {{< submit "Hamming" >}}
### **Goals**

- To learn about error-correcting codes.
- To learn to program in a machine language.
- To appreciate the programming and debugging advantages of a structured programming language like Java over machine language.


### **Getting Started**

- Download the project `zip` file for this assignment from {{< tigerfile "Hamming" >}}, which contains the files you will need for this assignment, which contains:
  - two Java programs (`HammingEncoder.java` and `HammingDecoder.java`) that illustrate the encoding and decoding procedures; 
  - sample input and output files; 
  - TOY reference card; 
  - the `readme.txt` template; 
  - the `acknowledgments.txt` template; 
  - `multiply.toy` TOY program; 
  - Visual X-TOY simulator; 
  - TOY simulator `TOY.java`


- This is a **partner** assignment.  Instructions for help finding a partner and creating a TigerFile group can be found on Ed.

- The rules for partnering:
  - Choose a partner whose skill level is close to your own - only two partners per group.
  - Your partner does not have to be in your precept.
  - The rules for partnering are specified on the [course syllabus]({{< ref "syllabus.md#partners" >}}).  Make sure you read and understand these rules, and please post on Ed if you have questions. In your `acknowledgments.txt` file, you must indicate that you adhered to the COS 126 partnering rules.


### **Background**

*Error-correcting* codes enable data to be sent through a noisy communication channel without corruption. To accomplish this, the sender appends redundant information to the message, so that even if some of the original data is corrupted during transmission, the receiver can still recover the original message intact. Transmission errors are common and can arise from scratches on a CD, static on a cell phone, or atmospheric interference. In a noisy environment, error-correcting codes can increase the throughput of a communication link since there is no need to retransmit the message if it becomes partially corrupted during transmission. For this reason, error-correcting codes are used in many common systems, such as storage devices (CD, DVD, DRAM), mobile communication (cell phones, wireless, microwave links), and digital television.

A *Hamming code* is a specific type of error-correcting code that allows the detection and correction of single-bit transmission errors. Hamming codes are used in many applications where such errors are common, including DRAM memory chips and satellite communication hardware. Hamming codes work by repeatedly reading four message bits, which we denote by \\( m_1, m_2, m_3, m_4 \\), and then inserting three parity bits, which we denote by \\(p_1, p_2, p_3\\). If any one of these seven bits is corrupted during transmission, the receiver can detect the error and recover the original four message bits intact. This is called single-bit error correction because at most one bit can be corrected per unit of data sent. The overhead for using this method is a 75% increase in bandwidth because it requires three extra parity bits for every four message bits. An alternate approach is sending three copies of each bit (and using the one that appears most frequently), which results in a 200% increase in bandwidth.

### **Approach**

Before we describe the algebra of Hamming codes, we first visualize the calculation of the parity bits using Venn diagrams. As an example, suppose we wish to send the 4-bit message `1101`. We associate each of the four message bits with a specific intersection region of three pairwise overlapping circles, as illustrated below:

![Venn Diagram]({{< resource url="static/assignments/hamming/images/venn.png" >}})
The Hamming code adds three parity bits so that each of the three circles has *even parity*:

![Venn Diagram - Even Parity]({{< resource url="static/assignments/hamming/images/even.png" >}})



That is, the sum of the four bits contained in each of the three circles is even:

![Venn Diagram - Even Parity Sum 4]({{< resource url="static/assignments/hamming/images/sum4-even.png" >}})

For this example, the three parity bits are `1` (top), `0` (left), and `0` (right). So, to send a version of the message `1101` that is robust against single-bit errors, the actual message we send is the 7-bit message `1101100`.

Now, imagine this picture is transmitted over a noisy communication channel, and that one bit is corrupted so that the following picture arrives at the receiving station (corresponding to `1001100`):

![Venn Diagram - Noisy]({{< resource url="static/assignments/hamming/images/noisy.png" >}})

The receiver discovers that an error occurred by checking the parity of the three circles. Moreover, the receiver can identify where the error occurred (the second bit) and recover the four original message bits!

![Venn Diagram - Recover]({{< resource url="static/assignments/hamming/images/recover.png" >}})


The parity check for the top circle and right circle failed, but the left circle was ok. So, the only 7-bit message that balances all three parity checks and has at most one changed bit is the one obtained by flipping \\(m_2\\), since \\(m_2\\) represents the intersection of exactly those two circles. Thus the receiver knows that the message originally sent was `1101100`—representing the four message bits `1101` plus the three parity check bits `100`. This achieves the goal, since the original message bits sent were indeed `1101`.

If the center bit \\(m_4\\) is corrupted, then all three parity checks will fail. If a parity bit itself is corrupted, then only one parity check will fail. If the communication channel is so noisy that two or more bits are simultaneously corrupted, then the scheme will not work. Can you see why? More sophisticated types of error-correcting codes can handle such situations.

Of course, in practice, only the seven bits are transmitted, rather than the Venn diagrams.

#### Requirements

- Write two programs `encode.toy` and `decode.toy`.
- Each TOY instruction must have corresponding pseudocode. (This is auto-generated by Visual X-Toy – see below.)
- It is good practice to add line breaks between logically related _sections_ of TOY code.
- Write a comment above each _section_ explaining what that code does.
- The third column is used for your own comments. *Write comments as you write your code. This will help you understand your design as well as help during office hours.* Hint: use the Java code from `HammingEncoder.java` and `HammingDecoder.java` as comments. 


#### TOY Program: `encode.toy`

Write a TOY program `encode.toy` to encode a binary message using the scheme described above. Repeatedly read four (4) bits \\( m_1, m_2, m_3, m_4 \\) from TOY standard input and write the seven (7) bits \\( m_1, m_2, m_3, m_4, p_1, p_2, p_3 \\) to TOY standard output. Stop upon reading `FFFF` from standard input and then output `FFFF` to standard output.

- \\(p_1 = m_1 \wedge m_2 \wedge m_4\\)
- \\(p_2 = m_1 \wedge m_3 \wedge m_4\\)
- \\(p_3 = m_2 \wedge m_3 \wedge m_4\\)

Recall that ^ is the *exclusive or* operator in Java and TOY. This captures the parity concept described above.

#### TOY Program: `decode.toy`

Write a TOY program `decode.toy` to decode and correct a Hamming encoded message. Repeatedly read seven (7) bits \\( m_1, m_2, m_3, m_4, p_1, p_2, p_3 \\) from TOY standard input and write the four (4) **correct** bits \\( m_1, m_2, m_3, m_4 \\) to TOY standard output. Stop upon reading `FFFF` from standard input and then output `FFFF` to standard output. Recall, to determine which one, if any, of the message bits is corrupted, perform the parity checks:

- \\(p_1 = m_1 \wedge m_2 \wedge m_4\\)
- \\(p_2 = m_1 \wedge m_3 \wedge m_4\\)
- \\(p_3 = m_2 \wedge m_3 \wedge m_4\\)

Compare the parity bits you computed with the parity bits you received. If they do not match, then some bit was flipped. Here's what to do with the results:

- If exactly zero or one of the parity checks fail, then all four message bits are correct.
- If checks 1 and 2 fail (but not check 3), then bit \\(m_1\\) is incorrect.
- If checks 1 and 3 fail (but not check 2), then bit \\(m_2\\) is incorrect.
- If checks 2 and 3 fail (but not check 1), then bit \\(m_3\\) is incorrect.
- If all three checks fail, then bit \\(m_4\\) is incorrect.

Flip the corrupted message bit (if necessary) and write (only) \\( m_1, m_2, m_3, m_4 \\) to standard output.

#### Input format

The input format for `encode.toy` is a text file that contains the sequence of bits to be transmitted. Each line consists of a sequence of four (4) bits, with each bit specified as a 4-digit hexadecimal integer (either `0000` or `0001`), separated by whitespace. The last line consists of the single integer `FFFF`, representing the end of the file.  If there are no bits to be transmitted, the file contains only the  single integer `FFFF`.

The file `encode3.txt` contains three lines of 4-hex sequences, each representing a 4-bit sequence:

```bash
more encode3.txt
```
```plaintext
0001 0001 0000 0001
0001 0001 0001 0000
0001 0001 0001 0001
FFFF
```

The 4-hex sequence `0001 0001 0000 0001`represents the 4-bit sequence `1101`.<br>
The 4-hex sequence `0001 0001 0001 0000` represents the 4-bit sequence `1110`.<br>
The 4-hex sequence `0001 0001 0001 0001` represents the 4-bit sequence `1111`.<br>

The input format for `decode.toy` is similar, except that each line consists of a sequence of seven (7) hex digits, each representing a 7-bit sequence. For example:

```bash
more decode4.txt
```
```plaintext
0001 0001 0000 0001 0001 0000 0000
0001 0000 0000 0001 0001 0000 0000
0001 0001 0000 0000 0001 0000 0000
0001 0001 0000 0001 0001 0000 0001
FFFF
```

You can assume the input files are in the prescribed format. The input files will contain **only** a sequence of `0000`'s and `0001`'s, followed by `FFFF`. If there are no bits to be decoded, the file contains only the  single integer `FFFF`.

In summary, input files for `encode.toy` will have a multiple of four `0000`'s and `0001`'s; input files for `decode.toy` will have a multiple of seven `0000`'s and `0001`'s; and  the last line consists of the single integer `FFFF`, representing the end of the file.




#### Using the Visual X-TOY Simulator

Use the [Visual X-TOY simulator](https://lift.cs.princeton.edu/xtoy/) by executing the following command in the IntelliJ terminal:

```bash
java -jar toy.jar
```

The Visual X-TOY simulator provides lots of useful development features. There are two major modes: *edit* and *debug*. Use *Mode > Edit Mode* to edit `.toy` programs and *Mode > Debug Mode* to debug or run `.toy` programs. In Debug Mode, use the *Reference*, *Stdin*, *Stdout* and *Core* tabs on the right to help debug your program.  

To **open** a TOY program from a file, use *Select File > Open*, then browse to find the program, e.g., `multiply.toy`.

To **edit** a TOY program, use *Select Mode > Edit Mode*. You can type your program directly in the edit window. Use `multiply.toy` as a reference for formatting and commenting.

To **redirect** standard input from a file, in Edit Mode, select *Mode > Load File to Stdin*.

To **execute** a TOY program, use *Select Mode > Debug Mode*. Then click the *Run* button at the bottom. You can enter data on standard input by typing the value in the *Stdin* tab and clicking the *Add* button. You will have to click the *Run* button to resume execution. You can see the results on standard output in the *Stdout* tab.


#### Common TOY programming issues

- The program counter starts at `10` so make sure that you place your code starting at memory address `10`.
- Be sure that each line follows the required format `XX: XXXX`.
- Check for duplicated line numbers or out-of-order line numbers.
- Check for using a letter like `O` or `I` instead of the number `0` or `1`.
- Remember that all values, memory addresses, and arithmetic are in hex. For example, memory address `1A` follows `19`. If you don't specify the value for a memory address, that memory address will have the default value of `0000`, which is a `HALT` instruction.
- Watch out for jump statements — if you insert a new line of code between existing lines, you will have to renumber all the following lines. Also, be sure to update any jump statements that have hardwired line numbers.
- Be very careful to update both your comments and your actual code at the same time! It is very common to update only the pseudocode and not the machine instruction.
- All registers are global variables. One way to be sure that a register is not used for two different purposes is to fill in the question in the `readme.txt` about registers before or during debugging.

#### Possible Progress Steps

These are purely suggestions for how you might make progress. You do not have to follow these steps. If you get stumped or frustrated on some portion of the assignment, you should not hesitate to consult a preceptor.

1. Open the Visual X-Toy Simulator and experiment with some of the example programs.
	1. Try the sample programs *Add*, *Echo*, and *Sum* by going to *File > Open Example Programs*.
	1. Open and experiment with `multiply.toy`, which is included in the project folder.
	1. Try executing some of the programs from precept.
1. Read, compile, and run the reference solutions `HammingEncoder.java` and `HammingDecoder.java`. Use these reference solutions as a *design* for your TOY programs.
1. The `readme.txt` provides tables to help your design/implementation/debugging of `encode.toy` and `decode.toy.`  This will help you think about how you plan to use and/or re-use the Toy registers as you implement these Toy programs.  Before you start coding, complete these tables, and update as needed.  They  will be very helpful for you as well as those helping you during office hours and/or Lab TA debugging sessions.
1. As you write your TOY programs, remember:
	- The first column contains the TOY instruction ***in hex***.
	- The second column contains the TOY pseudo-code.
	- The third column is used for your own comments. *Write comments as you write your code. This will help you understand your design as well as help during office hours, grading, etc.*
1. In X-Toy, create a new file (*File > New*).
1. Save the file (*File > Save*) as `encode.toy` in your Hamming assignment project folder.
1. Incrementally implement and test `encode.toy`, using `HammingEncoder.java` as a guide. Remember that as you revise your program, you will need to edit your TOY statement numbers accordingly.
	- Write TOY code that reads a number from standard input. If the number is negative, the program shall halt. (Note, the only valid negative input is `FFFF`.) Otherwise, it prints the number and inputs the next number.
	- Edit your TOY program from step (a) so that it now reads in sequences of four numbers, halting if the first number is negative. Otherwise, it writes the four numbers and inputs the next sequence of numbers.
	-  Edit your TOY program from step (b) so that it now computes the parity bits, and writes the parity bits.
1. Incrementally implement and test `decode.toy`, using `HammingDecoder.java` as a guide.

#### Testing

We provide several input test files of different sizes for both `encode.toy` and `decode.toy`, as well as the answers for each. Note that for convenience, the answer files list each input line and just a shortened version of the corresponding outputs.

_Encoding_

|input file     |output file          |
|---------------|---------------------|
|`encode0.txt`  |`encode0-answer.txt` |
|`encode1.txt`  |`encode1-answer.txt` |
|`encode3.txt`  |`encode3-answer.txt` |
|`encode16.txt` |`encode16-answer.txt`|

_Decoding_

|input file     |output file          |
|---------------|---------------------|
|`decode0.txt`  |`decode0-answer.txt` |
|`decode1.txt`  |`decode1-answer.txt` |
|`decode3.txt`  |`decode3-answer.txt` |
|`decode4.txt`  |`decode4-answer.txt` |
|`decode6.txt`  |`decode6-answer.txt` |
|`decode16.txt` |`decode16-answer.txt`|
|`decode128.txt`|`decode128-answer.txt`|


To test your TOY program using the command-line `TOY.java` simulator, type the commands below into the IntelliJ embedded terminal. You should see the following output - one _bit_ (Toy hex word) per line:

```plaintext
java-introcs TOY encode.toy < encode3.txt
```
```
0001
0001
0000
0001
0001
0000
0000
0001
0001
0001
0000
0000
0000
0000
0001
0001
0001
0001
0001
0001
0001
FFFF
```

```plaintext
java-introcs TOY decode.toy < decode4.txt
```
```
0001
0001
0000
0001
0001
0001
0000
0001
0001
0001
0000
0001
0001
0001
0000
0001
FFFF
```

You can then compare the output to the corresponding answer file.

The most complete way to test your TOY programs is to encode and decode all possible inputs. Use the sample inputs `encode16.txt` and `decode128.txt` to do this.

<!--### **HammingChecker**

{{< details "Click to show instructions for using HammingChecker" >}}
Optionally, you can also use `HammingChecker.java` to easily compare the output to the corresponding answer file. First, redirect standard output from running your TOY program to an output file (e.g., `decode4-output.txt`).

```bash
java-introcs TOY decode.toy < decode4.txt > decode4-output.txt
```

Then, run `HammingChecker`, passing in three (3) command-line arguments:

- `args[0]`: `encode` or `decode` (denotes whether you're checking the output of `encode.toy` or `decode.toy`)
- `args[1]`: path to answer file (e.g., `decode4-answer.txt`)
- `args[2]`: path to output file (e.g., `decode4-output.txt`)

For example:
```bash
java-introcs HammingChecker decode decode4-answer.txt decode4-output.txt
```
```plaintext
SUCCESS: Actual output is identical to the expected output.
```

If your output doesn't match the expected output, an error message will highlight which lines don't match.
```bash
java-introcs HammingChecker decode decode4-answer.txt decode4-output.txt
```
```plaintext
ERROR: Actual output differs from the expected output.

Expected Output (line 003):     1 1 0 1 
Actual Output (line 001):       0 1 0 1 
...

Expected Output (line 006):     1 1 0 1 
Actual Output (line 016):       1 1 0 0 
```

Note: You don't need to fully understand the code in `HammingChecker.java`, as it uses some programming constructs that are out of scope for COS 126. You just need to be able run `HammingChecker` properly in order to use it.

{{< /details >}}
-->


### **Complete `readme.txt`**

Your comments in your Toy program should help you answer these questions.
 
#### `encode.toy` registers

Describe what, if anything, you use each of the registers for in `encode.toy`. 

#### `decode.toy` registers

Describe what, if anything, you use each of the registers for in `decode.toy`.


### **Submission**

Submit to {{< tigerfile "Hamming" >}}: `encode.toy`, `decode.toy`, completed `readme.txt` and `acknowledgments.txt` files.

### **Grading**
| Files                | Points    |
| ------------------   | ----------|
| encode.toy           |  16       |
| decode.toy           |  22       |
| readme.txt           |  2        |
| Total                |  40       |


### **Leaderboard - Optional**

Submit an *optimized* `decode.toy` to the Hamming Leaderboard for class fame and glory. Optimize `decode.toy` with the goal of using the fewest (non-zero) words of TOY main memory. Under 40 (in decimal) is very good; under 35 is great; the all-time record is 27. You may work with your partner or by yourself.

{{< leaderboard "Hamming_Leaderboard" >}} | {{<leaderboard-results "hamming" >}}

Also
1. Upload a `nickname.txt` file that contains the name of your group.  Please be respectful with the nickname.
2. Upload a `leaderboard.txt` file that describes your approach.




<!--
### **TigerTalk - Optional** 

{{< details "The project folder contains an application called the *TigerTalk Simulator*, which integrates with _your_ `encode.toy` and `decode.toy` solutions.  The simulator allows you  to experiment with transmitting text between two parties using different error rates. For more details, click to expand." >}}

Run TigerTalk by executing the following command in the IntelliJ embedded terminal:

`java-introcs -jar TigerTalk.jar`


In this simulator,  Alan Turing and John von Neumann can send (ASCII) text messages to each other, and you can independently:

- Control whether you want hamming encoding on or off.
- Control the bit error rate.

In TigerTalk, text is encoded using ASCII.  For example, a lower-case `a` is represented by the character code `97` (in decimal base) or `61` (in hexadecimal base) or `01100001` (in binary base).   The character code  can then be encoded (represented) in a number of different ways. Since  the value of a character code is a value between 0 and 255, then we  need 8 bits (\\(log_2(256) = 8\\)); in TigerTalk, we will transfer this as two 4-bit words, that together contain the whole information.  Since we are using TOY, **each bit** is represented by a TOY word, either `0000` or `0001`. So, the character `'a'` in ASCII is:

```
01100001
```
which in TOY is represented by the eight (8) TOY words:  

```
0000 0001 0001 0000 0000 0000 0000 0001
```

and we will use two (2) TOY words
```
0000 0001 0001 0000
```
and
```
 0000 0000 0000 0001
 ```

In general, in the TigerTalk simulator, each character sent by the sender is

1. de-assembled into two 4-bit TOY words;
1. encoded using your `encode.toy`;
1. decoded (if Hamming correction is turned on); and
1. finally re-assembled by the receiver. 

Try typing some text into either Turing’s or von Neumann’s composition area (to the right of their icons, press return/enter after you send). You can turn on/off your Hamming Error Correction (encode/decode) as well as adjust the error rate.

For example, with Hamming Error Correction switched **OFF** and the Error Rate set to 0%, observe the encoding/decoding when Turing sends the following text to von Neumann:

*In praise of Old Nassau, we sing, hurrah, hurrah, hurrah!*

Use the simulation to answer the following questions and include in your `readme.txt`:

1. With Hamming Error Correction switched **OFF**, can you find an error rate that produces errors in the transmission without errors in the received text? For example, Alan Turing composes/sends a text, a bit gets flipped, yet John von Neumann receives the correct text. Explain, including the text you used.
1. With Hamming Error Correction switched **ON**, what error rate will produce errors in the transmission without errors in the received text? For example, Alan Turing composes/sends the text you used in (1), a bit gets flipped, yet John von Neumann receives the correct text. Explain, including the text you used.
1. With Hamming Error Correction switched **ON**, what is the smallest error rate that will **always** produce errors in received text? Explain.

{{< /details >}}
-->

### **Enrichment**
Error correcting codes are used in CDs. You can drill a 2.5mm hole through it, and it will still play. Only 1/3 of data is music.

Error correcting codes are also widely used on noisy analog channels such as radio links. This enables you to reduce the amount of power needed to transmit signals.



Copyright © 1999–2024, [Robert Sedgewick](https://www.cs.princeton.edu/~rs) and [Kevin Wayne](https://www.cs.princeton.edu/~wayne).


