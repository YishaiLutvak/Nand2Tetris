package presentation.functional.closures.B_parameters

//https://groovy-lang.org/closures.html
/**
 * It is possible for a closure to declare variable arguments like any other method.
 * Vargs methods are methods that can accept a variable number of arguments
 * if the last parameter is of variable length (or an array) like in the next examples:
 */
def concat1 = { String... args -> args.join('') }
assert concat1('abc','def') == 'abcdef'
def concat2 = { String[] args -> args.join('') }
assert concat2('abc', 'def') == 'abcdef'

def multiConcat = { int n, String... args ->
    args.join('')*n
}
assert multiConcat(2, 'abc','def') == 'abcdefabcdef'