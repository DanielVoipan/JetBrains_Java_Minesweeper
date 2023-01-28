package minesweeper;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Minesweeper main = new Minesweeper();
        Random randomNr = new Random();
        System.out.print("How many mines do you want on the field? ");
        int nr = Integer.parseInt(scanner.nextLine());

        // generate field with number of mines
        randMines(main, nr, 0, randomNr);
        // add number for cells that are near mines
        addNumberOfMines(main);
        // show field
        main.showField();
        // start game
        Minesweeper(main);
        }

        // game start
    static void Minesweeper(Minesweeper main) {
        Scanner scanner = new Scanner(System.in);
        boolean start = true;
        while (start) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            String positions = scanner.nextLine();
            String[] posSplit = positions.split(" ");
            int posLine = Integer.parseInt(posSplit[1]) - 1;
            int posCol = Integer.parseInt(posSplit[0]) - 1;
            String type = posSplit[2];
            if ("mine".equals(type)) {
                main.markCell(posLine, posCol);
                main.showField();
            } else if ("free".equals(type)) {
                int status = freeCell(main, posLine, posCol);
                if (status == 0) {
                    System.out.println("You stepped on a mine and failed!");
                    main.setFailedField();
                    start = false;
                }
                main.showField();
            }
            boolean win = main.isWin();
            if (win) {
                main.showField();
                System.out.println("Congratulations! You found all mines!");
                start = false;
            }
        }
    }

    // start freeing cells that doesnt have mines near
    static int freeCell(Minesweeper main, int posLine, int posCol) {

        if (main.isMine(posLine, posCol)) {
            return 0;
        }
        if (main.isNumber(posLine, posCol)) {
            main.cellFree(posLine, posCol);
            return 1;
        }
        Deque<String> stack = main.getStack();
        stack.add(posLine+""+posCol);

        while(stack.size() > 0) {
            String positions = stack.pollLast();
            int getPosLine = Integer.parseInt(String.valueOf(positions.charAt(0)));
            int getPosCol = Integer.parseInt(String.valueOf(positions.charAt(1)));
            if (isParsed(main, getPosLine, getPosCol))
                parseCells(main, stack, getPosLine, getPosCol);
        }
        return -1;
    }

    // set cell as parsed
    static void freeCellOthers(Minesweeper main, int posLine, int posCol, Deque<String> stack) {
            if (main.isNumber(posLine, posCol)) {
                main.cellFree(posLine, posCol);
            } else if (!main.isMine(posLine, posCol)) {
                main.setEmptyCell(posLine, posCol);
                stack.add(posLine + "" + posCol);
        }
            main.setParsedCell(posLine, posCol);
    }

    // check for other free cells.
    static void parseCells(Minesweeper main, Deque<String> stack, int x, int y) {
        for (int i = 0; i < main.getLineNr(); i++) {
            for (int j = 0; j < main.getColNr(); j++) {
                // check left corner up
                if (i == 0 && j == 0 && i == x && j == y) {
                    freeCellOthers(main, i, j + 1, stack);
                    freeCellOthers(main, i + 1, j, stack);
                    freeCellOthers(main, i + 1, j + 1, stack);
                }
                // check right corner up
                if (i == 0 && j == 8 && i == x && j == y) {
                    freeCellOthers(main, i, j - 1, stack);
                    freeCellOthers(main, i + 1, j - 1, stack);
                    freeCellOthers(main, i + 1, j, stack);
                }
                // check left corner down
                if (i == 8 && j == 0 && i == x && j == y) {
                    freeCellOthers(main, i, j + 1, stack);
                    freeCellOthers(main, i - 1, j + 1, stack);
                    freeCellOthers(main, i - 1, j, stack);
                }
                // check right corner down
                if (i == 8 && j == 8 && i == x && j == y) {
                    freeCellOthers(main, i, j - 1, stack);
                    freeCellOthers(main, i - 1, j - 1, stack);
                    freeCellOthers(main, i - 1, j, stack);
                }
                // check upside
                if (i == 0 && j != 0 && j != 8 && i == x && j == y) {
                    freeCellOthers(main, i, j + 1, stack);
                    freeCellOthers(main, i + 1, j + 1, stack);
                    freeCellOthers(main, i + 1, j, stack);
                    freeCellOthers(main, i + 1, j - 1, stack);
                    freeCellOthers(main, i, j - 1, stack);
                }
                // check downside
                if (i == 8 && j != 0 && j != 8 && i == x && j == y) {
                    freeCellOthers(main, i, j + 1, stack);
                    freeCellOthers(main, i - 1, j + 1, stack);
                    freeCellOthers(main, i - 1, j, stack);
                    freeCellOthers(main, i - 1, j - 1, stack);
                    freeCellOthers(main, i, j - 1, stack);
                }
                // check left side
                if (j == 0 && i != 0 && i != 8 && i == x && j == y) {
                    freeCellOthers(main, i + 1, j, stack);
                    freeCellOthers(main, i + 1, j + 1, stack);
                    freeCellOthers(main, i, j + 1, stack);
                    freeCellOthers(main, i - 1, j + 1, stack);
                    freeCellOthers(main, i - 1, j, stack);
                }
                // check right side
                if (j == 8 && i != 0 && i != 8 && i == x && j == y) {
                    freeCellOthers(main, i + 1, j, stack);
                    freeCellOthers(main, i + 1, j - 1, stack);
                    freeCellOthers(main, i, j - 1, stack);
                    freeCellOthers(main, i - 1, j - 1, stack);
                    freeCellOthers(main, i - 1, j, stack);
                }
                // check middle
                if (j != 0 && j != 8 && i != 0 && i != 8) {
                    freeCellOthers(main, i, j + 1, stack);
                    freeCellOthers(main, i - 1, j + 1, stack);
                    freeCellOthers(main, i - 1, j, stack);
                    freeCellOthers(main, i - 1, j - 1, stack);
                    freeCellOthers(main, i, j - 1, stack);
                    freeCellOthers(main, i + 1, j - 1, stack);
                    freeCellOthers(main, i + 1, j, stack);
                    freeCellOthers(main, i + 1, j + 1, stack);
                }
            }
        }
    }

    // check if we already visited this cell
    static boolean isParsed(Minesweeper main, int x, int y) {
        char[][] parsedField = main.getParsedField();
        return x >= 0 && x < main.getLineNr() && y >= 0 && y < main.getColNr() && parsedField[x][y] != 'p';
    }

    // add number of mines for each cell if they are near
    // mines
    static void addNumberOfMines(Minesweeper main) {
        List<String> arraysList = new ArrayList<>();
        for (int i = 0; i < main.getLineNr(); i++) {
            for (int j = 0; j < main.getColNr(); j++) {
                // check left corner up
                if (i == 0 && j == 0 && !main.isMine(i, j)) {
                    arraysList.add(i + "" + (j + 1));
                    arraysList.add((i + 1) + "" + j);
                    arraysList.add((i + 1) + "" + (j + 1));
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check right corner up
                if (i == 0 && j == 8 && !main.isMine(i, j)) {
                    arraysList.add(i + "" + (j - 1));
                    arraysList.add((i + 1) + "" + (j - 1));
                    arraysList.add((i + 1) + "" + j);
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check left corner down
                if (i == 8 && j == 0 && !main.isMine(i, j)) {
                    arraysList.add(i + "" + (j + 1));
                    arraysList.add((i - 1) + "" + (j + 1));
                    arraysList.add((i - 1) + "" + j);
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check right corner down
                if (i == 8 && j == 8 && !main.isMine(i, j)) {
                    arraysList.add(i + "" + (j - 1));
                    arraysList.add((i - 1) + "" + (j - 1));
                    arraysList.add((i - 1) + "" + j);
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check upside
                if (i == 0 && j != 0 && j != 8 && !main.isMine(i, j)) {
                    arraysList.add(i + "" + (j + 1));
                    arraysList.add((i + 1) + "" + (j + 1));
                    arraysList.add((i + 1) + "" + j);
                    arraysList.add((i + 1) + "" + (j - 1));
                    arraysList.add(i + "" + (j - 1));
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check downside
                if (i == 8 && j != 0 && j != 8 && !main.isMine(i, j)) {
                    arraysList.add(i + "" + (j + 1));
                    arraysList.add((i - 1) + "" + (j + 1));
                    arraysList.add((i - 1) + "" + j);
                    arraysList.add((i - 1) + "" + (j - 1));
                    arraysList.add(i + "" + (j - 1));
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check left side
                if (j == 0 && i != 0 && i != 8 && !main.isMine(i, j)) {
                    arraysList.add((i + 1) + "" + j);
                    arraysList.add((i + 1) + "" + (j + 1));
                    arraysList.add(i + "" + (j + 1));
                    arraysList.add((i - 1) + "" + (j + 1));
                    arraysList.add((i - 1) + "" + j);
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check right side
                if (j == 8 && i != 0 && i != 8 && !main.isMine(i, j)) {
                    arraysList.add((i + 1) + "" + j);
                    arraysList.add((i + 1) + "" + (j - 1));
                    arraysList.add(i + "" + (j - 1));
                    arraysList.add((i - 1) + "" + (j - 1));
                    arraysList.add((i - 1) + "" + j);
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
                // check middle
                if (j != 0 && j != 8 && i != 0 && i != 8 && !main.isMine(i, j)) {
                    arraysList.add(i + "" + (j + 1));
                    arraysList.add((i - 1) + "" + (j + 1));
                    arraysList.add((i - 1) + "" + j);
                    arraysList.add((i - 1) + "" + (j - 1));
                    arraysList.add(i + "" + (j - 1));
                    arraysList.add((i + 1) + "" + (j - 1));
                    arraysList.add((i + 1) + "" + j);
                    arraysList.add((i + 1) + "" + (j + 1));
                    countMines(main, arraysList, i, j);
                    arraysList.clear();
                }
            }
        }
    }

    // count mines that are near to the cell
    // replace cell with the number of mines found.
    static void countMines(Minesweeper main, List<String> arraysList, int l, int c) {
        int counter = 0;
        for (String s : arraysList) {
            int posL = Integer.parseInt(String.valueOf(s.charAt(0)));
            int posC = Integer.parseInt(String.valueOf(s.charAt(1)));
            if (main.isMine(posL, posC)) {
                counter++;
            }
        }
        if (counter > 0) {
            main.setNumberOfMines(l, c, counter);
        }
    }

    // put random mines on fields based on number specified
    static void randMines(Minesweeper main, int nr, int counter, Random randomNr) {
            int i = randomNr.nextInt(main.getColNr());
            // get random colon number from 0 to half
            int colNr = randomNr.nextInt(main.getColNr() / 2 - randomNr.nextInt(2));
            int added = 0;
            int pos;
            int noMine = 0;
            // while added is less then the random and and there are more
            // mines to put, execute the while
            while (added < colNr && counter < nr) {
                // get the random position of the mine in the line's columns
                pos = randomNr.nextInt(main.getColNr());
                // if isn't a mine there yet..
                if (!main.isMine(i, pos)) {
                    // set mine
                    main.setMine(i, pos);
                    // increment the number of mines in the line
                    added++;
                    // increment the total mines to add on the field
                    counter++;
                } else {
                    // increment if there no space on line for mines
                    noMine++;
                }
                // if there are no mines left to put on line, jump to next line
                if (main.getColNr() == noMine) {
                    break;
                }
            }
        // if there are more mines to add, recursive use of the method
        // until there no more left.
        if (counter < nr) {
            randMines(main, nr, counter, randomNr);
        }
    }
}
