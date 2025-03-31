---
title: 8. Chat126
subtitle: 
summary:  Use a Markov chain to create a statistical model of a piece of English text. Simulate the Markov chain to generate stylized pseudo-random text. <br>Partner assignment   {{< project "chat126" >}} |  {{< submit "Chat126" >}}<!--<br>{{<notreleased>}}-->
weight: 9
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false
---
{{<construction>}}
{{< project "chat126" >}} |  {{< submit "Chat126" >}}

### **Goals**

- To learn about symbol tables.
- To learn about natural language processing.
- To use a Markov chain to create a statistical model of a piece of English text.
- To simulate the Markov chain to generate stylized pseudo-random text.



### **Getting Started**

- Download and expand the  project `zip` file for this assignment, which contains the files you will need for this assignment.

- Review the beginning of Section 4.4 (pages 582–586) on using symbol tables in client code. For reference, here is a partial API of the [ST](https://introcs.cs.princeton.edu/java/11cheatsheet#ST) data type.

- Review the material on the textbook on parameterized data type and generics (pages 566–570). The `Key` type parameter for the `ST` generic class can be any comparable type, such as `String` or `Integer`.

- Review precept exercises, especially `FrequencyTable.java`.

- Review the `String` data type. To extract a substring of a given string, review the `substring` method: `s.substring(i, i + k)` returns the the `k`-character substring of `String s`, starting at `i`. Note that it includes the left endpoint but excludes the right endpoint.  Try some examples using `jshell` to understand how `substring` is used.

- Review the `StdRandom.discrete()` methods.  There are two overloaded methods named `StdRandom.discrete()`.  You may want to write some small programs to understand how they are used:
    - One takes a floating-point array `probabilities[]` and returns index `i` with probability equal to `probabilities[i]`. The array entries must be non-negative and sum to 1.
    - The other takes an integer array `frequencies[]` and returns index `i` with probability proportional to `frequencies[i]`. The array entries must be non-negative and not all zero.

- This is a **partner** assignment.  Instructions for help finding a partner and creating a TigerFile group can be found on Ed.

- The rules for partnering:
  - Choose a partner whose skill level is close to your own - only two partners per group.
  - Your partner does not have to be in your precept.
  - The rules for partnering are specified on the [course syllabus]({{< ref "syllabus.md#partners" >}}).  Make sure you read and understand these rules, and please post on Ed if you have questions. In your `acknowledgments.txt` file, you must indicate that you adhered to the COS 126 partnering rules.



### **Background**

#### LLMs
Generative AI platforms, popularized recently by ChatGPT, exhibit remarkable proficiency in generating coherent text based on provided prompts. At a high level, the fundamental operation is predicting the next word (or token) in a sentence given the previous words (or tokens). This is known as language modeling.

Generative AI platforms predominantly rely on large language models (LLMs), extensively trained on vast datasets to learn about patterns and relationships within the training data.  In the context of this programming assignment, you will develop a program called Chat126, differentiated by its utilization of a small language model - a Markov model -  to achieve simpler, though still quite compelling, text generation capabilities.

LLMs have been used for various tasks, such as translating between languages, answering questions, summarizing text, generating coherent and relevant text, planning robot tasks and more.  LLMs require significant computational resources to train and operation, whereas Markov models are computationally inexpensive.

#### Perspective


In the 1948 landmark paper [A Mathematical Theory of Communication](https://en.wikipedia.org/wiki/A_Mathematical_Theory_of_Communication), Claude Shannon founded the field of information theory and revolutionized the telecommunications industry, laying the groundwork for today's Information Age. In this paper, Shannon proposed using a *Markov chain* to create a statistical model of the sequences of letters in a piece of English text. Markov chains are now widely used in speech recognition, handwriting recognition, information retrieval, data compression, and spam filtering. They also have many scientific computing applications including the genemark algorithm for gene prediction, the Metropolis algorithm for measuring thermodynamical properties, and Google's PageRank algorithm for web search. For this assignment, we consider a whimsical variant—generating stylized pseudo-random text.

#### Markov model of natural language

Shannon approximated the statistical structure of a piece of text using a mathematical model known as a Markov model. A Markov model of order 0 predicts that each letter in the alphabet occurs with a fixed probability. We can fit a Markov model of order 0 to a specific piece of text by counting the number of occurrences of each letter in that text, and using those frequencies as probabilities. For example, suppose that the input text is `"gagggagaggcgagaaa"`. Then, the Markov model of order 0 predicts that each letter is an `a` with probability 7/17, a `c` with probability 1/17, and a `g` with probability 9/17 because these are the fraction of times each letter occurs. The following sequence of characters is a typical example generated from this model:

```linux
g a g g c g a g a a g a g a a g a a a g a g a g a g a a a g a g a a g ...
```


A Markov model of order 0 assumes that each letter is chosen independently. This independence does not coincide with statistical properties of English text because there a high correlation among successive characters in a word or sentence. For example, `w` is more likely to be followed by `e` than by `u`, and `q` is much more likely to be followed by `u` than by `e`.

We obtain a more refined model by allowing the probability of choosing each successive letter to depend on the preceding letter or letters. A Markov model of order \\(k\\) predicts that each letter occurs with a fixed probability, but that probability can depend on the previous \\(k\\) characters, which we refer to as a \\(k\\)-gram. For example, suppose that the input text has 100 occurrences of `"th"`, with 60 occurrences of `"the"`, 25 occurrences of `"thi"`, 10 occurrences of `"tha"`, and 5 occurrences of `"tho"`. Then, the Markov model of order \\(2\\) predicts that the letter immediately following any occurrence of `"th"` is `e` with probability 3/5, `i` with probability 1/4, `a` with probability 1/10, and `o` with probability 1/20.

#### A brute-force solution

Claude Shannon proposed the following brute-force scheme to generate text according to a Markov model of order 1:

_To construct [a Markov model of order 1], for example, one opens a book at random and selects a letter at random on the page. This letter is recorded. The book is then opened to another page and one reads until this letter is encountered. The succeeding letter is then recorded. Turning to another page this second letter is searched for and the succeeding letter recorded, etc. It would be interesting if further approximations could be constructed, but the labor involved becomes enormous at the next stage._

Your task is to write a Java program to automate this laborious task, in an efficient manner.  Shannon's approach is prohibitively slow when the length of the input text is large.



### **Implementation Tasks**

- Implement two classes:
    - `MarkovLM.java`
    - `Chat126.java`
- Submit a completed `readme.txt` file.
- Complete the `acknowledgments.txt` file.


### **`MarkovLM`**

Create an _immutable data type_  - _a markov language model_ - to represent a Markov model of order \\(k\\), based on a given input text. Implement the following API:

```java
public class MarkovLM {

    // creates a Markov model of order k based on the specified text
    public MarkovLM(String text, int k)

    // returns the order of the model (also known as k)
    public int order()

    // returns a String representation of the model (more info below)
    public String toString()

    // returns the # of times 'kgram' appeared in the input text
    public int freq(String kgram)

    // returns the # of times 'c' followed 'kgram' in the input text
    public int freq(String kgram, char c)

    // returns a character, chosen with weight proportional to the
    // number of times each character followed 'kgram' in the input text
    public char predictNext(String kgram)

   // tests all instance methods to make sure they're working as expected
    public static void main(String[] args)
}
```

#### Constructor

You may assume that the input text is limited to a sequence of characters over the ASCII alphabet, so that all `char` values are between 0 and 127.

#### String representation.

Build a string representation of the Markov model, as illustrated in the example below. 

```bash
aa: a 1 g 1
ag: a 3 g 2
cg: a 1
ga: a 1 g 4
gc: g 1
gg: a 1 c 1 g 1
```

Include one line for each \\(k\\)-gram that appears in the text. Each line contains the \\(k\\)-gram, followed by a colon; followed by each character that appears in the text immediately after that \\(k\\)-gram and the number of times it appears, with a space between each component. The \\(k\\)-grams must appear in lexicographic order; the characters associated with each \\(k\\)-gram must appear in ASCII order.

#### Predict next character.

The `predictNext()` method must return a character that immediately follows the specified \\(k\\)-gram and do so with probability proportional to the number of times that character follows the specified \\(k\\)-gram. For example if the \\(k\\)-gram `"ga"` appears in the text five times, once followed by the character `a` and four times followed by the character `g`, then `predictNext("ga")` returns `a` with probability 1/5 and `g` with probability 4/5, independently for each call. 


#### Circular string.

To avoid dead ends, treat the input text as a _circular string_: the first character is considered to succeed the last character. For example, if \\(k = 2\\) and the text is the 17-character string `"gagggagaggcgagaaa"`, then the salient features of the Markov model are captured in the table below.  Note that the frequency of `"ag"` is 5 (and not 4) because we treat the string as circular. 

{{< staticimg src="chat126/images/example-circular.jpg" wid=500 >}}
<!--```plaintext
               frequency of   probability that
                next char       next char is
kgram   freq    a   c   g        a    c    g
----------------------------------------------
 aa      2      1   0   1       1/2   0   1/2
 ag      5      3   0   2       3/5   0   2/5
 cg      1      1   0   0        1    0    0
 ga      5      1   0   4       1/5   0   4/5
 gc      1      0   0   1        0    0    1
 gg      3      1   1   1       1/3  1/3  1/3
----------------------------------------------
        17      7   1   9
```-->





<!-- As another example, consider the text 6-character string `"banana"`.  If \\(k = 2\\), then there are 6 2-grams: `"ba"`, `"an"`, `"na"`, `"an"`, `"na"`, `"ab"`:

{{< staticimg src="chat126/images/banana-circular.jpg" wid="400" >}} -->

#### Corner cases.

- Throw an `IllegalArgumentException` in `freq()` and `predictNext()` if the argument kgram is not of length \\(k\\).

- Throw an `IllegalArgumentException` in `predictNext()` if kgram does not appear in the text.

> Suggestion: Use short exception messages or split the exception message into two `String` literals appended using  `+` so the message can placed on two lines. This will avoid Checkstyle long line messages (i.e., over 87 characters). 

#### Performance requirements.

If \\(k\\) is a fixed constant, then the constructor must take \\(n\log{}n\\) time (or better); the `order()` method must take constant time; the `predictNext()` and two `freq()` methods must take \\(\log{}n\\) time (or better), where \\(n\\) is the number of characters in the input text. To achieve these performance requirements, use one (or more) symbol tables whose keys are `String` \\(k\\)-grams and whose values enable efficient implementation of the two `freq()` methods.

> Note - For `ST` and `java.util.TreeMap` classes: the order of growth for the number of comparisons in the worst case for the `put()`, `get()` and `contains()/containsKey()` methods is   \\(\Theta(log n)\\).

### **Text generation client - `Chat126`**

A Markov chain is a stochastic process where the state change depends on only the current state. For text generation, the current state is a \\(k\\)-gram. The next character is selected at random, using the probabilities from the Markov model. For example, if the current state is `"ga"` in the Markov model of order \\(2\\) discussed above, then the next character will be `a` with probability 1/5 and `g` with probability 4/5. The next state in the Markov chain is obtained by appending the new character to the end of the \\(k\\)-gram and discarding the first character. A trajectory through the Markov chain is a sequence of such states. Below is a possible trajectory consisting of nine (9) transitions.

```bash
trajectory:          ga  -->  ag  -->  gg  -->  gc  -->  cg  -->  ga  -->  ag  -->  ga  -->  aa  -->  ag
probability for a:       1/5      3/5      1/3       0        1       1/5      3/5      1/5      1/2
probability for c:        0        0       1/3       0        0        0        0        0        0
probability for g:       4/5      2/5      1/3       1        0       4/5      2/5      4/5      1/2
```

Treating the input text as a circular string ensures that the Markov chain never gets stuck in a state without any next characters.

To generate random text from a Markov model of order \\(k\\), set the initial state to the first \\(k\\) characters in the input text. Then, simulate a trajectory through the Markov chain by performing \\(T − k\\) transitions, printing the random character selected at each step. For example, if \\(k = 2\\) and \\(T = 11\\), then the following is a possible trajectory, leading to the output `gaggcgagaag`.

```bash
trajectory:          ga  -->  ag  -->  gg  -->  gc  -->  cg  -->  ga  -->  ag  -->  ga  -->  aa  -->  ag
output:              ga        g        g        c        g        a        g        a        a        g
```

Write a client program `Chat126` that takes two integer command-line arguments \\(k\\) and \\(T\\); reads the input text from standard input; builds a Markov model of order \\(k\\) from the input text; then, starting with the \\(k\\)-gram consisting of the first \\(k\\) characters of the input text, prints \\(T\\) characters generated by simulating a trajectory through the corresponding Markov chain.

```plaintext
more input17.txt
gagggagaggcgagaaa
```
```plaintext
java-introcs Chat126 2 11 < input17.txt 
gaggcgagaag
```
```plaintext
java-introcs Chat126 2 11 < input17.txt 
gaaaaaaagag
```

#### Corner cases.

You may assume that the length of the text is at least \\(k\\) and \\(T \geq k\\).

#### Performance requirement.

If \\(k\\) is a fixed constant, then the running time of Chat126 must be proportional to \\(n\log{}n + T\log{}n \\) (or better), where \\(n\\) is the number of characters in the input text and \\(T\\) is the number of characters in the output.

### **Possible Progress Steps**
We provide some additional instructions below.  Click on the &#9658;  icon to expand *some possible progress steps* or you may try to solve Chat126 without them.  It is up to you!

{{< details "Click here to show possible progress steps for MarkovLM" >}}

#### Implementing `MarkovLM.java`

1. Review precept exercises, especially `FrequencyTable`,  as well as [`IntegerSort.java`](https://introcs.cs.princeton.edu/42sort/IntegerSort.java.html).
2. Create one or more instance variables to support the two `freq()` methods. _One_ strategy is to maintain two symbol tables—one for the one-argument `freq()` method and one for the two-argument `freq()` method.
- For each \\(k\\)-gram (a string), the first symbol table tells you how many times it appears in the text (an integer).
- For each \\(k\\)-gram (a string), the second symbol table tells you how many times each ASCII character succeeds the \\(k\\)-gram in the text (an array of 128 integers).
- Character (i.e., `char` values) can be used as an index into an array.  In Java, characters are 16-bit (unsigned) integers; they are promoted to ints in any context that expects one. For example, `array['c']` is equivalent to `array[99]` because the ASCII code for `'c'` is 99. 
- You can avoid hardwiring the constant 128 by using a static instance variable: `private static final int ASCII = 128;`
- This code should appear inside the class block, but before any instance variables or methods. By convention, constant variable names should be ALL_UPPERCASE. The `static` modifier means that every instance method will refer to the same variable (as opposed to instance variables, for which there is one variable per object); the `final` modifier means that you can't change its value, once initialized. 
3. Write the constructor to create the circular version of the input text. Then initialize and populate your symbol tables, using the symbol table methods `contains()`, `get()`, and `put()`. This will be a substantial amount of code, relative to the other methods in this class.
- Do not save the original text (or the circular text) as an instance variable because no instance method will need this information after the symbol table is initialized.
- There are a number of approaches for emulating the circular text. One way is to append the first \\(k\\) characters of the input text to the input text.
4. Test: In the `main()`, try creating some `MarkovLM` objects. For example: 
   ```java
   String text1 = "banana";
   MarkovLM model1 = new MarkovLM(text1, 2);
   ...
   String text2 = "gagggagaggcgagaaa";
   MarkovLM model2 = new MarkovLM(text2, 2);
   ```
5. Write the `toString()` method. Use the enhanced for loop to access each key–value pair in your symbol table - see the `FrequencyTable.java` from precept as another example.
6. Test the constructor and `toString() method`: This can help you debug small test cases. In the `main()`, print some `MarkovLM` objects. For example:
   ```java
   String text1 = "banana";
   MarkovLM model1 = new MarkovLM(text1, 2);
   StdOut.println(model1);
   ...
   String text2 = "gagggagaggcgagaaa";
   MarkovLM model2 = new MarkovLM(text2, 2);
   StdOut.println(model2);
   ```
- Sample output from the code snippet above:
   ```plaintext
   ab: a 1
   an: a 2
   ba: n 1
   na: b 1 n 1
   
   aa: a 1 g 1
   ag: a 3 g 2
   cg: a 1
   ga: a 1 g 4
   gc: g 1
   gg: a 1 c 1 g 1
   ```
- You can insert a _line break_ in a `String` by using the characters `\n`. For example, if you print  `"ab\ncd"`, `ab` and `cd` will appear on separate lines.
7. Write the `order()` method. This should be a one-liner.
8. Using the symbol table instance variables, write the two `freq()` methods.
9. Use the `main()` provided above to test your code before continuing. In the `main` method, you should add more tests of the constructor, `order()`, and `freq()` methods.
10. Write the `predictNext()` method. To generate a random character with probability proportional to its frequency you may call either of the two [`StdRandom.discrete()`](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdRandom.html#discrete-double:A-) methods.
11. It may not be obvious from your final results whether `predictNext()` is working as intended, so be sure to test it thoroughly. Next, test your complete `MarkovLM` data type before continuing. (See *Testing Your `MarkovLM.java` Implementation* below.)
{{< /details >}}

{{< details "Click here to show possible progress steps for Chat126" >}}

#### Implementing `Chat126.java`

1.  Read in `k` and `T` from the command line; read the input text from standard input.
2. Create a `MarkovLM` object of order `k` using the input text.
3. To generate a trajectory of length `T`, use the first `k` characters in the input text as the initial \\(k\\)-gram and print the initial \\(k\\)-gram. Then, repeatedly generate and print a new random character according to the Markov model and update the \\(k\\)-gram to store the last `k` characters printed.
4. Make sure to test your program on large inputs (we provide several), large outputs, and different values of `k`.
5. Some FAQs: 
- *Should my program generate a different output each time I run it?*  Yes.
- *How can I read in the input text from standard input?* Use `StdIn.readAll()`. Do not remove whitespace.
- *My random text ends in the middle of a sentence. Is that OK?* Yes, that's to be expected.
- *After executing the program, the command prompt appears on the same line as the random text. Is that OK?* Yes. It's because the random text does not end with a newline character. If you want to add a call to `StdOut.println()` at the end of your program, that's fine—we promise not to deduct.
- *What does this message mean: The local variable 'T' must start with a lowercase letter and use camelCase?* Since `T` is a constant, you should declare it as `final`, e.g., `final int T = ...`.  Alternatively, you can rename the constant using a variable name that is more than one character.
- *For which values of \\(k\\) should my program work?* It should work for all well defined values of \\(k\\), from 0 up to, and including, the length of the input text. As \\(k\\) gets larger, your program will use more memory and take longer.
- *My program is slow when \\(T\\) is large because I am concatenating the \\(T\\) characters, one at a time, to form string of length \\(T\\)). What else can I do?* Do you need to form the entire string? Why not print the characters, one at a time, as you generate them?
- *I get an `OutOfMemoryException`. How do I tell Java to use more of my computer's memory?* Depending on your operating system, you may have to ask the Java Virtual Machine for more main memory beyond the default.  The `500m` means 500MB, and you should adjust this number depending on the size of the input text. 
  ```plaintext
  java-introcs -Xmx500m Chat126 7 1000 < input.txt
  ```
{{< /details >}}

### **Testing**

#### Testing Your `MarkovLM.java` Implementation

We provide a `main()` as a _start_ to your testing.  You will need to provide _more_ tests.

```java
public static void main(String[] args) {
   String text1 = "banana";
   MarkovLM model1 = new MarkovLM(text1, 2);
   StdOut.println("freq(\"an\", 'a')    = " + model1.freq("an", 'a'));
   StdOut.println("freq(\"na\", 'b')    = " + model1.freq("na", 'b'));
   StdOut.println("freq(\"na\", 'a')    = " + model1.freq("na", 'a'));
   StdOut.println("freq(\"na\")         = " + model1.freq("na"));
   StdOut.println();

   String text3 = "one fish two fish red fish blue fish";
   MarkovLM model3 = new MarkovLM(text3, 4);
   StdOut.println("freq(\"ish \", 'r') = " + model3.freq("ish ", 'r'));
   StdOut.println("freq(\"ish \", 'x') = " + model3.freq("ish ", 'x'));
   StdOut.println("freq(\"ish \")      = " + model3.freq("ish "));
   StdOut.println("freq(\"tuna\")      = " + model3.freq("tuna"));
}
```

 If your method is working properly, you will get the following output:
 
```plaintext
java-introcs MarkovLM
freq("an", 'a')    = 2
freq("na", 'b')    = 1
freq("na", 'a')    = 0
freq("na")         = 2

freq("ish ", 'r') = 1
freq("ish ", 'x') = 0
freq("ish ")      = 3
freq("tuna")      = 0
```

To test `predictNext()`, write a loop that calls `predictNext()` repeatedly, and count how many times a particular character is returned. For example, with `model1` above, `predictNext("na")` should return `b` about one-half of the time; with `model3` above, `predictNext("fish")` should return `o` about one-quarter of the time.


Of course, you should try to define and test other models.

#### Testing Your `Chat126.java` Implementation

Be sure to test `Chat126` with different values of `k`.

An order-0 Markov model generates a random sequence of letters where each letter appears with probability proportional to its frequency in the input text. For `input17.txt` there are nine `g`'s, seven `a`'s, and one `c`. So we want the probability of generating a `g` to be 9/17, an `a` to be 7/17, and a `c` to be be 1/17. In a sequence of 100 characters, we would therefore expect on average about 53 `g`'s, 41 `a`'s, and 6 `c`'s.

```plaintext
java-introcs Chat126 0 100 < input17.txt
gaaagaacagcagacgacggaagaaggaggaaaaggaggggaggggggaggaggaagggagaaaggagacagcggaggggacgggaggagaggaggagag
```

 As documented in the assignment specification, in an order-2 model for `input17.txt`, the next character after `"ga"` is `a` with probability 1/5 and `g` with probability 4/5. So, if you run the following command ten times, you should expect (on average) to see `"gag`" approximately eight times and `"gaa"` approximately two times.

```plaintext
java-introcs Chat126 2 3 < input17.txt
```
```
gag
```

### **Generating Text Using Different Inputs and Orders**

Once you get the program working, test it on different inputs of different sizes and different orders. Does increasing the order have the effect you expect? Try your model on something that you have written or some other text you know well. Make sure to test both long inputs (we provide several) and long outputs.  Here are a couple of examples (click on the &#9658;  icon to expand to expand/collapse):

{{< details "Example 1 Input: Speech to the Class of 2018">}}

Good afternoon and welcome to Opening Exercises. What a special pleasure it is to greet Princetons Great Class of 2018! I also offer a warm welcome to our new graduate students, faculty and staff members, and all of you who are returning to campus after the summer.

Today we carry on a tradition that dates back at least to 1802, when Nassau Hall was the site of an opening exercise for Princeton students. The event switched to other sites before moving in 1929 to the University Chapel, where we gather today. Todays interfaith ceremony is far different from the Christian services that greeted students in 1929, but the chapels soaring architecture and inspirational spaces continue to invite all of us, whatever our religious or ethical traditions might be, to reflect on the larger purposes that should guide our community as we begin another year on this glorious campus.

Today you join the ranks of students who have left their marks on the Princeton campus and the world for generations through their intellect, creativity and passion. You, the 1,312 members of the Class of 2018, are an extraordinarily accomplished, inspiring and diverse group. You hail from 46 states, as well as the District of Columbia. You come from 50 countries outside of the United States from Chile to the Czech Republic, from Iceland to India, from Nigeria to New Zealand. You grew to become upstanding, compassionate citizens in Happy Valley, Oregon, and Niceville, Florida. You weathered the ups and downs of life in Boiling Springs, Pennsylvania, and Frostburg, Maryland. And you learned to appreciate the lyrical majesty of language in Ho Ho Kus, New Jersey, and Oologah, Oklahoma.

*Example 1 output: random Eisgruber, using order 7 model*

```plaintext
java-introcs Chat126 7 798 < opening-exercises.txt
```

Good afternoon and welcome to a universities around you here.

I often ask Princeton is a truly global institution. As scholars who matter most to you. And you here.

I often ask Princeton you have come to our social natures, and, more specifically, with drums and choirs and distinguished teachers, whose contributions will become upstanding.

This Universitys GREAT CLASS OF 2018! Welcome new members play indispensable roles in helping our Universitys GREAT CLASS OF 2018! I also offer insignificant, or puzzling, or uninteresting, or unsympathetic may turn out to be discourse in all disciplines here as rich with meaningful, not just of any story, can make it easy to feel without knowing exactly what he was destined to appreciate the lyrical majesty of language in Ho Ho Kus, New Jersey, and
{{< /details >}}

{{< details "Example 2 Input: As you Like It, Act 2">}}

[Enter DUKE SENIOR, AMIENS, and two or three Lords,
	like foresters]

DUKE SENIOR	Now, my co-mates and brothers in exile,
	Hath not old custom made this life more sweet
	Than that of painted pomp? Are not these woods
	More free from peril than the envious court?
	Here feel we but the penalty of Adam,
	The seasons' difference, as the icy fang
	And churlish chiding of the winter's wind,
	Which, when it bites and blows upon my body,
	Even till I shrink with cold, I smile and say
	'This is no flattery: these are counsellors
	That feelingly persuade me what I am.'
	Sweet are the uses of adversity,
	Which, like the toad, ugly and venomous,
	Wears yet a precious jewel in his head;
	And this our life exempt from public haunt
	Finds tongues in trees, books in the running brooks,
	Sermons in stones and good in every thing.
	I would not change it.

AMIENS	Happy is your grace,
	That can translate the stubbornness of fortune
	Into so quiet and so sweet a style.

DUKE SENIOR	Come, shall we go and kill us venison?
	And yet it irks me the poor dappled fools,
	Being native burghers of this desert city,
	Should in their own confines with forked heads
	Have their round haunches gored.

*Example 2 output: random Shakespeare, using order 6 model*
```plaintext
java-introcs Chat126 7 1135 < as-you-like-it.txt
```

DUKE SENIOR	Now, my co-mates and thus bolden'd, man, how now, monsieur Jaques,
	Unclaim'd of his absence, as the holly!
	Though in the slightest for the fashion of his absence, as the only wear.

TOUCHSTONE	I care not for meed!
	This I must woo yours: your request than your father: the time,
	That ever love I broke
	my sword upon some kind of men
	Then, heigh-ho! sing, heigh-ho! sing, heigh-ho! sing, heigh-ho! unto the needless stream;
	'Poor deer,' quoth he,
	'Call me not so keen,
	Because thou the creeping hours of the sun,
	As man's feasts and women merely players:
	Thus we may rest ourselves and neglect the cottage, pasture?
	Enter DUKE FREDERICK	Can in his time in my heartily,
	And have me go with your fortune
	In all this fruit
	Till than bear
	the arm's end: I will through
	Cleanse the uses of the way to look you.
	Know you not, master,
	Sighing like upon a stone another down his bravery is not so with his effigies with my food:
	To speak my mind, and inquisition
	And unregarded age in corners throat,
	He will come hither:
	He dies that hath engender'd:
	And you to
	the bed untreasured of the brutish sting it.
{{< /details >}}

### **Submission**
  
Submit to to  {{< tigerfile "Markov" >}}:  `MarkovLM.java`, `Chat126.java`, completed `readme.txt` and `acknowledgments.txt` files  .  Include in your `readme.txt` two of the most entertaining language-modeling fragments that you discover.

### **Grading**
| Files                | Points    |
| ------------------   | ----------|
| MarkovLM.java        |  26       |
| Chat126.java         |  10       |
| readme.txt           |   4       |
| Total                |  40       |

### **Challenges**

#### Challenge for the bored 1.

The current assignment only handles ASCII characters.  However, many text collections use Unicode characters, such as those with  diacritic marks (e.g.:  ā ă ą ç é ē î ï ĩ í ĝ ġ ń ñ ö š ŝ ś û ů ŷ Á Ç) and other characters (e.g., 😀 ł đ ħ œ ⺆). 

Extend your solution so that it replaces and/or removes such Unicode characters in your text. Hint - use the API provided in the `ToASCII.java` class.  Try your solution on a text that contains Unicode.

#### Challenge for the bored 2.

Extend your solution to handle Unicode text, not just ASCII.

#### Challenge for the bored 3.

Imagine you receive a message where some of the characters have been corrupted by noise. We represent unknown characters by the ~ symbol (and assume the character ~ does not appear in the original text). Devise a scheme based on the Markov model to determine the most likely value for each corrupted character. Assume unknown characters are at least \\(k\\) characters apart (and appear at least \\(k\\) characters away from the start and end of the message). Test your new method by writing a client program `FixCorrupted.java` that takes as arguments the model order and the noisy string. The program prints the most likely original string:

```linux
Original:  it was the best of times, it was the worst of times.
Noisy:     it w~s th~ bes~ of tim~s, i~ was ~he wo~st of~times.

java-introcs FixCorrupted 4 "it w~s th~ bes~ of tim~s, i~ was ~he wo~st of~times." < wiki_100k.txt 
it was the best of times, it was the worst of times.
```




### **Enrichment**

{{< details "Click each item  to expand.">}} Click again to collapse the answer.{{< /details >}}


{{< details "What is the relationship between markov models and LLMs for generating text?">}}

We actually asked ChatGPT this question, and here's its response (slightly editted):

Markov models and Large Language Models (LLMs) are both used for generating text but operate on different principles.

Markov Models: These are based on the Markov property, which states that the future state of a system depends only on its present state, not on the sequence of events that preceded it. In the context of text generation, a Markov model predicts the next word in a sequence based current k-gram. 

Large Language Models (LLMs): These are more sophisticated models based on neural networks, particularly variants like GPT (Generative Pre-trained Transformer) models. LLMs leverage deep learning techniques to capture complex patterns and dependencies in text data. They are trained on large corpora of text and can generate coherent and contextually relevant sequences of text. Unlike Markov models, LLMs do not rely solely on fixed-length context windows but instead learn contextual representations of words and phrases based on their entire input sequence.

While both Markov models and LLMs can generate text, LLMs generally produce more fluent and contextually appropriate text due to their ability to capture long-range dependencies and contextual nuances. However, Markov models are simpler and more interpretable, making them suitable for certain text generation tasks, especially when computational resources are limited.
{{< /details >}}

{{< details "What is the origin of the Markov text generator?">}}

It was first described by Claude Shannon in 1948. The first computer version was apparently written by Don P. Mitchell, adapted by Bruce Ellis, and popularized by A. K. Dewdney in the Computing Recreations section of Scientific American. Brian Kernighan and Rob Pike revived the program in a University setting and described it as an example of design in The Practice of Programming. The program is also described in Jon Bentley's Programming Pearls.
{{< /details >}}


{{< details "What else can I do with a random text generator?">}}

One former COS 126 student recited its output during the [Frist filibuster](https://www.cnn.com/2005/POLITICS/05/10/princeton.filibuster/index.html).
{{< /details >}}

{{< details "Some other interesting topics.">}}

- We are implementing the model Shannon described in his landmark paper. But the _one reads until this letter is encountered_ method in his quote on the assignment page is, ironically, not a statistically accurate example of his model. If we run your program with input `wawawaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaawy` then `w` should be followed by `a` 75% of the time, while the _read until_ model will follow `w` with `a` only 15% of the time. What would be involved in simulating this other model? Which one do you think gives more realistic text?

-  Here's a website that generates [pseudo-random computer science papers](https://pdos.csail.mit.edu/archive/scigen/). It uses something called a _context-free grammar_ instead of a Markov chain, but otherwise is similar in spirit to what you are doing on this assignment.

- Here are [Garfield comics](http://joshmillard.com/garkov) generated by a Markov chain.

{{< /details >}}

*Copyright © 2005–2024 Robert Sedgewick and Kevin Wayne, All Rights Reserved*