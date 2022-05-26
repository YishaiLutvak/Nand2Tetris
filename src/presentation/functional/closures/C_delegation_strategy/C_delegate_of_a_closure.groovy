package presentation.functional.closures.C_delegation_strategy

//https://groovy-lang.org/closures.html

class Enclosing1 {
    void run() {
        println('Enclosing1.run()')
        def cl = { getDelegate() }
        def cl2 = { delegate }
        assert cl() == cl2()
        assert cl() == this
        println('line 11 this:')
        println(this)
        def enclosed = {
            { ->
                println('line 15 delegate:')
                println(delegate)
                delegate
            }.call()
        }
        println('line 20 enclosed:')
        println(enclosed)
        println('line 22 enclosed():')
        println(enclosed())
        println('line 24 enclosed().toString():')
        println(enclosed().toString())
        println('line 26 assert enclosed() == enclosed:')
        assert enclosed() == enclosed
    }
}

def enclosing1 = new Enclosing1()
enclosing1.run()

class Person1 {
    String name
}

class Thing1 {
    String name
}

def p = new Person1(name: 'Norman')
def t = new Thing1(name: 'Teapot')

def upperCasedName = { delegate.name.toUpperCase() }
//upperCasedName()

upperCasedName.delegate = p
assert upperCasedName() == 'NORMAN'
upperCasedName.delegate = t
assert upperCasedName() == 'TEAPOT'

def target = p
def upperCasedNameUsingVar = { target.name.toUpperCase() }
assert upperCasedNameUsingVar() == 'NORMAN'