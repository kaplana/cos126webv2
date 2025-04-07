---
title: 7. Guitar Hero 
subtitle: 
summary:  Simulate the plucking of a guitar string using the Karplus–Strong algorithm, transforming your computer into a musical instrument. <br>Partner assignment  {{< project "guitar" >}} |  {{< submit "Guitar" >}} <!--{{< notreleased >}}-->
weight: 8
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false
---
<!--{{<construction>}}-->
{{< project "guitar" >}} |  {{< submit "Guitar" >}}
### **Goals**

- To create user-defined data types in Java.
- To model and implement digital audio. Specifically to compute sound waveforms using a mathematical model of a musical instrument. 
- To learn about efficient data structures that are crucial for application performance.



### **Getting Started**

- Download and expand the  project `zip` file for this assignment, which contains the files you will need for this assignment.
- This is a **partner** assignment.  Instructions for help finding a partner and creating a TigerFile group can be found on Ed.

- The rules for partnering:
  - Choose a partner whose skill level is close to your own - only two partners per group.
  - Your partner does not have to be in your precept.
  - The rules for partnering are specified on the [course syllabus]({{< ref "syllabus.md#partners" >}}).  Make sure you read and understand these rules, and please post on Ed if you have questions. In your `acknowledgments.txt` file, you must indicate that you adhered to the COS 126 partnering rules.


### **Background**

For this assignment, you will write a program to simulate plucking a guitar string using the *Karplus–Strong algorithm*. This algorithm played a seminal role in the emergence of physically modeled sound synthesis (where a physical description of a musical instrument is used to synthesize sound electronically).

### **Approach / Design**

When a guitar string is plucked, the string vibrates and creates sound. The length of the string determines its *fundamental frequency* of vibration. We model a guitar string by sampling its *displacement* (a real number between –½ and +½) at \\(n\\) equally spaced points in time. The integer \\(n\\) equals the sampling rate (44,100 Hz) divided by the desired fundamental frequency, *rounded up* to the nearest integer.


- *Plucking the string.* The excitation of the string can contain energy at any frequency. We simulate the excitation with white noise: set each of the \\(n\\) displacements to a random real number between –½ and +½.

![White Noise]({{< resource url="static/assignments/guitar/images/white-noise.png" >}})


- *The resulting vibrations.* After the string is plucked, the string vibrates. The pluck causes a displacement that spreads wave-like over time. The Karplus–Strong algorithm simulates this vibration by maintaining a *ring buffer* of the \\(n\\) samples: the algorithm *repeatedly* deletes the first sample from the ring buffer and adds to the end of the ring buffer the average of the deleted sample and the first sample, scaled by an *energy decay* factor of 0.996. For example:

![Karplus-Strong averaging]({{< resource url="static/assignments/guitar/images/k-p-average.png" >}})


*Why does it work?* The two primary components that make the Karplus–Strong algorithm work are the *ring buffer feedback mechanism* and the *averaging operation*. (From a mathematical physics viewpoint, the Karplus–Strong algorithm approximately solves the 1D wave equation, which describes the transverse motion of the string as a function of time.)

1. *The ring buffer feedback mechanism.* The ring buffer models the medium (a string tied down at both ends) in which the energy travels back and forth. The length of the ring buffer determines the fundamental frequency of the resulting sound. Sonically, the feedback mechanism reinforces only the fundamental frequency and its harmonics (frequencies at integer multiples of the fundamental). The energy decay factor (0.996 in this case) models the slight dissipation in energy as the wave makes a round trip through the string.
1. *The averaging operation.* The averaging operation serves as a gentle low-pass filter (which removes higher frequencies while allowing lower frequencies to pass). Because it is in the path of the feedback, this has the effect of gradually attenuating the higher harmonics while keeping the lower ones, which corresponds closely to the sound that a guitar string makes when plucked.




### **Implementation Tasks**

#### Overall Requirements


- Implement three classes: 
	- `RingBuffer.java`
	- `GuitarString.java`
	- `GuitarHero.java`
- We provide two other classes:
	- `GuitarHeroLite.java`
	- `Keyboard.java`
- You **must** follow the prescribed API for each class. We will be testing the methods in the API directly. If your method has a different signature or does not behave as specified, you will lose a substantial number of points. 
- You **must not** add public methods to the API; however, you **may** add private instance variables or methods (which are only accessible in the class in which they are declared).

### **`RingBuffer`**

Your first task is to create a data type to model the ring buffer. Write a class named `RingBuffer` that implements the following API:

