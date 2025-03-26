package assembler

import java.io.File

/**
 * The parser encapsulates access to the input assembly code. In particular, it provides a convenient means for
 * advancing through the source code, skipping comments and white space, and line by line, breaking each symbolic
 * instruction into its underlying components.
 */
class Parser(private val file: File) {

    var curInstructionIndex = -1
        private set

    private val instructions = file.readLines().filter {
        /**
         * Lines beginning with two slashes (//) are considered comments and are ignored.
         */
        !it.trim().startsWith("//") &&
                /**
                 * Blank lines are also ignored.
                 */
                it.trim().isNotEmpty()
    }

    /**
     * Are there more lines in the input?
     */
    fun hasMoreLines() = curInstructionIndex < instructions.lastIndex

    /**
     * Reads the next instruction from the input, and makes it the current instruction.
     * This routine should be called only if [hasMoreLines] is true.
     * Initially, there is no current instruction.
     *
     * There is no need to skip comments or blank lines here, because that has already been ignored.
     */
    fun advance() {
        curInstructionIndex++
    }

    fun getCurInstruction(): Instruction {
        val instructionText = instructions[curInstructionIndex].trim()

        return when {
            instructionText.startsWith("@") -> {
                Instruction.Address(instructionText.removePrefix("@"))
            }
            instructionText.startsWith("(") && instructionText.endsWith(")") -> {
                Instruction.Label(instructionText.removePrefix("(").removeSuffix(")"))
            }
            else -> {
                // We assume if the instruction is neither address nor label, then it must be a compute instruction.
                Instruction.Compute(
                    // Destination is optional; If it is not set, use a blank text. "=" exists if there is a destination component.
                    dest = instructionText.substringBefore(delimiter = "=", missingDelimiterValue = ""),
                    comp = instructionText.substringAfter(delimiter = "=").substringBefore(delimiter = ";"),
                    // Jump is optional; If it is not set, use a blank text. ";" exists if there is a jump component.
                    jump = instructionText.substringAfter(delimiter = ";", missingDelimiterValue = "")
                )
            }
        }
    }

    sealed interface Instruction {

        data class Address(
            /**
             * The symbol or decimal "xxx" used in an address instruction "@xxx" in an assembly program.
             */
            val symbol: String
        ): Instruction

        data class Label(
            /**
             * The symbol "xxx" used in a label instruction "(xxx)" in an assembly program.
             */
            val symbol: String
        ): Instruction

        data class Compute(
            /**
             * The symbolic "destination" part of the compute instruction (8 possibilities).
             */
            val dest: String,
            /**
             * The symbolic "compute" part of the compute instruction (28 possibilities).
             */
            val comp: String,
            /**
             * The symbolic "jump" part of the compute instruction (8 possibilities).
             */
            val jump: String
        ): Instruction
    }
}