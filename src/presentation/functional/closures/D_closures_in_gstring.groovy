package presentation.functional.closures

//https://groovy-lang.org/closures.html

def x = 1
def gs = "x = ${x}"
assert gs == 'x = 1'
x = 2
//assert gs == 'x = 2' //Caught: Assertion failed:...

def x0 = 1
def gs0 = "x0 = ${-> x0}"
assert gs0 == 'x0 = 1'
x0 = 2
assert gs0 == 'x0 = 2'

class Person {
    String name
    String toString() { name }
}
def sam = new Person(name:'Sam')
def lucy = new Person(name:'Lucy')
def p = sam
def gs1 = "Name: ${p}"
assert gs1 == 'Name: Sam'
p = lucy
assert gs1 == 'Name: Sam'
sam.name = 'Lucy'
assert gs1 == 'Name: Lucy'

class Person0 {
    String name
    String toString() { name }
}
def sam2 = new Person(name:'Sam')
def lucy2 = new Person(name:'Lucy')
def p2 = sam
// Create a GString with lazy evaluation of "p2"
def gs2 = "Name: ${-> p2}"
assert gs2 == 'Name: Sam'
p2 = lucy2
assert gs2 == 'Name: Lucy'