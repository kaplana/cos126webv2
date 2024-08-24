---
title: 4. Recursion
subtitle: 
summary:   Plot a Sierpinski triangle; and plot a recursive pattern of your own design; evaluate and compare the performance of a recursive program and a dynamic programming program.   {{< project "recursion" >}} |  {{< submit "Recursion" >}}
weight: 5
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false
---
{{<construction>}}
{{< project "recursion" >}} |  {{< submit "Recursion" >}}


### **Goals**

- To gain practice designing and implementing recursive functions.
- To write a program that plots a Sierpinski triangle.
- To design and develop a program that plots a recursive pattern of your own design.
- To understand the trade-offs between recursive and dynamic programming approaches to solving problems.

### **Getting Started**

- Make sure you understand the lecture materials and the precept exercises before proceeding. Refer to the lecture and precept examples while you are coding.

- Download and expand the zip file, which will create the folder `recursion`. We recommend this folder be placed in a `cos126` folder.



- You may not call library functions except those in
   - `java.lang` such as `Integer.parseInt()`, etc.
   -  [`StdDraw`](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdDraw)
- You may not use Java features that have not yet been introduced in the course (such as objects).
- Use the [`StdRandom`](https://introcs.cs.princeton.edu/java/11cheatsheet/index.html#StdRandom) API instead of `Math.random()`


### **Background**

Read Sections 2.3 and 4.1 in the textbook. You may also find it instructive to work through the precept exercises. You should also familiarize yourself with the [`StdDraw` API](https://introcs.cs.princeton.edu/java/11cheatsheet/#StdDraw).


### **Delannoy numbers (brute force).**

Write a program `DelannoyBrute.java` that takes two integer command-line arguments \\(m\\) and \\(n\\) and computes the corresponding *Delannoy number*. The Delannoy number \\(D(m, n)\\) is the number of paths from \\((0,0)\\) to \\((m, n)\\) in a rectangular grid using only single steps north, east, and northeast. For example, \\(D(2, 2) = 13\\) because these are the \\(13\\) possible Delannoy paths:

![13 possible Delannoy paths in a 2-by-2 grid]({{< resource url="static/assignments/recursion/images/delannoy22.png"  >}})

Implement a recursive function `count()` to compute \\(D(m,n)\\) using the following recurrence relation:

$$
\\displaystyle D(m, n) \\; = \\;
\begin{cases}
\\;\\; 1 & \text{if } m = 0 \\\\[.5em]
\\;\\; 1 & \text{if } n = 0 \\\\[.5em]
\\;\\; D(m-1, n) + D(m-1, n-1) + D(m, n-1) & \\text{otherwise}
\end{cases}
$$
 To do so, organize your program according to the following public API:

```java
public class DelannoyBrute {

    // Returns the Delannoy number D(m, n).
    public static long count(int m, int n)

    // Takes two integer command-line arguments m and n and prints D(m, n).
    public static void main(String[] args)
}
```

As you will see, this approach is hopelessly slow, even for moderately small values of \\(m\\) and \\(n\\).

```plaintext
~/Desktop/recursion> java-introcs DelannoyBrute 2 2
13

~/Desktop/recursion> java-introcs DelannoyBrute 3 2
25

~/Desktop/recursion> java-introcs DelannoyBrute 2 3
25

~/Desktop/recursion> java-introcs DelannoyBrute 3 3
63

~/Desktop/recursion> java-introcs DelannoyBrute 20 20
[takes too long]
```

Notes:
- You may assume that \\(m\\) and \\(n\\) are non-negative integers.
- You must not add `public` methods to the API; however, you may add `private` methods (which are accessible only in the class in which they are declared).


Delannoy numbers arise in combinatorics and computational biology. For example, it is the number of global alignments of two strings of lengths \\(m\\) and \\(n\\).


### **Sierpinski Triangle**
The Sierpinski triangle is an example of a fractal pattern like the H-tree pattern from Section 2.3 of the textbook.


||||
|:-----:|:-----:|:-----:|
|![Sierpinski level 1]({{< resource url="static/assignments/recursion/images/sierpinski1.png" >}}) <br> **Order 1**|![Sierpinski level 2]({{< resource url="static/assignments/recursion/images/sierpinski2.png" >}}) <br> **Order 2**|![Sierpinski level 3]({{< resource url="static/assignments/recursion/images/sierpinski3.png" >}})<br> **Order 3**|
|![Sierpinski level 4]({{< resource url="static/assignments/recursion/images/sierpinski4.png" >}}) <br> **Order 4**|![Sierpinski level 5]({{< resource url="static/assignments/recursion/images/sierpinski5.png" >}}) <br> **Order 5**|![Sierpinski level 6]({{< resource url="static/assignments/recursion/images/sierpinski6.png" >}})<br> **Order 6**|


The Polish mathematician Wacław Sierpiński described the pattern in 1915, but it has appeared in Italian art since the 13th century. Though the Sierpinski triangle looks complex, it can be generated with a short recursive function. 

Your primary task is to write a recursive function `sierpinski()` that plots a Sierpinski triangle of order `n` to standard drawing. Think recursively: `sierpinski()` should draw one filled equilateral triangle (pointed downwards) and then call itself recursively three times (with an appropriate stopping condition). It should draw one (1) filled triangle for `n = 1`; four (4) filled triangles for `n = 2`; and thirteen (13) filled triangles for `n = 3`; and so forth.

When writing your program, exercise modular design by organizing it into four functions, as specified in the following API:

```java
public class Sierpinski {

    // Height of an equilateral triangle with the specified side length.
    public static double height(double length)

    // Draws a filled equilateral triangle with the specified side length
    // whose bottom vertex is (x, y).
    public static void filledTriangle(double x, double y, double length)

    // Draws a Sierpinski triangle of order n, such that the largest filled
    // triangle has the specified side length and bottom vertex (x, y).
    public static void sierpinski(int n, double x, double y, double length)

    // Takes an integer command-line argument n;
    // draws the outline of an upwards equilateral triangle of length 1
    // whose bottom-left vertex is (0, 0) and bottom-right vertex is (1, 0);
    // and draws a Sierpinski triangle of order n that fits inside the outline.
    public static void main(String[] args)

}
```

The formula for the *height* of an equilateral triangle of side length \\( s \\) is \\( h = s \times \frac{\sqrt{3}}{2} \\). 

![Equilateral triangle example]({{< resource url="static/assignments/recursion/images/equilateral-triangle.png"  >}})

Here is the layout of the initial equilateral triangle. The top vertex lies at \\( (\frac{1}{2},\frac {\sqrt{3}}{2}) \\).

![Initial triangle example]({{< resource url="static/assignments/recursion/images/initial-equilateral-triangle.png" >}})


Here is the layout of an inverted equilateral triangle.

![Inverted triangle example]({{< resource  url="static/assignments/recursion/images/inverted-equilateral-triangle.png" >}})





#### Requirements

1. The program must take an integer command-line argument `n`.
1. To draw a *filled* equilateral triangle, you should call the method `StdDraw.filledPolygon()` with appropriate arguments.
1. To draw an *unfilled* equilateral triangle, you should call the method `StdDraw.polygon()` with appropriate arguments.
1. You **must not** call `StdDraw.save()`, `StdDraw.setCanvasSize()`, `StdDraw.setXscale()`, `StdDraw.setYscale()`, or `StdDraw.setScale()`. These method calls interfere with grading.
1. You may use only `StdDraw.BLACK` to draw the outline triangle and the filled triangles.

### **Possible Progress Steps**
We provide some additional instructions below.  Click on the &#9658;  icon to expand *some possible progress steps* or you may try to solve Sierpinski without them.  It is up to you!

{{< details "Click to show possible progress steps" >}}
These are purely suggestions for how you might make progress. You do not have to follow these steps. Note that your final `Sierpinski.java` program should not be very long (no longer than `Htree.java`, not including comments and blank lines).

- Review [Htree.java](https://introcs.cs.princeton.edu/23recursion/Htree.java.html) from the textbook, lecture and precept.
- Write a (non-recursive) function `height()` that takes the length of the side of an equilateral triangle as an argument and returns its height. The body of this method should be a one-liner.
	- **Test** your `height()` function. This means try your `height()` function with various values. Does it return the correct calculation?
- In `main()`, draw the outline of the initial equilateral triangle. Use the `height()` function to calculate the vertices of the triangle.
- Write a (nonrecursive) function `filledTriangle()` that takes three (3) arguments `(x, y, length)` and draws a filled equilateral triangle (pointed downward) with the specified side length and the bottom vertex at \\( (x, y)\\).
	- To **test** your function, write `main()` so that it calls `filledTriangle()` a few times with different arguments. You will be able to use this function without modification in `Sierpinski.java`.
- Ultimately, you must write a *recursive* function `sierpinski()` that takes four (4) arguments `(n, x, y, length)` and plots a Sierpinski triangle of order `n`, whose largest triangle has the specified side length and bottom vertex \\((x, y)\\). However, to implement this function, use an *incremental* approach:
	- Write a recursive function `sierpinski()` that takes one argument `n`, prints the value `n`, and then calls itself three times with the value `n-1`. The recursion should stop when `n` becomes 0.
	- To **test** your function, write `main()` so that it takes an integer command-line argument `n` and calls `sierpinski(n)`. Ignoring whitespace, you should get the following output when you call `sierpinski()` with `n` ranging from 0 to 5. Make sure you understand how this function works, and why it prints the numbers in the order it does. 

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 0
[no output]
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 1
1
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 2
2
1
1
1
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 3
3
2 1 1 1
2 1 1 1
2 1 1 1
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 4
4
3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 5
5
4 3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
4 3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
4 3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
3 2 1 1 1 2 1 1 1 2 1 1 1
```
		
- Modify `sierpinski()` so that in addition to printing `n`, it also prints the length of the triangle to be plotted. Your function should now take two arguments: `n` and `length`. The initial call from `main()` should be to `sierpinski(n, 0.5)`, since the largest Sierpinski triangle has side length 0.5. Each successive level of recursion halves the length. Ignoring whitespace, your function should produce the following output:


```plaintext
~/Desktop/recursion> java-introcs Sierpinski 0
[no output]
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 1
1 0.5
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 2
2 0.5
1 0.25
1 0.25
1 0.25
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 3
3 0.5
2 0.25  1 0.125  1 0.125  1 0.125
2 0.25  1 0.125  1 0.125  1 0.125
2 0.25  1 0.125  1 0.125  1 0.125
```

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 4
4 0.5
3 0.25  2 0.125  1 0.0625  1 0.0625  1 0.0625
2 0.125  1 0.0625  1 0.0625  1 0.0625
2 0.125  1 0.0625  1 0.0625  1 0.0625
3 0.25  2 0.125  1 0.0625  1 0.0625  1 0.0625
2 0.125  1 0.0625  1 0.0625  1 0.0625
2 0.125  1 0.0625  1 0.0625  1 0.0625
3 0.25  2 0.125  1 0.0625  1 0.0625  1 0.0625
2 0.125  1 0.0625  1 0.0625  1 0.0625
2 0.125  1 0.0625  1 0.0625  1 0.0625
```

- Modify `sierpinski()` so that it takes four (4) arguments `(n, x, y, length)` and plots a Sierpinski triangle of order `n`, whose largest triangle has the specified side length and bottom vertex \\( (x, y)\\). Start by drawing Sierpinski triangles with pencil and paper. What values need to change between each recursive call?
- **Remove all print statements before submitting to TigerFile.**

{{< /details >}}

#### Testing

Below are the target Sierpinski triangles for different values of `n`.

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 1
```

![Sierpinski level 1]({{< resource url="static/assignments/recursion/images/sierpinski1.png" >}})

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 2
```

![Sierpinski level 2]({{< resource url="static/assignments/recursion/images/sierpinski2.png" >}})

```plaintext
~/Desktop/recursion> java-introcs Sierpinski 3
```

![Sierpinski level 3]({{< resource url="static/assignments/recursion/images/sierpinski3.png" >}})




### **Create Your Own Art**

#### `Art.java`

In this part you will create a program `Art.java` that produces a recursive drawing of your own design. This part is meant to be fun, but here are some guidelines in case you're not so artistic.  

A very good approach is to first choose a self-referential pattern as a target output. Check out the graphics exercises in [Section 2.3](https://introcs.cs.princeton.edu/23recursion). Here are some of our favorite [student submissions from a previous year](https://www.cs.princeton.edu/courses/archive/fall15/cos126/art/index.php). See also the Famous Fractals in [Fractals Unleashed](https://web.archive.org/web/20120404193223/http://library.thinkquest.org/26242/full/index.html) for some ideas. Here is a [list of fractals, by Hausdorff dimension](https://en.wikipedia.org/wiki/List_of_fractals_by_Hausdorff_dimension). Some pictures are harder to generate than others (and some require trigonometry).

#### Requirements

1. `Art.java` must take **one** (1) integer command-line argument `n` that controls the depth of recursion.
1. Your drawing **must** stay within the drawing window when `n` is between 1 and 6 inclusive. (The autograder will not test values of `n` outside of this range.)
1. You may not change the size of the drawing window (but you may change the scale). Do not add sound.
1. Your drawing can be a geometric pattern, a random construction, or anything else that takes advantage of recursive functions.
1. Optionally, you may use the `Transform2D` library you implemented in precept. You may also define additional geometric transforms in `Art.java`, such as sheer, reflect across the \\(x\\)- or \\(y\\)- axis, or rotate about an arbitrary point (as opposed to the origin).
1. Your program must be organized into at least *three* separate functions, including `main()`. All functions except `main()` must be `private`.
1. For full credit, `Art.java` must not be something that could be easily rewritten to use loops in place of recursion, and some aspects of the recursive function-call tree (or how parameters or overlapping are used) must be distinct from the in-class examples (`HTree`, `NestedCircles`, etc.). You must do **at least two** of the following to get full credit on `Art.java` (and doing more ***may*** yield a small amount of extra credit):
	- call one or more `Transform2D` methods
	- use different parameters than in examples: `f(n, x, y, size)`
	- use different `StdDraw` methods than in examples (e.g., ellipses, arcs, text; take a look at the [`StdDraw` API](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html))
	- have non-constant number of recursive calls per level (e.g., conditional recursion)
	- have mutually recursive methods
	- have multiple recursive methods
	- use recursion that doesn't always recur from level `n` to level `n-1`
	- draw between recursive calls, not just before or after all recursive calls
	- use recursive level for secondary purpose (e.g., level dictates color)
1. Contrast this with the examples `HTree`, `Sierpinski`, and `NestedCircles`, which have very similar structures to one another.
1. You will also lose points if your artwork can be created just as easily without recursion (such as [`Factorial.java`](https://introcs.cs.princeton.edu/java/23recursion/Factorial.java.html)). If the recursive function-call tree for your method is a straight line, it probably falls under this category.
1. You may use GIF, JPG, or PNG files in your artistic creation. If you do, be sure to submit them along with your other files. Make it clear in your `readme.txt` what part of the design is yours and what part is borrowed from the image file.

#### FAQ

**The API checker says that I need to make my methods `private`.** Use the access modifier `private` instead of `public` in the method signature. A public method can be called directly in another class; a private method cannot. The only public method that you should have in `Art.java` is `main()`.

**What will cause me to lose points on the artistic part?** We consider two things: the structure of the code and the structure of the recursive function-call tree.

For example, the [Quadricross](https://en.wikipedia.org/wiki/File:Quadriccross.gif) looks very different from the in-class examples, but the code to generate it looks extremely similar to `HTree`, so it is a bad choice. On the other hand, even though the [Sierpinski curve](https://www.robertdickau.com/sierpinskiarrow.html) eventually generates something that looks like the Sierpinski triangle, the code is very different (probably including an angle argument in the recursive method), and so it would earn full marks.

**Here is an animation we produced  using student art:** {{< youtube JDNrg1z-M7A >}}


### **Delannoy numbers (dynamic programming)**

You have now written three recursive programs.  This programming exercise demonstrates the efficiency of using a *dynamic programming* approach to solving a problem.

Write a program `DelannoyMemo.java` that takes two integer command-line arguments \\(m\\) and \\(n\\) and computes the Delannoy number \\(D(m, n)\\) using dynamic programming.

- Use a `private static` two-dimensional array with element `memo[i][j]` storing \\(D(i,j)\\).
- Review  `FibonnaciMemo.java` (from lecture) and `PalindromeMemo.java` (from precept).  Your code `DelannoyMemo.java` should be structured similarly to these exercises.
- You must not add `public` methods to the API; however, you may add `private` methods (which are accessible only in the class in which they are declared).



To do so, organize your program according to the following public API: 

```java
public class DelannoyMemo {

    // Returns the Delannoy number D(m, n).
    public static long count(int m, int n)

    // Takes two integer command-line arguments m and n and prints D(m, n).
    public static void main(String[] args)
}
```

This version should be fast enough to handle larger values of \\(m\\) and \\(n\\). 

```plaintext
~/Desktop/recursion> java-introcs DelannoyMemo 2 2
13

~/Desktop/recursion> java-introcs DelannoyMemo 3 2
25

~/Desktop/recursion> java-introcs DelannoyMemo 2 3
25

~/Desktop/recursion> java-introcs DelannoyMemo 3 3
63

~/Desktop/recursion> java-introcs DelannoyMemo 20 20
260543813797441
```

### **Analysis - `readme.txt`**
Provide your answers to the following questions in  the  `readme.txt` file.

Conduct the following experiments, where \\(m\\) and \\(n\\) are the same:

```plaintext
~/Desktop/recursion> java-introcs DelannoyBrute 2 2
~/Desktop/recursion> java-introcs DelannoyBrute 4 4
~/Desktop/recursion> java-introcs DelannoyBrute 8 8
~/Desktop/recursion> java-introcs DelannoyBrute 16 16
~/Desktop/recursion> java-introcs DelannoyMemo 2 2
~/Desktop/recursion> java-introcs DelannoyMemo 4 4
~/Desktop/recursion> java-introcs DelannoyMemo 8 8
~/Desktop/recursion> java-introcs DelannoyMemo 16 16
```

1. Based your computational experiments, which function runs faster:   `DelannoyBrute.count()` or `DelannoyMemo.count()` ?

2. Using a mathematical model, what is the  estimated order of growth of the running time of `DelannoyBrute.count(n, n)`   as a function of \\(n\\)?

<!--
-  \\(\Theta(1)\\) - constant, \\(\Theta(\log n)\\) - logarithmic, \\(\Theta(n)\\) - linear, \\(\Theta(n\log n)\\) - linearithmic, \\(\Theta(n^2)\\) - quadratic, \\(\Theta(n^3)\\) - cubic or \\(\Theta(c^n)\\) - exponential?   -->
3. Empirically analyze  `DelannoyMemo.count(n, n)` using the doubling method. Complete the following table by conducting experiments for n = 3000, 6000, 12000, 24000. 

```plaintext
~/Desktop/recursion> java-introcs -Xss10m  DelannoyMemo 3000 3000
~/Desktop/recursion> java-introcs -Xss10m  DelannoyMemo 6000 6000
~/Desktop/recursion> java-introcs -Xss10m  DelannoyMemo 12000 12000
~/Desktop/recursion> java-introcs -Xss10m  DelannoyMemo 24000 24000
```

| \\(n\\) | Time (seconds) |
| -----  | -------------- |
| 3000   |                |
| 6000   |                |
| 12000  |                |
| 24000  |                |

4. Based on your empirical analysis, what is the estimated  order of growth of the running time of `DelannoyMemo.count(n, n)`   as a function of \\(n\\)?


> Notes:
> - For your empirical analysis, you may observe the result returned by `count()` will *overflow* a `long` data type.  However, you can ignore the values returned by `count()` since they do not impact the order of growth of the implementation.
> - Instrument your working version `DelannoyMemo.java` similarly to how your code was instrumented in precept using `System.System.currentTimeMillis()`. Do not submit the instrumented version of `DelannoyMemo.java` to TigerFile.
> - If you receive a message such as `Exception in thread "main" java.lang.OutOfMemoryError: Java heap space`, try allocating more memory to your Java process. For example:
>
>    `java-introcs -Xss50m -Xmx5g DelannoyMemo 24000 24000`




### **Submission**

- Submit to {{< tigerfile "Recursion" >}}: `DelannoyBrute.java`, `Sierpinski.java`, `Art.java` (and optional image files),  `DelannoyMemo.java`, and completed `readme.txt` and `acknowledgments.txt` files.

- Note that, as part of this assignment, we may anonymously publish your art. If you object, please indicate so in your `readme.txt` when asked. We also reserve the right to pull any art, at any time, for whatever reason; by submitting your assignment, you implicitly agree with this policy.


### **Grading**
| Files               | Points    |
| ------------------  | ----------|
| DelannoyBrute.java  |   6       |
| Sierpinski.java     |  16       |
| Art.java            |   6       |
| DelannoyMemo.java   |   6       |
| readme.txt          |   6       |
| Total               |  40       |

### **Enrichment**

**Fractals in the wild.** Here's a Sierpinski triangle in [polymer clay](https://www.evilmadscientist.com/article.php/fimofractals), a [Sierpinski carpet cookie](https://www.evilmadscientist.com/article.php/fractalcookies), a [fractal pizza](https://slice.seriouseats.com/archives/2010/09/john-riepenhoffs-recursive-pizza.html), and a [Sierpinski hamantaschen](https://seattlelocalfood.com/2011/03/20/sierpinski-hamantaschen-sierpinskitaschen/).

**Fractal dimension (optional diversion).** In grade school, you learn that the dimension of a line segment is 1, the dimension of a square is 2, and the dimension of a cube is 3. But you probably didn't learn what is really meant by the term *dimension*. How can we express what it means mathematically or computationally? Formally, we can define the *Hausdorff dimension* or *similarity dimension* of a self-similar figure by partitioning the figure into a number of self-similar pieces of smaller size. We define the dimension to be the \\(\log\\) (# self similar pieces) / \\(\log\\) (scaling factor in each spatial direction). For example, we can decompose the unit square into four smaller squares, each of side length 1/2; or we can decompose it into 25 squares, each of side length 1/5. Here, the number of self-similar pieces is 4 (or 25) and the scaling factor is 2 (or 5). Thus, the dimension of a square is 2, since \\(\log (4) / \log(2) = \log (25) / \log (5) = 2\\). Furthermore, we can decompose the unit cube into 8 cubes, each of side length 1/2; or we can decompose it into 125 cubes, each of side length 1/5. Therefore, the dimension of a cube is \\(\log(8) / \log (2) = \log(125) / \log(5) = 3\\).

We can also apply this definition directly to the (set of white points in) Sierpinski triangle. We can decompose the unit Sierpinski triangle into three Sierpinski triangles, each of side length 1/2. Thus, the dimension of a Sierpinski triangle is \\(\log (3) / \log (2) ≈ 1.585\\). Its dimension is fractional—more than a line segment, but less than a square! With Euclidean geometry, the dimension is always an integer; with fractal geometry, it can be something in between. Fractals are similar to many physical objects; for example, the coastline of Britain resembles a fractal; its fractal dimension has been measured to be approximately 1.25.

[^1]: The Polish mathematician Wacław Sierpiński described the pattern in 1915, but it has appeared in Italian art since the 13th century. Though the Sierpinski triangle looks complex, it can be generated with a short recursive function.

