package differencesWithJava

import static groovy.test.GroovyTestCase.assertEquals

/**
 * https://groovy-lang.org/differences.html
 */
class DifferencesGroovy {

    /**
     * 2. Multi-methods
     * In Groovy, the methods which will be invoked are chosen at runtime. This is called runtime dispatch or multi-methods.
     * It means that the method will be chosen based on the types of the arguments at runtime.
     * In Java, this is the opposite: methods are chosen at compile time, based on the declared types.
     * The following code, written as Java code, can be compiled in both Java and Groovy, but it will behave differently:
     *
     * In Java, you would have:
     * assertEquals(2, result);
     * Whereas in Groovy:
     * assertEquals(1, result);
     *
     * That is because Java will use the static information type, which is that o is declared as an Object,
     * whereas Groovy will choose at runtime, when the method is actually called. Since it is called with a String,
     * then the String version is called.
     */
    public static void main(String[] args){
        Object o = "Object";
        int result = method(o);
        assertEquals(1, result)
    }
    int method(String arg) {
        return 1;
    }
    int method(Object arg) {
        return 2;
    }
}


