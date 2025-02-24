# CSC 207: Text Editor

**Author**: Raymond Chu

## Resources Used

+ course materials & textbook - It gave me the instructions and guidance on how to complete the project. 
+ textbook for guidance – https://osera.cs.grinnell.edu/ttap/data-structures/from-c-to-java.html
+ project information – https://osera.cs.grinnell.edu/ttap/data-structures-labs/speed-reader.html 
+ Visual Studio Code – I used it to write the code
+ Maven – I used it to run the code 
+ Oracle Java API Documentation – to check for String or Scanner methods. 
+ Java Version 18.0.2

## Changelog

daf151f (HEAD -> main, origin/main, origin/HEAD) Added TUI, buffer, and tests
32a9049 Project files
02dc921 initial commit

## Part 2: Analyzing the Simple String Buffer – Complexity Analysis of `insert` in `SimpleStringBuffer`

Relevant Input(s):
A character `ch` inserted at the cursor position in the buffer.

Critical Operation(s):
- Shifting characters after the insertion point.  
- Creating a new string due to Java’s immutable `String`.  
- Concatenating substrings.

Mathematical Model:
Let `n` be the current length of the string. 
The `insert()` method math model is:  
f(n) = n + 1 + n + n = 3n + 1

first, you copy the left part of the string     O(n)
then insert new character                       O(1)
then copy right part of string                  O(n)
afterwards, combine all this into a new immutable string, which requires another full copy  O(n)

Big-O Characterization
O(n)

Justification:
Since `String` is immutable, every modification creates a new string, 
requiring O(n) copying of existing characters. This makes repeated insertions inefficient, 
especially for large text buffers.