```java
public class RingBuffer {

     // Creates an empty ring buffer with the specified capacity.
     public RingBuffer(int capacity)

     // Returns the capacity of this ring buffer.
     public int capacity()

     // Returns the number of items currently in this ring buffer.
     public int size()

     // Is this ring buffer empty (size equals zero)?
     public boolean isEmpty()

     // Is this ring buffer full (size equals capacity)?
     public boolean isFull()

     // Adds item x to the end of this ring buffer.
     public void enqueue(double x)

     // Deletes and returns the item at the front of this ring buffer.
     public double dequeue()

     // Returns the item at the front of this ring buffer.
     public double peek()

     // Tests this class by directly calling all instance methods.
     public static void main(String[] args)
}
```

Since the ring buffer has a known maximum capacity, you can implement it using a `double` array of that length. For efficiency, use ***cyclic wrap-around***: maintain an integer instance variable `first` that stores the index of the least recently inserted item and a second integer instance variable `last` that stores the index one beyond the most recently inserted item.  For example, this is a ring buffer of size 4 with a capacity 10:

![a ring buffer of size 4 with a capacity 10]({{< resource url="static/assignments/guitar/images/ring-buffer.png" >}})

To insert an item, put it at index `last` and increment `last`. To remove an item, take it from index `first` and increment `first`. When the value of either index (`last`, `first`) equals `capacity`, make it wrap-around by changing the index to `0`. To determine the current number of valid entries in the ring buffer (and whether it is full or empty), you will also need a third integer instance variable `size`.  To determine the size of the ring buffer (and whether it is full or empty), you will also need a third integer instance variable `size` that stores the number of items currently in the ring buffer. 

<!-- Illustrating the `double` array as a *ring* helps understand the operation of the ring buffer.  For example, the diagram below shows a ring buffer with a capacity of four (4), containing a single value.-->

<!--![a ring buffer with a capacity of four, containing a single value]({{< resource url="static/assignments/guitar/images/r-b-diagram.png" >}})-->



#### Requirements

1. _Corner cases_: `RingBuffer` **must** throw a `IllegalStateException` with a custom message if a client calls either `dequeue()` or `peek()` when the ring buffer is *empty*, or calls `enqueue()` when the ring buffer is *full*.
1. _Performance_: The constructor **must** take time at most proportional to the capacity.  Every other method **MUST** take **constant** time.

### **`GuitarString`**

Create a data type to model a vibrating guitar string. Write a class named `GuitarString` that implements the following API:

```java
public class GuitarString {

    // Creates a guitar string of the specified frequency,
    // using a sampling rate of 44,100, where all samples are
    // initially set to 0.0. 
    public GuitarString(double frequency)

    // Creates a guitar string whose length and initial values
    // are given by the specified array.
    public GuitarString(double[] init)

    // Returns the number of samples in the ring buffer.
    public int length()

    // Returns the current sample.
    public double sample()

    // Plucks this guitar string by replacing the ring buffer with white noise.
    public void pluck()

    // Advances the Karplus-Strong simulation one time step.
    public void tic()

    // Tests this class by directly calling both constructors
    // and all instance methods.
    public static void main(String[] args)
}
```
#### Requirements

1. Your `GuitarString` must be implemented using the `RingBuffer` data type.  You should not use `Stack`, `Queue`, `Set` or other data types to represent a guitar string.
2. Constructors. There are two ways to create a `GuitarString` object.
   - The first constructor creates a `RingBuffer` of the desired capacity $n$ (the sampling rate $44,100$ divided by the frequency, rounded up to the nearest integer), and initializes it to represent a guitar string at rest by enqueuing $n$ zeros.
   - The second constructor creates a `RingBuffer` of capacity equal to the length $n$ of the array, and initializes the contents of the ring buffer to the corresponding values in the array. In this assignment, this constructor's main purpose is to facilitate testing and debugging.
3. `length()`. Return the number of samples $n$ in the ring buffer.
4. `pluck()`. Replace the $n$ items in the ring buffer with $n$ random values between $−0.5$ and $+0.5$.
5. `tic()`. Apply the Karplus–Strong update: delete the sample at the front of the ring buffer and add to the end of the ring buffer the average of the first two samples, multiplied by the energy decay factor.
6. `sample()`. Return the value of the item at the front of the ring buffer.

### **`GuitarHero` client**

Implement an interactive guitar player. `GuitarHeroLite.java` is a `GuitarString` client that plays the guitar in real time. It relies on a helper class `Keyboard.java` that provides a graphical user interface (GUI) to play notes using the keyboard.  When a user types the lowercase letter `p` or `v`, the program plucks the guitar string corresponding to middle C or concert A, respectively.  Since the combined result of several sound waves is the *superposition* of the individual sound waves, it plays the sum of the two string samples.

#### Requirements

