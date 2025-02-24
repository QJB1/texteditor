package edu.grinnell.csc207.texteditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;

public class GapBufferTests {
    @Test
    public void testInsert() {
        GapBuffer buffer = new GapBuffer();
        buffer.insert('H');
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');
        assertEquals("Hello", buffer.toString());
        assertEquals(5, buffer.getCursorPosition());
    }

    @Test
    public void testDelete() {
        GapBuffer buffer = new GapBuffer();
        buffer.insert('A');
        buffer.insert('B');
        buffer.insert('C');
        buffer.delete();
        assertEquals("AB", buffer.toString());
        assertEquals(2, buffer.getCursorPosition());

        buffer.delete();
        buffer.delete();
        assertEquals("", buffer.toString());
        assertEquals(0, buffer.getCursorPosition());

        buffer.delete(); // Should not cause an error
        assertEquals("", buffer.toString());
        assertEquals(0, buffer.getCursorPosition());
    }

    @Test
    public void testMoveCursor() {
        GapBuffer buffer = new GapBuffer();
        buffer.insert('X');
        buffer.insert('Y');
        buffer.insert('Z');

        buffer.moveLeft();
        assertEquals(2, buffer.getCursorPosition());

        buffer.moveLeft();
        assertEquals(1, buffer.getCursorPosition());

        buffer.moveLeft();
        assertEquals(0, buffer.getCursorPosition());

        buffer.moveLeft(); // Should not move beyond start
        assertEquals(0, buffer.getCursorPosition());

        buffer.moveRight();
        assertEquals(1, buffer.getCursorPosition());

        buffer.moveRight();
        buffer.moveRight();
        assertEquals(3, buffer.getCursorPosition());

        buffer.moveRight(); // Should not move beyond end
        assertEquals(3, buffer.getCursorPosition());
    }

    @Test
    public void testGetChar() {
        GapBuffer buffer = new GapBuffer();
        buffer.insert('~');
        buffer.insert('?');
        buffer.insert('>');

        assertEquals('~', buffer.getChar(0));
        assertEquals('?', buffer.getChar(1));
        assertEquals('>', buffer.getChar(2));

        assertThrows(IndexOutOfBoundsException.class, () -> buffer.getChar(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.getChar(3));
    }

    /**
     * Property-based test: inserting a sequence of characters should result in a buffer
     * matching the concatenation of those characters.
     */
    @Property
    public void testInsertProperty(@ForAll String input) {
        GapBuffer buffer = new GapBuffer();
        for (char ch : input.toCharArray()) {
            buffer.insert(ch);
        }
        assertEquals(input, buffer.toString());
        assertEquals(input.length(), buffer.getCursorPosition());
    }

    /**
     * Property-based test: deleting all characters from a buffer should leave it empty.
     */
    @Property
    public void testDeleteProperty(@ForAll String input) {
        GapBuffer buffer = new GapBuffer();
        for (char ch : input.toCharArray()) {
            buffer.insert(ch);
        }
        for (int i = 0; i < input.length(); i++) {
            buffer.delete();
        }
        assertEquals("", buffer.toString());
        assertEquals(0, buffer.getCursorPosition());
    }

    /**
     * Test buffer expansion by inserting beyond initial capacity.
     */
    @Test
    public void testBufferExpansion() {
        GapBuffer buffer = new GapBuffer();
        for (int i = 0; i < 100; i++) {  // Insert more than default capacity
            buffer.insert((char) ('A' + (i % 26)));
        }
        assertEquals(100, buffer.getCursorPosition());
        assertEquals(100, buffer.toString().length());
    }

    /**
     * Property-based test: GapBuffer should correctly expand when inserting large text.
     */
    @Property
    public void testBufferExpansionProperty(@ForAll @Size(max = 500) String input) {
        GapBuffer buffer = new GapBuffer();
        for (char ch : input.toCharArray()) {
            buffer.insert(ch);
        }
        assertEquals(input, buffer.toString());
    }
}
