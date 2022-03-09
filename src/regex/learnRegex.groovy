package regex

import java.util.regex.Matcher


//********************************************
//https://www.tutorialspoint.com/groovy/groovy_regular_expressions.htm
//********************************************

//println('Groovy' =~ 'Groovy'  )
//println('Groovy'.matches('Groovy'))
//println()
//println('Groovy' =~ 'oo')
//println('Groovy'.matches('oo'))
//println()
//println('Groovy' ==~ 'Groovy' )
//println('Groovy'.matches('Groovy'))
//println()
//println('Groovy' ==~ 'oo'     )
//println('Groovy'.matches('oo'))
//println()
//println('Groovy' =~ '∧G'      )
//println('Groovy'.matches('∧G'))
//println()
//println('Groovy' =~ 'G$'      )
//println('Groovy'.matches('G$'))
//println()
//println('Groovy' =~ 'Gro*vy'  )
//println('Groovy'.matches('Gro*vy'))
//println()
//println('Groovy' =~ 'Gro{2}vy')
//println('Groovy'.matches('Gro{2}vy'))

//********************************************
//https://e.printstacktrace.blog/groovy-regular-expressions-the-definitive-guide/
//********************************************

//import java.util.regex.Pattern
//
//def pattern1 = ~"[Gg]roovy"
//
//assert pattern1.class == Pattern
//
//def pattern2 = Pattern.compile("([Gg])roovy")
//
//assert pattern2.class == Pattern

//********************************************

//def number = 2
//
//def str1 = /The number is 2/
//def str2 = /The number is $number/

//assert str1 instanceof String
//assert str2 instanceof GString

//********************************************

//assert (/Version \d+\.\d+\.\d+/) == 'Version \\d+\\.\\d+\\.\\d+'
//
//assert 'The price is $99' ==~ /The price is \$\d+/

//********************************************

//def pattern = ~/\S+er\b/
//def matcher = "My code is groovier and better when I use Groovy there" =~ pattern
//
//println(pattern)
//println(matcher)
//
//assert pattern instanceof java.util.regex.Pattern
//assert matcher instanceof java.util.regex.Matcher
//
//assert matcher.find()
//assert matcher.size() == 2
//assert matcher[0..-1] == ["groovier", "better"]

def matcher = "My code is groovier and better when I use Groovy there" =~ /\S+er\b/

assert matcher instanceof Matcher

assert matcher.find()
assert matcher.size() == 2
assert matcher[0..-1] == ["groovier", "better"]

//********************************************

//def myMatcher = "My code is groovier and better when I use Groovy there" =~ /\S+er\b/
//
//if (myMatcher) {
//    println "At least one element matches the pattern..."
//}
//
//if ("Lorem ipsum dolor sit amet" =~ /\d+/) {
//    println "This line is not executed..."
//}

//********************************************

//assert "v3.12.4" ==~ /v\d{1,3}\.\d{1,3}\.\d{1,3}/
//
//assert !("GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}/)
//
//assert "GROOVY-123: some change" ==~ /[A-Z]{3,6}-\d{1,4}.{1,100}/

//********************************************
// https://stackoverflow.com/questions/10954870/groovy-remove-multiline-comment
//********************************************

//String a = '''
///**
// * Implements the Square Dance game.
// * This simple game allows the user to move a black square around
// * the screen, and change the square's size during the movement.
// * When the game starts, a square of 30 by 30 pixels is shown at the
// * top-left corner of the screen. The user controls the square as follows.
// * The 4 arrow keys are used to move the square up, down, left, and right.
// * The 'z' and 'x' keys are used, respectively, to decrement and increment
// * the square's size. The 'q' key is used to quit the game.
// */
//
//class SquareGame {
//   field Square square; // the square of this game
//   field int direction; // the square's current direction:
//                        // 0=none, 1=up, 2=down, 3=left, 4=right
//
//   /** Constructs a new Square Game. */
//   constructor SquareGame new() {
//      // Creates a 30 by 30 pixels square and positions it at the top-left
//      // of the screen.
//      let square = Square.new(0, 0, 30);
//      let direction = 0;  // initial state is no movement
//      return this;
//   }
//
//   /** Disposes */this game. */
//   method void dispose() {
//      do square.dispose();
//      do Memory.deAlloc(this);
//      return;
//   }
//
//   /** Moves the square in/* the current direction. */
//   method void moveSquare() {
//      if (direction = 1) { do square.moveUp(); }
//      if (direction = 2) { do square.moveDown(); }
//      if (direction = 3) { do square.moveLeft(); }
//      if (direction = 4) { do square.moveRight(); }
//      do Sys.wait(5);  // delays the next movement
//      return;
//   }
//
//   /** Runs the game: handles the user's inputs and moves the square accordingly */
//   method void run() {
//      var char key;  // the key currently pressed by the user
//      var boolean exit;
//      let exit = false;
//
//      while (~exit) {
//         // waits for a key to be pressed
//         while (key = 0) {
//            let key = Keyboard.keyPressed();
//            do moveSquare();
//         }
//         if (key = 81)  { let exit = true; }     // q key
//         if (key = 90)  { do square.decSize(); } // z key
//         if (key = 88)  { do square.incSize(); } // x key
//         if (key = 131) { let direction = 1; }   // up arrow
//         if (key = 133) { let direction = 2; }   // down arrow
//         if (key = 130) { let direction = 3; }   // left arrow
//         if (key = 132) { let direction = 4; }   // right arrow
//
//         // waits for the key to be released
//         while (~(key = 0)) {
//            let key = Keyboard.keyPressed();
//            do moveSquare();
//         }
//     } // while
//     return;
//   }
//}
//'''
//
//println(a.replaceAll('(?s)/\\*.*?\\*/',''))

//********************************************
