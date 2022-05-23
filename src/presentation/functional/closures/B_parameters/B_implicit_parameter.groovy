package presentation.functional.closures.B_parameters

//https://groovy-lang.org/closures.html

def greeting = { "Hello, $it!" }
assert greeting('Patrick') == 'Hello, Patrick!'
//is strictly equivalent to this one:
def greeting0 = { it -> "Hello, $it!" }
assert greeting0('Patrick') == 'Hello, Patrick!'

/**
 * If you want to declare a closure which accepts no argument and must be restricted to calls without arguments,
 * then you must declare it with an explicit empty argument list:
 */
def magicNumber = { -> 42 }
// this call will fail because the closure doesn't accept any argument
magicNumber()
//magicNumber(11)
def magicNumber0 = { 42 }
magicNumber0(11)