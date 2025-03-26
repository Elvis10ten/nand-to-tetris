package compiler.project_11

import java.io.File

/**
 * This module features a set of simple routines for writing VM commands into the output file.
 */
class VMWriter(
    val file: File
) {

    private val fileWriter = file.bufferedWriter()

    init {
        // Clears file.
        fileWriter.write("")
    }

    /**
     * Writes a VM push command.
     */
    fun writePush(segment: MemorySegment, index: Int) {
        fileWriter.appendLine("push ${segment.id} $index")
    }

    /**
     * Writes a VM pop command.
     */
    fun writePop(segment: MemorySegment, index: Int) {
        fileWriter.appendLine("pop ${segment.id} $index")
    }

    /**
     * Writes a VM arithmetic-logical command.
     */
    fun writeArithmeticLogical(operation: ArithmeticLogicalOperation) {
        fileWriter.appendLine(operation.id)
    }

    /**
     * Writes a VM LABEL command.
     */
    fun writeLabel(labelName: String) {
        fileWriter.appendLine("label $labelName")
    }

    /**
     * Writes a VM GOTO command.
     */
    fun writeGoto(labelName: String) {
        fileWriter.appendLine("goto $labelName")
    }

    /**
     * Writes a VM IF-GOTO command.
     */
    fun writeIfGoto(labelName: String) {
        fileWriter.appendLine("if-goto $labelName")
    }

    /**
     * Writes a VM CALL command.
     */
    fun writeCall(subroutineName: String, numArgs: Int) {
        fileWriter.appendLine("call $subroutineName $numArgs")
    }

    /**
     * Writes a VM FUNCTION command.
     */
    fun writeFunction(subroutineName: String, numVars: Int) {
        fileWriter.appendLine("function $subroutineName $numVars")
    }

    /**
     * Writes a VM RETURN command.
     */
    fun writeReturn() {
        fileWriter.appendLine("return")
    }

    fun close() {
        fileWriter.flush()
        fileWriter.close()
    }
}

