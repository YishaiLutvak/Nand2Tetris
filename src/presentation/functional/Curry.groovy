package presentation.functional

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
