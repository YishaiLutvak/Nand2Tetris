package presentation.vsJava;

import java.util.List;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;

/**
 * https://groovy-lang.org/differences.html
 *
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


public class DifferencesJava {

    public static void main(String[] args){
        Object o = "Object";
        int result = method(o);
        assertEquals(2, result);
    }
    static int method(String arg) {
        return 1;
    }
    static int method(Object arg) {
        return 2;
    }
}

class EraseMain {
    private List keepShorterThan(List strings, int length){
        List result = new ArrayList();
        for (int i = 0; i < strings.size(); i++){
            String s = (String) strings.get(i);
            if (s.length() < length){
                result.add(s);
            }
        }
        return result;
    }
    public static void main(String[] args){
        List names = new ArrayList();
        names.add("Ted");
        names.add("Fred");
        names.add("Jed");
        names.add("Ned");
        System.out.println(names);
        EraseMain e = new EraseMain();
        List shortNames = e.keepShorterThan(names, 4);
        System.out.println(shortNames.size());
        for (int i = 0; i < shortNames.size(); i++){
            String s = (String) shortNames.get(i);
            System.out.println(s);
        }
    }
}