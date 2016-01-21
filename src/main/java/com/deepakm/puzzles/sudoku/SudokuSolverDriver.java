package com.deepakm.puzzles.sudoku;

import com.deepakm.puzzles.sudoku.board.ArrayBackedBoard;
import com.deepakm.puzzles.sudoku.board.Board;
import com.deepakm.puzzles.sudoku.solver.BacktrackingSudokuSolver;
import com.deepakm.puzzles.sudoku.solver.SudokuSolver;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by dmarathe on 1/21/16.
 */
public class SudokuSolverDriver {
    private static String inputFileCommandString = "inputFile";
    private static String outputFileCommandString = "outputFile";
    private static String delimiterCommandString = "delimiter";
    private static String dimentionCommandString = "dimention";
    private static String testCommandString = "test";
    private static String helpCommandString = "help";


    public static Options buildOptionParser(String[] args) {
        Option help = new Option(helpCommandString, "print help message");
        Option test = new Option(testCommandString, "test program");
        Option inputFile = OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("use given file for sudoku puzzle")
                .create(inputFileCommandString);
        Option outputFile = OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("output file the solved sudoku is redirected to.")
                .create(outputFileCommandString);
        Option delimiter = OptionBuilder.withArgName("delimiter")
                .hasArg()
                .withDescription("delimiter in the input file")
                .create(delimiterCommandString);
        Option dimention = OptionBuilder.withArgName("dimention")
                .hasArg()
                .withDescription("dimention of the sudoku grid")
                .create(dimentionCommandString);
        Options options = new Options();
        options.addOption(help);
        options.addOption(inputFile);
        options.addOption(outputFile);
        options.addOption(dimention);
        options.addOption(delimiter);
        options.addOption(test);
        return options;
    }

    public static int[][] parseFile(InputStream sourceInputStream, String delimiter, String dimention) throws IOException {
        Scanner scanner = new Scanner(sourceInputStream);
        int dim = Integer.valueOf(dimention);
        int[][] container = new int[dim][dim];
        int rows = 0;
        while (scanner.hasNextLine() && rows != dim) {
            String line = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
            if (tokenizer.countTokens() != Integer.valueOf(dimention)) {
                throw new IllegalArgumentException("dimention does not match the grid in input file.");
            }
            int columns = 0;
            container[rows] = new int[dim];
            while (tokenizer.hasMoreTokens()) {
                String digit = tokenizer.nextToken().trim();
                container[rows][columns] = Integer.valueOf(digit);
                columns = columns + 1;
            }
            rows = rows + 1;
        }
        return container;
    }

    public static void main(String[] args) throws FileNotFoundException {
        CommandLineParser parser = new DefaultParser();
        Options options = buildOptionParser(args);
        HelpFormatter formatter = new HelpFormatter();

        String inputFileString = null;
        String delimiterString = null;
        String dimentionString = null;
        String outputFileString = null;
        int grid[][] = null;

        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption(testCommandString)) {
                delimiterString = ",";
                dimentionString = "9";
                grid = parseFile(new FileInputStream(new File(SudokuSolverDriver.class.getResource("/input.txt").getPath())),
                        delimiterString, dimentionString);
            } else if (commandLine.hasOption(helpCommandString)) {
                formatter.printHelp("java -cp <sudoku-solver-${version}-jar-with-dependencies.jar>  com.deepakm.puzzles.sudoku.SudokuSolverDriver", options);
                System.exit(0);
            } else {
                if (commandLine.hasOption(inputFileCommandString)) {
                    inputFileString = commandLine.getOptionValue(inputFileCommandString);
                } else {
                    formatter.printHelp("java -cp <sudoku-solver-${version}-jar-with-dependencies.jar>  com.deepakm.puzzles.sudoku.SudokuSolverDriver", options);
                }
                if (commandLine.hasOption(outputFileCommandString)) {
                    outputFileString = commandLine.getOptionValue(outputFileCommandString);
                }
                if (commandLine.hasOption(delimiterCommandString)) {
                    delimiterString = commandLine.getOptionValue(delimiterCommandString);
                }
                if (!commandLine.hasOption(dimentionCommandString)) {
                    formatter.printHelp("java -cp <sudoku-solver-${version}-jar-with-dependencies.jar>  com.deepakm.puzzles.sudoku.SudokuSolverDriver", options);
                } else {
                    dimentionString = commandLine.getOptionValue(dimentionCommandString);
                }

                grid = parseFile(new FileInputStream(new File(inputFileString)), delimiterString, dimentionString);
            }
        } catch (ParseException e) {
            formatter.printHelp("java -cp <sudoku-solver-${version}-jar-with-dependencies.jar>  com.deepakm.puzzles.sudoku.SudokuSolverDriver", options);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintStream out = System.out;
        if (outputFileString != null) {
            out = new PrintStream(new File(outputFileString));
        }

        Board board = new ArrayBackedBoard(Integer.valueOf(dimentionString), Integer.valueOf(dimentionString), 0);
        board.initialise(grid);
        board.printBoard(System.out);

        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver(board);
        sudokuSolver.solve();
        System.out.println("Solved puzzle : ");
        board.printBoard(out);
    }
}
