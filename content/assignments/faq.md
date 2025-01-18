---
title: Assignment FAQ
subtitle: 
summary:   Some frequently asked questions *and* answers concerning assignments.
weight: 1000
type: "page"
  
reading_time: false
share: false
profile: false
comments: false
---
Click the Question below to expand.  Click the Question again to collapse.

### Submission and  Check All Submitted Files

{{< details "What's ***TigerFile***?"  >}}
**Answer:** A web-based system that you will use to submit assignments. Every assignment has its own submission link.  
{{< /details >}}

{{< details "Can I submit assignments via email, hardcopy, or Snapchat?"  >}}
**Answer** No, all assignments submissions must be done via TigerFile.
{{< /details >}}

{{< details "How do I successfully submit an assignment?"  >}}
**Answer:** Remember to:
- Remove any extraneous debugging statements from your code.
- Upload all of the required files. The filenames are case-sensitive. Be sure to submit the most up-to-date version of your code.
- After testing on *your* computer, click the `Check Submitted Files` button to receive feedback from the *autograder*. Fix any errors.  (Note - the autograder does NOT actually produce a grade, but does perform various tests in your code.)
- Complete and upload the `acknowledgments.txt` file to indicate that your solutions are ready to be graded.
{{< /details >}}

{{< details "What is a `readme.txt` file?"  >}}
**Answer:** It is a plain text file that contains a narrative description of your work. This is the first thing the grader will read. Each week, we'll provide a template `readme.txt` file. Be sure to answer any questions that it contains.  Do not place any identifying information in the `readme.txt` file - e.g., name, netid or email.
{{< /details >}}


{{< details "What is an `acknowledgments.txt` file?"  >}}

