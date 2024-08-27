---
title: 0. Hello, World 
subtitle: 
summary:   Install a Java programming environment, take a collaboration quiz, answer a survey, and then  write a few short Java programs, and submit them using TigerFile. <br> {{< submit "Hello" >}}<br>{{<notreleased>}}
weight: 1
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false
---

{{< submit "Hello" >}}



The purpose of this assignment is to introduce you to programming in Java and familiarize you with the mechanics of preparing and submitting assignment solutions. You will learn to use IntelliJ editor for writing, compiling, and executing Java programs, and TigerFile for submitting your work electronically.

<!--{{< semester "**Tjos**" >}}-->

### **Requirements**

- Install the COS 126 Java programming environment, called IntelliJ, on your computer.  Follow these step-by-step instructions carefully. If you encounter difficulties, please consult the undergraduate [Lab TAs](https://labta.cs.princeton.edu) schedule and go to Lewis Library for assistance. Don't be afraid to ask for help!  _Note - even if you have an existing version of IntelliJ, you should install the COS 126 version._

   - [Mac OS X](https://lift.cs.princeton.edu/java/mac/)
   - [Windows](https://lift.cs.princeton.edu/java/windows/)
   - [Linux](https://lift.cs.princeton.edu/java/linux/)

-  Use the *project folder* called `hello` that you downloaded after you installed IntelliJ. All assignments require that you download a project `zip` file and expand this this into a project folder. It’s a good idea to save this folder somewhere safe, along with all the folders you create for future assignments in the class.

- Read both Sections 1.1 and 1.2 of the textbook. If you don't understand something, post a question on Ed or attend office hours for assistance. Don't be bashful about asking for help.

- Collaboration Policy Quiz: Please read the course collaboration policy and take a short quiz on the policy (available in the Quizzes section on [Canvas](https://canvas.princeton.edu)).  You must repeat the quiz until you answer **all** questions correctly; otherwise, you will not receive credit for any programming assignment.

- Entry Survey: Complete the following brief [survey]({{< param surveyurl >}}). 

- Implement these programs:
	- `HelloWorld.java`
	- `Greeting.java`
	- `SayHelloWorld.java`
	- `SumThree.java`
<!--SPRING	- `Ordered.java` -->
<!--SPRING	- `RGBtoCMYK.java`-->
   Recall, in IntelliJ, you create a new Java class with *LIFT → New Java Class*.
   
- Complete the `readme.txt` and `acknowledgment.txt` files.


### **HelloWorld**

Your first programming task is to write the `HelloWorld` program. You have actually already completed this task: first, when when you installed IntelliJ, and  then again in lecture and precept, when you discussed `HelloWorld.java`.  

![HelloWorld.java]({{< resource url="static/assignments/hello/images//helloworld-intellij.png" >}})




<!--
### **DrawCat**

This exercise will give you experience identifying compile-time, run-time and logic errors in Java code.

ASCII Art is a technique that uses sequences of characters printed on successive lines to depict a graphic on non-graphical display.  The program `DrawCat.java` - included in your project folder -  uses ASCII Art to draw a cat. It takes two characters on the command-line - the first for the cat's eyes and the second for its mouth.  Using these command-line arguments, `DrawCat` should draw a cat, as shown below:

```java
java DrawCat @ +
          ^_^   
    ____( @ @)
  /~____  =+= /
 (______)__m_m)
 ```

Fix all the errors in the provided file `DrawCat.java`.  Your solution should have no errors, including *Checkstyle* warnings.

Background: This [website](https://asciiart.website) contains many interesting ASCII Art pics.
-->

### **Greeting**

Write a program `Greeting.java` that takes a greeting and two names as command-line arguments and prints the greeting as shown below (with the names for the first greeting in the same order as the command-line arguments and the names for the second greeting in reverse order).

```plaintext 
java Greeting Hi Sonia Samuel
```
```
Hi Sonia and Samuel.
Hi Samuel and Sonia.
```
```plaintext 
java Greeting Ciao Kellia Yacoub
```
```
Ciao Kellia and Yacoub.
Ciao Yacoub and Kellia.
```
```
java Greeting γεια Alexandria Homer
```
```
γεια Alexandria and Homer.
γεια Homer and Alexandria.
```
```plaintext
java Greeting "Nǐ hǎo" Ahmed Hagop
```
```
Nǐ hǎo Ahmed and Hagop.
Nǐ hǎo Hagop and Ahmed.
```


For greetings in a large number of languages, see [HelloWorldMultilingual.java](https://introcs.cs.princeton.edu/java/11hello/HelloWorldMultilingual.java.html)

### **SayHelloWorld**

Write a program `SayHelloWorld.java` that takes the name of a sound file (of someone speaking Hello World) and plays that sound file using `StdAudio.play()`. Recall, `StdAudio.play()` takes a string as an argument (such as `"HelloWorld.wav"`); treats that string as the name of a sound file; and plays that sound file to your speaker.

Please note that you will need to use `javac-introcs` and `java-introcs` commands (instead of the default `javac` and `java` commands) to compile and execute your programs.
These versions provide access to our course libraries, including `StdAudio`, which you will use in this program. To try these commands, in IntelliJ, select *LIFT > Terminal*. Compile your program using the `javac-introcs` command and execute it using the `java-introcs` command:

The `people`, `google` and `siri` subdirectories in the project directory contains a large number of WAV files of _Hello World_ spoken in different voices and languages, both real (from various individuals) and synthetic (from [Google Translate](https://translate.google.com) and [Apple Siri](https://www.apple.com/siri)). Below are some sample executions:

```plaintext 
javac-introcs SayHelloWorld.java
```

```plaintext
java-introcs SayHelloWorld people/KevinWayne.wav
```
{{< audio src="hello/people/KevinWayne" type="audio/wav" preload="auto" >}}


```plaintext
java-introcs SayHelloWorld people/AloeBlacc.wav
```
{{< audio src="hello/people/AloeBlacc" type="audio/wav" preload="auto" >}}

```plaintext
java-introcs SayHelloWorld google/Spanish.wav
```
{{< audio src="hello/google/Spanish" type="audio/wav" preload="auto" >}}


```plaintext
java-introcs SayHelloWorld siri/Kyoko.wav
```
{{< audio src="hello/siri/Kyoko" type="audio/wav" preload="auto" >}}


Also, record yourself speaking *Hello, World*, convert it to a WAV file named `MyHelloWorld.wav`, and submit that WAV file. You may speak *Hello, World* in whatever language you like.  Here are several ways to record and convert your audio:

   - Mac OS X:  *Voice Memos app  →  Record*. Drag the recording to a folder - this will create an M4A file. Then convert the M4A file to a WAV file via [Zamzar](https://zamzar.com)
   - Mac OS X:  *Quicktime app  →  File  →  New Audio Recording*. After making the recording, select *File  →  Save*. Then convert the M4A file to a WAV file via [Zamzar](https://zamzar.com)
   - Windows:  *Sound Recorder  →  Record*. After making the recording, select *Save As*. Then convert the WMA file to a WAV file via [Zamzar](https://zamzar.com)
   - iPhone:  *Voice Memo app  →  Record*. Then, copy the recording to your laptop and convert the M4A file to a WAV file via [Zamzar](https://zamzar.com)
   - Android: Use an app that allows you record audio.  Then, copy the audio file to your laptop and convert to a WAV via [Zamzar](https://zamzar.com)

<!-- SPRING
### **Ordered**

This exercise demonstrates the use of the `int` and `boolean` data types. Write a program `Ordered.java` that takes three integer command-line arguments, \\(x\\), \\(y\\), and \\(z\\). Define a `boolean` variable whose value is `true` if the three values are either in strictly ascending order \\((x < y < z)\\) or in strictly descending order \\((x > y > z)\\), and `false` otherwise. Then, print this `boolean` value.  Do not use a conditional statement or a loop statement (such as an `if`, `while`, or `for` statement) in your solution. 

Examples:

```plaintext 
java-introcs Ordered 10 17 49
true

java-introcs Ordered 49 17 10
true

java-introcs Ordered 10 49 17
false
```
### **RGBtoCMYK**

This exercise demonstrates the use of  type conversions. Several different formats are used to represent color. For example, the primary format for LCD displays, digital cameras, and web pages — known as the *RGB format* — specifies the level of red (R), green (G), and blue (B) on an integer scale from 0 to 255 inclusive. The primary format for publishing books and magazines — known as the *CMYK format* — specifies the level of cyan (C), magenta (M), yellow (Y), and black (K) on a real scale from 0.0 to 1.0 inclusive.

Write a program `RGBtoCMYK.java` that converts from RGB format to CMYK format. Your program must take three integer command-line arguments, red, green, and blue; print the RGB values; then print the equivalent CMYK values using the following mathematical formulas.

$$ white = max ({ \frac{red}{255}, \frac{green}{255}, \frac{blue}{255}}) $$

$$ cyan = (white - \frac{red}{255}) \div white $$

$$ magenta = (white - \frac{green}{255}) \div white $$

$$ yellow = (white - \frac{blue}{255}) \div white $$

$$ black = 1 - white $$

CMYK is a subtractive color space, because its primary colors are subtracted from white light to produce the resulting color: cyan absorbs red, magenta absorbs green, and yellow absorbs blue.

Examples: 

```plaintext 
# indigo                                                        # Princeton orange
java-introcs RGBtoCMYK 75 0 130                               java-introcs RGBtoCMYK 255 143 0
red     = 75                                                    red     = 255
green   = 0                                                     green   = 143
blue    = 130                                                   blue    = 0
cyan    = 0.423076923076923                                     cyan    = 0.0
magenta = 1.0                                                   magenta = 0.4392156862745098
yellow  = 0.0                                                   yellow  = 1.0
black   = 0.4901960784313726                                    black   = 0.0
```

*Restriction:* You may not use `if` statements on this assignment, but you may assume that the command-line arguments are not all simultaneously zero.

> Hint. Recall that `Math.max(x, y)` returns the maximum of `x` and `y`.
-->

### **SumThree**

Write a program `SumThree.java` that takes three integer command-line arguments and prints the three integers and their sum in the form of an equation. 

```plaintext
java-introcs SumThree 2 5 8
```
```
2 + 5 + 8 = 15
```

```plaintext
java-introcs SumThree -2 5 -8
```
```
-2 + 5 + -8 = -5
```

### **readme.txt**

Edit the text file named `readme.txt` that is a narrative description of your work. Each week, we provide a `readme.txt` file for you to download and use as a template, answering all questions in the space provided. Submit this file with your `.java` files.

### **acknowledgments.txt**

Submitting the `acknowledgments.txt` file indicates that you have stopped working on your assignment and your submitted work is ready to be graded.   The  `acknowledgments.txt` file contains two important sections:
1. The names and dates of those who helped you with this assignment. When you attend office hours or lab TAs sessions, you should introduce yourself and record the person's name. For example:<br>
   ```bash
   Grace Hopper (Faculty), 9/16/22
   John von Neumann (Lab TA), 9/16/22
   ```

2. The *Student Acknowledgment of Original Work* from [Rights, Rules, Responsibilities](https://rrr.princeton.edu) with your digital signature - `/s/` followed by your full name.  For example:<br>
   ```bash
   This programming assignment represents my own work in accordance with University regulations. /s/ Grace Hopper
   ```

### **Submission**

Submit `HelloWorld.java`, `Greeting.java`, `SayHelloWorld.java`, `Ordered.java`, `RGBtoCMYK.java`, and a completed `readme.txt`  to {{< tigerfile "Hello" >}}  Please do not include your name, netid and/or email address in your  `.java` and `readme.txt` files.</p>


Login using your OIT NetID (if necessary) and upload the specified files. Finally, click the *Check Submitted Files* button:

![TigerFile]({{< resource url="static/assignments/hello/images/tigerfile-check-submitted-files.png" >}})

This will compile and execute your programs, alerting you to potential problems before we grade your work. Fix any problems and resubmit. **You may submit one file at a time or several files at a time. You may submit as many times as you like!**

Also, complete and submit both the Collaboration Policy Quiz on  [Canvas](https://canvas.princeton.edu) and the [Entry Survey]({{< param surveyurl >}})

Complete and submit the `acknowledgments.txt` file when you have completed the assignment.

### **Grading**
| Files                | Points   |
| ---------------      | ---------|
| HelloWorld.java      |   8      |
| Greeting.java        |   8      |
| SayHelloWorld.java   |   8      |
| SumThree.java        |   8      |
| readme.txt           |   4      |
| Survey               |   4      |
| Total                |  40      |

You must repeat the Collaboration Policy Quiz until you answer **all** questions correctly; otherwise, you will not receive credit for any programming assignment.

Submissions without any comments will receive style deductions.
Code that is not properly formatted will also receive style deductions. (Hint - in IntelliJ, use Code → Reformat Code.)

### **Enrichment**

***Hello World in 200 Languages***.  Here is [Hello World](http://helloworldcollection.de) in over 200 different programming languages.
