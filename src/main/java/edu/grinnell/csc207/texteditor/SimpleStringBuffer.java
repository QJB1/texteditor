package edu.grinnell.csc207.texteditor;

/**
 * A naive implementation of a text buffer using a <code>String</code>.
 */
public class SimpleStringBuffer {
    private StringBuilder buffer;
    private int cursorPosition;

    /**
     * Constructs a new, empty SimpleStringBuffer.
     */
    public SimpleStringBuffer() {
        this.buffer = new StringBuilder();
        this.cursorPosition = 0;
    }

    /**
     * Inserts character ch into the buffer at the cursor's current position,
     * advancing the cursor one position forward.
     *
     * @param ch the character to insert
     */
    public void insert(char ch) {
        buffer.insert(cursorPosition, ch);
        cursorPosition++;
    }

    /**
     * Deletes the character at the cursor's current position, moving the cursor one
     * position backwards.
     * Does nothing if there are no characters in the buffer.
     */
    public void delete() {
        if (cursorPosition > 0) {
            buffer.deleteCharAt(cursorPosition - 1);
            cursorPosition--;
        }
    }

    /**
     * Returns the current position of the cursor.
     *
     * @return the cursor position
     */
    public int getCursorPosition() {
        return cursorPosition;
    }

    /**
     * Moves the cursor one position backwards.
     * The cursor stays put if it is already at the beginning of the buffer.
     */
    public void moveBackwards() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }

    /**
     * Moves the cursor one position forwards.
     * The cursor stays put if it is already at the end of the buffer.
     */
    public void moveForwards() {
        if (cursorPosition < buffer.length()) {
            cursorPosition++;
        }
    }

    public int getSize() {
        throw new UnsupportedOperationException("Unimplemented method 'getSize'");
    }

    /**
     * Returns the ith character of the buffer, zero-indexed.
     *
     * @param i the index of the character to retrieve
     * @return the character at index i
     * @throws IndexOutOfBoundsException if i is out of bounds
     */
    public char getChar(int i) {
        if (i < 0 || i >= buffer.length()) {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }
        return buffer.charAt(i);
    }

    /**
     * Returns the contents of the buffer as a String.
     *
     * @return the buffer contents
     */
    @Override
    public String toString() {
        return buffer.toString();
    }

    
}
