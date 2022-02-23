package project07

import project07.Constants.CommandType

class CodeWriter {

    private static CodeWriter instance = null //singleton
    private static FileWriter out = null
    private static String currentFileName = null
    private static eqIndex = 0
    private static gtIndex = 0
    private static ltIndex = 0
    private static final myMap = [local:'LCL',argument:'ARG',this:'THIS',that:'THAT',temp:'5']

    /**
     *
     * @param outputFile
     */
    private CodeWriter(File outputFile) {
        if (outputFile.isDirectory()) {
            out = new FileWriter(new File(outputFile, "${outputFile.name}.asm"))
        }
        else {
            out = new FileWriter(new File(
                    outputFile.getParent(), "${outputFile.name.split('\\.')[0]}.asm"))
        }
    }

    /**
     * singleton
     * @param file
     * @return
     */
    static CodeWriter getInstance(File file) {
        if (instance == null) {
            instance = new CodeWriter(file)
        }
        return instance
    }

    /**
     * setter and add comment at assembly file
     * @param file
     */
    static void setCurrentFileName(String file) {
        currentFileName = file.split('\\.')[0]
        out << "// ${file}\n"
    }

    /**
     *
     * @param command
     */
    static void writeArithmetic(String command) {
        switch (command) {
            case 'add' -> out << Constants.ADD
            case 'sub' -> out << Constants.SUB
            case 'neg' -> out << Constants.NEG
            case 'eq' -> out << Constants.EQ.replace('[index]', "${eqIndex++}")
            case 'gt' -> out << Constants.GT.replace('[index]', "${gtIndex++}")
            case 'lt' -> out << Constants.LT.replace('[index]', "${ltIndex++}")
            case 'and' -> out << Constants.AND
            case 'or' -> out << Constants.OR
            case 'not' -> out << Constants.NOT
        }
    }

    /**
     *
     * @param command
     * @param segment
     * @param index
     */
    static void writePushPop(CommandType command, String segment, int index) {
        switch (command) {
            case CommandType.C_PUSH-> switch (segment) {
                case 'constant' -> out << Constants.PUSH_CONSTANT
                        .replace('[value]', "${index}")
                case 'local', 'argument', 'this', 'that' -> out << Constants.PUSH_LCL_ARG_THIS_THAT
                        .replace('[index]', "${index}")
                        .replace('[segment]', "${myMap[segment]}")
                case 'temp' -> out << Constants.PUSH_TEMP
                        .replace('[index]', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants.PUSH_POINTER.replace('[index]','THIS')
                    case 1 -> out << Constants.PUSH_POINTER.replace('[index]','THAT')}
                case 'static' -> out << Constants.PUSH_STATIC
                        .replace('[index]', "${currentFileName}.${index}")
            }
            case CommandType.C_POP -> switch (segment) {
                case 'local', 'argument', 'this', 'that' -> out << Constants.POP_LCL_ARG_THIS_THAT
                        .replace('[offset]', 'A=A+1\n  ' * index)
                        .replace('[segment]',
                                "${myMap[segment]}")
                case 'temp' -> out << Constants.POP_TEMP
                        .replace('[index]', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants.POP_POINTER.replace('[index]','THIS')
                    case 1 -> out << Constants.POP_POINTER.replace('[index]','THAT')
                }
                case 'static' -> out << Constants.POP_STATIC
                        .replace('[index]', "${currentFileName}.${index}")
            }
        }
    }

    /**
     *
     * @param command
     * @param numberLine
     */
    static void emitComment(String command, int numberLine) {
        out << "// ${command}    (line ${numberLine})\n"
    }

    /**
     *
     */
    static void close() {
        out.close()
    }
}