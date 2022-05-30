package presentation.functional.closures.C_delegation_strategy

//https://groovy-lang.org/closures.html

class Person2 {
    String name
    def pretty = { "My name is $name" }
    String toString() {
        pretty()
    }
}
class Thing2 {
    String name
}

def p2 = new Person2(name: 'Sarah')
def t2 = new Thing2(name: 'Teapot')

assert p2.toString() == 'My name is Sarah'
p2.pretty.delegate = t2
assert p2.toString() == 'My name is Sarah'


class Person {
    String name
    def pretty = { "My name is $name" }
    String toString() {
        return pretty()
    }
}
class Thing {
    String name
}

def p = new Person(name: 'Sarah')
def t = new Thing(name: 'Teapot')

assert p.toString() == 'My name is Sarah'
p.pretty.delegate = t
assert p.toString() == 'My name is Sarah'

p.pretty.resolveStrategy = Closure.DELEGATE_FIRST
assert p.toString() == 'My name is Teapot'


class Person4 {
    String name
    int age
    def fetchAge = { age }
}
class Thing4 {
    String name
}

def p4 = new Person4(name:'Jessica', age:42)
def t4 = new Thing4(name:'Printer')
def cl = p4.fetchAge
cl.delegate = p4
assert cl() == 42
cl.delegate = t4
assert cl() == 42
cl.resolveStrategy = Closure.DELEGATE_ONLY
cl.delegate = p4
assert cl() == 42
cl.delegate = t4
try {
    cl()
    assert false
} catch (MissingPropertyException ex) {
    // "age" is not defined on the delegate
}

