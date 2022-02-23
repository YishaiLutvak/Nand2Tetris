package project07

import project07.Constants.CommandType

class Parser {

    private static Parser instance = null //singleton
    private static File file = null
    private static String currentCommand

    /**
     *
     * @param file
     */
    private Parser(File inputFile) {
        file = inputFile
    }

    /**
     * singleton
     * @param file
     * @return
     */
    static Parser getInstance(File inputFile) {
        if (instance == null) {
            instance = new Parser(inputFile)
        }
        return instance
    }

    /**
     * setter
     * @param cmd
     */
    static void setCurrentCommand(String cmd) {
        currentCommand = cmd
    }

    /**
     * getter
     * @return
     */
    static def getCommandType() {
        switch (currentCommand.split(' ')[0]) {
            case 'push'-> CommandType.C_PUSH
            case 'pop'-> CommandType.C_POP
            case 'add','sub','neg','eq','gt','lt','and','or','not'-> CommandType.C_ARITHMETIC
            default -> CommandType.C_NOTHING
        }
    }

    /**
     *
     * @return
     */
    static String arg1() {
        currentCommand.split(' ')[1]
    }

    /**
     *
     * @return
     */
    static int arg2() {
        currentCommand.split(' ')[2] as int
    }

    /* static boolean hasMoreCommands() {}
    static def advance() {} */
}