package edu.grinnell.csc207.texteditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.googlecode.lanterna.input.KeyStroke;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * The driver for the TextEditor Application.
 */
public class TextEditor {

    /**
     * The main entry point for the TextEditor application.
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java TextEditor <filename>");
            System.exit(1);
        }

        // TODO: fill me in with a text editor TUI!
        String path = args[0];
        System.out.format("Loading %s...\n", path);

        GapBuffer buffer = new GapBuffer();
        //SimpleStringBuffer buffer = new SimpleStringBuffer();

        // Load file contents if it exists
        Path filePath = Paths.get(path);
        if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
            try {
                String content = Files.readString(filePath);
                for (char ch : content.toCharArray()) {
                    buffer.insert(ch);
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                System.exit(1);
            }
        }

        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(new TerminalPosition(0, 0));

            boolean isRunning = true;
            while (isRunning) {
                drawBuffer(buffer, screen);
                KeyStroke stroke = screen.readInput();

                switch (stroke.getKeyType()) {
                    case Character:
                        buffer.insert(stroke.getCharacter());
                        break;
                    case Backspace:
                        buffer.delete();
                        break;
                    /*
                    case ArrowLeft:
                        buffer.moveBackwards();     // for SimpleStringBuffer.java
                        break;
                    case ArrowRight:
                        buffer.moveForwards();   
                        break;
                    */
                    // /*
                    case ArrowLeft:
                        buffer.moveLeft();          // for GapBuffer.java
                        break;
                    case ArrowRight:
                        buffer.moveRight();
                        break;
                    // */
                    case Escape:
                        isRunning = false;
                        break;
                    default:
                        break;
                }
            }

            // Save file before exiting
            Files.writeString(filePath, buffer.toString());

            screen.stopScreen();
        } catch (IOException e) {
            System.err.println("Error handling terminal input: " + e.getMessage());
        }
    }

    /**
     * Draws the buffer to the screen.
     *
     * @param buffer The text buffer.
     * @param screen The screen object.
     * @throws IOException If an I/O error occurs.
     */
    private static void drawBuffer(GapBuffer buffer, Screen screen) throws IOException {   
        screen.clear();

        String text = buffer.toString();
        for (int i = 0; i < text.length(); i++) {
            screen.setCharacter(i, 0, TextCharacter.fromCharacter(text.charAt(i))[0]);
        }

        screen.setCursorPosition(new TerminalPosition(buffer.getCursorPosition(), 0));
        screen.refresh();
    }
}
