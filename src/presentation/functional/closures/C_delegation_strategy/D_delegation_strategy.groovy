package presentation.functional.closures.C_delegation_strategy

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


class Person3 {
    String name
    def pretty = { "My name is $name" }
    String toString() {
        pretty()
    }
}
class Thing3 {
    String name
}

def p3 = new Person3(name: 'Sarah')
def t3 = new Thing3(name: 'Teapot')

assert p3.toString() == 'My name is Sarah'
p3.pretty.delegate = t3
assert p3.toString() == 'My name is Sarah'

p3.pretty.resolveStrategy = Closure.DELEGATE_FIRST
assert p3.toString() == 'My name is Teapot'


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