1. Write a program `GuitarHero` that is similar to `GuitarHeroLite`. `GuitarHero` **must** support a total of 37 notes on the chromatic scale from 110 Hz to 880 Hz.
1. Use the following 37 keys to represent the keyboard, from lowest to highest note: <br> 
```java
String keyboardString = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
```

1. This keyboard arrangement imitates a piano keyboard: The *white keys* are on the qwerty and zxcv rows and the *black keys* are on the 12345 and asdf rows of the keyboard. <br>![Keyboard]({{< resource url="static/assignments/guitar/images/keyboard.png" >}})
1. Each character `i` of `keyboardString` corresponds to a frequency of
$$ 440 \times 2^{(i − 24) / 12} $$ Hz, so that the character `'q'` is 110 Hz, `'i'` is 220 Hz, `'v'` is 440 Hz, and the space character is 880 Hz. 
1. You **must not** declare 37 individual `GuitarString` variables and/or utilize 37-way `if` statements! Instead, create and initialize an array of 37 `GuitarString` objects and call `keyboardString.indexOf(key)` to determine which key was played.

### **Possible Progress Steps**

We provide some additional instructions below.  Click on the &#9658;  icon to expand *some possible progress steps* or you may try to code/test without them.  It is up to you!


{{< details "Click here to show possible progress steps for RingBuffer" >}}

#### Implementing `RingBuffer.java`
1. Implement `RingBuffer` in an incremental and iterative manner.  Edit your initial `RingBuffer.java` so that it can compile (recall the `Data.java` exercise from precept).  Then, implement *one constructor/method at a time*, together with at least one *corresponding test* in `main()`.  We provide _some_ test cases below.  You should also include your own test cases in `main()`, such as the ones from the precept worksheet.
2. We recommend defining the instance variables as follows:
```java
    private double[] rb;   // items in the buffer
    private int first;     // index for the next dequeue or peek
    private int last;      // index for the next enqueue
    private int size;      // number of items in the buffer
```
3. Implement the constructor for `RingBuffer`. You will need to allocate and initialize an array of `doubles` using the `new` operator. Observe that you have to do this in the constructor (and not when you declare the instance variables) because you do not know the length of the array until the constructor is called.
4. Test the constructor in `main()`.  You can run this test by trying various `RingBuffer` sizes specified on the command line.
```java
   int n = Integer.parseInt(args[0]); // get the size of n from the command-line
   StdOut.printf("Test #0 - create a RingBuffer object with %d\n", n);
   RingBuffer buffer = new RingBuffer(n);
```
5. Implement `capacity()` and then test it:
```java
   StdOut.printf("Test #1 - check capacity - should be %d\n", n);
   StdOut.printf("**** Capacity is %d\n", buffer.capacity());
```
6. Implement `size()` and then test it:
```java
   StdOut.printf("Test #2 - check size - should be %d\n", 0);
   StdOut.printf("**** Size is %d\n", buffer.size());
```
7. Implement `isFull()` and `enqueue()` and then test them:
```java
   StdOut.printf("Test #3 - perform %d enqueues: 1.0, 2.0, ...\n", n);
   StdOut.printf("**** isFull() should be false- is %b\n", buffer.isFull());
   for (int i = 1; i <= n; i++) { // enqueue n values:  1.0, 2.0, ...
       buffer.enqueue(i);
       StdOut.printf("Test #3.%d - check size after %d enqueues- should be %d\n",
                        i, i, i);
       StdOut.printf("**** Size is %d\n", buffer.size());
   }
   StdOut.printf("**** isFull() should be true- is %b\n", buffer.isFull());
```
8. Implement `isEmpty() and `peek()` and then test them:
```java
   StdOut.printf("Test #4 - check peek value == %.1f\n", 1.0);
   StdOut.printf("**** isEmpty() should be false- is %b\n", buffer.isEmpty());
   double val1 = buffer.peek();
   StdOut.printf("**** Value is %.1f\n", val1);
```
9. Implement `dequeue()` and then test it:
```java
   double val2 = buffer.dequeue();
   StdOut.printf("Test #5 - dequeue a value, then check value == %.1f and "
                      + "size == %d after a dequeue\n", 1.0, n - 1);
   StdOut.printf("**** Value is %.1f\n", val2);
   StdOut.printf("**** Size is %d\n", buffer.size());
   for (int i = 0; i < n - 1; i++) buffer.dequeue(); // dequeue everything left
   StdOut.printf("**** remove %d items and isEmpty() should be true- is %b\n",
                      n - 1, buffer.isEmpty());
