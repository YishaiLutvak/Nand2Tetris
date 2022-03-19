package project08

import project08.Constants.CommandType

class Parser {

    private static File file = null
    private static String currentCommand

    /**
     * Constructor
     * Open the input file and gets reade to parse.
     * @param inputFile.
     */
    Parser(File inputFile) {
        file = inputFile
    }

    /**
     * Setter
     * @param cmd - string of cmd.
     */
    static void setCurrentCommand(String cmd) {
        currentCommand = cmd.split("//")[0].trim() // for comments in end of line
    }

    /**
     * Getter
     * @param cmd - string of cmd.
     */
    static String getCurrentCommand() {
        currentCommand
    }

    /**
     * @return CommandType - a constant representing the type of the current command.
     * ARITHMETIC is returned for all the arithmetic/logical commands.
     */
    static def getCommandType() {
        switch (currentCommand.split(' ')[0]) {
            case 'push'-> CommandType.PUSH
            case 'pop'-> CommandType.POP
            case 'add','sub','neg','eq','gt','lt','and','or','not'-> CommandType.ARITHMETIC
            case 'label'-> CommandType.LABEL
            case 'goto' -> CommandType.GOTO
            case 'if-goto' -> CommandType.IF
            case 'call' -> CommandType.CALL
            case 'return' -> CommandType.RETURN
            case 'function' -> CommandType.FUNCTION
            default -> CommandType.NONE
        }
    }

    /**
     * @return the first argument or current command.
     * In the case of ARITHMETIC, the command itself (add, sub, etc.) is returned.
     * Should not be called if the current command is RETURN.
     */
    static String arg1() {
        currentCommand.split(' ')[1]
    }

    /**
     * @return the second argument of current command.
     * Should be called only if the current command is PUSH, POP, FUNCTION, or CALL.
     */
    static int arg2() {
        currentCommand.split(' ')[2] as int
    }
}