package groovyVSjava;

import static junit.framework.TestCase.assertEquals;

public class difJava {

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
