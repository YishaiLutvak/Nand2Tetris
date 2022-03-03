package project08

import project08.Constants.CommandType

class Parser {

    private static Parser instance = null //singleton
    private static File file = null
    private static String currentCommand

    /**
     * Constructor
     * Open the input file and gets reade to parse.
     * @param inputFile.
     */
    private Parser(File inputFile) {
        file = inputFile
    }

    /**
     * Singleton
     * @param inputFile.
     * @return instance of Parser.
     */
    static Parser getInstance(File inputFile) {
        if (instance == null) {
            instance = new Parser(inputFile)
        }
        return instance
    }

    /**
     * Setter
     * @param cmd - string of cmd.
     */
    static void setCurrentCommand(String cmd) {
        currentCommand = cmd.split("//")[0].trim() // for comments in end of line
    }

    /**
     * @return CommandType - a constant representing the type of the current command.
     * C_ARITHMETIC is returned for all the arithmetic/logical commands.
     */
    static def getCommandType() {
        switch (currentCommand.split(' ')[0]) {
            case 'push'-> CommandType.C_PUSH
            case 'pop'-> CommandType.C_POP
            case 'add','sub','neg','eq','gt','lt','and','or','not'-> CommandType.C_ARITHMETIC
            case 'label'-> CommandType.C_LABEL
            case 'goto' -> CommandType.C_GOTO
            case 'if-goto' -> CommandType.C_IF
            case 'call' -> CommandType.C_CALL
            case 'return' -> CommandType.C_RETURN
            case 'function' -> CommandType.C_FUNCTION
            default -> CommandType.C_NOTHING
        }
    }

    /**
     * @return the first argument or current command.
     * In the case of C_ARITHMETIC, the command itself (add, sub, etc.) is returned.
     * Should not be called if the current command is C_RETURN.
     */
    static String arg1() {
        currentCommand.split(' ')[1]
    }

    /**
     * @return the second argument of current command.
     * Should be called only if the current command is C_PUSH, C_POP, C_FUNCTION, or C_CALL.
     */
    static int arg2() {
        currentCommand.split(' ')[2] as int
    }

    /* static boolean hasMoreCommands() {}
    static def advance() {} */
}