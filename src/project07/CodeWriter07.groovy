package project07

import project07.Constants07.CommandType

class CodeWriter07 {

    private static CodeWriter07 instance = null // singleton
    private static FileWriter out = null
    private static String currentFileName = null
    private static eqCounter = 0
    private static gtICounter = 0
    private static ltCounter = 0
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
    private CodeWriter07(File outputFile) {
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
    static CodeWriter07 getInstance(File outputFile) {
        if (instance == null)
            instance = new CodeWriter07(outputFile)
        return instance
    }

    /**
     * Setter and add comment at assembly file.
     * @param file - name of file.
     */
    static void setCurrentFileName(String file) {
        currentFileName = file.split(/\./)[0]
        out << "// $file\n"
    }

    /**
     * Writes to the output file the assembly code that implements the given arithmetic command.
     * @param command - string arithmetic of command.
     */
    static void writeArithmetic(String command) {
        switch (command) {
            case 'add' -> out << Constants07.ADD
            case 'sub' -> out << Constants07.SUB
            case 'neg' -> out << Constants07.NEG
            case 'eq' -> out << Constants07.EQ.replace('{index}', "${eqCounter++}")
            case 'gt' -> out << Constants07.GT.replace('{index}', "${gtICounter++}")
            case 'lt' -> out << Constants07.LT.replace('{index}', "${ltCounter++}")
            case 'and' -> out << Constants07.AND
            case 'or' -> out << Constants07.OR
            case 'not' -> out << Constants07.NOT
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
                case 'constant' -> out << Constants07.PUSH_CONSTANT
                        .replace('{value}', "${index}")
                case 'local', 'argument', 'this', 'that' -> out << Constants07.PUSH_LCL_ARG_THIS_THAT
                        .replace('{index}', "${index}")
                        .replace('{segment}', "${myMap[segment]}")
                case 'temp' -> out << Constants07.PUSH_TEMP
                        .replace('{index}', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants07.PUSH_POINTER.replace('{index}','THIS')
                    case 1 -> out << Constants07.PUSH_POINTER.replace('{index}','THAT')}
                case 'static' -> out << Constants07.PUSH_STATIC
                        .replace('{index}', "${currentFileName}.${index}")
            }
            //
            case CommandType.C_POP -> switch (segment) {
                case 'local', 'argument', 'this', 'that' -> out << Constants07.POP_LCL_ARG_THIS_THAT
                        .replace('{index}', 'A=A+1\n  ' * index)
                        .replace('{segment}',
                                "${myMap[segment]}")
                case 'temp' -> out << Constants07.POP_TEMP
                        .replace('{index}', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants07.POP_POINTER.replace('{index}','THIS')
                    case 1 -> out << Constants07.POP_POINTER.replace('{index}','THAT')
                }
                case 'static' -> out << Constants07.POP_STATIC
                        .replace('{index}', "${currentFileName}.${index}")
            }
        }
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