```

***Is the size of a RingBuffer equal to its capacity?*** The size is the number of elements currently in it; the `capacity` is its maximum possible size. 

***Is the size of a RingBuffer equal to the number of its non-zero elements?***  No. Some of the elements in the RingBuffer can be zero. 

***How do I throw a `IllegalStateException` with a custom message?***  Read the *Exceptions* paragraph on pages 465–466 of the textbook. Also, see the precept exercises for examples.

***I get an `ArrayIndexOutOfBoundsException` or `NullPointerException`. What could cause this?*** Does your constructor correctly initialize all of the instance variables? Did you allocate memory for your array? Did you inadvertently re-declare an instance variable in a method or constructor, thereby shadowing the instance variable with the same name? 


{{< /details >}}

{{< details "Click here to show possible progress steps for GuitarString" >}}

#### Implementing `GuitarString.java`
0. Again, employ an _incremental/iterative implementation/testing/debugging_ approach.  Edit your initial `GuitarString.java` so that it can compile (recall the `Data.java` exercise from precept).  Then, implement *one constructor/method at a time*, together with at least one *corresponding test* in `main()`.
1. Implement and test the `GuitarString(double frequency)` constructor, which creates a `RingBuffer` of the desired capacity `n` (the sampling rate 44,100 divided by the frequency, rounded up to the nearest integer), and initializes it to represent a guitar string at rest by enqueuing `n` zeros. (Use [`Math.ceil`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Math.html#ceil(double)) and cast the result to an integer.)
2. Implement the `length()` method, which returns the number of samples in the ring buffer.
3. Implement the `sample()` method, which returns the value of the item at the front of the ring buffer. Use `peek()`.

***How can I test the `GuitarString(double frequency)` constructor and these methods?***
- In `main()`, instantiate a few different `GuitarString` objects using the `GuitarString(double frequency)` constructor.
- Invoke `length()` on each `GuitarString` object. Is the value returned by `length()` what you expect?
- Invoke `sample()` on each `GuitarString` object. Is the value returned by `sample()` what you expect? 

4. Implement and test the `GuitarString(double[])` constructor, which creates a `RingBuffer` of capacity equal to the length `n` of the array, and initializes the contents of the ring buffer to the corresponding values in the array. In this assignment, this constructor's primary purpose is to facilitate testing and debugging.

***How can I test the  `GuitarString(double[])` constructor and these methods?***
- Instantiate a few different `GuitarString` objects using the `GuitarString(double frequency)` constructor.
- Invoke `length()` on each `GuitarString` object. Is the value returned by `length()` what you expect?
- Invoke `sample()` on each `GuitarString` object. Is the value returned by `sample()` what you expect? 

5. Implement `pluck()`, which replaces the `n` items in the ring buffer with `n` _random_ values between −0.5 and +0.5. Use a combination of `RingBuffer` methods including `dequeue()` and `enqueue()` to replace the buffer with values between −0.5 and 0.5. Hint: [StdRandom](https://introcs.cs.princeton.edu/java/11cheatsheet/#StdRandom)

***When generating random values between −0.5 and +0.5, should I include the two endpoints?*** The assignment specification does not specify, so you are free to choose whichever convention you find most convenient. In Java, we typically include the left endpoint of an interval and exclude the right endpoint. For example, `StdRandom.uniformDouble(-0.5, 0.5)` generates a uniformly random value in the interval $[−0.5, 0.5)$ and `s.substring(i, j)` returns the substring of the `String s` beginning at index `i` and ending at index `j - 1`.

***How can I test the constructor and these methods?***
- Instantiate a `GuitarString` object.
- Invoke `length()` and `sample()` on this object.
- Next invoke `pluck()` on this object.
- Next invoke `length()` and `sample()` again on each object. Are the values returned what you expect?
- Try this with a few `GuitarString` objects.

6. Implement `tic()`. Use the `enqueue()`, `dequeue()`, and `peek()` methods. This method applies the Karplus–Strong update: delete the first sample from the ring buffer and adds to the end of the ring buffer the average of the deleted sample and the first sample, scaled by an energy decay factor. 

***How can I test the constructor and these methods?***
- Instantiate a `GuitarString` object.
- Invoke `length()` and `sample()` on this object.
- Next invoke `tic()` on this object.
- Next invoke `length()` and `sample()` again on each object. Are the values returned what you expect?
- Try this with a few `GuitarString` objects.
7. _After_ you have implemented and tested each of the methods for `GuitarString`, run `GuitarString` using the test client in the ***Testing*** section.

#### Testing Your `GuitarString.java` Implementation
You can test your `GuitarString`  data type using the following `main()`. Note - this test is *not* exhaustive, but it should help you debug.  

```java
int n = Integer.parseInt(args[0]);
StdOut.printf("Test #0 - create a Guitar String object with %d\n", n);
GuitarString gs1 = new GuitarString(n);

