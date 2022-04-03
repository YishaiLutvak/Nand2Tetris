package project07

import project07.Constants07.COMMAND_TYPE

class Parser07 {

    private static File file
    private static String currentCommand

    /**
     * Constructor
     * Open the input file and gets reade to parse.
     * @param inputFile.
     */
    Parser07(File inputFile) {
        file = inputFile
    }

    /**
     * Setter
     * @param cmd - string of cmd.
     */
    static void setCurrentCommand(String cmd) {
        currentCommand = cmd.split('//')[0].trim() // for comments in end of line
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
            case 'pop'-> commandType = COMMAND_TYPE.POP
            case 'add','sub','neg','eq','gt','lt','and','or','not'-> commandType = COMMAND_TYPE.ARITHMETIC
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

    /* static boolean hasMoreCommands() {}
    static def advance() {} */
}