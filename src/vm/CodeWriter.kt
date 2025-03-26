package vm

import java.io.File
import java.io.Writer
import vm.CodeWriter.MemorySegment.*
import vm.CodeWriter.OperatorWriter.*
import vm.CodeWriter.BinaryArithmeticLogicalOperator.*
import vm.CodeWriter.BinaryComparisonOperator.*
import vm.CodeWriter.UnaryArithmeticLogicalOperator.*

class CodeWriter(private val outputFile: File) {

    private val fileWriter = outputFile.bufferedWriter()
    private var comparisonIndex = 0

    init {
        // Clears the file content.
        fileWriter.write("")
    }

    /**
     * Writes the arithmetic logical [command].
     */
    fun writeArithmeticLogical(command: String) {
        val operatorWriter = when (command) {
            "add" -> BinaryArithmeticLogical(ADD)
            "sub" -> BinaryArithmeticLogical(SUBTRACT)
            "or" -> BinaryArithmeticLogical(OR)
            "and" -> BinaryArithmeticLogical(AND)
            "lt" -> {
                comparisonIndex++
                BinaryComparison(comparisonIndex, LESS_THAN)
            }
            "gt" -> {
                comparisonIndex++
                BinaryComparison(comparisonIndex, GREATER_THAN)
            }
            "eq" -> {
                comparisonIndex++
                BinaryComparison(comparisonIndex, EQUAL)
            }
            "not" -> Unary(NOT)
            "neg" -> Unary(NEGATE)
            else -> throw UnsupportedOperationException("$command is not supported")
        }

        operatorWriter.write(this)
        fileWriter.appendLine()
    }

    /**
     * Writes a push or pop command.
     */
    fun writePushPop(isPush: Boolean, segmentName: String, index: Int) {
        val segment = getMemorySegment(segmentName, index)
        if (isPush) {
            writePush(segment)
        } else {
            writePop(segment)
        }

        fileWriter.appendLine()
    }

    private fun getMemorySegment(segment: String, index: Int): MemorySegment {
        return when (segment) {
            // Each reference to 'static i' in a VM program stored in file 'Foo.vm' is translated to the assembly symbol 'Foo.i'.
            "static" -> SymbolicAddress("${outputFile.nameWithoutExtension}.${index}")
            "constant" -> Constant(index)
            "temp" -> Temp(index)
            "pointer" -> Pointer(index)
            // All the segments below are all relative to a base symbolic address.
            "argument" -> Relative(baseSymbolicAddress = "ARG", index)
            "local" -> Relative(baseSymbolicAddress = "LCL", index)
            "this" -> Relative(baseSymbolicAddress = "THIS", index)
            "that" -> Relative(baseSymbolicAddress = "THAT", index)
            else -> throw UnsupportedOperationException("$segment is unsupported")
        }
    }

    private fun writePush(segment: MemorySegment) {
        with(fileWriter) {
            appendLine("// Pushing '$segment' to the stack.")
            segment.writeToDRegister(this)
            writeSPValueIntoARegister()
            appendLine("// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`")
            appendLine("M=D")
            writeIncrementSP()
        }
    }

    private fun writePop(segment: MemorySegment) {
        with(fileWriter) {
            appendLine("// Popping '$segment' from the stack.")
            writeDecrementSP()
            writeSPValueIntoARegister()
            appendLine("// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`")
            appendLine("D=M")
            segment.readFromDRegister(this)
        }
    }

    private fun Writer.writeIncrementSP() {
        appendLine("// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`")
        appendLine("@SP")
        appendLine("M=M+1")
    }

    private fun Writer.writeDecrementSP() {
        appendLine("// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`")
        appendLine("@SP")
        appendLine("M=M-1")
    }

    private fun Writer.writeSPValueIntoARegister() {
        appendLine("// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`")
        appendLine("@SP")
        appendLine("A=M")
    }

    fun close() {
        fileWriter.flush()
        fileWriter.close()
    }

    sealed interface MemorySegment {

        fun writeToDRegister(fileWriter: Writer)

        fun readFromDRegister(fileWriter: Writer)

        data class Constant(val value: Int) : MemorySegment {

            override fun readFromDRegister(fileWriter: Writer) {
                throw UnsupportedOperationException("Reading from D register is not supported for the constant memory segment.")
            }

