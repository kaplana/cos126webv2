---
title: 3. Conjunction Function 
subtitle: 
summary:  Implement and test a collection of functions (also known as methods), which manipulate audio, and take a short quiz on how to get help in COS 126. {{< project "functions" >}} |  {{< submit "Functions" >}}
weight: 4
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false
---
{{<construction>}}
{{< project "functions" >}} |  {{< submit "Functions" >}}
 
### **Goals**

- To provide experience defining and using a collection of (a conjunction of) functions.
- To deepen your understanding of passing arrays as arguments and returning values.
- To gain more experience producing and manipulating audio.

### **Background**
This assignment is based on the concepts and techniques described in Sections 2.1 and 2.2 of the textbook (and the corresponding lectures and precepts). You may also find it instructive to work through some of the other exercises and look at the solutions on the [booksite](https://introcs.cs.princeton.edu/java/home) afterwards.



### **Getting started**

- Make sure you understand the lecture materials and the precept exercises before proceeding. Refer to the lecture and precept examples while you are coding.

- Download and expand the zip file, which will create the folder `functions`. We recommend this folder be placed in a `cos126` folder.

- You should also familiarize yourself with the [StdAudio API](https://introcs.cs.princeton.edu/java/11cheatsheet/#StdAudio)



### **Approach / Design**

Create a library to manipulate digital audio and use that library to create an *audio collage*. As in lecture, we will represent sound as an array of real numbers between –1 and +1, with 44,100 samples per second. You will write a library of functions to produce audio effects by manipulating such arrays.

Write an audio processing library `AudioCollage.java` by implementing the following API:

```java
public class AudioCollage {

    // Returns a new array that rescales a[] by a factor of alpha.
    public static double[] amplify(double[] a, double alpha)

    // Returns a new array that replaces samples > +1.0 with +1.0
   //  and samples < -1.0 with -1.0.  Other samples are unchanged.
    public static double[] clamp(double[] a)

    // Returns a new array that is the reverse of a[].
    public static double[] reverse(double[] a)

    // Returns a new array that is the concatenation of a[] and b[].
    public static double[] merge(double[] a, double[] b)

    // Returns a new array that is the sum of a[] and b[],
    // padding the shorter array with trailing 0s if necessary.
    public static double[] mix(double[] a, double[] b)

    // Returns a new array that changes the speed of a[] by a factor of alpha.
    public static double[] changeSpeed(double[] a, double alpha)

    // Creates an audio collage and plays it on standard audio.
    // See below for the requirements.
    public static void main(String[] args)

}
```

## **Requirements**

- You may not call library functions except those in
   - `java.lang` such as `Integer.parseInt()`,
   -  [`StdAudio`](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdAudio), [`StdRandom`](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdRandom), [`StdOut`](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdOut)
- You may not use Java features that have not yet been introduced in the course (such as objects).
- Implement a single class: `AudioCollage.java`
- The functions **must not** *mutate* the argument array(s).
- You may assume that the array arguments to all functions are not **`null`**.
- All samples sent to standard audio **must be** between –1 and +1. Note that it is possible that the input samples to each method may not be between –1 and +1. The functions should operate as specified. While you should not play samples whose absolute value is greater than 1, it’s fine to manipulate such values along the way. However, to ensure that your final sample compiles with this requirement, you will need to call `clamp()` on your final sample before sending it to standard audio.  
- You must not add `public` methods to the API; however, you may add `private` methods (which are accessible only in the class in which they are declared).
- *Getting Help in COS 126 Quiz:* Take a short quiz on getting help in COS 126 (available in the Quizzes section on [Canvas](https://canvas.princeton.edu)).  You may repeat the quiz as many times as you like.  For full credit, answer **all** questions correctly.
- Submit a `readme.txt`.
- Complete the `acknowledgments.txt` file.


#### `amplify()`

Creates a new sound that contains the same samples as an existing sound, but with each sample multiplied by a constant \\( \alpha \\). This increases the volume when \\( \alpha > 1 \\) and decreases it when \\( 0 < \alpha < 1\\). For example:

{{< imgfig "https://www.cs.princeton.edu/courses/archive/fall24/cos126/static/assignments/functions/images/amplify.png" >}}


Note: `amplify()` may produce samples whose absolute value is larger than 1 even if all of the input samples are between –1 and +1. While you should not play samples whose absolute value is greater than 1, it’s fine to produce them as intermediate results.

#### `clamp()`
Replaces all samples *greater than* +1.0 with 1.0; and all samples *less than* -1.0 with -1.0. For example:

{{< imgfig "https://www.cs.princeton.edu/courses/archive/fall24/cos126/static/assignments/functions/images/clamp.png" >}}


#### `reverse()`


Creates a new sound that contains the same samples as an existing sound, but in reverse order. This can lead to unexpected and entertaining results. For example:

{{< imgfig "https://www.cs.princeton.edu/courses/archive/fall24/cos126/static/assignments/functions/images/reverse.png" >}}


#### `merge()`

Creates a new sound that combines two existing sounds by appending the second one after the first. If the two sounds have \\(m\\) and \\(n\\) samples, then the resulting sound has \\(m + n\\) samples. This enables you to play two sounds sequentially. For example:

{{< imgfig "https://www.cs.princeton.edu/courses/archive/fall24/cos126/static/assignments/functions/images/merge.png" >}}


#### `mix()`

Creates a new sound that combines two existing sounds by summing the values of the corresponding samples. This enables you to play two sounds simultaneously. If, when summing, one sound is longer than the other, treat the shorter array as if 0s were appended to the end. For example:

{{< imgfig "https://www.cs.princeton.edu/courses/archive/fall24/cos126/static/assignments/functions/images/mix.png" >}}


Note: `mix()` may produce samples whose absolute value is larger than 1 even if all of the input samples are between –1 and +1. While you should not play samples whose absolute value is greater than 1, it is fine to produce them as intermediate results.

#### `changeSpeed()`

Creates a new sound that changes the duration of an existing sound via resampling. If the existing sound has \\(n\\) samples, then the new sound has \\(⌊n / \alpha ⌋\\) samples for some constant \\( \alpha > 0 \\), with sample \\(i\\) of the new sound having the same amplitude as sample \\(⌊i \alpha ⌋\\) of the existing sound. Assume that \\(\alpha > 0\\).  For example:

{{< imgfig "https://www.cs.princeton.edu/courses/archive/fall24/cos126/static/assignments/functions/images/changeSpeed.png" >}}

The floor function \\(⌊x⌋\\) returns the largest integer less than or equal to \\(x\\). You can compute it in Java by either casting to an integer (resulting in an `int`) or calling `Math.floor(x)` (resulting in a `double`).

Note: the `changeSpeed()` function changes not only the speed of the sound, but also the pitch.  Speeding up the sound using resampling raises the pitch and leads to the [Chipmunk](https://en.wikipedia.org/wiki/Alvin_and_the_Chipmunks#Recording_technique) effect. More sophisticated [time stretching techniques](https://en.wikipedia.org/wiki/Audio_time_stretching_and_pitch_scaling) are preferred in practice (such as when watching the generic videos, lecture recordings, etc. at 1.5× or 2× speed) because they change the speed but not the pitch.

#### `main()`

- The `main()` method must create an *audio collage* and play it using `StdAudio.play()`. Use the methods you wrote to combine various audio samples into a *single* array, then play it using `StdAudio`.
- The duration **must be** between ten (10)  and sixty (60) seconds (441,000 to 2,646,000 samples).
- **Do not** use any command-line arguments.
- The audio collage **must** use at least **five** different `.wav` files. Several `.wav` files are provided. You may optionally supply or create your own `wav` files. 
- Use `StdAudio.read()` to read a `.wav` file and extract its samples as an array of floating-point numbers between –1.0 and +1.0. For example: <br>

  ```java
  double[] samples1 = StdAudio.read("cow.wav");
  ```
- The audio collage **must** use **all** of the audio effects specified in the API (amplify, reverse, merge, mix, change speed, and clamp). Feel free to add additional audio effects (see below for some ideas).
- All played samples **must be** valid; i.e., all samples must be between -1.0 and 1.0 before calling  `StdAudio.play()`.
- To convert an audio file into an appropriate format for use with `StdAudio`:
	- Convert it to a `.wav` file.
	- You may use an online conversion utility, such as [Online-Convert](https://audio.online-convert.com/convert-to-wav).
- Be creative! We plan to post your wildly creative productions!

#### Implementing Other Audio Effects

You may experiment with implementing other functions that produce various audio effects. However, these functions **must** be `private`. For example:

  ```java
  private static double[] mirror(double[] samples) {...}
  ```

Some ideas include:

- *normalize*: rescale a sound so that all values are between –1.0 and +1.0.
- *cut*: extract a contiguous subarray from a given sound.
- *trim*: remove leading / trailing sequence of samples that are 0.0 (or nearly 0.0).
- *loop*: repeat a given sound a specified number of times.
- *mirror*: concatenate a sound with its reverse.
- *hip–hop*: increase speed of a sound; mirror it; then loop it.
- *echo, delay, reverb*: add a time-delayed version of a sound to itself, attenuated by a given factor.
- *fade-in, fade-out*: gradually increase/decrease the volume at the beginning/end of a sound.
- *crossfade*: fade-out first sound; fade-in second sound; overlap.
- *tremolo*: create a trembling effect by modulating the amplitude up and down.
- You could also synthesize your own sounds by creating a sine wave, square wave, triangle wave, or sawtooth wave of a given amplitude, frequency, and duration.

<!--
#### The keywords `public` vs `private`

You have probably noticed that we have been starting every `static` method with the keyword `public`. This keyword identifies the method as available for use by any other program with access to the file. There is an alternative keyword we can use to identify each method: `private`. A `private` method cannot be used by another program, it can only be used by other methods in the same file. This is useful when we are implementing an API that requires some internal methods that we want to hide from API users. We will learn more about `private` methods and also other types of methods later in the class.
-->

#### **Edit the `readme.txt`**

Edit the text file named `readme.txt` that is a narrative description of your work.

### **Submission**

- Submit to {{< tigerfile "Functions" >}}:  `AudioCollage.java`, completed `readme.txt` and `acknowledgments.txt` files.
- Do not submit the `.wav` files we provided.
- You may also submit supplementary `.wav` files if you used any — put them in the same directory as your `.java` files. Please cite the sources for  supplementary `.wav` files in your `readme.txt` file. 

- Note that, as part of this assignment, we may anonymously publish your collage. If you object, please indicate so in your `readme.txt` when asked. We also reserve the right to remove any collage, at any time, for whatever reason.  By submitting your assignment, you implicitly agree with this policy.

- Also, complete the quiz on getting help in COS 126 on  [Canvas](https://canvas.princeton.edu)

### **Grading**
| Files               | Points    |
| ------------------  | ----------|
| AudioCollage.java   |  34       |
| readme.txt          |  4        |
| Quiz                |  2        |
| Total               |  40       |


### **Enrichment**

- Wave Audio Format File (or WAV) is a popular file format for storing raw and uncompressed audio data. For more information, see the [Wikipedia page](https://en.wikipedia.org/wiki/WAV).



- This problem was inspired by an audio effects [programming assignment](http://katie.mtech.edu/classes/archive/f11/csci135/assign/audio/index.php) by Keith Vertanen.

- The assignment name is a play on the Grammar Rock title _Conjunction Junction_ – see [https://en.wikipedia.org/wiki/Schoolhouse_Rock!](https://en.wikipedia.org/wiki/Schoolhouse_Rock!)

