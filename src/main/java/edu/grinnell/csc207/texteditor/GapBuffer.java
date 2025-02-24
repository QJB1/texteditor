package edu.grinnell.csc207.texteditor;

/**
 * A gap buffer-based implementation of a text buffer.
 */
public class GapBuffer {
    private char[] buffer;
    private int gapStart;
    private int gapEnd;
    private static final int INITIAL_CAPACITY = 10;

    /**
     * Constructs an empty GapBuffer with initial capacity.
     */
    public GapBuffer() {
        buffer = new char[INITIAL_CAPACITY];
        gapStart = 0;
        gapEnd = INITIAL_CAPACITY;
    }

    /**
     * Inserts a character at the cursor position and moves the cursor forward.
     * If the gap is full, it expands the buffer.
     *
     * @param ch the character to insert
     */
    public void insert(char ch) {
        if (gapStart == gapEnd) {
            expandBuffer();
        }
        buffer[gapStart] = ch;
        gapStart++;
    }

    /**
     * Deletes the character before the cursor.
     */
    public void delete() {
        if (gapStart > 0) {
            gapStart--;
        }
    }

    /**
     * Returns the cursor position.
     *
     * @return the cursor position
     */
    public int getCursorPosition() {
        return gapStart;
    }

    /**
     * Moves the cursor one position left.
     */
    public void moveLeft() {
        if (gapStart > 0) {
            gapEnd--;
            buffer[gapEnd] = buffer[gapStart - 1];
            gapStart--;
        }
    }

    /**
     * Moves the cursor one position right.
     */
    public void moveRight() {
        if (gapEnd < buffer.length) {
            buffer[gapStart] = buffer[gapEnd];
            gapStart++;
            gapEnd++;
        }
    }

    /**
     * Returns the character at the specified index.
     *
     * @param i the index to retrieve
     * @return the character at index i
     */
    public char getChar(int i) {
        if (i < 0 || i >= getSize()) {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }
        return (i < gapStart) ? buffer[i] : buffer[i + (gapEnd - gapStart)];
    }

    /**
     * Returns the contents of the buffer as a String.
     *
     * @return the buffer contents
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gapStart; i++) {
            sb.append(buffer[i]);
        }
        for (int i = gapEnd; i < buffer.length; i++) {
            sb.append(buffer[i]);
        }
        return sb.toString();
    }

    /**
     * Returns the size of the buffer.
     *
     * @return the number of characters stored
     */
    public int getSize() {
        return buffer.length - (gapEnd - gapStart);
    }

    /**
     * Expands the buffer when the gap is full.
     */
    private void expandBuffer() {
        int newSize = buffer.length * 2;
        char[] newBuffer = new char[newSize];

        System.arraycopy(buffer, 0, newBuffer, 0, gapStart);
        System.arraycopy(buffer, gapEnd, newBuffer, newSize - (buffer.length - gapEnd), buffer.length - gapEnd);

        gapEnd = newSize - (buffer.length - gapEnd);
        buffer = newBuffer;
    }
}