            override fun writeToDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Writing '$value' constant to the D register. I.e: `A = $value; D = A`.")
                    appendLine("@$value")
                    appendLine("D=A")
                }
            }
        }

        data class SymbolicAddress(val name: String) : MemorySegment {

            override fun readFromDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting the '$name' symbolic address's value to the value from the D register. I.e: `A = $name; M[A] = D`.")
                    appendLine("@$name")
                    appendLine("M=D")
                }
            }

            override fun writeToDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting the D register's value to the value of the '$name' symbolic address. I.e: `A = $name; D = M[A]`")
                    appendLine("@$name")
                    appendLine("D=M")
                }
            }
        }

        /**
         * The pointer segment contains exactly two values and is mapped directly onto RAM locations 3 and 4.
         * Recall that these RAM locations are also called, respectively, 'THIS' and 'THAT'.
         */
        data class Pointer(val index: Int) : MemorySegment {

            private val address = when (index) {
                0 -> 3
                1 -> 4
                else -> throw UnsupportedOperationException("'$index' index is unsupported in the pointer segment.")
            }

            override fun writeToDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting the D register's value to the value of the '${this@Pointer}' pointer address. I.e: `A = $address; D = M[A]`")
                    appendLine("@$address")
                    appendLine("D=M")
                }
            }

            override fun readFromDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting the '${this@Pointer}' pointer address's value to the value from the D register. I.e: `A = $address; M[A] = D`.")
                    appendLine("@$address")
                    appendLine("M=D")
                }
            }
        }

        /**
         * This 8-word segment is fixed and mapped directly on RAM locations 5–12.
         * Any access to 'temp i', where 'i' varies from 0 to 7, should be translated into assembly code that accesses RAM location '5 + i'.
         */
        data class Temp(val index: Int) : MemorySegment {

            private val address = 5 + index

            override fun writeToDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting the D register's value to the value of the '${this@Temp}' temp address. I.e: `A = 5 + $index; D = M[A]`")
                    appendLine("@$address")
                    appendLine("D=M")
                }
            }

            override fun readFromDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting the '${this@Temp}' temp address's value to the value from the D register. I.e: `A = 5 + $index; M[A] = D`.")
                    appendLine("@$address")
                    appendLine("M=D")
                }
            }
        }

        data class Relative(
            /**
             * The address this symbolic address points to is an address. I.e:
             * 1. `[baseSymbolicAddress]`: The name of the symbolic address.
             * 2. `A = [baseSymbolicAddress]`: The value `A` now holds the address to the memory location that holds the base address.
             * 3. `M[A]` = This is the base address.
             */
            private val baseSymbolicAddress: String,
            private val index: Int
        ) : MemorySegment {

            private fun writeFullAddressToARegister(fileWriter: Writer) {
                with(fileWriter) {
                    // We need this convoluted code because we can't directly compute the actual address from a baseSymbolicAddress.
                    appendLine("// Resolving relative address into a full address and setting it into the A register.")
                    appendLine("@$baseSymbolicAddress") // Set the A register to symbolic address pointer address.
                    appendLine("D=M") // Retrieve the symbolic address pointer value (i.e. the base address) and store it in the D register.
                    appendLine("@$index") // Set the A register to the constant index number.
                    appendLine("A=D+A") // Increment sy
                }
            }

            override fun writeToDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting the D register's value to the value of the '$baseSymbolicAddress + $index' address. I.e: `A = $baseSymbolicAddress + $index; D = M[A]`.")
                    writeFullAddressToARegister(fileWriter)
                    appendLine("// Finally, we have the address, set it to the D register.")
                    appendLine("D=M")
                }
            }

            override fun readFromDRegister(fileWriter: Writer) {
                with(fileWriter) {
                    appendLine("// Setting '$baseSymbolicAddress + $index' address's value to the value of the D register.")

                    appendLine("// Temporarily store D's value in R13.")
                    appendLine("@R13")
                    appendLine("M=D")

                    appendLine("// Use the addressing functionality to get the relative address and store the address in R14.")
                    writeFullAddressToARegister(this)
                    appendLine("D=A")
                    appendLine("@R14")
                    appendLine("M=D")

                    appendLine("// Retrieve D from R13.")
                    appendLine("@R13")
                    appendLine("D=M")

                    appendLine("// Retrieve address from R14 and store the value of D in it.")
                    appendLine("@R14")
                    appendLine("A=M")
                    appendLine("M=D")
                }
            }
        }
    }

    enum class BinaryComparisonOperator(
        // The assembly operator code.
        val code: String
    ) {
        GREATER_THAN(code = "JGT"),
        LESS_THAN(code = "JLT"),
        EQUAL(code = "JEQ")
    }

    enum class BinaryArithmeticLogicalOperator(
        // The assembly operator code.
        val code: String
    ) {
        ADD("+"),
        SUBTRACT("-"),
        OR("|"),
        AND("&")
    }

    enum class UnaryArithmeticLogicalOperator(
        // The assembly operator code.
        val code: String
    ) {
        NOT("!"),
        NEGATE("-")
    }

    sealed interface OperatorWriter {

        fun write(codeWriter: CodeWriter)


        data class Unary(val operator: UnaryArithmeticLogicalOperator) : OperatorWriter {

            override fun write(codeWriter: CodeWriter) {
                with(codeWriter) {
                    fileWriter.appendLine("// Execute the '$operator' operation.")

                    fileWriter.appendLine("// Pop a value into the R13 virtual register.")
                    writePop(SymbolicAddress("R13"))

                    fileWriter.appendLine("// Get the single operand from the R13 virtual register.")
                    fileWriter.appendLine("@R13")
                    fileWriter.appendLine("D=M")
                    fileWriter.appendLine("// Apply the '$operator' operator to the operand.")
                    fileWriter.appendLine("D=${operator.code}D")

                    fileWriter.appendLine("// Push the result back into the stack.")
                    fileWriter.appendLine("@R15")
                    fileWriter.appendLine("M=D")
                    writePush(SymbolicAddress("R15"))
                }
            }
        }

        data class BinaryArithmeticLogical(val operator: BinaryArithmeticLogicalOperator) : OperatorWriter {

            override fun write(codeWriter: CodeWriter) {
                with(codeWriter) {
                    fileWriter.appendLine("// Execute the '$operator' operation.")

                    fileWriter.appendLine("// Pop the top two values into the R13 and R14 virtual registers.")
                    writePop(SymbolicAddress("R13"))
                    writePop(SymbolicAddress("R14"))

                    fileWriter.appendLine("// Get the two operands from the R13 and R14 virtual registers.")
                    fileWriter.appendLine("@R14")
                    fileWriter.appendLine("D=M")
                    fileWriter.appendLine("@R13")
                    fileWriter.appendLine("// Apply the '${operator}' operator to the operands.")
                    fileWriter.appendLine("D=D${operator.code}M")

                    fileWriter.appendLine("// Push the result back into the stack.")
                    fileWriter.appendLine("@R16")
                    fileWriter.appendLine("M=D")
                    writePush(SymbolicAddress("R16"))
                }
            }
        }

        data class BinaryComparison(
            val comparisonIndex: Int,
            val operator: BinaryComparisonOperator
        ) : OperatorWriter {

            override fun write(codeWriter: CodeWriter) {
                with(codeWriter) {
                    fileWriter.appendLine("// Execute the '$operator' operation.")

                    fileWriter.appendLine("// Retrieve both operands into the R13 and R14 virtual registers.")
                    writePop(SymbolicAddress("R13"))
                    writePop(SymbolicAddress("R14"))

                    fileWriter.appendLine("// Get the two operands from the R13 and R14 virtual registers and perform a subtraction.")
                    fileWriter.appendLine("@R14")
                    fileWriter.appendLine("D=M")
                    fileWriter.appendLine("@R13")
                    fileWriter.appendLine("D=D-M")
                    fileWriter.appendLine("// If R14 > R13, then D is positive; If R14 = R13, then D is negative; Else, D is 0.")

                    fileWriter.appendLine("@comparison_true_start_$comparisonIndex")
                    fileWriter.appendLine("D;${operator.code}")
                    fileWriter.appendLine("// Goes here if comparison is false.")
                    writePush(Constant(0)) // The condition is false and '0' represents false.
                    fileWriter.appendLine("// Then jump to the end of the true case.")
                    fileWriter.appendLine("@comparison_true_end_$comparisonIndex")
                    fileWriter.appendLine("0;JMP")

                    fileWriter.appendLine("// Label to branch to if comparison is true.")
                    fileWriter.appendLine("(comparison_true_start_$comparisonIndex)")
                    writePush(Constant(1)) // The condition is true and '1' represents true.
                    fileWriter.appendLine("(comparison_true_end_$comparisonIndex)")
                }
            }
        }
    }
}