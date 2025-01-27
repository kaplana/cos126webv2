---
title: 6. Image Classifier
subtitle: 
summary:  Write a program to classify images using the perceptron algorithm. <br>Partner assignment   {{< project "classifier" >}} |  {{< submit "Classifier" >}}
weight: 7
type: "page"
  
draft: false
reading_time: false
share: false
profile: false
comments: false

---
{{<construction>}}
{{< project "classifier" >}} |  {{< submit "Classifier" >}}

### **Goals**

- To learn about object-oriented programming concepts.
- To learn about machine learning (ML).
- To implement the *perceptron algorithm* -  a simple and beautiful example of ML in action.
- To write a client program that classifies images.



### **Getting Started**

- Read/scan this entire project description before starting to code.  This will provide a _big picture_ before diving into the assignment!

- Download and expand the  project `zip` file for this assignment, which contains the files you will need for this assignment.


- Read Sections 3.2 and 3.3 of the textbook on creating data types and designing data types.
- Review the [`Picture`](https://introcs.cs.princeton.edu/java/11cheatsheet/#Picture) and [`Color`](https://introcs.cs.princeton.edu/java/11cheatsheet/#Color) data types.

- This is a **partner** assignment.  Instructions for help finding a partner and creating a TigerFile group can be found on Ed.

- The rules for partnering are specified on the [course syllabus]({{< ref "syllabus.md#partners" >}}).  Make sure you read and understand these rules, and please post on Ed if you have questions. In your `acknowledgments.txt` file, you must indicate that you adhered to the COS 126 partnering rules.




### **Background**

Classification is one of the central problems in *ML*, a discipline that is transforming 21st century computing. As a familiar example, consider the problem of classifying handwritten digits using this web application {{< handwriting >}}



ML algorithms like this are widely used to classify handwritten digits (e.g., to recognize postal ZIP codes, process bank checks, and parse income tax forms).  The full power of ML derives from its amazing versatility. ML algorithms rely upon data to learn to make predictions, without being explicitly programmed for the task. For example, the same code you will write to classify handwritten digits extends to classifying other types of images, simply by training the algorithm with different data.

Moreover, ML techniques apply not only to images but also to numerical, text, audio, and video data. Modern applications of ML span science, engineering, and commerce: from autonomous vehicles, medical diagnostics, and video surveillance to product recommendations, voice recognition, and language translation.

### **Implementation Tasks**

- Implement three classes:
  - `Perceptron.java`
  - `MultiPerceptron.java`
  - `ImageClassifier.java` - a template is provided. 
- Submit a completed `readme.txt` file.
- Submit a completed `acknowledgments.txt` file.


### **Approach**

**Supervised learning.** To classify images, we will use a *supervised learning algorithm.* Supervised learning is divided into two phases — **training** and **testing**.

**Training.** In the *training phase*, the algorithm *learns* a function that maps an input to an output (or a label) using training data consisting of known input–output pairs. For the handwritten digit application, the training data comprise 60,000 grayscale images (inputs) and associated digits (labels). Here is a small subset:

![small training subset]({{< resource url="static/assignments/classifier/images/training-subset.png" >}})


In the *binary classification problem*, we seek to classify images into one of *two* classes (e.g., either the digit 6 or some digit other than 6). By convention, we use the _binary labels_: \\(+1\\) (*positive*) and \\(-1\\) (*negative*) to denote the two classes. In the *multiclass classification problem*, we allow for \\( m \\) classes and label them \\( 0, 1, ..., m-1 \\). For our handwritten digit application, there are \\( m = 10 \\) classes, with class \\( i \\) corresponding to digit \\( i \\).

**Testing.** In the *testing phase*, the algorithm uses the learned function to predict class labels for unseen inputs.

![small testing subset]({{< resource url="static/assignments/classifier/images/testing-subset.png" >}})

Typically, the algorithm makes some prediction errors (e.g., predicts 9 when the handwritten digit is 6). An important quality metric is the *test error rate* — the fraction of testing inputs that the algorithm misclassifies. It measures how well the learning algorithm generalizes from the training data to new data.


**Perceptrons**. A *perceptron* is a simplified model of a biological neuron. It is a function that takes a vector \\( x = x\_0, x\_1, \ldots, x\_{n-1} \\) of \\( n \\) real numbers as input and outputs (or predicts) either a \\(+1\\) or \\(−1\\) binary label . A perceptron is characterized by a vector \\( w = w\_0, w\_1, \ldots, w\_{n-1} \\) of \\( n \\) real numbers known as the *weight vector*. The perceptron computes the weighted sum

$$ S = w_0 \times x_0  +w_1 \times x_1 + \ldots + w_{n-1} \times x_{n-1} $$

and outputs the *sign* of the sum.

![weighted sum and sign]({{< resource url="static/assignments/classifier/images/weighted-sum.png" >}})

For the handwritten digit application, a perceptron will be trained to predict the binary label \\(+1\\) for images that correspond to a target digit and the binary label \\(−1\\) otherwise; the input vector \\( x \\) holds the grayscale values of each pixel in an image; and the weight vector \\( w \\) is pre-computed by a process described in the next paragraph.

**Perceptron algorithm.** How do we determine the values of the weight vector \\( w \\) so that the perceptron makes accurate predictions? The core idea is to use the training data of known input–output pairs to incrementally refine the weights. Specifically, we initialize all the weights to 0 and then process the labeled inputs one at a time. When we process a labeled input, there are three possibilities:

1. *Correct prediction*: The perceptron predicts the correct binary label (\\(+1\\) or \\(-1\\)) for the given input vector \\( x \\). In this case, we leave \\( w \\) unchanged.

1. *False positive*: The given input vector \\( x \\) is labeled \\(-1\\) but the perceptron predicts \\(+1\\). In this case, we adjust \\( w \\) as follows - for each \\(j\\): $$ w^\prime_j=w_j-x_j $$
     
1. *False negative*: The given input vector \\( x \\) is labeled \\(+1\\) but the perceptron predicts \\(-1\\). In this case, we adjust \\( w \\) as follows - for each \\(j\\): $$ w^\prime_j=w_j+ x_j $$

**Example.** Here is an example trace of the perceptron algorithm using four (4) labeled inputs, each of length \\( n=3 \\). In this example, an input \\( x \\) has a positive label \\(+1\\) if and only if the following is true: \\( x\_0 \le x\_1 \le x\_2 \\) ; otherwise it has a negative label \\(-1\\).

![example trace of the perceptron algorithm using four (4) labeled inputs]({{< resource url="static/assignments/classifier/images/example-labeled-inputs.png" >}})


### **Perceptron data type**

Create a data type that represents a perceptron by implementing the following API:

```java
public class Perceptron {

    // Creates a perceptron with n inputs. It should create an array
    // of n weights and initialize them all to 0.
    public Perceptron(int n)

    // Returns the number of inputs n.
    public int numberOfInputs()

    // Returns the weighted sum of the weight vector and x. 
    public double weightedSum(double[] x)

    // Predicts the binary label (+1 or -1) of input x. It returns +1
    // if the weighted sum is positive and -1 if it is negative (or zero).
    public int predict(double[] x)

    // Trains this perceptron on the binary labeled (+1 or -1) input x.
    // The weights vector is updated accordingly.
    public void train(double[] x, int binaryLabel)

    // Returns a String representation of the weight vector, with the
    // individual weights separated by commas and enclosed in parentheses.
    // Example: (2.0, 1.0, -1.0, 5.0, 3.0)
    public String toString()

    // Tests this class by directly calling all instance methods.   
    public static void main(String[] args)
}    
```



*Corner cases.* You may assume that the arguments to the constructor and instance methods are valid. For example, you may assume that any binary label is either \\(+1\\)  or \\(-1\\), any input vector \\( x \\) is of length \\( n \\), and \\( n \ge 1 \\).


### **Multiclass classification**

In the previous section, we used a single perceptron for the binary classification problem. For a multiclass classification problem with \\( m \\) classes, we create an array of \\( m \\) perceptrons, each solving its own binary classification problem. For our handwritten digit application, each perceptron \\( i \\) solves a binary classification problem: *does the image correspond to the digit \\( i \\)*


![multiclass classification problem with m classes]({{< resource url="static/assignments/classifier/images/multiclass-classification.png" >}})


We train each perceptron independently and make predictions by distilling the results from the \\( m \\) perceptrons.

- *Multiclass training.* Initialize the weight vector of each of the \\( m \\) perceptrons to be zero and process the labeled training inputs one at a time. To train the perceptrons on an input vector \\( x \\) with class label \\( i \\) (0 to \\( m-1 \\)):
	- Train perceptron \\( i \\) on input vector \\( x \\) with the binary label \\(+1\\)
	- Train the other \\( m-1 \\) perceptrons on input vector \\( x \\) with the binary label  \\(-1\\)  <br>

	That is, when training perceptron \\( i \\), we treat an input vector labeled \\( i \\) as a positive example and an input vector with any other label as a negative example.

- *Multiclass prediction.* To make a prediction for an input vector \\( x \\), compute the weighted sum for each of the \\( m \\) perceptrons on that input. The multiclass prediction is the index of the perceptron with the _largest_ weighted sum.
Intuitively, each perceptron with a positive weighted sum predicts that x is a positive example for its class, but we need to pick only one. Note that the perceptron with the largest weighted sum makes that prediction with the most intensity, so we assign the class label associated with that perceptron, even if the largest weighted sum is negative.

This *one-vs-all* strategy decomposes a multiclass classification task into \\(m\\) binary classification tasks. In computer science, this decomposition is known as a *reduction*; this particular kind of reduction is used all over ML.


**Example**. Here is an example of a multi-perceptron with \\(m\\) = 2 classes and \\(n =\\) 3 inputs.



![multi-perceptron with m = 2 classes and n = 3 inputs]({{< resource url="static/assignments/classifier/images/multiperceptron-m2-n3.png" >}})



### **MultiPerceptron data type**

Create a data type that represents a multi-perceptron by implementing the following API:

```java
public class MultiPerceptron {
    // Creates a multi-perceptron object with m classes and n inputs.
    // It creates an array of m perceptrons, each with n inputs.
    public MultiPerceptron(int m, int n) 

    // Returns the number of classes m.
    public int numberOfClasses() 

    // Returns the number of inputs n (length of the feature vector).
    public int numberOfInputs() 

    // Returns the predicted class label (between 0 and m-1) for the given input.
    public int predictMulti(double[] x)

    // Trains this multi-perceptron on the labeled (between 0 and m-1) input.
    public void trainMulti(double[] x, int classLabel) 

    // Returns a String representation of this MultiPerceptron, with
    // the string representations of the perceptrons separated by commas
    // and enclosed in parentheses.
    // Example with m = 2 and n = 3: ((2.0, 0.0, -2.0), (3.0, 4.0, 5.0))
    public String toString() 

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) 
}    
```

*Ties*. If two (or more) perceptrons tie for the largest weighted sum in `predictMulti()`, return the index of any such perceptron (between 0 and \\( m-1 \\)).

*Corner cases*. You may assume that the arguments to the constructor and instance methods are valid. For example, you may assume that any class label is an integer between 0 and \\( m-1 \\), any input vector \\( x \\) is of length \\( n \\), and \\( m \ge 1 \\) and \\( n \ge 1 \\).


### **ImageClassifier data type**

Your final task is to write a data type `ImageClassifier.java` that classifies images using the `MultiPerceptron` data type described in the previous section by:

- Training it using the input–output pairs specified in a *training data file.*
- Testing the predictions using the input–output pairs specified in a *testing data file*.
- Printing a list of misclassified images and the test error rate.

Organize your client according to the following API:

```java
public class ImageClassifier {
    // Uses the provided configuration file to create an
    // ImageClassifier object.
    public ImageClassifier(String configFile) 

    // Creates a feature vector (1D array) from the given picture.
    public double[] extractFeatures(Picture picture) 

    // Trains the perceptron on the given training data file.
    public void trainClassifier(String trainFile) 

    // Returns the name of the class for the given class label.
    public String classNameOf(int classLabel) 

    // Returns the predicted class for the given picture.
    public int classifyImage(Picture picture) 

    // Returns the error rate on the given testing data file.
    // Also prints the misclassified examples - see specification.
    public double testClassifier(String testFile) 

    // Tests this class using a configuration file, training file and test file.
    // See below.
    public static void main(String[] args) {
        ImageClassifier classifier = new ImageClassifier(args[0]);
        classifier.trainClassifier(args[1]);
        double testErrorRate = classifier.testClassifier(args[2]);
        System.out.println("test error rate = " + testErrorRate);
    }
}

```

Here are some details about the API: 

**Configuration file format.** A configuration file consists of a sequence of lines:
- the first line contains the width and height, respectively, of the images in the training and testing data files;
- the second line contains the number of classes \\(m\\);
- the remaining \\(m\\) lines contain the names of each class.

![Configuration file format]({{< resource url="static/assignments/classifier/images/configuration-file.png" >}})


**Training and testing file format.** A training data file  and testing data file have the format - a sequence of lines where:
- each line contains the name of an image file (e.g., corresponding to a handwritten digit) followed by an integer label (e.g., identifying the correct digit), separated by whitespace.


![Input file format]({{< resource url="static/assignments/classifier/images/input-file-format.png" >}})


For testing data files you will use the integer labels only to check the accuracy of your predictions.

**Input files.** We provide a variety of datasets in the specified format, including handwritten digits, fashion articles from Zalando, Hirigana characters, and doodles of fruit, animals, and musical instruments.  The handwritten digits and fashion articles are provided in your project folder. You can download the other datasets from {{< download "classifier" "datasets">}}

| Data files      | Description                  | <div style="width:390px">Examples</div>                                                                           | Source                                                           |
| :-------------: | :--------------------------: | :---------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------: |
| digits.jar      | *handwritten digits*         | ![hand written digits]({{< resource url="static/assignments/classifier/images/handwritten-digits.png" >}})        | [MNIST](http://yann.lecun.com/exdb/mnist/)<br> (Click _Cancel_ if prompted to sign in)                       |
| fashion.jar     | *fashion articles*           | ![fashion articles]({{< resource url="static/assignments/classifier/images/fashion-articles.png" >}})             | [Fashion MNIST](https://github.com/zalandoresearch/fashion-mnist)|
| Kuzushiji.jar   | *Hirigana characters*        | ![hirigana characters]({{< resource url="static/assignments/classifier/images/hirigana-characters.png" >}})       | [Kuzushiji MNIST](https://github.com/rois-codh/kmnist)           |
| fruit.jar       | *fruit doodles*              | ![fruit doodles]({{< resource url="static/assignments/classifier/images/fruit-doodles.png" >}})                   | [Quick, Draw!](https://quickdraw.withgoogle.com/data)            |
| animals.jar     | *animal doodles*             | ![]({{<resource url="static/assignments/classifier/images/animal-doodles.png" >}})                                | [Quick, Draw!](https://quickdraw.withgoogle.com/data)            |
| music.jar       | *musical instrument doodles* | ![musical instrument doodles]({{< resource url="static/assignments/classifier/images/musical-doodles.png" >}})    | [Quick, Draw!](https://quickdraw.withgoogle.com/data)            |

	


**Constructor**.  The `ImageClassifier` constructor takes a single argument - the file name for a _configuration_ file.  The constructor reads the data from configuration file and creates a `MultiPerceptron` object with \\( m \\) classes and \\( n = width \times height \\) inputs.  It also stores the class names provided in the configuration file.


**Feature extraction**. The `extractFeatures()` method converts a grayscale image into a one-dimensional array suitable for use with the `MultiPerceptron` data type. In one pass over the pixels of the image:
1. extract the grayscale values from each pixel and
1. rearrange them into a single 1D array (vector).

`extractFeatures()` **must** throw an `IllegalArgumentException` when the image's dimensions are not equals the dimensions provided in the configuration file.

Notes:
- Recall that a shade of gray has its *red, green, and blue components all equal*
- The one-dimensional array must be of length *width x height*

- In one pass, you must iterate over the image RGB pixel values in _row-major order_, extracting the grayscale value and setting the appropriate element in the  1D array (vector).

![Grayscale Values Matrix]({{< resource url="static/assignments/classifier/images/grayscale-values-matrix.png" >}})

**Training a classifier**. The `trainClassifier()` method takes the name of the training data file, and trains the image classifier using the images and labels provided in the file.

**classNameOf()**.  The `classNameOf(int classLabel)` method *must* thrown an
`IllegalArgumentException(` when `classLabel` less than \\(0\\) or greater than  \\(m - 1\\), where \\(m\\) is the number of class names.

**classifyImage()**.  The `classifyImage(Picture picture)` returns the predicted class \\(i\\), where \\(0 \leq i \leq m - 1\\) and \\(m\\) is the number of classes.

**Testing a classifier**.  The `testClassifier()` method takes the name of the testing data file, and tests the image classifier using the images and labels provided in the file.  *For each misclassified image*, it must also output the following information:

- the misclassified image’s filename,
- its correct class name, and
- the incorrectly predicted class name

in the format shown below. For example:
```plaintext
jar:file:digits.jar!/testing/6/4814.png, label = seven, predict = nine
jar:file:digits.jar!/testing/6/66.png, label = seven, predict = eight
jar:file:digits.jar!/testing/5/9982.png, label = six, predict = seven
jar:file:digits.jar!/testing/7/6561.png, label = eight, predict = ten
```

The `testClassifier()` method returns  the test error rate (the fraction of test images that the algorithm misclassified).


**Main.** The `main()` method takes three command-line arguments:
  1. The name of a file that contains the  configuration data.
  1. The name of a file that contains the training data.
  1. The name of a file that contains the testing data.

It then creates an `ImageClassifier` object, trains the classifier,  tests the classifier and prints the error rate.

A template for the `main()` test client is provided in `ImageClassifier.java` in the project folder.  

Here are some sample executions you should try:
	
```plaintext
java-introcs ImageClassifier digits.txt digits-training20.txt digits-testing10.txt
```
```plaintext
digits/testing/1/46.png, label = one, predict = three
digits/testing/7/36.png, label = seven, predict = two
digits/testing/7/80.png, label = seven, predict = two
digits/testing/1/40.png, label = one, predict = two
digits/testing/1/39.png, label = one, predict = three
digits/testing/7/79.png, label = seven, predict = two
digits/testing/9/20.png, label = nine, predict = two
digits/testing/9/58.png, label = nine, predict = four
test error rate = 0.8
```

```plaintext
java-introcs ImageClassifier digits.txt digits-training60K.txt digits-testing10K.txt
```
```plaintext
jar:file:digits.jar!/testing/6/4814.png, label = six, predict = zero
jar:file:digits.jar!/testing/5/4915.png, label = five, predict = eight
jar:file:digits.jar!/testing/6/2754.png, label = six, predict = zero
...
jar:file:digits.jar!/testing/4/1751.png, label = four, predict = three
jar:file:digits.jar!/testing/3/9614.png, label = three, predict = five
jar:file:digits.jar!/testing/5/6043.png, label = five, predict = three
test error rate = 0.1318
```

### **Possible Progress Steps**
We provide some additional instructions below.  Click on the &#9658;  icon to expand *some possible progress steps* or you may try to solve Classifier without them.  It is up to you!


{{< details "Click here to show possible progress steps" >}}



#### Implementing `Perceptron.java`

1. Implement the `Perceptron()` constructor and the method `numberOfInputs()`. Initialize any instance variables. We recommend using *two* instance variables: an integer  `n` and an array of floating-point numbers `weights[]`.
	- Test: In the `main()` method, instantiate a few `Perceptron` objects and print the number of inputs for each object.
1. Implement the `toString()` method.
	- Test: In the `main()` method, print the various `Perceptron` objects. What should the output be for a newly instantiated `Perceptron` object?
1. Implement the `weightedSum()` method.
	- Test: In the `main()` method, print the result of invoking the `weightedSum()` method on the various `Perceptron` objects (using, of course, appropriately sized arrays).
1. Implement the `predict()` method.
1. Implement the `train()` method.  Note: `train()` should call `predict()`.
1. You can test your implementation by using the code in the ***Testing*** section (below) and then submitting  to TigerFile. **Do not** move onto `MultiPerceptron` until `Perceptron` is working properly.

#### Testing Your `Perceptron.java` Implementation

Here is Java code that trains a `Perceptron` on four input vectors (of length three) based on the example provided in the assignment specification:


```java
// create a Perceptron with n inputs
int n = 3;
Perceptron perceptron = new Perceptron(n);
StdOut.printf("Test #1 - constructor Perceptron(%d)\n", n);
StdOut.println("  expect: (0.0, 0.0, 0.0)");
StdOut.println("  result: " + perceptron);

StdOut.println("Test #2 - numberOfInputs");
StdOut.printf("  expect: %d\n", n);
StdOut.println("  result: " + perceptron.numberOfInputs());

double[] training1 = { 3.0, 4.0, 5.0 };  // +1 (yes)
double[] training2 = { 2.0, 0.0, -2.0 };  // -1 (no)
double[] training3 = { -2.0, 0.0, 2.0 };  // +1 (yes)
double[] training4 = { 5.0, 4.0, 3.0 };  // -1 (no)

// Test 3: perceptron.weightedSum(training1))
//         perceptron.predict(training1))
//         perceptron.train(training1, +1); // yes
StdOut.println("Test #3a - weightedSum(3.0, 4.0, 5.0)");
StdOut.printf("  expect: %2.1f\n", 0.0);
StdOut.println("  result: " + perceptron.weightedSum(training1));

StdOut.println("Test #3b - predict(3.0, 4.0, 5.0)");
StdOut.printf("  expect: %d\n", -1);
StdOut.println("  result: " + perceptron.predict(training1));

StdOut.println("Test #3c - train(3.0, 4.0, 5.0, +1)");
perceptron.train(training1, +1); // yes
StdOut.println("  expect: (3.0, 4.0, 5.0)");
StdOut.println("  result: " + perceptron);

// Test 4: perceptron.weightedSum(training2))
//         perceptron.predict(training2))
//         perceptron.train(training2, -1); // no
// Write these tests - similar to Test #3

// Test 5: perceptron.weightedSum(training3))
//         perceptron.predict(training3))
//         perceptron.train(training3, +1); // yes
// Write these tests - similar to Test #3

// Test 6: perceptron.weightedSum(training4))
//         perceptron.predict(training4))
//         perceptron.train(training4, -1); // no
// Write these tests - similar to Test #3
```




#### Implementing `MultiPerceptron.java`

1. Implement the `MultiPerceptron()` constructor and the methods `numberOfClasses()` and `numberOfInputs()`. Initialize any instance variables; we recommend using three instance variables: an integer `m` (for the number of classes), an integer `n` (for the length of the input feature vector), and an array of `Perceptron` objects `perceptrons[]`.
	- Test: In the `main()` method, instantiate a few `MultiPerceptron` objects and print the number of classes and inputs for each object.  
1. Implement the `toString()` method.
	- Test: In the `main()` method, print the various `MultiPerceptron` objects. What should the output be for a newly instantiated `MultiPerceptron` object?
1. Implement the `predictMulti()` method.  
1. Implement the `trainMulti()` method.  
1. You can test by using the code in the ***Testing*** section (below) and then submitting to TigerFile.

#### Testing Your `MultiPerceptron.java` Implementation

Here is Java code that trains a `MultiPerceptron` on four input vectors (of length three) and then tests   a `MultiPerceptron` on two input vectors (of length three)  based on the example provided in the assignment specification:

```java
// training data
double[] training1 = { 3.0, 4.0, 5.0 };  // class 1
double[] training2 = { 2.0, 0.0, -2.0 };  // class 0
double[] training3 = { -2.0, 0.0, 2.0 };  // class 1
double[] training4 = { 5.0, 4.0, 3.0 };  // class 0

// Training test #1 - class 1
StdOut.println("Training test #1 class 1: trainMulti((3, 4, 5), 1)");
mp1.trainMulti(training1, 1);
StdOut.println("  expect: ((0.0, 0.0, 0.0), (3.0, 4.0, 5.0))");
StdOut.println("  result: " + mp1);

// Training test #2 - class 0
StdOut.println("Training test #2 class 0: trainMulti((2, 0, -2), 0)");
mp1.trainMulti(training2, 0);
StdOut.println("  expect: ((2.0, 0.0, -2.0), (3.0, 4.0, 5.0))");
StdOut.println("  result: " + mp1);

// Training test #3 - class 1
StdOut.println("Training test #3 class 1: trainMulti((-2, 0, 2), 1)");
mp1.trainMulti(training3, 1);
StdOut.println("  expect: ((2.0, 0.0, -2.0), (3.0, 4.0, 5.0))");
StdOut.println("  result: " + mp1);

// Training test #4 - class 0
StdOut.println("Training test #4 class 0: trainMulti(( 5, 4, 3 ), 0)");
mp1.trainMulti(training4, 0);
StdOut.println("  expect: ((2.0, 0.0, -2.0), (-2.0, 0.0, 2.0))");
StdOut.println("  result: " + mp1);

// testing data
double[] testing1 = { -1.0, -2.0, 3.0 };
double[] testing2 = { 2.0, -5.0, 1.0 };

// Testing test #1 - expect class 1
StdOut.println("Testing test #1 - predictMulti(-1, 2, 3)");
StdOut.println("  expect class: 1");
StdOut.println("  result result: " + mp1.predictMulti(testing1));

// Testing test #2 - expect class 0
StdOut.println("Testing test #2 - predictMulti(2, -5, -1)");
StdOut.println("  expect class: 0");
StdOut.println("  result class: " + mp1.predictMulti(testing2));
```	

#### Implementing `ImageClassifier.java`

*Part I. Constructor*.
1. Implement the `ImageClassifier()` constructor. 
2. Read the configuration file data, and store the configuration data in your instance variables.
  - Review the [`In`](https://introcs.cs.princeton.edu/java/11cheatsheet/#In) data type from Section 3.1, which is an object-oriented version of `StdIn`.
  - We recommend using the instance variables: integers  `width`, `height`, a `String` array `classNames[]` and a `MultiPerceptron` with \\( m \\) classes and \\( n = width \times height \\) inputs.
3. Print the instances variables to standard output.
4. Test your constructor in `main()` by creating various `ImageClassifier` objects using some different configuration files (`image3x3.txt`, `digits.txt`, `fashion.txt`, etc.)
5. Printing is solely for checking progress; comment out the print statements once you have confidence your constructor is working properly.

*Part II. Feature extraction*.

1. Review Section 3.1 of the textbook, especially Program 3.1.4 ([`Grayscale.java`](https://introcs.cs.princeton.edu/31datatype/Grayscale.java.html)) for using the [`Picture`](https://introcs.cs.princeton.edu/java/11cheatsheet/#Picture) and [`Color`](https://introcs.cs.princeton.edu/java/11cheatsheet/#Color) data types. Note that the images are already grayscale, so you *don’t* need to use `Luminance.java`. In particular, the red, green, and blue components are equal, so you can use any of `getRed()`, `getGreen()`, or `getBlue()` to get the grayscale value.
2. Comment out the test client code provided in `ImageClassifier.main()`.
3. Create a `Picture` object for the image `49785.png` (in the project folder) and display it in a window. (Remove this code after you have successfully displayed the image.)
4. Create a `Picture` object for the image `image3x3.png` (in the project folder) corresponding to the 3-by-3 example given below.
![Grayscale Values Matrix]({{< resource url="static/assignments/classifier/images/grayscale-values-matrix.png" >}})
    - Extract its width and height and print the values to standard output. Then, extract the grayscale values of the pixels and print. If it’s not already in row-major order, adjust your code so that it prints the values in the specified order.
    - If you are using IntelliJ, do not type the `import java.awt.Color;` statement that is normally needed to access Java’s `Color` data type. IntelliJ is pre-configured to automatically add import statements when needed (and remove them when not needed).
5.  Create a one-dimensional array of length *width x height* and copy the grayscale values to the array.  Print the values of this array to confirm you can create a vector (row-major order) of values from a `Picture` object.
6. Using the code from steps (4) and (5) as a guide, implement the  method `extractFeatures()` that takes a `Picture` as an argument and returns the grayscale values as a `double[]` in *row-major order*.
7. Write a `main()` method that tests `extractFeatures()`. Using `image3-by-3.png`, your `main()` method should print the values returned by `extractFeatures()` as shown in the above figure:
   ```
   ImageClassifier test = new ImageClassifier("image3x3.txt"); // create a small test
   Picture image3x3 = new Picture("image3x3.png");             
   double[] values = test.extractFeatures(image3x3);
   // print the array values
   ```
8. Once you are confident that `extractFeatures()` works, remove your testing code before submitting the assignment to TigerFile.

*Part III. Classifying images.*

1. Implement the `classifyImage()` method. For the given image, extract its features and use the multi-perceptron to predict its class label.
2. Implement the `classNameOf()` method. For the given class label, return the class name associated with it.
3. Test these two methods by creating an `ImageClassifier` object using the `digits.txt` configuration file.
4. Implement the `trainClassifier()` method. Read the training file data just as you read the configuration file. For each training image, extract its corresponding features and train the classifier using the corresponding label.
5. Implement the `testClassifier()` method. Read the test file data just as you read the configuration file. For each testing image, predict its class. Print each misclassified image to standard output and compute the error rate on these images.

{{< /details >}}

### **Training and Testing `ImageClassifier` Using Large Datasets**

Now, the fun part. Use large training and testing input files. Be prepared to wait for one (1) minute (or more) while your program processes 60,000 images.

```plaintext
java-introcs ImageClassifier digits.txt digits-training60K.txt digits-testing10K.txt
```
```plaintext
jar:file:digits.jar!/testing/5/9428.png, label = 5, predict = 3
jar:file:digits.jar!/testing/6/4814.png, label = 6, predict = 0
jar:file:digits.jar!/testing/5/4915.png, label = 5, predict = 8
...
jar:file:digits.jar!/testing/5/7870.png, label = 5, predict = 4
jar:file:digits.jar!/testing/4/1751.png, label = 4, predict = 3
jar:file:digits.jar!/testing/5/6043.png, label = 5, predict = 3
test error rate = 0.1318
```

Don’t worry about the odd looking filenames. It's just a verbose way to specify the location to a specific image file in a JAR (*Java ARchive*) file. Modern operating systems are not so adept at manipulating hundreds of thousands of individual image files, so this makes training more efficient. In this case, `jar:file:digits.jar` identifies the JAR file `digits.jar`, and `/training/7/4545.png` identifies a file named `4545.png`, which is located in the subdirectory `/training/7/` of the JAR file.


Try experimenting with the other datasets:

```plaintext
java-introcs ImageClassifier fashion.txt fashion-training60K.txt fashion-testing10K.txt
```
```plaintext
...
```
```plaintext
java-introcs ImageClassifier Kuzushiji.txt Kuzushiji-training60K.txt Kuzushiji-testing10K.txt
```
```plaintext
...
```
```plaintext
java-introcs ImageClassifier music.txt  music-training50K.txt music-testing10K.txt
```
```plaintext
...
```
```plaintext
java-introcs ImageClassifier fruit.txt fruit-training30K.txt fruit-testing6K.txt 
```
```plaintext
...
```
```plaintext
java-introcs ImageClassifier animals.txt animals-training60K.txt animals-testing12K.txt 
```
```plaintext
...
```

### **Analysis - `readme.txt`**

Provide your answers to Parts 1, 2 and 3 (below) in your `readme.txt` file.

**Part 1**:  Run the following experiment (you may want to redirect standard output to a file):

```
java-introcs ImageClassifier digits.txt digits-training60K.txt digits-testing1K.txt
```

- What digit is misclassified the most frequently?
- For this digit, what are the top two digits that your MultiPerceptron incorrectly predicts?
- Examine some of these misclassified images. Provide an explanation of what might have caused these misclassifications.

**Part 2**: Compute the following quantities using the experiments:

- The error rate on the images specified in `digits-testing1K.txt` before training your classifier.
>  To do this, you will need to  modify the main in `ImageClassifier`. Comment out the line: `// classifier.trainClassifier(args[1]);`
```
java-introcs ImageClassifier digits.txt digits-training60K.txt digits-testing1K.txt
```

- The error rate on the images specified in `digits-testing1K.txt` after training your classifier.
> To do this, you will need to  modify the main in `ImageClassifier`.  Uncomment the line: `classifier.trainClassifier(args[1]);`
```
java-introcs ImageClassifier digits.txt digits-training60K.txt digits-testing1K.txt
```

- Can we conclude that the classifier is actually learning?

**Part 3**: Some people (especially in Europe and Latin America) write a 7 with a line through the middle, while others (especially in Japan and Korea) make the top line crooked.


| Our data set | 7 with line through it | 7 with top line crooked |
| :-------------------------: | :---------------------------: | :--------------------------: |
| ![American 7]({{< resource url="static/assignments/classifier/images/7.png" >}})  | ![Europe and Latin America 7]({{< resource url="static/assignments/classifier/images/7withdash.png" >}}) | ![Asian 7]({{< resource url="static/assignments/classifier/images/7withhang.png" >}})  |



- Suppose that the training data consists solely of samples that do not use any of these conventions. How well do you think the algorithm will perform when you test it on different populations? What are the possible consequences?
- Now suppose that you are using a supervised learning algorithm to diagnose cancer. Suppose the training data consists of examples solely on individuals from population X but you use it on individuals from population Y. What are the possible consequences?

<!--
**Part 2**:  Run the following experiment (you may wish to redirect the standard output to a file):

```plaintext
java-introcs ImageClassifier digits.txt digits-training60K.txt digits-testing1K.txt
```

- What digit is misclassified the most frequently? 
- For this digit, what are the top two digits that your `MultiPerceptron` incorrectly predicts?
- Examine some of these misclassified images. Provide an explanation of what might have caused these misclassifications.

-->



### **Submission**

Submit to {{< tigerfile "Classifier" >}}: `Perceptron.java`, `MultiPerceptron.java`, `ImageClassifier.java`, and  completed `readme.txt` and `acknowledgments.txt` files.

### **Grading**
| Files                | Points    |
| ------------------   | ----------|
| Perceptron.java      |  12       |
| MultiPerceptron.java |  12       |
| Classifier.java      |  12       |
| readme.txt           |   4       |
| Total                |  40       |


### **Enrichment**

{{< details "Click each item  to expand.">}} Click again to collapse the answer.{{< /details >}}

{{< details "When was the perceptron algorithm first discovered?" >}} It was proposed in 1958 by Frank Rosenblatt, an American psychologist. It is one of the earliest machine-learning algorithms. At the time, researchers were overly optimistic about its short-term potential and grossly underestimated the formidable challenge of building intelligent machines.{{< /details >}}

{{< details "What is the significance of the perceptron algorithm?" >}}  It is a really simple, beautiful algorithm that, nevertheless, can do something interesting.

* The perceptron algorithm is one of the most fundamental algorithms in an area of ML called online learning (learning from samples one at a time).
* The perceptron algorithm is closely related to the support-vector machine algorithm, another fundamental ML algorithm.
* The perceptron algorithm has some beautiful theoretical properties. For example, if the training data set is linearly separable (i.e., there exists some weight vector that correctly classifies all of the training examples), and if we cycle through the training data repeatedly, the perceptron algorithm will eventually (and provably) find such a weight vector.
* Perceptrons are a building block in neural networks. In neural networks, many perceptrons (or other artificial neurons) are connected in a network architecture, in which the outputs of some neurons are used as the inputs to other neurons. Training multi-layer neural networks requires a more sophisticated algorithm to adjust the weights, such as gradient descent.{{< /details >}}

{{< details "Does the perceptron algorithm produce a different weight vector depending on the order in which it processes the training examples?" >}} Yes. We've randomized the training data. It would be a bad idea, for example, to process all of the handwritten 0s before all of the handwritten 6s.{{< /details >}}

{{< details "Any intuition for why the perceptron algorithm works?" >}} Geometrically, you can view each input vector \\( x \\) as a *point* in \\( R^n \\) and the weight vector \\( w \\) as a *hyperplane* through the origin. The goal of the perceptron algorithm is to find a hyperplane that separates the positive examples from the negative examples. Using vector terminology, the weighted sum is known as the *dot product*; its sign determines on which side of the hyperplane the point lies.

![Dot product]({{< resource url="static/assignments/classifier/images/dot-product.png" >}})

During training, when we encounter a point \\( x \\) that is on the wrong side of the hyperplane (i.e., a false positive or negative), we update the weight vector, thereby rotating the hyperplane slightly. After the rotation, x is either on the correct side of the hyperplane or, if not, at least a bit closer to the hyperplane (in terms of Euclidean distance).{{< /details >}}


{{< details "How can I improve accuracy of the perceptron algorithm?" >}} Here are three simple ideas:

* *Multiclass perceptron*. Instead of training all `m` perceptrons on each input vector, when there is a prediction error (multiclass perceptron predicts \\( i \\) but correct label is \\( k \\)), train only two perceptrons: train perceptron \\( i \\) (with label −1) and perceptron \\( k \\) (with label \\(+1\\)).
* Adjust the weights with a fraction of \\(+1\\) or \\(-1\\) for correct or incorrect predictions (this helps with a smoother convergence) and iterate over the training step multiple times, each time training the perceptron with the same set of training data (randomized in order). 
* Normalize the Features array data to have values between 0 and 1 (divide the values with 255) and initialize the perceptron weights to random values (with uniform random or Gaussian random to be less than 1 and on average 0).
* *Averaged perceptron*. Instead of using the last weight vector, take the average of the weight vectors that are computed along the way.
* *Incorporate more features*. Instead of using the feature vector \\( x\_0, x\_1,\ldots, x\_{n-1} \\), create additional features. In particular, for each pair of features \\( x\_i \\) and \\( x\_k \\), create a new feature  \\(x_{ik} = x\_i * x\_k \\). You could also keep going, adding not just pairs of features, but also triples, etc. This can significantly improve accuracy, but it becomes prohibitively expensive in terms of computation.

See this [paper](http://rob.schapire.net/papers/FreundSc98.pdf) for additional ideas, including the kernel trick and the voted-perceptron algorithm.
{{< /details >}}

{{< details "I noticed that the weights are always integral throughout the perceptron algorithm. Why is this?" >}} Adding or subtracting an integer to an integer yields an integer. The weights are always adjusted by either adding or subtracting the input vector; for the image classification problems we consider in this assignment, the input vector elements are integers between 0 and 255 (grayscale values).{{< /details >}}

{{< details "As a perceptron is trained with more and more data, the weights increase in absolute value. Is this to be expected?" >}} Yes. This implies that the algorithm makes smaller and smaller relative changes to the weights as it learns from more and more input–output pairs.{{< /details >}}

{{< details "Why not use the testing data for training?" >}} We seek a model that can make good predictions for unseen input–output pairs. If we use the testing data when training, the algorithm could “memorize” the input–output pairs. While this might achieve higher accuracy on those inputs, the results may not generalize to unseen inputs.{{< /details >}}

{{< details "What is the difference between supervised and unsupervised learning?" >}} In supervised learning, the algorithm has access to training data for which we know the correct labels. These labels act as a teacher supervising the learning process. In unsupervised learning, the training data is unlabeled, so there are no correct answers; instead, the goal may be to cluster similar inputs together.{{< /details >}}

{{< details "How can I make it recognize my own handwritten digits?" >}} Use the same technique, but you must be careful to size-normalize and center the images, as is done in the [MNIST training data](https://en.wikipedia.org/wiki/MNIST_database).{{< /details >}}

{{< details "Can ML algorithms be trained to classify alphabetic and mathematical symbols?" >}} Yes. Here are web apps that let you draw and find the most similar [Unicode characters](https://shapecatcher.com/) or [LaTeX symbols](https://detexify.kirelabs.org/classify.html).{{< /details >}}

{{< details "Any games based on classifying hand-drawn images?" >}} Yes. Try [Quick, Draw!](https://quickdraw.withgoogle.com/), which uses neural networks and the world’s largest doodling data set.{{< /details >}}
{{< details "What is the best performing algorithm for classifying handwritten digits using the MNIST dataset?" >}} The current champion uses convolution neural networks and achieves a 99.79% accuracy rate on the MNIST testing database consisting of 10,000 images. Here are the 21 incorrect predictions:

![Incorrect Predictions]({{< resource url="static/assignments/classifier/images/incorrect.png" >}})


There is not much room for improvement; indeed, some of the errors appear to be due to incorrectly labeled (or ambiguous) inputs.{{< /details >}}




This assignment was developed by Sebastian Caldas, Robert DeLuca, Ruth Fong, Maia Ginsburg, Alan Kaplan, Kevin Jeon, and Kevin Wayne.


