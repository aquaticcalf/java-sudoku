## Sudoku

[![randown](https://aqclf.xyz/randown/randown.svg)](https://aqclf.xyz/randown?username=aquaticcalf&reponame=java-sudoku)

This project is a simple Sudoku game built using Java Swing for the GUI. It includes features for generating puzzles, validating solutions, providing hints, and resetting the game.

### Features
- Generate a new Sudoku puzzle each time
- Validate the Sudoku grid
- Provide hints for empty cells
- Reset the puzzle

### Requirements
Java Development Kit (JDK) 8 or higher

### How to Run
1. Clone the repository:
```
git clone https://github.com/aquaticcalf/java-sudoku
cd java-sudoku
```
2. Compile the Java file:
```
javac Sudoku.java
```
3. Run the application:
```
java Sudoku
```

### Usage
1. Start the application.
2. A new Sudoku puzzle will be generated automatically.
3. Use the grid to fill in numbers from 1 to 9.
4. Click the "Validate" button to check if the solution is correct.
5. Click the "Hint" button to get a hint for an empty cell.
6. Click the "Reset" button to reset the puzzle.

### Code Overview
- **GUI Setup:** The main method sets up the GUI using Java Swing components.
- **Puzzle Generation:** The `generatePuzzle` method generates a new Sudoku puzzle.
- **Validation:** The `validateGrid` method checks if the Sudoku grid is correctly filled.
- **Hint Provision:** The `provideHint` method provides hints for empty cells.
- **Reset Functionality:** The `resetPuzzle` method resets the puzzle to its initial state.

### License
This project is licensed under the MIT License. See the `license.md` file for more details.
