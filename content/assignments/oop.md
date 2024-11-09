---
title: 5. Object-Oriented Programming
subtitle: 
summary:  Gain experience with using and creating data types. <br>Partner assignment   {{< project "oop" >}} |  {{< submit "OOP" >}}
weight: 6
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false

---
{{< project "oop" >}} |  {{< submit "OOP" >}}

### **Goals**

- To learn about object-oriented programming concepts.
- To gain experience using data types, such as `Picture`, `Color` and `In`.




### **Getting Started**

- Read/scan this entire project description before starting to code.  This will provide a _big picture_ before diving into the assignment!

- Download and expand the  project `zip` file for this assignment, which contains the files you will need for this assignment.


- Read Sections 3.1 and 3.2 of the textbook to learn the basics of object-oriented programming.
- Review [`Picture`](https://introcs.cs.princeton.edu/java/11cheatsheet/#Picture) data type.
- Review the [`Color`](https://introcs.cs.princeton.edu/java/11cheatsheet/#Color) data type.
- Review the [`In`](https://introcs.cs.princeton.edu/java/11cheatsheet/#In) data type.

- This is a **partner** assignment.  Instructions for help finding a partner and creating a TigerFile group can be found on Ed.

- The rules for partnering:
  - Choose a partner whose skill level is close to your own - only two partners per group.
  - Your partner does not have to be in your precept.
  - Complete *all* work with your partner, sharing the same screen. This includes debugging, testing, commenting, writing the `readme.txt` and `acknowledgments.txt`, and submitting the files.
  - You and your partner must work together on *all* components. You may not split up the work.  One partner may not start the assignment without the other partner present.
  - Go to office hours or the Lab TAs with your partner; one partner may not edit the code without the other partner present.
  - Use the following pair programming protocol: one partner, the driver, types the code and the other partner, the navigator, reviews the code, identifies bugs, and asks questions. Swap roles every thirty (30) minutes.
  - Do not post your code where it is visible to anyone but you and your partner. Code can be shared using Google Drive, Dropbox, etc.
  - If you are working with a partner, any late penalties apply to the group. The number of free late days a group can use is the minimum of the number of free late days each member has remaining. So you may want to discuss this before you form a partnership.
  - You and your partner must indicate that you both adhered to the partner collaboration rules in the `acknowledgments.txt` file.
  - To dissolve a partnership, you must first contact the course administrator.





### **Implementation Tasks**

- Implement four Java classes:
  - `Tile.java`
  - `Tracker.java`
  - `Note.java`
  - `PlayTheseNotes.java`
- Submit a `music.txt` file (optional).
- Submit a completed `readme.txt` file.
- Submit a completed `acknowledgments.txt` file.


### **Rectangular tile of an image**

Write a program to create a _rectangular tile of an image_. A rectangular tile of an image is created by repeating copies of an image in a rectangular grid, with a specified number of columns and rows. For example, consider the following image:


<img src = "../../static/assignments/oop/images/heart.png" width = 63 alt = "1-by-1 heart">

 Here is a 6-by-3 rectangular tile: 

<img src = "../../static/assignments/oop/images/heart6x3.png" width = 378 alt = "6-by-3 heart">

Write a program `Tile.java` that takes three command-line arguments (the file name of the image, the number of columns, and the number of rows) and displays a rectangular tile of the image, as described above. Using the [`Picture` API](https://introcs.cs.princeton.edu/java/11cheatsheet/#Picture) data type, organize your program using the following API:

```java
public class Tile {

    // Returns a cols-by-rows tiling of the specified image.
    public static Picture tile(Picture picture, int cols, int rows)

    // Takes three command-line arguments (the name of the image, the number
    // of columns, and the number of rows), and displays a rectangular tiling
    // of the image with the specified number of columns and rows.
    public static void main(String[] args)
}
```

Here are some sample executions:

```plaintext
~/Desktop/oop> java-introcs Tile heart.png 1 1
```
<img src = "../../static/assignments/oop/images/heart.png" width = 63 alt = "1-by-1 heart">


```plaintext
~/Desktop/oop> java-introcs Tile heart.png 6 3
```
<img src = "../../static/assignments/oop/images/heart6x3.png" width = 378 alt = "6-by-3 heart">

```plaintext
~/Desktop/oop> java-introcs Tile princeton.png 1 1 
```
<img src = "../../static/assignments/oop/images/princeton.png" width = 100 alt = "1-by-1 Princeton">

```plaintext
~/Desktop/oop> java-introcs Tile princeton.png 5 2
```
<img src = "../../static/assignments/oop/images/princeton5x2.png" width = 500 alt = "5-by-2 Princeton">

```plaintext
~/Desktop/oop> java-introcs Tile bricks.png 1 1 
```
<img src = "../../static/assignments/oop/images/bricks.png" width = 90 alt = "1-by-1 bricks">


```plaintext
~/Desktop/oop> java-introcs Tile bricks.png 9 4
```
<img src = "../../static/assignments/oop/images/bricks9x4.png" width = 810 alt = "9-by-4 bricks">

### **Tracker**


Write a program to create a data type for a _tracking device_, such as an [AirTag](https://www.apple.com/airtag/) or [Tile Pro](https://www.tile.com/products/tile-pro). Each tracking device has a name and a location. A tracking device can also be in _lost mode_ (which conserves power and locks access to personal information).


Write a data type `Tracker.java` that represents a tracking device, as specified in the following API:

```java
public class Tracker {

    // Creates a new Tracker with the given name and initial location.
    // By default, a new Tracker's lost mode is disabled.
    public Tracker(String name, double initialLatitude, double initialLongitude)

    // Is this tracker in lost mode?
    public boolean isInLostMode()

    // Enables lost mode on this tracker.
    public void enableLostMode()

    // Disables lost mode on this tracker.
    public void disableLostMode()

    // Moves this tracker to the new location.
    public void move(double newLatitude, double newLongitude)

    // Returns the great circle distance between the two trackers.
    public double distanceTo(Tracker other)

    // Returns a string representation of this tracker.
    public String toString()

    // Unit tests the Tracker data type.
    public static void main(String[] args)

}
```

Here is some more information about the required behavior:

- _Corner cases._ You may assume that all of the arguments are meaningful values (e.g., not `null`, `NaN`, `Double.POSITIVE_INFINITY` or `Double.NEGATIVE_INFINITY`).

- _Distance._ Let \\((x_1, y_1\\)) and \\((x_2, y_2\\)) be the latitude and longitude of two points on the surface of a sphere of radius \\(r\\). The _great-circle distance_ between them is the length of the shortest path, where the path is constrained to be along the surface.

<img src = "../../static/assignments/oop/images/great-circle.png" width = 200 alt = "great-circle distance between two points">

The [Haversine formula](https://en.wikipedia.org/wiki/Haversine_formula) gives the great circle distance:

$$
distance \\; = \\; 2r \arcsin \left ( \sqrt{ \sin^2 \left ( \frac{x_2 - x_1}{2} \right ) + \cos x_1 \cos x_2  \sin^2 \left ( \frac{y_2 - y_1}{2} \right ) } \right )
$$

Although the Earth is not a perfect sphere, this formula provides a reasonably accurate approximation
to the true distance, using \\(r = \text{6,371.0}\\) kilometers, which is the mean radius of the earth.
For example, the great circle distance between \\((60^{\circ}, 15^{\circ})\\) and
\\((60^{\circ}, 105^{\circ})\\) is approximately \\(4604.5\\) kilometers:

<img src = "../../static/assignments/oop/images/great-circle-trace.png" width = 700 alt = "great circle trace">


- _Locations._ Each location is specified using latitude and longitude in degrees. For example, Nassau Hall is located at 40.34386° latitude and −74.6593° longitude. Note that `Math.sin()` and other trigonometric functions in Java take arguments in _radians_, not degrees. Use `Math.toRadians()` to convert from degrees to radians.

- _String representation._ Return a string composed of the name and the current location (latitude, longitude), using the format:

   `"backpack (40.34386, -74.6593)"`



- _Test client._ The `main()` method must call every public method. 

#### FAQs ####

Click on the &#9658;  icon to expand and the  &#9660; icon to hide each FAQ.

{{< details "How do I write and debug a program that involves a complicated formula?" >}}
First, decompose a formula into smaller pieces and store each piece in a separate variable. Next, select input values for which computing the pieces by hand are relatively easy. Finally, check that each piece matches the value you expected. For example, the great-circle distance between (60°, 15°) and (60°, 105°) is approximately 4,604.53989 kilometers, as shown in the figure above.  Print the intermediate values for \\(\sin^2 \left ( \frac{x_2 - x_1}{2} \right )\\), \\( \cos x_1 \cos x_2\\), and \\(\sin^2 \left ( \frac{y_2 - y_1}{2} \right ) \\) to help you debug.
{{< /details >}}

{{< details "Which `Math` library functions should I use?" >}}
Use whichever functions from Java’s `Math` library that you need. The functions `Math.toRadians()`, `Math.sqrt()`, `Math.pow()`, `Math.sin()`, `Math.cos()`, and `Math.asin()` are particularly relevant here. 
{{< /details >}}



{{< details "Can I use a different formula to compute the great-circle distance, such as one based on the spherical law of cosines?" >}}
No. Please use the formula provided. There are some differences due to floating-point precision, especially for antipodal points or points that are very close. The Haversine formula is more resilient to floating-point precision issues than the one based on the spherical law of cosines. 
{{< /details >}}

### **Musical notes**

####  Part 1: Create a data type for _musical notes_.

Musical notes are the building blocks of music. For the purposes of this assignment, each musical note is characterized by its [MIDI](https://en.wikipedia.org/wiki/MIDI) number and a musical instrument.

Write a data type `Note.java` that represents a musical note, as specified in the following API:

```java
public class Note {

    // Creates a new Note corresponding to the MIDI number and instrument.
    public Note(int midiNumber, String instrumentName)

    // Returns the MIDI number.
    public int midi()

    // Returns the frequency of this note.
    public double frequency()

    // Plays this note to standard audio using an associated WAV file.
    public void play()

    // Returns the name of this note (such as C or A#).
    public String name()

    // Returns the octave of this note.
    public int octave()

    // Returns a new Note that is transposed delta halftones.
    public Note transpose(int delta)

    // Returns a string representation of this note.
    public String toString()

    // Unit tests the Note data type.
    public static void main(String[] args)

}
```
Here is some more information about the required behavior:

- _Corner cases._ You may assume that all of the arguments are meaningful values (e.g., MIDI numbers are between 0 and 127).

- _Note names and octaves._ Musicians often use [scientific pitch notation](https://en.wikipedia.org/wiki/Scientific_pitch_notation) to describe notes, such as A4 to describe concert A (MIDI number 69) or C4 to describe middle C (MIDI number 60). Scientific pitch notation combines two components: the note name (C, C#, D, D#, E, F, F#, G, G#, A, A#, or B), followed by the octave (an integer). Each octave consists of twelve (12) notes. The following table documents the mapping between the two notations:
|      |MIDI| pitch | |MIDI| pitch| |MIDI| pitch| |MIDI| pitch| |MIDI|pitch |     |
|:----:|:--:|:----: |-|:--:|:----:|-|:--:|:----:|-|:--:|:----:|-|:--:|:----:|:---:|
|      | 36 |  C2   | | 48 |  C3  | | 60 |  C4  | | 72 |  C5  | | 84 |  C6  |     |
|      | 37 |  C#2  | | 49 |  C#3 | | 61 |  C#4 | | 73 |  C#5 | | 85 |  C#6 |     |
|      | 38 |  D2   | | 50 |  D3  | | 62 |  D4  | | 74 |  D5  | | 86 |  D6  |     |
|      | 39 |  D#2  | | 51 |  D#3 | | 63 |  D#4 | | 75 |  D#5 | | 87 |  D#6 |     |
|      | 40 |  E2   | | 52 |  E3  | | 64 |  E4  | | 76 |  E5  | | 88 |  E6  |     |
| ...  | 41 |  F2   | | 53 |  F3  | | 65 |  F4  | | 77 |  F5  | | 89 |  F6  | ... |
|      | 42 |  F#2  | | 54 |  F#3 | | 66 |  F#4 | | 78 |  F#5 | | 90 |  F#6 |     |
|      | 43 |  G2   | | 55 |  G3  | | 67 |  G4  | | 79 |  G5  | | 91 |  G6  |     |
|      | 44 |  G#2  | | 56 |  G#3 | | 68 |  G#4 | | 80 |  G#5 | | 92 |  G#6 |     |
|      | 45 |  A2   | | 57 |  A3  | | 69 |  A4  | | 81 |  A5  | | 93 |  A6  |     |
|      | 46 |  A#2  | | 58 |  A#3 | | 70 |  A#4 | | 82 |  A#5 | | 94 |  A#6 |     |
|      | 47 |  B2   | | 59 |  B3  | | 71 |  B4  | | 83 |  B5  | | 95 |  B6  |     |

<!--
MIDI| pitch | |MIDI| pitch |    MIDI| pitch| |MIDI| pitch|
:--:|:----: |-|:--:|:----: |	:--:|:----:|-|:--:|:----:|
 12 |  C0   | | 24 |  C1   |	 96 |  C7  | | 108|  C8  |
 13 |  C#0  | | 25 |  C#1  |	 97 |  C#7 | | 109|  C#8 |
 14 |  D0   | | 26 |  D1   |	 98 |  D7  | | 110|  D8  |
 15 |  D#0  | | 27 |  D#1  |	 99 |  D#7 | | 111|  D#8 |
 16 |  E0   | | 28 |  E1   |	 100|  E7  | | 112|  E7  |
 17 |  F0   | | 29 |  F1   |	 101|  F7  | | 113|  F8  |
 18 |  F#0  | | 30 |  F#1  |	 102|  F#7 | | 114|  F#8 |
 19 |  G0   | | 31 |  G1   |	 103|  G7  | | 115|  G8  |
 20 |  G#0  | | 32 |  G#1  |	 104|  G#7 | | 116|  G#8 |
 21 |  A0   | | 33 |  A1   |	 105|  A7  | | 117|  A8  |
 22 |  A#0  | | 34 |  A#1  |	 106|  A#7 | | 118|  A#8 |
 23 |  B0   | | 35 |  B1   |	 107|  B7  | | 119|  B8  |
-->

> For the sake of brevity, we use only sharp accidentals (such as A#) for the note names, and not the equivalent flat accidentals such as (B♭).



- _String representation._ Return a string composed of the MIDI number, the name, the octave, and the instrument, using the format:
`"69 A4 (piano)"`


- _Play._ Play the WAV file whose name is the concatenation of the instrument name, the MIDI number, and the .wav file extension. Assume that the WAV file is located in a subdirectory whose name is the name of the instrument. For example, to play concert A (MIDI number 69) on the piano, call `StdAudio.play("piano/piano69.wav")`.

- _Frequency._ The frequency \\(f\\) corresponding to MIDI number \\(m\\) is given by the following formula:
$$
f \\; = \\; 440 \\; \times \\; 2^{\\, (m - 69) \\, / \\, 12}
$$

- _Transpose._ Transposing a note whose MIDI number is \\(m\\) by \\(\Delta\\) halftones yields a note whose MIDI number is \\(m + \Delta\\). Note that this method returns a new `Note` object; it should not change the value of the invoking `Note` object.

- _Test client._ The `main()` method must call every public method.

#### FAQs ####

Click on the &#9658;  icon to expand and the  &#9660; icon to hide each FAQ.

{{< details "How can I define constants that are global to a class?" >}}
Recall from the _Creating Data Types_ lecture that you can declare a constant using the `private static final` modifiers. For example: `private static final int MINUTES_PER_HOUR = 60;`
{{< /details >}}

{{< details "MIDI values go from 0 to 127, however the table provided only shows the relationship between MIDI and octaves from 36 to 95. Should we only focus on those MIDI values? Or are we expected to generalize the relationship for the other values as well?" >}}
You need to generalize.
{{< /details >}}


{{< details "There are only instrument audio files for MIDI numbers 12 through 116. Are our programs purposefully not intended to play MIDI numbers 0 through 11 and 117 through 127?" >}}
While MIDI is defined for values 0 to 127, most instruments do not support that full range. For example, a typical piano has only 88 keys, not 128. You need not worry about that. The autograder won't call play() on a MIDI note for which there is no accompanying WAV file.
{{< /details >}}

{{< details "Do I need conditional (`if`) statements to determine a note name or octave?" >}}
You can implement `name()` and `octave()` without any `if` statements. If you a have large multiway `if` statement ladder, you are probably not finding the most elegant way to implement these methods. Consider using integer division/remainder and an array.
{{< /details >}}

####  Part 2: Write a client that uses the musical note data type.

Your final task is to write a client program `PlayTheseNotes.java` that reads a sequence of musical notes from standard input and plays the musical notes on standard audio and prints the notes on standard output.  The program takes three command-line arguments:
- the  name of the input file
- the name of the instrument: piano, guitar, clarinet, or mbira
- the value for delta for transposing notes

Organize your client according to the following API:
```java
public class PlayTheseNotes {

    // Reads a sequence of notes from the given file and returns a 
    // Note array based on the given instrument 
    public static Note[] read(String filename, String instrument)

    // Takes three command-line arguments (a file name, an instrument name,
    // a delta value for transpose), reads a sequence of notes from the file
    // and plays the musical notes, according to the given instrument on
    // standard audio
    public static void main(String[] args)
}
```

Here are some details about the API:

*Input file format.* A musical notes file consists of a sequence of lines:

- the first line contains the number of notes, and
- each remaining line contains the MIDI number, followed by the floating-point duration in seconds, separated by whitespace.

<img src = "../../static/assignments/oop/images/inputfile.png" width = 250 alt = "input file format">

*Duration.*  Duration is typically used to represent eighth notes, quarter notes, half notes and whole notes.  In this programming assignment, we provide only quarter notes.  You must read the duration from standard input, but its value will not be used in creating/playing musical notes.

*The `In` API.*  The [`In`](https://introcs.cs.princeton.edu/java/11cheatsheet/#In)  data type from Section 3.1 is an object-oriented version of [`StdIn`](https://introcs.cs.princeton.edu/java/11cheatsheet/#StdIn). You will need the object-oriented version in this program since you will be reading from a file in the `read()` method.

*Approach.*  The `main()` should first call `read()`, which returns an array of `Note` objects corresponding to the input file and desired instrument. Next, it should play and print all notes in order, using the provided value for transposing notes.

*Input files.* We provide various files containing some simple songs in the specified format.   You may optionally create and upload your own song in a file named `music.txt`!  It may be an original or written by another artist.  Please provide credits at the end of your file.

*Ouput.* Your program should print each pitch (note name and octave) to standard output as it plays the note (on standard audio).  All the notes should be on one line, each separated by a space, ending in a new line.

Here are some sample executions:
```plaintext
~/Desktop/oop> java-introcs PlayTheseNotes C4-major-scale.txt piano 0 
C4 D4 E4 F4 G4 A4 B4 C5
```

```plaintext
~/Desktop/oop> java-introcs PlayTheseNotes C4-major-scale.txt guitar 0
C4 D4 E4 F4 G4 A4 B4 C5
```

```plaintext
~/Desktop/oop> java-introcs PlayTheseNotes C4-major-scale.txt piano 8
G#4 A#4 C5 C#5 D#5 F5 G5 G#5 
```



### **Submission**

Submit to {{< tigerfile "OOP" >}}: `Tile.java`, `Tracker.java`, `Note.java`, `PlayTheseNotes.java` and  completed `readme.txt` and `acknowledgments.txt` files.
Submit a `music.txt` file if you created your own composition. 
### **Grading**
| Files                | Points    |
| ------------------   | ----------|
| Tile.java            |  10       |
| Tracker.java         |  10       |
| Note.java            |  10       |
| PlayTheseNotes.java  |   6       |
| readme.txt           |   4       |
| Total                |  40       |



This assignment was developed by [Kevin Wayne](http://www.cs.princeton.edu/~wayne). Copyright © 2024.


