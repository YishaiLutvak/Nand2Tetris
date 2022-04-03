package project08

import project08.Constants.COMMAND_TYPE

class Parser {

    private static File file
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
        // currentCommand = cmd.split('//')[0].trim()
        currentCommand = cmd.replaceFirst(~'//.*','').trim() // for all comments
    }

    /**
     * Getter
     * @param cmd - string of cmd.
     */
    static String getCurrentCommand() {
        return currentCommand
    }

    /**
     * @return COMMAND_TYPE - a constant representing the type of the current command.
     * ARITHMETIC is returned for all the arithmetic/logical commands.
     */
    static COMMAND_TYPE getCommandType() {
        COMMAND_TYPE commandType = COMMAND_TYPE.NONE
        switch (currentCommand.split(' ')[0]) {
            case 'push'-> commandType = COMMAND_TYPE.PUSH
            case 'pop' -> commandType = COMMAND_TYPE.POP
            case 'add','sub','neg','eq','gt','lt','and','or','not'-> commandType = COMMAND_TYPE.ARITHMETIC
            case 'label'-> commandType = COMMAND_TYPE.LABEL
            case 'goto' -> commandType = COMMAND_TYPE.GOTO
            case 'if-goto' -> commandType = COMMAND_TYPE.IF
            case 'call' -> commandType = COMMAND_TYPE.CALL
            case 'return' -> commandType = COMMAND_TYPE.RETURN
            case 'function' -> commandType = COMMAND_TYPE.FUNCTION
        }
        return commandType
    }

    /**
     * @return the first argument or current command.
     * In the case of ARITHMETIC, the command itself (add, sub, etc.) is returned.
     * Should not be called if the current command is RETURN.
     */
    static String arg1() {
        return currentCommand.split(' ')[1]
    }

    /**
     * @return the second argument of current command.
     * Should be called only if the current command is PUSH, POP, FUNCTION, or CALL.
     */
    static int arg2() {
        return currentCommand.split(' ')[2] as int
    }
}