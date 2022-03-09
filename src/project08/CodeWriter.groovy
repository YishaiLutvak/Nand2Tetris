package project08

import project08.Constants.CommandType

class CodeWriter {

    private static CodeWriter instance = null // singleton
    private static FileWriter out = null
    private static String currentFileName = null
    private static eqCounter = 0
    private static gtICounter = 0
    private static ltCounter = 0
    private static callCounter = 0
    private static final myMap = [
            local:'LCL',
            argument:'ARG',
            this:'THIS',
            that:'THAT',
            temp:'5'
    ]

    /**
     * Constructor
     * Opens the output file and gets ready to write into it.
     * @param outputFile - path of output file.
     */
    private CodeWriter(File outputFile) {
        if (outputFile.isDirectory()) {
            out = new FileWriter(new File(outputFile, "${outputFile.name}.asm"))
        }
        else {
            out = new FileWriter(new File(
                    outputFile.getParent(), "${outputFile.name.split(/\./)[0]}.asm"))
        }
    }

    /**
     * Singleton
     * @param outputFile - path of output file.
     * @return instance of CodeWriter.
     */
    static CodeWriter getInstance(File outputFile) {
        if (instance == null) {
            instance = new CodeWriter(outputFile)
        }
        instance
    }

    /**
     * Setter and add comment at assembly file.
     * @param file - name of file.
     */
    static void setCurrentFileName(String file) {
        currentFileName = file.split(/\./)[0]
        out << "// ${file}\n"
    }

    /**
     * Writes to the output file the assembly code that implements the given arithmetic command.
     * @param command - string arithmetic of command.
     */
    static void writeArithmetic(String command) {
        switch (command) {
            case 'add' -> out << Constants.ADD
            case 'sub' -> out << Constants.SUB
            case 'neg' -> out << Constants.NEG
            case 'eq' -> out << Constants.EQ.replace('{index}', "${eqCounter++}")
            case 'gt' -> out << Constants.GT.replace('{index}', "${gtICounter++}")
            case 'lt' -> out << Constants.LT.replace('{index}', "${ltCounter++}")
            case 'and' -> out << Constants.AND
            case 'or' -> out << Constants.OR
            case 'not' -> out << Constants.NOT
        }
    }

    /**
     * Writes to the output file the assembly code that implements the given command,
     * where command is either C_PUSH or C_POP.
     * @param command - type of command - first word in command.
     * @param segment - name of segment - second word in command.
     * @param index - offset in segment - last word in command.
     */
    static void writePushPop(CommandType command, String segment, int index) {
        switch (command) {
        //
            case CommandType.C_PUSH-> switch (segment) {
                case 'constant' -> out << Constants.PUSH_CONSTANT
                        .replace('{value}', "${index}")
                case 'local', 'argument', 'this', 'that' -> out << Constants.PUSH_LCL_ARG_THIS_THAT
                        .replace('{index}', "${index}")
                        .replace('{segment}', "${myMap[segment]}")
                case 'temp' -> out << Constants.PUSH_TEMP
                        .replace('{index}', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants.PUSH_POINTER.replace('{index}','THIS')
                    case 1 -> out << Constants.PUSH_POINTER.replace('{index}','THAT')}
                case 'static' -> out << Constants.PUSH_STATIC
                        .replace('{index}', "${currentFileName}.${index}")
            }
                //
            case CommandType.C_POP -> switch (segment) {
                case 'local', 'argument', 'this', 'that' -> out << Constants.POP_LCL_ARG_THIS_THAT
                        .replace('{index}', 'A=A+1\n  ' * index)
                        .replace('{segment}',
                                "${myMap[segment]}")
                case 'temp' -> out << Constants.POP_TEMP
                        .replace('{index}', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants.POP_POINTER.replace('{index}','THIS')
                    case 1 -> out << Constants.POP_POINTER.replace('{index}','THAT')
                }
                case 'static' -> out << Constants.POP_STATIC
                        .replace('{index}', "${currentFileName}.${index}")
            }
        }
    }

    /**
     *
     */
    static void writeInit(){
        out << Constants.BOOTSTRAPPING
    }

    /**
     *
     * @param label
     */
    static void writeLabel(String label) {
        out <<  Constants.LABEL.replace('{label}', "${currentFileName}.${label}")
    }

    /**
     *
     * @param label
     */
    static void writeGoto(String label) {
        out << Constants.GOTO.replace('{label}', "${currentFileName}.${label}")
    }

    /**
     *
     * @param label
     */
    static void writeIf(String label) {
        out << Constants.IF_GOTO.replace('{label}', "${currentFileName}.${label}")
    }

    /**
     *
     * @param functionName
     * @param numOfArguments
     */
    static void writeCall(String functionName, int numOfArguments) {
        out << Constants.CALL
                .replace('{nameOfFunction}', "${functionName}")
                .replace('{numARG}', "${numOfArguments}")
                .replace('{index}', "${callCounter++}")
    }

    /**
     *
     */
    static void writeReturn() {
        out << Constants.RETURN
    }

    /**
     *
     * @param functionName
     * @param numOfLocals
     */
    static void writeFunction(String functionName, int numOfLocals) {
        out << Constants.FUNCTION
                .replace('{nameOfFunction}', "${functionName}")
                .replace('{numberOfLocals}', "${numOfLocals}")
    }

    /**
     * Emit comment in output file.
     * @param command - string of command.
     * @param numberLine - number of line.
     */
    static void emitComment(String command, int numberLine) {
        out << "// ${command}    (line ${numberLine})\n"
    }

    /**
     * Closes the output file.
     */
    static void close() {
        out.close()
    }
}
