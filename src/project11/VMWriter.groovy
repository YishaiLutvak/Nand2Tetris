package project11
import static project11.CompilationEngine.Indentation
import static project11.CompilationEngine.width

/**
 *
 */
class VMWriter {

    static enum SEGMENT {CONST,ARG,LOCAL,STATIC,THIS,THAT,POINTER,TEMP,NONE}
    static enum COMMAND {ADD,SUB,NEG,EQ,GT,LT,AND,OR,NOT}
    static private PrintWriter printWriter
    private static segmentStringHashMap = [
            (SEGMENT.CONST):"constant",
            (SEGMENT.ARG):"argument",
            (SEGMENT.LOCAL):"local",
            (SEGMENT.STATIC):"static",
            (SEGMENT.THIS):"this",
            (SEGMENT.THAT):"that",
            (SEGMENT.POINTER):"pointer",
            (SEGMENT.TEMP):"temp",
    ]
    private static commandStringHashMap = [
            (COMMAND.ADD):"add",
            (COMMAND.SUB):"sub",
            (COMMAND.NEG):"neg",
            (COMMAND.EQ):"eq",
            (COMMAND.GT):"gt",
            (COMMAND.LT):"lt",
            (COMMAND.AND):"and",
            (COMMAND.OR):"or",
            (COMMAND.NOT):"not",
    ]

    /**
     * creates a new file and prepares it for writing
     * @param outFile
     */
    VMWriter(File outFile) {
        try {
            printWriter = new PrintWriter(outFile)
        } catch (FileNotFoundException e) {
            e.printStackTrace()
        }
    }

    /**
     * writes a VM push command
     * @param segment
     * @param index
     */
    static void writePush(SEGMENT segment, int index){
        writeCommand("push",segmentStringHashMap[segment], index as String)
    }

    /**
     * writes a VM pop command
     * @param segment
     * @param index
     */
    static void writePop(SEGMENT segment, int index){
        writeCommand("pop",segmentStringHashMap[segment], index as String)
    }

    /**
     * writes a VM arithmetic command
     * @param command
     */
    static void writeArithmetic(COMMAND command){
        writeCommand(commandStringHashMap[command])
    }

    /**
     * writes a VM label command
     * @param label
     */
    static void writeLabel(String label){
        writeCommand("label",label)
    }

    /**
     * writes a VM goto command
     * @param label
     */
    static void writeGoto(String label){
        writeCommand("goto",label)
    }

    /**
     * writes a VM if-goto command
     * @param label
     */
    static void writeIf(String label){
        writeCommand("if-goto",label)
    }

    /**
     * writes a VM call command
     * @param name
     * @param nArgs
     */
    static void writeCall(String name, int nArgs){
        writeCommand("call", name, nArgs as String)
    }

    /**
     * writes a VM function command
     * @param name
     * @param nLocals
     */
    static void writeFunction(String name, int nLocals){
        writeCommand("function", name, nLocals as String)
    }

    /**
     * writes a VM return command
     */
    static void writeReturn(){
        writeCommand("return")
    }

    /**
     * write a VM command
     * @param cmd
     * @param arg1
     * @param arg2
     */
    static void writeCommand(String cmd, String arg1="", String arg2=""){
        println("${width*Indentation}-----{ $cmd $arg1 $arg2 }-----")
        printWriter.print("$cmd $arg1 $arg2\n")
    }

    /**
     * close the output file
     */
    static void close(){
        printWriter.close()
    }
}
