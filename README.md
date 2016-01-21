
#Git cloning 
    git clone https://github.com/deepakmarathe/sudoku-solver.git 
    cd sudoku-solver

#Build
    mvn clean install 

#Preparing input
######Input is supplied in a matrix representation of the sudoku contents, in a file. 
######Here is an example representation of sudoku puzzle as input:
    3, 0, 6, 5, 0, 8, 4, 0, 0
    5, 2, 0, 0, 0, 0, 0, 0, 0
    0, 8, 7, 0, 0, 0, 0, 3, 1
    0, 0, 3, 0, 1, 0, 0, 8, 0
    9, 0, 0, 8, 6, 3, 0, 0, 5
    0, 5, 0, 0, 9, 0, 6, 0, 0
    1, 3, 0, 0, 0, 0, 2, 5, 0
    0, 0, 0, 0, 0, 0, 0, 7, 4
    0, 0, 5, 2, 0, 6, 3, 0, 0

#Execution
####program commands
    usage: java -cp <sudoku-solver-${version}-jar-with-dependencies.jar>
                com.deepakm.puzzles.sudoku.SudokuSolverDriver
     -delimiter <delimiter>   delimiter in the input file
     -dimention <dimention>   dimention of the sudoku grid
     -help                    print help message
     -inputFile <file>        use given file for sudoku puzzle
     -outputFile <file>       output file the solved sudoku is redirected to.
     -printInput              display the input sudoku.
     -printOutput             display the solved sudoku puzzle.


####Run the program on external inputfile and output on stdout.
    java -cp ./target/sudoku-solver-0.0.1-SNAPSHOT-jar-with-dependencies.jar  com.deepakm.puzzles.sudoku.SudokuSolverDriver -inputFile <inputfile> -delimiter "," -dimention 9 -printOutput
    
####Output solved puzzle to outputfile as well as stdout.
    java -cp ./target/sudoku-solver-0.0.1-SNAPSHOT-jar-with-dependencies.jar  com.deepakm.puzzles.sudoku.SudokuSolverDriver -inputFile <inputfile> -outputFile <outputfile> -delimiter "," -dimention 9 -printOutput
     
#Output
######The solved sudoku puzzle will be found in the output file. Here is the solved sudoku for the sample input:
    Solved sudoku :
    
    - - - - - - - - - - - - -
    | 3 1 6 | 5 7 8 | 4 9 2 |
    | 5 2 9 | 1 3 4 | 7 6 8 |
    | 4 8 7 | 6 2 9 | 5 3 1 |
    - - - - - - - - - - - - -
    | 2 6 3 | 4 1 5 | 9 8 7 |
    | 9 7 4 | 8 6 3 | 1 2 5 |
    | 8 5 1 | 7 9 2 | 6 4 3 |
    - - - - - - - - - - - - -
    | 1 3 8 | 9 4 7 | 2 5 6 |
    | 6 9 2 | 3 5 1 | 8 7 4 |
    | 7 4 5 | 2 8 6 | 3 1 9 |
    - - - - - - - - - - - - -