StdOut.printf("Test #1 - check length based on given n == %d\n", n);
StdOut.printf("**** Length is %d\n", gs1.length());

StdOut.printf("Test #2 - check sample is %.1f\n", 0.0);
StdOut.printf("**** Sample is %.1f\n", gs1.sample());

double[] samples = { -0.7, +0.8, -0.9, +0.6 };
GuitarString gs2 = new GuitarString(samples);
int len = samples.length;
StdOut.printf("Test #3 - check length based on given samples == %d\n",
              len);
StdOut.printf("**** Length is %d\n", gs2.length());

StdOut.printf("Test #4 - check sample is %.2f\n", samples[0]);
StdOut.printf("**** Sample is %.2f\n", gs2.sample());

gs2.pluck();
StdOut.printf("Test #5 - check length after pluck is still == %d\n",
              len);
StdOut.printf("**** Length is %d\n", gs2.length());

StdOut.printf("Test #6 - check sample is range [-0.5, +0.5)\n");
StdOut.printf("**** Sample is %.2f\n", gs2.sample());

GuitarString gs3 = new GuitarString(samples);
StdOut.printf("Test #7 - check sample is %.2f\n", samples[0]);
StdOut.printf("**** Sample is %.1f\n", gs3.sample());
gs3.tic();
StdOut.printf("Test #8 - check sample is %.2f\n", samples[1]);
StdOut.printf("**** Sample is %.2f\n", gs3.sample());

