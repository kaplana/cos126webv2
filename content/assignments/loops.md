---
title: 1. Conditionals & Loops 
subtitle: 
summary: The goals of this assignment are to deepen your understanding of loops and conditionals and to learn how to debug code.  {{< project "loops" >}} |  {{< submit "Loops" >}}
weight: 2
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false
---
{{<construction>}}

{{< project "loops" >}} |  {{< submit "Loops" >}}

### **Goals**
- To deepen your understanding of conditionals and loops.
- To  learn how to debug code.

### **Background**
This assignment is based on the concepts and techniques described in Section 1.3 of the textbook (and the corresponding lectures and precepts). You may also find it instructive to work through some of the other exercises and look at the solutions on the [booksite](https://introcs.cs.princeton.edu/java/home) afterwards.


### **Getting Started**

- Download and expand the zip file, which will create the folder `loops`. We recommend this folder be placed in a `cos126` folder.
- Open the `loops` folder (i.e., the project) in IntelliJ.
- Implement these programs:
   - `SayMilitaryTime.java`
   - `RandomWalkers.java`
   - `Duotone.java`
   - `MusicalDiceGame.java`
- Recall: To create a new Java class files,  use LIFT → New → Java Class.
- Complete a `readme.txt`.

### **Requirements**

- You may not call library functions except those in
   - `java.lang` such as `Integer.parseInt()` and `Math.random()`,
   -  [`StdAudio`](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdAudio) and [`StdPicture`](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdPicture).
- You may not use Java features that have not yet been introduced in the course (such as arrays or functions).


### **Say Military Time**

*Military time* is a notation for specifying time of day using the form `hhmm`, where `hh` denotes the number of full hours that have passed since midnight and `mm` denotes the number of full minutes that have passed since the last full hour. For example, `1200` is noon; `2359` is one minute before midnight; and `0100` is one hour after midnight.

To speak and read military time, use the following conventions:

- Say the *number of hours* `hh`; if it is between 1 and 9, precede it with the word *zero*.
- Say the word *hundred*.
- Say the *number of minutes* `mm` (unless it is 00).
- Say the word *hours*.

For example, `2359` is spoken as *twenty-three hundred fifty-nine hours* and `0100` is spoken as *zero one hundred hours*.

Write a program `SayMilitaryTime.java` to say a given military time using these conventions. Your program must take two command-line arguments:

- *Time.* The first command-line argument is an integer that represents the time using military notation, such as 1200, 2359, or 0100.

- *Voice.* The second command-line argument is a string representing the voice for speaking the time. The subdirectories `Alex`, `Amélie`, `Luca`, `Paulina`, `Samantha`, `Veena`,  and `Siri` contain voice recordings of people saying the numbers 0 through 59, along with the words for hundred and hours. For example, `Alex/59.wav` is Alex saying the number *59* and `Siri/hundred.wav` is Siri saying the word *hundred*.

Below are some sample executions:
```plaintext
java-introcs SayMilitaryTime 2359 Alex
```
{{< audio src="loops/audio/Alex2359" type="audio/wav" preload="auto" >}}
```plaintext
java-introcs SayMilitaryTime 1100 Siri
```
{{< audio src="loops/audio/Siri1100" type="audio/wav" preload="auto" >}}
```plaintext
java-introcs SayMilitaryTime 0609 Veena
```
{{< audio src="loops/audio/Veena0609" type="audio/wav" preload="auto" >}}
```plaintext
java-introcs SayMilitaryTime 0011 Paulina
```
{{< audio src="loops/audio/Paulina0011" type="audio/wav" preload="auto" >}}


You can access the `StdAudio` library [here](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdAudio).

Background: The 24-hour clock is used widely in Asia, Europe, Africa, and Latin America.  In the United States, it is used in the military, aviation, navigation, tourism, meteorology, astronomy, computing, logistics, and hospitals. 

### **Random Walkers**

This exercise will give you experience identifying compile-time, run-time and logic errors in Java code.

The project folder contains a program called [`RandomWalkers.java`](static/assignments/loops/files/RandomWalkers.java) that takes two integer command-line arguments: the first argument is  \\(n\\) and the second argument is \\(trials\\). In each of \\(trials\\) independent experiments, the program is supposed to simulate a *random walk* of \\(n\\) steps and compute the squared distance for each trial. The program then outputs the mean squared distance (the average of the trials’ squared distances).

Each random walk starts at the origin \\((0, 0)\\). At each time step, the walker moves one meter in a random direction, either north, east, south, or west, with probability 25%.  The random walk finishes at some postion \\((x, y)\\). The squared distance for each trial is computed using the formula: \\(x^2 + y^2\\).

![Random Walk]({{< resource url="static/assignments/loops/images/random-walk.png" >}})



Here are sample executions of the corrected `RandomWalkers`, with the expected output.

```bash
java-introcs RandomWalkers 100 10000
mean squared distance = 101.446

java-introcs RandomWalkers 100 10000
mean squared distance = 99.1674

java-introcs RandomWalkers 200 1000  
mean squared distance = 195.75

java-introcs RandomWalkers 200 1000  
mean squared distance = 198.21

java-introcs RandomWalkers 1600 100000      
mean squared distance = 1600.13064

java-introcs RandomWalkers 1600 100000      
mean squared distance = 1603.72
```

Fix all errors in the provided file `RandomWalkers.java`.  Your solution should have no errors or warnings, including from Checkstyle.


Background: This process is a discrete version of a natural phenomenon known as *Brownian motion*. It serves as a scientific model for an astonishing range of physical processes, from the dispersion of ink flowing in water, to the formation of polymer chains in chemistry, to cascades of neurons firing in the brain.




### **Duotone Filter**

A *duotone filter* involves using two colors to create a new image.  In particular, a duotone filter is a way to reproduce an image, using combinations of only two ink colors, \\(color_1\\) and \\(color_2\\). It is a popular effect for photographers and digital artists. (Here's a nice [site](https://medialoot.com/duotones) for experimentation.)


To apply a duotone filter to an image, consider each pixel of a source image one at a time:

- Let \\((r, g, b)\\) denote the *red*, *green*, and *blue* components, respectively, of the pixel. Each component is an integer between \\(0\\) and \\(255\\). 
- Let \\((r_1, g_1, b_1)\\) and  \\((r_2, g_2, b_2)\\) denote the *red*, *green*, and *blue* components of \\(color_1\\) and  \\(color_2\\), respectively. 
- Change the color of the pixel from \\((r, g, b)\\) to \\((r^\prime, g^\prime, b^\prime)\\) by applying the following formulas:

  - First, compute the *monochrome luminance* of the given color as an intensity between \\(0.0\\) and \\(255.0\\) using the [NTSC](https://en.wikipedia.org/wiki/NTSC) formula:
     - \\(luminance = (0.299r + 0.587g + 0.114b) / 255.0 \\)
  - Then, compute  \\(r^\prime, g^\prime, b^\prime\\) using the formulas:
     - \\(r^\prime = luminance \times  r_1 + (1 - luminance) \times  r_2\\)
     - \\(g^\prime = luminance \times  g_1 + (1 - luminance) \times  g_2\\)
     - \\(b^\prime = luminance \times  b_1 + (1 - luminance) \times  b_2\\)
     - When computing  \\(r^\prime, g^\prime, b^\prime\\), round the results to the nearest integer, so that \\(r^\prime\\), \\(g^\prime\\), and \\(b^\prime\\) are integers between \\(0\\) and \\(255\\).

Write a program `Duotone.java` that takes the name of an image file as a command-line argument, along with the values for  \\(r_1, g_1, b_1, r_2, g_2, b_2\\), and applies a duotone filter to that image, and displays the results in a window. Use `StdPicture` to read, modify, and display the picture.

For example, given a photo of Johnson Arch:

![Johnson Arch]({{< resource url="static/assignments/loops/images/JohnsonArch.jpg" >}})

executing the command:

```plaintext
java-introcs Duotone JohnsonArch.jpg 245 128 37 0 0 0
```
produces the image:

![Duotone Johnson Arch]({{< resource url="static/assignments/loops/images/JohnsonArch-duotone.jpg" >}})

> In this sample execution, \\(color_1\\) is *Princeton orange* (245, 128, 37) and \\(color_2\\) is *black* (0, 0, 0).

Also, take a photo of a building, archway, gate, or other structure on campus and apply the duotone filter to that image. Submit a JPEG file named `MyPhoto.jpg` of the source (original) image, and  include the values you used to generate the duotone version of  `MyPhoto.jpg` in your `readme.txt`.

You can access the `StdPicture` library [here](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdPicture).

> FAQ: How do I round a `double x` to the nearest integer? Use `(int) Math.round(x)`.


<!--Background: NTSC (National Television System Committee) is a US standardization body that was established in 1940 to standardize analog television broadcasting. NTSC was eventually replaced by the Advanced Television Systems Committee, which maintains the digital television broadcasting standards.-->


### **Musical Dice Game**

A [Musikalisches Würfelspiel](https://en.wikipedia.org/wiki/Musikalisches_Würfelspiel) is a process for generating music randomly by concatenating a sequence of precomposed musical elements. In this exercise, you will compose a Viennese waltz in the style of Mozart by playing a sequence of 32 precomposed measures that are chosen according to the random process described below. The *waltz* has two parts — the *minuet*, followed by the *trio*. 

- The *minuet* consists of a sequence of 16 measures (numbered 0 to 15), each chosen at random. To determine which WAV file to play for measure \\(i\\), roll *two fair dice*. If the sum is \\(s\\), then play `minueti-s.wav`. For example, if you roll an 8 for the measure 3, then play `minuet3-8.wav`. Note that each roll has 11 possible outcomes (2 through 12) but some outcomes (such as 7) are more likely than others (such as 2 or 12).

![Two Fair Die]({{< resource url="static/assignments/loops/images/two-fair-die.png" >}})


- The *trio* consists of a sequence of 16 measures (numbered 0 to 15), each chosen at random. To determine which WAV file to play for measure \\(i\\), roll one fair die.  If the result is \\(s\\), then play `trioi-s.wav`. For example, if you roll a 6 for the measure 15, then play `trio15-6.wav`. 

![One Fair Die]({{< resource url="static/assignments/loops/images/one-fair-die.png" >}})



The `mozart-piano` subdirectory of the project file contains the 272 WAV files (11 × 16 = 176 for the minuet measures and 6 × 16 = 96 for the trio measures), using the naming conventions described above. Other subdirectories (`mozart-clarinet`, `mozart-flute-harp`, and `mozart-mbira`) correspond to different musical instruments.

Write a program `MusicalDiceGame.java` that plays a waltz according to the rules above. Your program should take one string as a command-line argument. That string specifies the name of a subdirectory containing the WAV files, such as `mozart-piano`. Here are some sample executions:


```plaintext
java-introcs MusicalDiceGame mozart-piano
```
{{< audio src="loops/audio/waltz-piano1" type="audio/wav" preload="auto" >}}
```plaintext
java-introcs MusicalDiceGame mozart-piano
```
{{< audio src="loops/audio/waltz-piano2" type="audio/wav" preload="auto" >}}
```plaintext
java-introcs MusicalDiceGame mozart-clarinet
```
{{< audio src="loops/audio/waltz-clarinet" type="audio/wav" preload="auto" >}}
```plaintext
java-introcs MusicalDiceGame mozart-flute-harp
```
{{< audio src="loops/audio/waltz-flute-harp" type="audio/wav" preload="auto" >}}
```plaintext
java-introcs MusicalDiceGame mozart-mbira
```
{{< audio src="loops/audio/waltz-mbira" type="audio/wav" preload="auto" >}}


Context: each time you run the program you will compose a piece of music that has never been heard before! The precomposed measures obey a rigid harmonic structure so that each waltz reflects Mozart's distinct style. However, due to this rigid structure, the process never results in anything truly extraordinary.

### **Submission**
Submit the files `Military.java`, `RandomWalkers.java`, `Duotone.java`, `MyPhoto.jpg` and `MusicalDiceGame.java`, along with  completed `readme.txt` and `acknowledgments.txt` files to {{< tigerfile "Loops" >}}. 

### **Grading**
| Files                 | Points    |
| --------------------  | ----------|
| RandomWalkers.java    |  9        |
| SayMilitaryTime.java  |  9        |
| Duotone.java          |  9        |
| MusicalDiceGame.java  |  9        |
| readme.txt            |  4        |
| Total                 |  40       |

This assignment was developed by [Kevin Wayne](http://www.cs.princeton.edu/~wayne). Copyright © 2022–2024.