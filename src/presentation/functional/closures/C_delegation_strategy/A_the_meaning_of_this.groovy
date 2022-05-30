package presentation.functional.closures.C_delegation_strategy

//https://groovy-lang.org/closures.html

class Enclosing {
    void run() {
        println('Enclosing.run()')
        def whatIsOwnerMethod = { getOwner() }
        assert whatIsOwnerMethod() == this
        def whatIsOwner = { owner }
        assert whatIsOwner() == this
    }
}

class EnclosedInInnerClass {
    class Inner {
        Closure cl = { owner }
    }
    void run() {
        println('EnclosedInInnerClass.run()')
        def inner = new Inner()
        assert inner.cl() == inner
    }
}

class NestedClosures {
    void run() {
        println('NestedClosures.run()')
        def nestedClosures = {
            def cl = { owner }
            cl()
        }
        assert nestedClosures() == nestedClosures
    }
}

def enclosing = new Enclosing()
enclosing.run()

def enclosedInInnerClass = new EnclosedInInnerClass()
enclosedInInnerClass.run()

def nestedClosures = new NestedClosures()
nestedClosures.run()

class MyPerson {
    String name
    int age
    String toString() { "$name is $age years old" }

    String dump() {
        def cl = {
            String msg = this.toString()
            println msg
            msg
        }
        cl()
    }
}

def p = new MyPerson(name:'Janice', age:74)
assert p.dump() == 'Janice is 74 years old'