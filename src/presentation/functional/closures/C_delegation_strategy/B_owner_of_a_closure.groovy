package presentation.functional.closures.C_delegation_strategy

//https://groovy-lang.org/closures.html

class Enclosing0 {
    void run() {
        println('Enclosing0.run()')
        def whatIsOwnerMethod = { getOwner() }
        assert whatIsOwnerMethod() == this
        def whatIsOwner = { owner }
        assert whatIsOwner() == this
    }
}

class EnclosedInInnerClass0 {
    class Inner0 {
        Closure cl = { owner }
    }
    void run() {
        println('EnclosedInInnerClass0.run()')
        def inner = new Inner0()
        assert inner.cl() == inner
    }
}

class NestedClosures0 {
    void run() {
        println('NestedClosures0.run()')
        def nestedClosures = {
            def cl = { owner }
            cl()
        }
        /**
         * https://groovy-lang.org/closures.html#_delegate_of_a_closure
         * ...but in case of nested closures,
         * like here cl being defined inside the scope of nestedClosures
         * then owner corresponds to the enclosing closure,
         * hence a different object from this!
         */
        assert nestedClosures() == nestedClosures
    }
}

def enclosing0 = new Enclosing0()
enclosing0.run()

def enclosedInInnerClass0 = new EnclosedInInnerClass0()
enclosedInInnerClass0.run()

def nestedClosures0 = new NestedClosures0()
nestedClosures0.run()