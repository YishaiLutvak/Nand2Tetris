package presentation.functional


/**
 * https://en.wikipedia.org/wiki/Apache_Groovy
 * Functional programming
 * Although Groovy is mostly an object-oriented language, it also offers Functional programming features.
 */

/**
 * Closures
 * According to Groovy's documentation: "Closures in Groovy work similar to a 'method pointer', enabling code to be written and run in a later point in time".[24] Groovy's closures support free variables, i.e. variables that have not been explicitly passed as a parameter to it, but exist in its declaration context, partial application (that it terms 'currying'[25]), delegation, implicit, typed and untyped parameters.
 * When working on Collections of a determined type, the closure passed to an operation on the collection can be inferred:
 */

list = [1, 2, 3, 4, 5, 6, 7, 8, 9]

/**
 * Non-zero numbers are coerced to true, so when it % 2 == 0 (even), it is false.
 * The type of the implicit "it" parameter can be inferred as an Integer by the IDE.
 * It could also be written as:
 * list.findAll { Integer i -> i % 2 }
 * list.findAll { i -> i % 2 }
 */

def odds = list.findAll { it % 2 }

assert odds == [1, 3, 5, 7, 9]

/**
 * A group of expressions can be written in a closure block without reference to an
 * implementation and the responding object can be assigned at a later point using delegation:
 */

// This block of code contains expressions without reference to an implementation
def operations = {
    declare 5
    sum 4
    divide 3
    print
}

/**
 * This class will handle the operations that can be used in the closure above. Another class
 * could be declared having the same methods, but using, for example, webservice operations
 * in the calculations.
 */

class Expression {
    BigDecimal value

    /**
     * Though an Integer is passed as a parameter, it is coerced into a BigDecimal, as was
     * defined. If the class had a 'declare(Integer value)' method, it would be used instead.
     */
    def declare(BigDecimal value) {
        this.value = value
    }

    def sum(BigDecimal valueToAdd) {
        this.value += valueToAdd
    }

    def divide(BigDecimal divisor) {
        this.value /= divisor
    }

    def propertyMissing(String property) {
        if (property == "print") println value
    }
}

// Here is defined who is going to respond the expressions in the block of code above.
operations.delegate = new Expression()
operations()



/**
 * Curry
 * Usually called partial application,[25] this Groovy feature allows closures' parameters to be set to a default parameter in any of their arguments, creating a new closure with the bound value. Supplying one argument to the curry() method will fix argument one. Supplying N arguments will fix arguments 1 .. N.
 */
def joinTwoWordsWithSymbol = { symbol, first, second -> first + symbol + second }
assert joinTwoWordsWithSymbol('#', 'Hello', 'World') == 'Hello#World'

def concatWords = joinTwoWordsWithSymbol.curry(' ')
assert concatWords('Hello', 'World') == 'Hello World'

def prependHello = concatWords.curry('Hello')
//def prependHello = joinTwoWordsWithSymbol.curry(' ', 'Hello')
assert prependHello('World') == 'Hello World'

/**
 * Curry can also be used in the reverse direction (fixing the last N arguments) using rcurry().
 */

def power = { BigDecimal value, BigDecimal power ->
    value**power
}

def square = power.rcurry(2)
def cube = power.rcurry(3)

assert power(2, 2) == 4
assert square(4) == 16
assert cube(3) == 27

/**
 * Groovy also supports lazy evaluation,[26][27] reduce/fold,[28] infinite structures and immutability,[29] among others.[30]
 */