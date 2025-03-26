package assembler

import java.io.File
import java.io.IOException

object Assembler {

    /**
     * This function drives the entire assembly process, using the services of the Parser and Code modules.
     */
    fun assemble(input: File) {
        val assemblyFiles = if (input.isDirectory) {
            input.listFiles { _, name -> name.endsWith(".asm") }
        } else {
            arrayOf(input)
        }

        assemblyFiles.forEach { assemblyFile ->
            try {
                val firstPassSymbolTable = firstPass(assemblyFile)
                secondPass(assemblyFile, firstPassSymbolTable)
            } catch (t: Throwable) {
                throw IOException("Error assembling '${assemblyFile}", t)
            }
        }
    }

    /**
     * The assembler goes through the entire assembly program, line by line, keeping track of the line number.
     *
     * This number starts at 0 and is incremented by 1 whenever an A-instruction or a C-instruction is encountered,
     * but does not change when a comment or a label declaration is encountered.
     *
     * Each time a label declaration (xxx) is encountered, the assembler adds a new entry to the symbol table,
     * associating the symbol "xxx" with the current line number (this will be the ROM address of the next instruction
     * in the program).
     *
     * This pass results in adding to the symbol table all the program's label symbols, along with their corresponding values.
     * No code is generated during the first pass.
     */
    private fun firstPass(assemblyFile: File): SymbolTable {
        val parser = Parser(assemblyFile)
        val symbolTable = SymbolTable()
        var numLabelsSoFar = 0

        while(parser.hasMoreLines()) {
            parser.advance()
            val curInstruction = parser.getCurInstruction()

            if (curInstruction is Parser.Instruction.Label) {
                /**
                 * The parser already filters out comments and blank lines, so no need to handle them here.
                 * The only thing left to handle are label declarations, which are pseudo-instructions not written to the binary file.
                 * The instruction address pointed to by a label is equal to the current instruction index minus the number of labels seen so far.
                 *
                 * We use the current instruction index because the label which is the current instruction is a pseudo instruction and
                 * is removed from the generated binary. This makes the next instruction have the same index as this label in the generated binary.
                 *
                 * Secondly, we subtract the number of labels seen so far to account for the removal of preceding labels.
                 */
                symbolTable.addEntry(curInstruction.symbol, parser.curInstructionIndex - numLabelsSoFar)
                numLabelsSoFar++
            }
        }

        return symbolTable
    }

    /**
     * The assembler goes again through the entire program and parses each line as follows:
     * * Each time an A-instruction with a symbolic reference is encountered, namely, @xxx, where xxx is a symbol and not a
     * number, the assembler looks up xxx in the symbol table.
     * * If the symbol is found, the assembler replaces it with its numeric value and completes the instruction’s translation.
     * * If the symbol is not found, then it must represent a new variable. To handle it, the assembler:
     *      i. Adds the entry <xxx, value> to the symbol table, where value is the next available address in the RAM
     *         space designated for variables, and
     *      ii. Completes the instruction’s translation, using this address.
     *
     * * In the Hack platform, the RAM space designated for storing variables starts at 16 and is incremented by 1 after
     * each time a new variable is found in the code.
     */
    private fun secondPass(assemblyFile: File, symbolTable: SymbolTable) {
        val parser = Parser(assemblyFile)
        val outputWriter = File(assemblyFile.parent, "${assemblyFile.nameWithoutExtension}.hack").bufferedWriter()
        var variableAddress = 16

        while (parser.hasMoreLines()) {
            parser.advance()

            when (val curInstruction = parser.getCurInstruction()) {
                is Parser.Instruction.Address -> {
                    val address = if (curInstruction.symbol.all { it.isDigit() }) {
                        // We have a physical memory address.
                        curInstruction.symbol.toInt().to15BitBinaryString()
                    } else {
                        // We have a symbolic address.
                        if (symbolTable.contains(curInstruction.symbol)) {
                            // Must be a label or must be a variable we already encountered.
                            symbolTable.getAddress(curInstruction.symbol).to15BitBinaryString()
                        } else {
                            val curVariableAddress = variableAddress
                            variableAddress++

                            symbolTable.addEntry(curInstruction.symbol, curVariableAddress)
                            curVariableAddress.to15BitBinaryString()
                        }
                    }

                    outputWriter.appendLine("0${address}")
                }
                is Parser.Instruction.Compute -> {
                    val compField = Code.comp(curInstruction)
                    val destField = Code.dest(curInstruction)
                    val jumpField = Code.jump(curInstruction)
                    outputWriter.appendLine("111${compField}${destField}${jumpField}")
                }
                is Parser.Instruction.Label -> {
                    // Label instructions are pseudo-instructions and are not written.
                }
            }
        }

        outputWriter.flush()
        outputWriter.close()
    }

    private fun Int.to15BitBinaryString(): String {
        return this.and(0x7FFF).toString(2).padStart(15, '0')
    }
}