package presentation.regex

import java.util.regex.Pattern


//********************************************
// https://stackoverflow.com/questions/10954870/groovy-remove-multiline-comment
//********************************************

String program = '''
/**
 * Implements the Square Dance game.
 * This simple game allows the user to move a black square around
 * the screen, and change the square's size during the movement.
 * When the game starts, a square of 30 by 30 pixels is shown at the
 * top-left corner of the screen. The user controls the square as follows.
 * The 4 arrow keys are used to move the square up, down, left, and right.
 * The 'z' and 'x' keys are used, respectively, to decrement and increment
 * the square's size. The 'q' key is used to quit the game.
 */

class SquareGame {
   field Square square; // the square of this game
   field int direction; // the square's current direction:
                        // 0=none, 1=up, 2=down, 3=left, 4=right

   /** Constructs a new Square Game. */
   constructor SquareGame new() {
      // Creates a 30 by 30 pixels square and positions it at the top-left
      // of the screen.
      let square = Square.new(0, 0, 30);
      let direction = 0;  // initial state is no movement
      return this;
   }

   /** Disposes */this game. */
   method void dispose() {
      do square.dispose();
      do Memory.deAlloc(this);
      return;
   }

   /** Moves the square in the current direction. */
   method void moveSquare() {
      if (direction = 1) { do square.moveUp(); }
      if (direction = 2) { do square.moveDown(); }
      if (direction = 3) { do square.moveLeft(); }
      if (direction = 4) { do square.moveRight(); }
      do Sys.wait(5);  // delays the next movement
      return;
   }

   /** Runs the game: handles the user's inputs and moves the square accordingly */
   method void run() {
      var char key;  // the key currently pressed by the user
      var boolean exit;
      let exit = false;

      while (~exit) {
         // waits for a key to be pressed
         while (key = 0) {
            let key = Keyboard.keyPressed();
            do moveSquare();
         }
         if (key = 81)  { let exit = true; }     // q key
         if (key = 90)  { do square.decSize(); } // z key
         if (key = 88)  { do square.incSize(); } // x key
         if (key = 131) { let direction = 1; }   // up arrow
         if (key = 133) { let direction = 2; }   // down arrow
         if (key = 130) { let direction = 3; }   // left arrow
         if (key = 132) { let direction = 4; }   // right arrow

         // waits for the key to be released
         while (~(key = 0)) {
            let key = Keyboard.keyPressed();
            do moveSquare();
         }
     } // while
     return;
   }
}
'''
println("\nprogram before tarstormations:\n\n$program")
program = program.replaceAll(~'//.*','').trim()
println("\nprogram after program.replaceAll(~'//.*','').trim():\n\n$program")
program = program.replaceAll($/(?s)/\*.*?\*//$,'').trim()
println("\nprogram after program.replaceAll(\$/(?s)/\\*.*?\\*//\$,'').trim():\n\n$program")

// /\*.*\*/
// /\*.*?\*/
// (?s)/\*.*?\*/
//********************************************

println("groovy.java.jack".split(/\.jack/))
println("groovy.java.jack".split(/\.jack/)[0])
println()

//********************************************

println($/('y' ==~ /[xyz]/) is ${'y' ==~ /[xyz]/}/$)
println($/('xyz' ==~ /[xyz]/) is ${'xyz' ==~ /[xyz]/}/$)
println($/('xyz' ==~ /[xyz]?/) is ${'xyz' ==~ /[xyz]?/}/$)
println("Pattern.matches('[xyz]?','xyz') is ${Pattern.matches('[xyz]?','xyz')}")
println()
