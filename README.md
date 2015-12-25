This repository contains code, exercises and solutions from the book "Functional Programming in Java". Along with the book itself, it's the
closest you'll get to having your own private functional programming tutor without actually having one.

[![Build Status][badge-travis]][travis]

[badge-travis]: https://travis-ci.org/fpinjava/fpinjava.png?branch=master
[travis]: https://travis-ci.org/fpinjava/fpinjava

## How to use the project

The code is available as a Gradle project that may be used  by:

- Running Gradle commands in a terminal
- Importing in Eclipse (with limitations)
- Importing in IntelliJ
- Importing in NetBeans

One may of course use only the source files to import them into any other editor and/or compile and run them from the command line. Running Gradle
commands will not be described here. If you choose this way, you should already know how to do it.

## Downloading the project

There are two ways to download this project. You may:

- Click on the **Download ZIP** button on this page (https://github.com/fpinjava/fpinjava) to download a ZIP archive, then unzip it in the directory
of your choice
- Or you can clone the project using Git, in order to be able to update it easily when modifications or additions are made to the project. For this,
you will need to have Git installed on your PC and use the URL available on this page in the **SSH clone URL** area.

## Importing into Eclipse

Note: Eclipse is not fully compatible with Java. Eclipse does not use the Oracle compiler. The compiler used by Eclipse is not fully compatible with the Oracle Java 8 compiler. This might change in the future, but at this time, not all chapter examples will run under Eclipse. You can use Eclipse Luna to edit and run the code through chapter 8. You can't use Eclipse Mars. We have no information about if and when this will be fixed.

To import the project into Eclipse, you must first install the Gradle plug-in. To do this:

1. Select **Help > Eclipse** MarketPlace
1. In the **Find** text area, type **Gradle**
1. Click the **Go** button
1. Select the **Gradle integration for Eclipse** plugin and click the corresponding **Install** button.

You will have to accept installing unverified software, and then restarting Eclipse.

### Importing the project

You now need to import the `fpinjava-parent` project into Eclipse:

1. Select **File > Import > Gradle > Gradle Project**
1. In the **Import Gradle** project dialog box, click the **Browse** button, navigate to the directory were you put the project and select
the `fpinjava-parent` directory
1. Click on the **Build Model** button
1. In the dialog box, verify that all modules are selected. If they are not, selecting the parent module will automatically select all sub-modules.
1. Keep all standard settings untouched and click **Finish**. The project should now be imported.

## Importing into IntelliJ

To import the project into intelliJ:

1. Select **File > Import** project
1. In the **Select file or directory to import** dialog box, navigate to the directory where you have installed the project and select
the `fpinjava-parent` directory.
1. In the import project dialog box, select Gradle and click the **Next** button.
1. Click the **Finish** button
1. Find the `fpinjava-parent` project in the **Project** pane, right-click  the project and select **Open module settings**.
1. In the **Project Structure** dialog box, click on **Project** in the **Project setting** pane and select **8 - Lambdas, type annotations etc.**
for the **Project language level** option.
1. Click **OK**.

## Importing into NetBeans

To import the project into Netbeans, you must first install the Gradle plug-in. To do this:

1. Select **Tools > Plugins > Available Plugins**
1. In the **Search** text area, type **gradle**
1. Tick the **Install** checkbox on the **Gradle Support** row.
1. Click the **Install** button.

You will have to accept installing unsigned software, and then restarting NetBeans.

### Importing the project

You now need to import the `fpinjava-parent` project into NetBeans:

1. Select **File > Open Project...**
1. In the **Open Project** dialog box, go to the directory were you put the project and select
the `fpinjava-parent` project name
1. Click on the **Open Project** button

The project should now be imported.


## Doing the exercises

For each chapter, you will find two modules called `chaptername-exercises` and `chaptername-solutions` . Go to the first exercise in
the `src/main/java` hierarchy. Here, you will find some code with either a comment saying "To be implemented" or method(s) with the
implementation replaced with a single line throwing a runtime exception. Just implement the missing code.

Note that code is often duplicated from one exercise to the another, so you should not look at the code for exercise 2 before doing exercise 1,
since exercise 2 will often contain the solution to exercise one.

## Verifying your answers

To verify that your solution is working, go to the corresponding unit test, in the `src/test/java` hierarchy of the same module. Right-click
on the test class (which has the same name as the exercise class with the addition of the `Test` suffix) and select **Run as JUnit test**. The test should
succeed. If it doesn't, fix your code and try again.

## Looking at solutions

If you don't find the correct solution to an exercise, you can look at the corresponding `chaptername-solutions` module. You may run the solution
test to verify that the solution is working.

## Remarks

Lots of code is duplicated. This is done so that all exercises are made as independent as possible. However, code reused from previous chapters
is copied to the `fpinjava-common` module and should be used from there.

## Module names

Code modules are generally named after the chapter titles, and not the chapter numbers, which sometimes make them difficult to find. Here is the list of the modules:

* Chapter 1: fpinjava-introduction

* Chapter 2: fpinjava-usingfunctions

* Chapter 3: fpinjava-makingjavafunctional

* Chapter 4: fpinjava-recursion

* Chapter 5: fpinjava-lists

* Chapter 6: fpinjava-optionaldata

* Chapter 7: fpinjava-handlingerrors

* Chapter 8: fpinjava-advancedlisthandling

* Chapter 9: fpinjava-laziness

* Chapter 10: fpinjava-trees

* Chapter 11: fpinjava-advancedtrees

* Chapter 12: fpinjava-state

* Chapter 13: fpinjava-io

* Chapter 14: fpinjava-actors

* Chapter 15: fpinjava-applications

Most modules exist in two versions: exercises and solutions. However, chapters 1 (fpinjava-introduction), 14 (fpinjava-actors) and 15 (fpinjava-applications) have no exercises.

Most modules have unit tests that may be run to verify that your solutions to exercises are correct. However, chapter 13 has not unit tests. Instead, all packages in this chapter have executable programs that may be run to verify that the output corresponds to what is expected.