**Answer:** It is a plain text file that contains your acknowledgement of original work (as specified in [Rights, Rules, Responsibilities](https://rrr.princeton.edu), any citations, names and dates of those who provided help, etc. Submitting the  `acknowledgments.txt` indicates that you have stopped working on your assignment and your submitted work is ready to be graded.  Your assignment will only be graded if the  `acknowledgments.txt` is submitted.
{{< /details >}}

{{< details "Which program should I use to edit the `readme.txt` and `acknowledgments.txt` files?"  >}}

**Answer:** Use IntelliJ. Be careful to save the file as `readme.txt` or `acknowledgments.txt` and not as a `.java` file. The `.txt` file must be a plain text file. Note, Microsoft Word `.doc` or `.docx` formats and Mac TextEdit `.rtf` formats will not be accepted. Please do not rename your `.doc`, `.docx`, or `.rtf` file to `.txt` as this will cause your file not to be readable by our system.  

{{< /details >}}

{{< details "How do I submit an assignment after the deadline?"  >}}
**Answer:** There is no need to contact the staff.  Just submit the `acknowledgments.txt` file when you have completed your assignment. 
{{< /details >}}

{{< details "On assignments that allow partnering, how do I submit?"  >}}

**Answer:** First, you must form a group by clicking the green Create Group button in TigerFile. You must do this even if you are working alone—a group of one. If you are working with a partner, add your partner as a group member. Your partner will receive an email with a link to confirm the partnership. Once you have created a group, any group member may submit. 
{{< /details >}}

{{< details "Can I correct my program and resubmit?"  >}}
**Answer:** Absolutely. That's the point of this sytem. There is no penalty for resubmitting files prior to the deadline.
{{< /details >}}


{{< details "When I submit a file, TigerFile reports ***Filename is not in list***. Why is it rejecting my submission?"  >}}
**Answer:** Be sure the file has the correct name. Both the capitalization and .java extension are important. We recommend configuring your system to show the file extensions. Here are instructions for [Mac](https://support.apple.com/guide/mac-help/show-or-hide-filename-extensions-on-mac-mchlp2304/mac) and [Windows](https://www.thewindowsclub.com/show-file-extensions-in-windows)).
{{< /details >}}

{{< details "What exactly does the ***Check Submitted Files*** button in TigerFile do?"  >}}
**Answer:** It compiles your code and checks that it conforms to the assignment specifications by running a battery of correctness tests. On some assignments, it also performs memory and timing tests. It also runs three static code analysis tools—Spotbugs, PMD, and Checkstyle—that automatically identify common bug patterns and style issues. {{< /details >}}


{{< details "Must I submit all of the required files to receive feedback?"  >}}
**Answer:** No. The autograder will give feedback on whichever files you submit, provided that they compile. {{< /details >}}

{{< details "I receive various compiler errors and warnings. What does this mean?"  >}}
**Answer:** Your programs should compile cleanly, with no errors or warnings. Consult a staff member if you are unsure how to fix them. {{< /details >}}

{{< details "I receive various ***FAILED*** messages in the correctness, timing, or memory tests. What does this mean?"  >}}
Your program does not meet the assignment specifications on the tested inputs. Failing any of these tests will likely lead to a deduction. (Passing all of these tests is a good sign, but does not guarantee that your program is 100% correct; we run additional tests when grading.) Consult the course staff if you do not understand the error message. {{< /details >}}

{{< details "I receive various SpotBugs, PMD, or Checkstyle warnings. What does this mean?"  >}}
- [Spotbugs](https://spotbugs.github.io) is a program that looks for [common bug patterns](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html) in your code.  You should treat anything it reports as an error  (though, occasionally there are false positives). 
- [PMD](https://pmd.github.io/pmd-6.34) is a program that looks for [common programming flaws](https://pmd.github.io/pmd-6.34/pmd-java/index.html) in your code.  
-  [Checkstyle](http://checkstyle.sourceforge.net) is a program that looks for [common style issues](http://checkstyle.sourceforge.net/availablechecks.html) in your program.  The appearance of a warning message does not necessarily lead to a deduction (and, in some cases, it does not even indicate an error).  You should pay particular attention to the custom checks, which are  specialized to each program.    As you gain programming experience, you'll get used to the myriad (and sometimes seemingly arbitrary) conventions that good programmers employ, and you will learn to like Checkstyle.
- Consult the course staff if you are unsure what an error/warning message means or how to fix it.
 {{< /details >}}

{{< details "I receive the error ***Process took too long and was killed (output may be truncated)***. What could cause this?"  >}}
**Answer:** This usually means that the autograder could not complete in the allocated amount of time. One common cause is an infinite loop. Another possibility is that your program produces an enormous amount of output, perhaps because you left in a debugging statement.
 {{< /details >}}



{{< details "When I click the **Check Submitted Files** button, I receive a ***failed to fetch NetworkError when attempting to fetch resources*** message. How can I fix that?"   >}}
**Answer:** This is your browser’s way of telling you that you are not connected to the Internet.
 {{< /details >}}

### Testing

{{< details "Do my programs need to handle every conceivable input?"  >}}
**Answer:** No. Your program must handle all meaningful inputs, but you need not worry about meaningless ones. For example, when testing `RGBtoCMYK.java`, we will always supply three command-line arguments that are integers between 0 and 255.
{{< /details >}}


{{< details "Do I have to test my code?"  >}}

**Answer:** Yes. For some assignments, you will be given very clear instructions on how to implement the test client. In others, you will be asked to implement your own test client that tests the methods you have implemented. Although the autograder tests your code, you are expected to test your code before you use the autograder.

For grading purposes, your test client will satisfy the requirements if it calls every public method in your code at least once in a meaningful way (by printing the return value, for example). For genuinely testing your code, you will often need to do more than that.
{{< /details >}}



### Java Programming

{{< details "Which Java programming environment should I use?"  >}}

**Answer:** We strongly recommend using IntelliJ for this course.  We can only provide support for IntelliJ.

{{< /details >}}
{{< details "How can I download a program from the textbook?"  >}}

**Answer:** The [booksite](https://introcs.cs.princeton.edu) contains links to all of the Java programs in the textbook. Each link goes to an HTML file (such as `UseArgument.java.html`) that is suitable for display in a browser. You can copy-and-paste the code into IntelliJ as needed, with appropriate attribution. The HTML file contains a link to the Java source file (such as `UseArgument.java`); you can right click this link and save the file to your computer.

{{< /details >}}
{{< details "How should I read in user input?"  >}}

**Answer:** It depends on the assignment. If the assignment specifies command-line arguments, your program should accept command-line arguments; if the assignment specifies standard input, your program should accept input from standard input. You will receive a significant deduction for not following these directions.

{{< /details >}}
{{< details "May I use (advanced) features of Java that have not yet been covered in the course?"  >}}

**Answer:** No, your code should use only the Java language features previously introduced in the course. 

### Project Files

{{< /details >}}
{{< details "How can I extract the `.zip` file associated with an assignment?"  >}}

**Answer:** You must use the project folder with IntelliJ. This is provided as a .zip file. Often associated data files that are useful for testing are included. They are bundled together in a .zip file, which you will need to extract:

- Zip files on OS X. Download the `.zip` file listed on the assignment page. In Finder, view the directory containing the downloaded `.zip` file. Some browsers will automatically unzip the `.zip` file and create a project folder -- which will have the same name without the `.zip` extension -- containing the individual files. (Some browsers may also delete the original `.zip` file.) Otherwise, if you see the `.zip` file but no project folder, double-click the `.zip` file to create the project folder. You will want to create the .java files in this project folder.
- Zip files on Windows. Download the `.zip` file listed on the assignment page. In Explorer, view the directory containing the downloaded `.zip` file. Right-click on the `.zip` file and select Extract All..., which will ask you to choose the extraction location. This creates a new project folder -- which will have the same name without the `.zip` extension -- containing the individual files.. (Do not double click the `.zip` file—that browses the contents instead of extracting them.) You will want to create the `.java` files in this project folder.
{{< /details >}}
### Graded Work

{{< details "How will I access my work after it has been graded?"  >}}
**Answer:** You will login to [codePost](https://codepost.io), a system designed by Princeton undergraduates to streamline our grading workflow.  Always make sure you review your feedback!
{{< /details >}}

### Style

{{< details "How should I format my code?"  >}}

**Answer:** 

- Do not include personally identifiable information in your files.  The course is graded entirely anonymously. This is only possible if you do not also include your name or netid in the files you submit. 
- Indent your code consistently. Use four (4) spaces for each indentation level. IntelliJ does this automatically. Under the `Code` menu, select `Reformat Code`
- Do not use _hard_ tabs. Different systems and tools display and print hard tabs differently. Hard tabs are obsolete: in ancient times, they were used for data compression. Modern text editors support _soft_ tabs, which automatically substitute spaces for tabs. If you are using IntelliJ, you are all set.
- Do not exceed 80 characters per line. Long lines are hard to read and will annoy your grader. Use line breaks as needed. This rule also applies to the `readme.txt` file and `acknowledgments.txt` file.
- Use whitespace for readability. Insert blank lines to separate logical sections of your code. Insert a space between arithmetic operators. Until you get a feel for what is expected, use the output of Checkstyle as a guide.

{{< /details >}}
{{< details "How should I compose a program?"  >}}

**Answer:**
- Use meaningful variable names. Each variable's purpose should be obvious from its name, if possible. For example, name a variable `isOrdered` instead of `o` or `ordered`. One common exception to this rule is with loop-index variables: they often have short names, such as `i` or `j`.
- Use straightforward logic and flow-of-control. For example, you should simplify the statement if`(isBlue == true)` to `if (isBlue)`.
- Follow directions. For example, if a command-line argument is specified as an integer, read it into a variable of type `int`; if the assignment says to use a `while` loop, use a `while` loop.
- Follow output formats exactly. If the assignments specifies to print `Hello, World`, don't print `Hello Moo`n instead. You should also have the specified number of lines of output. This means that you should remove,or comment out, extraneous debugging print statements before submitting.)
- Avoid magic numbers. A *magic number* is an unnamed numeric constant (other than `-1`, `0`, `1`, or `2`, used in an obvious manner). Magic numbers make programs hard to debug and maintain. 
- Declare variables to minimize scope. For example, variables that are only used within a loop should be declared within the loop itself.  In object-oriented programs (starting with the _Classifier_ assignment), don't declare a variable to be an instance variable if it is used only as a local variable in one method. All instance variables must be private. 

{{< /details >}}

{{< details "How should I comment my code?"  >}}

**Answer:** 

- Include a brief comment above each code "paragraph." Some experienced programmers write the comments before they write the code. Outlining or mapping out your program before you code is the best way to make sure you fully understand what the program needs to do and to avoid bugs. But these comments can be succinct (a few words is okay).
- Include a comment describing every method and class (whether public or private).
- Include a comment describing every instance variable (including those for nested classes).
{{< /details >}}




