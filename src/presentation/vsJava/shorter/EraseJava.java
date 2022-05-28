package presentation.vsJava.shorter;

import java.util.ArrayList;
import java.util.List;

public class EraseJava {
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
        EraseJava e = new EraseJava();
        List shortNames = e.keepShorterThan(names, 4);
        System.out.println(shortNames.size());
        for (int i = 0; i < shortNames.size(); i++){
            String s = (String) shortNames.get(i);
            System.out.println(s);
        }
    }
}