int m = 25; // number of tics
double[] moreSamples = { 0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3 };
StdOut.printf("Test #9 - test %d tics\n", m);
GuitarString gs4 = new GuitarString(moreSamples);
for (int i = 0; i < m; i++) {
    double sample = gs4.sample();
    StdOut.printf("%6d %8.4f\n", i, sample);
    gs4.tic();
}
```

Run this test from the command line with some input n:

```plaintext
java-introcs GuitarString 4
(Test #1 - Test #8 not shown)...

Test #9 - test 25 tics
     0   0.2000
     1   0.4000
     2   0.5000
     3   0.3000
     4  -0.2000
     5   0.4000
     6   0.3000
     7   0.0000
     8  -0.1000
     9  -0.3000
    10   0.2988
    11   0.4482
    12   0.3984
    13   0.0498
    14   0.0996
    15   0.3486
    16   0.1494
    17  -0.0498
    18  -0.1992
    19  -0.0006
    20   0.3720
    21   0.4216
    22   0.2232
    23   0.0744
    24   0.2232
```


{{< /details >}}

{{< details "Click here to show possible progress steps for GuitarHero" >}}

#### Implementing `GuitarHero.java`

1. Test `GuitarString` and `RingBuffer` using `GuitarHeroLite`. Which additional constructor and method does `GuitarHeroLite` test?
1. To implement `GuitarHero.java`, use the code in `GuitarHeroLite.java` as a starting point.
1. Create an array of `GuitarString` objects. Remember: to create an array of objects, you need to create the array, then you need to create each individual object.
1. Can you play individual notes? Do the notes have the correct frequencies? Can you play a chord?

***Where do I play notes in `GuitarHeroLite` and `GuitarHero`?*** Be sure that the interactive `Keyboard` window has focus by clicking it. Then, either click the piano keys or type the keystrokes.

***What happens if I call `StdAudio.play(x)` where `x` is greater than 1 or less than −1?*** The value is clipped – it is replaced by the value +1.0 or −1.0, respectively. This is fine and can happen when you play chords.

***Why do I get a `RuntimeException` or an `ArrayIndexOutOfBoundsException` in `GuitarHeroLite` before I type any keystrokes?*** Did you forget to initialize the ring buffer to contain `n` zeros in your `GuitarString` constructor?

***When I run `GuitarHeroLite` for the first time, I hear no sound. What am I doing wrong?*** Make sure you have tested with the `main()` provided for `GuitarString`. If that works, there is likely something wrong with `pluck()` since the `main()` provided for `GuitarString` does not test that method. To diagnose the problem, print the values of `sample()` and check that they become nonzero after you type the keystrokes `p` and `v`.

***When I run `GuitarHeroLite`, I hear static (either just one click and then silence, or continual static). What am I doing wrong?*** It's likely that `pluck()` is working, but `tic()` is not. The best test is to run the `main()` provided for `GuitarString`.

***How do I use `keyboardString.indexOf(key)`?*** If `keyboardString` is a `String` and `key` is a character, then `keyboardString.indexOf(key)` returns the integer index of the first occurrence of the character `key` in the `String keyboardString`, or `–1` if it does not occur.  You can read about it in the [`String` Javadoc page](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/String.html#indexOf(int)).

***Can I hardwire the constants 44,100, 0.996, 440.0, and 37 in my program?*** As usual, we may deduct style points for using an unnamed constant, especially if you use it more than once. We recommend using `SAMPLING_RATE` for `44100`, `DECAY_FACTOR` for `0.996`, `CONCERT_A` for `440.0`, and the expression `keyboardString.length()` for `37`. You do not need to name all of the constants in the formula:
\\[ 440 \times 2^{(i − 24)/12} \\]
{{< /details >}}

### **Rock on! Play your GuitarHero**

Here are some example tunes you can play on your GuitarHero. Try these below, and then compose some of your own:

1. An A major chord. 

   ```plaintext
	i - z    i
	         -
	         z
   ```

{{< audio src="guitar/audio/Amajor" type="audio/wav" preload="auto" >}}

2. An A major scale.

    ```plaintext
	i o - [ z d f v   v f d z [ - o i
    ```
{{< audio src="guitar/audio/Ascale" type="audio/mp3" preload="auto" >}}


3. Type the following into your guitar to get the beginning of Led Zeppelin's *Stairway to Heaven*. Multiple notes in a column are dyads and chords. 
    ```plaintext
                                                  w q q
            8       u       7       y             o p p
    i p z v b z p b n z p n d [ i d z p i p z p i u i i
    ```

{{< audio src="guitar/audio/sth" type="audio/wav" preload="auto" >}}

4. What is this familiar melody? (**S** == space)
    ```plaintext
    nn//SS/ ..,,mmn //..,,m //..,,m nn//SS/ ..,,mmn
    ```


### **Submission**

Submit to {{< tigerfile "Guitar" >}}: `RingBuffer.java`, `GuitarString.java`, `GuitarHero.java`, completed `readme.txt`  and `acknowledgments.txt`. 

### **Grading**
| Files                | Points    |
| ------------------   | ----------|
| RingBuffer.java      |  12       |
| GuitarString.java    |  12       |
| GuitarHero.java      |  12       |
| readme.txt           |   4       |
| Total                |  40       |

### **Challenge**

Write a program `AutoGuitar.java` that will automatically play music using `GuitarString` objects.

#### Requirements

1. You **may** use a `.txt` file and read it using the [`In`](https://introcs.cs.princeton.edu/java/11cheatsheet/#In) data type.
1. The duration of your composition **must** be between 10 and 120 seconds. Since the sampling rate is 44,100 Hz, this translates to between 441,000 and 5,292,000 calls to `StdAudio.play()`. 
1. You **may** create chords, repetition, and phrase structure using loops, conditionals, arrays, and functions. Also, feel free to incorporate randomness. 
1. You **may** also create a new music instrument by modifying the Karplus–Strong algorithm; consider changing the excitation of the string (from white noise to something more structured) or changing the averaging formula (from the average of the first two samples to a more complicated rule) or anything else you might imagine. See below for some ideas.

### **Enrichment**

{{< details "Click each item  to expand.">}} Click again to collapse the answer.{{< /details >}}

<!-- Need to update
{{< details "**Challenge 2**: Write a program MidiGuitarHero that is similar to GuitarHero, but works with a MIDI keyboard controller keyboard or a MIDI Controller app running on your smartphone. The MIDI standard - Musical Instrument Digital Interface - is used by many electronic musical instruments.">}}

#### Background
You will write a program `MidiGuitarHero.java` that is similar to `GuitarHero.java`, but works with a MIDI keyboard controller keyboard or a MIDI keyboard controller app (running on your smartphone or tablet connected to your laptop).  MIDI (Musical Instrument Digital Interface) is a technical standard for digital music. More details can be found on the [Wikipedia](https://en.wikipedia.org/wiki/MIDI) page.


#### Approach

The `MidiSource.java` class, provided in the assignment project folder,   maintains a *queue of short MIDI messages*. See [javax.sound.midi.ShortMessage](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/sound/midi/ShortMessage.html) for details. The basic steps are: 

1. Connect a MIDI controller keyboard or app to a computer using USB.  See the figure below.
1. Implement a client, `MidiGuitarHero`, that uses the `MidiSource` and `GuitarString` classes.
1. Create an array of `GuitarString` objects, where each `GuitarString` object corresponds to a key on a MIDI controller keyboard or app. 
1. Create a `MidiSource` object (with `connectToSynth` set to `false`).
1. Start the `MidiSource` object so it can generate short MIDI messages each time a key is pressed/tapped. A MIDI message contains  an integer code that corresponds to a pressed key. 
Access and interpret each short MIDI message generated.  Similarly to  `GuitarHero`, `pluck` the appropriate string, then sum all the samples, `play` the resulting sample, then `tic` all the strings 

![Midi controller connected to computer via usb]({{< resource url="static/assignments/guitar/images/MIDI-USB-Computer.png" >}})

#### `MidiSource.java` API

```java
public class MidiSource {

   // Creates a MidiSource object that listens to the first found connected 
   // Midi input device.  Set verbose to true for verbose output (standard out).
   // Set  connectToSynth to true use Java’s MIDI player; false to use the
   // GuitarString sound
   public MidiSource(boolean verbose, boolean connectToSynth) 
                                                                                            
  
   // Starts the MidiSource object so it can produce messages.
   public void start()               
                                          
   // Closes the MidiSource object.
   public void close()     
                                                     
   // Does the MidiSource have more messages (keys pressed)?
   public boolean isEmpty()    
                                                 
   // Returns  code of the MIDI Controller key pressed
   public int nextKeyPressed()

   // Demonstrates the MidiSource class - playing from a
   // keyboard or a Midi file - using the default Java synthesizer
   public static void main(String[] args)   
                                    
}
```

#### Implementation Tasks

#### `MidiGuitarHero.java`
1. Connect your Midi keyboard controller (via USB) to your laptop - see below for using a smartphone or tablet.
2. On the command line terminal, enter the command:
    ```bash
    java-introcs MidiSource -p
    ```
3. Press some keys on your MIDI keyboard controller. You should see output similar to:
    ```
    MidiSource version .2
    DEVICE 0: Gervill, OpenJDK, Software MIDI Synthesizer, Midi device available, Not a MIDI keyboard controller, trying next...
    DEVICE 1: Port1, YAMAHA, YAMAHA PSR-1100 Port1, Midi device available, Valid MIDI controller connected.
    ShortMessage:  Command: NOTE_ON (144)  Channel: 0 Number:   77 Velocity: 21
    ShortMessage:  Command: NOTE_ON (144)  Channel: 0 Number:   77 Velocity: 0
    ```
4. Each time you press a key on the MIDI keyboard controller, a MIDI message is produced. The message contains:
   -  the command type
   -  the channel
   -  the data1 value
   -  the date2 value
   - (For details, see [javax.sound.midi.ShortMessage](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/sound/midi/ShortMessage.html))
5. For the most part, you will want to capture messages whose command type is `NOTE_ON`. The `data1` value corresponds to the key number (similarly to the index in the `String` keyboard in `GuitarHero.java`) and the `data2` value corresponds to the velocity of the pressed key. (Some MIDI controllers generate a `NOTE_ON` and a velocity value of 0 when releasing a key - you will want to ignore those messages.)
6. The general structure of your code should look like this:
   - Create an array of `GuitarString` objects. The size of the array depends on how many notes you want to be able to produce.
   - You need to generalize the formula for the frequency of each `GuitarString` object, i.e., \\(440 \times 2^{(k − 24) / 12}\\).   One way to do this is to determine the key number of A4 (concert A or 440 Hz).
   - For example, here’s an 88-key piano keyboard with A4 (and C4) labeled: ![https://en.wikipedia.org/wiki/Piano_key_frequencies]({{< resource url="static/assignments/guitar/images/MIDI-keyboard.png" >}})
   - Create and start the `MidiSource` object.
   - Similarly to `GuitarHero`, write a loop that gets the next short Midi message. Interpret the message, and if it's a `NOTE_ON` with a velocity *greater than zero (0)*, `pluck` the appropriate `GuitarString` object.  Then compute the sum  of the samples, `play` the sum and `tic` the `GuitarString` objects.
   

#### Additional Challenges!


1. When you execute the following command, try  pressing other buttons on your Midi keyboard controller. By understanding other MIDI codes (e.g., `CONTROL_CHANGE`), you can use your Midi keyboard controller to change instruments. Of course, you need to create new music instruments by modifying the Karplus–Strong algorithm. 
    ```bash
    java-introcs MidiSource -p
    ```
2. The MidiSource class can also generate MIDI messages from MIDI files. You can experiment by downloading chopstik.mid and executing the command:
    ```bash
    java-introcs MidiSource -p chopstik.mid
    ```
    As you can see, different channels correspond to different instruments. If you have implemented multiple instruments, you can write an `MidiAutoGuitar.java` that plays your instruments from an existing MIDI file.

#### MIDI Controllers

#### MIDI Controller Keyboard and USB

1. Almost any MIDI keyboard controller with a USB interface will suffice (for example, a 25-key MIDI keyboard controller can be purchased on Amazon for about $40).
2. Connect the MIDI keyboard to your laptop.
3. In the terminal window, execute the command:
    ```bash
    java-introcs MidiSource
    ```
4. Press the keys on the keyboard to see the MIDI messages (numbers) generated by each key.![MIDI messages]({{< resource url="static/assignments/guitar/images/java-introcs_MidiSource.png" >}})

#### iPhone with iOS, Mac OS and USB 
1. Download/install the [MIDI controller app](https://apps.apple.com/us/app/midi-wrench/id589243566) from the Apple App Store.
2. Connect your iPhone/iPad to your Mac laptop using the USB connector.
3. On your Mac:
    - Start the Audio Midi Setup application (in the Applications/Utilities folder)
    - Under Window/Show Audio Devices and enable your iPhone
    - In the terminal window on your Mac laptop, execute the command:
    ```bash
    java-introcs MidiSource
    ```
4. Start the MIDI controller app.
5. Press the keys on the MIDI Controller app to see the MIDI messages (numbers) generated by each key.

#### Android and USB

1. Download/install/start the [MIDI controller app](https://play.google.com/store/apps/details?id=com.mobileer.midikeyboard) from the Google Play Store.
2. Connect your Android device to your laptop using the USB connector. 
3. On your Android  device: 
    - Show the notification screen by dragging down from the top of the display.
    - Tap Use device MIDI. (Different versions of Android may require different settings.)
    - In the terminal window on your laptop, execute the command:
    ```bash
    java-introcs MidiSource
    ```
5. On the MIDI Controller app, tap Receivers for Keys and select Android USB Peripheral Port.
6. Press the keys on the MIDI Controller app to see the MIDI messages (numbers) generated by each key.




{{< /details >}}
-->



{{<details "**Synthesizing Additional Instruments**">}} Here are some approaches for synthesizing additional instruments. Some come from the [paper](http://www.jstor.org/stable/10.2307/3680062) of Karplus and Strong.

- Harp strings: Flipping the sign of the new value before enqueueing it in `tic()` will change the sound from guitar-like to harp-like. You may want to play with the decay factors to improve the realism, and adjust the buffer sizes by a factor of two since the natural resonance frequency is cut in half by the `tic()` change.
- Drums: Flipping the sign of a new value with probability 0.5 before enqueueing it in `tic()` will produce a drum sound. A decay factor of 1.0 (no decay) will yield a better sound, and you will need to adjust the set of frequencies used.
- Guitars play each note on one of six physical strings. To simulate this you can divide your `GuitarString` instances into six groups, and when a string is plucked, zero out all other strings in that group.
- Pianos come with a damper pedal which can be used to make the strings stationary. You can implement this by changing the decay factor on iterations where a certain key (such as Shift) is [held down](http://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html#isKeyPressed(int)).
- While we have used [equal temperament](http://en.wikipedia.org/wiki/Equal_temperament), the ear finds it more pleasing when musical intervals follow the small fractions in the [just intonation](http://en.wikipedia.org/wiki/Just_intonation) system. For example, when a musician uses a brass instrument to play a [perfect fifth](http://en.wikipedia.org/wiki/Perfect_fifth) harmonically, the ratio of frequencies is \\( 3/2 = 1.5 \\) rather than \\(2^{7/12} ∼ 1.498 \\). Write a program where each successive pair of notes has just intonation. {{< /details>}}

{{<details "**Related Work**" >}} 

- *ChucK*. [ChucK](http://chuck.cs.princeton.edu/) is a specialized programming language for real-time synthesis, composition, and performance originated by [Ge Wang](https://ccrma.stanford.edu/~ge) and [Perry Cook](http://www.cs.princeton.edu/~prc/) at Princeton University. Here's the [Karplus–Strong algorithm in ChucK](http://chuck.cs.princeton.edu/doc/examples/deep/plu.ck).
- *Slide flute*. Here's a description of a physically modeled [slide flute](http://ccrma.stanford.edu/software/clm/compmus/clm-tutorials/pm.html) by Perry Cook.
- *Electric guitar synthesis*. In COS 325/MUS 315 *Transforming Reality by Computer*, Charlie Sullivan '87 extended the Karplus–Strong algorithm to synthesize electric guitar timbres with distortion and feedback. Here's his Jimi Hendrix-ified version of [The Star Spangled Banner](https://www.cs.princeton.edu/courses/archive/spr20/cos126/assignments/guitar/autoguitar/CharlieSullivanStar.mp3).{{< /details>}}



*This assignment was developed by Andrew Appel, Jeff Bernstein, Alan Kaplan, Maia Ginsburg, Ken Steiglitz, Ge Wang, and Kevin Wayne.*

*Copyright © 2005–2025*

