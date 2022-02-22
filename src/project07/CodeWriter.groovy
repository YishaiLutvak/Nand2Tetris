package project07

import project07.Constants.CommandType

class CodeWriter {

    private static final myMap = [local:'LCL',argument:'ARG',this:'THIS',that:'THAT',temp:'5']
    private static CodeWriter instance = null //singleton
    private static FileWriter out = null
    private static eqIndex = 0
    private static gtIndex = 0
    private static ltIndex = 0
    private static String currentFileName = null

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
    void setCurrentFileName(String file) {
        this.currentFileName = file.split('\\.')[0]
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
    void writePushPop(CommandType command, String segment, int index) {
        switch (command) {
            case CommandType.C_PUSH-> switch (segment) {
                case 'constant' -> out << Constants.PUSH_CONSTANT
                        .replace('[value]', "${index}")
                case 'local', 'argument', 'this', 'that' -> out << Constants.PUSH_GROUP1
                        .replace('[index]', "${index}")
                        .replace('[segment]', "${myMap[segment]}")
                case 'temp' -> out << Constants.PUSH_GROUP2
                        .replace('[index]', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants.PUSH_GROUP4.replace('[index]','THIS')
                    case 1 -> out << Constants.PUSH_GROUP4.replace('[index]','THAT')}
                case 'static' -> out << Constants.PUSH_GROUP3
                        .replace('[index]', "${this.currentFileName}.${index}")
            }
            case CommandType.C_POP -> switch (segment) {
                case 'local', 'argument', 'this', 'that' -> out << Constants.POP_GROUP1
                        .replace('[offset]', 'A=A+1\n' * index)
                        .replace('[segment]',
                                "${myMap[segment]}")
                case 'temp' -> out << Constants.POP_GROUP2
                        .replace('[index]', "${index}")
                case 'pointer' -> switch (index) {
                    case 0 -> out << Constants.POP_GROUP4.replace('[index]','THIS')
                    case 1 -> out << Constants.POP_GROUP4.replace('[index]','THAT')
                }
                case 'static' -> out << Constants.POP_GROUP3
                        .replace('[index]', "${this.currentFileName}.${index}")
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