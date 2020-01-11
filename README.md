# LINGVO

## Tutorial
A language trainer program. You add words which you want to learn in some language, then LINGVO composes a test using the words. 
A utility program, which is used to configure LINGVO, is [LINGVO-configuration](https://github.com/AlexBobryshev93/LINGVO-configuration) .

### Steps to setup

**1. Clone the repository (alternatively you can just download it) using 
the following command:**

```bash
git clone https://github.com/AlexBobryshev93/LINGVO
```

**2. Compile the source code**

This can be done from the root directory of the project. Use the standard 
commands for JDK, e.g.:

```bash
javac *.java
```

**3. Use the program**

You can launch it from the project directory using the following:

```bash
java -cp . Lingvo
```

Now you are working with the standard configuration. Settings can be changed via the utility program.

The words are stored in files `lang1.txt` and `lang2.txt` . You should put every word on a single line without tabs and line-breaks.
The words should correspond their translation according to the line number (it's convenient to use text editors with line counters when you add
new words).