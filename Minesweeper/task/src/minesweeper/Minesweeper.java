package minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;

public class Minesweeper {
    private final int line = 9;
    private final int col = 9;

    private char[][] field;

    protected Deque<String> stack;

    private final char[][] mineField;

    private final char[][] parsedField;

    {
        mineField = new char[line][col];
        parsedField = new char[line][col];
        for (int i = 0; i < line; i++)
            for (int j = 0; j < col; j++) {
                mineField[i][j] = '.';
                parsedField[i][j] ='.';
            }
    }

    public Minesweeper() {
        createField();
        stack = new ArrayDeque<>();
    }

    // create empty field
    public void createField() {
        field = new char[line][col];
        for (int i = 0; i < line; i++)
            for (int j = 0; j < col; j++)
                field[i][j] = '.';
    }

    // show field
    public void showField() {
        int counter = 0;
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i =0; i < line; i++) {
            counter++;
            for (int j = 0; j < col; j++)
                if (j == 0) {
                    System.out.print(counter + "|" + field[i][j]);
                } else if (j == line - 1) {
                    System.out.print(field[i][j] + "|");
                } else {
                    System.out.print(field[i][j]);
                }
            System.out.println();
        }
        System.out.println("-|---------|");
    }


    public void setMine(int line, int col) {
        mineField[line][col] = 'X';
    }

    public void setParsedCell(int line, int col) {
        parsedField[line][col] = 'p';
    }

    // check if the cell specified contains a number
    public boolean isNumber(int line, int col) {
        return String.valueOf(mineField[line][col]).matches("[1-9]");
    }

    // check if the cell specified is marked
    // if yes, it will be unmatched
    public boolean isMark(int line, int col) {
        return String.valueOf(field[line][col]).equals("*");
    }

    // empty cell
    public void setEmptyCell(int line, int col) {
        field[line][col] = '/';
    }

    // mark cell
    public void markCell(int line, int col) {
        if (isMark(line, col)) {
            field[line][col] = '.';
        } else {
            field[line][col] = '*';
        }
    }

    // check if it is mine on position
    public boolean isMine(int line, int col) {
        return mineField[line][col] == 'X';
    }

    // free cell
    public void cellFree(int line, int col) {
        field[line][col] = mineField[line][col];
    }

    // set failed field
    public void setFailedField() {
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < col; j++) {
                if (mineField[i][j] == 'X' || mineField[i][j] != '.') {
                    field[i][j] = mineField[i][j];
                }
            }
        }
    }

    // check if user has Won
    public boolean isWin() {
        int counter = 0;
        int marked = 0;
        int counterSafeFound = 0;
        int counterSafe = 0;
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < col; j++) {
                if (mineField[i][j] == 'X' && field[i][j] == '*') {
                    counter++;
                }
                if (field[i][j] == '*') {
                    marked++;
                }
                if (field[i][j] != 'X') {
                    counterSafe++;
                }
                if (mineField[i][j] == 'X' && field[i][j] != '.') {
                    counterSafeFound++;
                }
            }
        }
        if (marked > 0) {
            return marked == counter;
        } else return counterSafe == counterSafeFound;
        }

        // set numer of mines on cells that have mines near
    public void setNumberOfMines(int line, int col, int nr) {
        mineField[line][col] = String.valueOf(nr).charAt(0);
    }

    // gets methods
    public int getLineNr() {
        return line;
    }

    public int getColNr() {
        return col;
    }

    public Deque<String> getStack() {
        return stack;
    }

    public char[][] getParsedField() {
        return parsedField;
    }
}
