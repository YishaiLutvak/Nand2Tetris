package project11
import static project11.Symbol.KIND


/**
 *  This module provides services for creating and using a symbol table.
 *  Recall that each symbol has a scope from which it is visible in the source code.
 *  The symbol table implements this abstraction by giving each symbol a running number (index) within the scope.
 *  The index starts at 0, increments by 1 each time an identifier is added to the table, and resets to 0 when starting a new scope.
 *  The following kinds of identifiers may appear in the symbol table:
 *  Static: Scope: class.
 *  Field: Scope: class.
 *  Argument: Scope: subroutine (method/function/constructor).
 *  Var: Scope: subroutine (method/function/constructor).
 *
 *  When compiling error-free Jack code, any identifier not found in the symbol table may be assumed to be a subroutine name or a class name.
 *  Since the Jack language syntax rules suffice for distinguishing between these two possibilities, and since no “linking” needs to be done by the compiler,
 *  there is no need to keep these identifiers in the symbol table.
 *
 *  Provides a symbol table abstraction.
 *  The symbol table associates the identifier names found in the program with identifier properties needed for compilation:
 *  type, kind, and running index. The symbol table for Jack programs has two nested scopes (class/subroutine).
 */
class SymbolTable {

    private static Map<String, Symbol> classSymbols = [:] // for STATIC, FIELD
    private static Map<String, Symbol> subroutineSymbols = [:] // for ARG, VAR
    private static Map<KIND, Integer> indices = [
            (KIND.ARG): 0,
            (KIND.FIELD): 0,
            (KIND.STATIC): 0,
            (KIND.VAR): 0,
    ]

    /**
     * constructor
     * starts a new class scope
     * resets the class's symbol table
     */
    SymbolTable(){
        classSymbols.clear()
        indices.put(KIND.STATIC, 0)
        indices.put(KIND.FIELD, 0)
    }

    /**
     * starts a new subroutine scope
     * resets the subroutine's symbol table
     */
    static void startSubroutine(){
        subroutineSymbols.clear()
        indices.put(KIND.VAR, 0)
        indices.put(KIND.ARG, 0)
    }

    /**
     * Defines a new identifier of a given name,type and kind
     * and assigns it a running index, STATIC and FIELD identifiers have a class scope,
     * while ARG and VAR identifiers have a subroutine scope
     * @param name of symbol - entry (key) in symbol table
     * @param type of symbol - int, char, boolean or class name
     * @param kind of symbol - ARG, VAR, STATIC and FIELD
     */
    static void define(String name, String type, KIND kind){
        if (kind in [KIND.ARG, KIND.VAR, KIND.STATIC, KIND.FIELD]){
            int index = indices[kind]
            Symbol symbol = new Symbol(type,kind,index)
            indices.put(kind, index+1)
            kind in [KIND.ARG, KIND.VAR] ?
                    subroutineSymbols.put(name, symbol) :
                    classSymbols.put(name, symbol)
        }
    }

    /**
     * returns the number of variables of the given kind already defined in the current scope
     * @param kind
     * @return
     */
    static int varCount(KIND kind){
        return indices[kind]
    }

    /**
     * returns the kind of the named identifier in the current scope
     * if the identifier is unknown in the current scope returns NONE
     * @param name
     * @return
     */
    static KIND kindOf(String name){
        Symbol symbol = lookUp(name)
        if (symbol != null) {
            return symbol.getKind()
        }
        return KIND.NONE
    }

    /**
     * returns the type of the named identifier in the current scope
     * @param name
     * @return
     */
    static String typeOf(String name){
        Symbol symbol = lookUp(name)
        if (symbol != null) {
            return symbol.getType()
        }
        return ""
    }

    /**
     * returns the index assigned to the named identifier
     * @param name
     * @return
     */
    static int indexOf(String name){
        Symbol symbol = lookUp(name)
        if (symbol != null) {
            return symbol.getIndex()
        }
        return -1
    }

    /**
     * check if target symbol is exist
     * @param name
     * @return
     */
    static private Symbol lookUp(String name){
        /**
         * Note! It is necessary to look in the symbol table of the function before the table of symbols of the class,
         * because the inner scoop should precede the outer scoop
         * (in case there is a variable in the class and a variable in the function that has the same name).
         */
        if (subroutineSymbols[name] != null){
            return subroutineSymbols[name] as Symbol
        } else if (classSymbols[name] != null){
            return classSymbols[name] as Symbol
        } else {
            return null
            //throw new IllegalStateException("The name '$name' a is not in the table of symbols")
        }
    }
}
