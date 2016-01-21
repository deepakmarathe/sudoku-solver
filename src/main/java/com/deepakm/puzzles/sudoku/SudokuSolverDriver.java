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
    private static String printInputCommandString = "printInput";
    private static String printOutputCommandString = "printOutput";
    private static String helpCommandString = "help";


    public static Options buildOptionParser(String[] args) {
        Option help = new Option(helpCommandString, "print help message");
        Option printInput = new Option(printInputCommandString, "display the input sudoku.");
        Option printOutput = new Option(printOutputCommandString, "display the solved sudoku puzzle.");

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
        options.addOption(printInput);
        options.addOption(printOutput);
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

        PrintStream out = System.out;
        String inputFileString = null;
        String delimiterString = null;
        String dimentionString = null;
        String outputFileString = null;
        boolean displayInput = false;
        boolean displaySolution = false;
        int grid[][] = null;

        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption(helpCommandString)) {
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
                    if (outputFileString != null) {
                        out = new PrintStream(new File(outputFileString));
                    }
                }
                if (commandLine.hasOption(delimiterCommandString)) {
                    delimiterString = commandLine.getOptionValue(delimiterCommandString);
                }
                if (!commandLine.hasOption(dimentionCommandString)) {
                    formatter.printHelp("java -cp <sudoku-solver-${version}-jar-with-dependencies.jar>  com.deepakm.puzzles.sudoku.SudokuSolverDriver", options);
                } else {
                    dimentionString = commandLine.getOptionValue(dimentionCommandString);
                }
                if (commandLine.hasOption(printInputCommandString)) {
                    displayInput = true;
                }
                if (commandLine.hasOption(printOutputCommandString)) {
                    displaySolution = true;
                }
                grid = parseFile(new FileInputStream(new File(inputFileString)), delimiterString, dimentionString);
            }
        } catch (ParseException e) {
            formatter.printHelp("java -cp <sudoku-solver-${version}-jar-with-dependencies.jar>  com.deepakm.puzzles.sudoku.SudokuSolverDriver", options);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Board board = new ArrayBackedBoard(Integer.valueOf(dimentionString), Integer.valueOf(dimentionString), 0);
        board.initialise(grid);
        if (displayInput) {
            System.out.println("\nInput (Unsolved Sudoku) : ");
            board.printBoard(System.out);
        }

        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver(board);
        sudokuSolver.solve();
        if (displaySolution) {
            System.out.println("\nSolved sudoku : ");
            board.printBoard(out);
        }
    }
}
