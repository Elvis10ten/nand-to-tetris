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
    private var curFunctionFullyQualifiedName = ""

    /**
     * Let `foo` be a function within the file `Xxx.vm`. The handling of each `call` command within `foo`'s code generates,
     * and injects into the assembly code stream, a symbol `Xxx.foo$ret.i`, where `i` is a running integer (one such symbol
     * is generated for each `call command with `foo`).
     *
     * This symbol is used to mark the return address within the caller's code.
     * In subsequent assembly process, the assembler translates this symbol into the physical memory address of the
     * command immediately following the `call` command.
     */
    private val functionReturnAddressRunningInteger = mutableMapOf<String, Int>()

    init {
        // Clears the file content.
        fileWriter.write("")
        writeBootstrap()
    }

    /**
     * Writes the assembly instructions that effect the bootstrap code that starts the program's execution.
     * This code must be placed at the beginning of the generated output file.
     *
     * The standard VM mapping on the Hack platform stipulates that the stack be mapped on the host RAM from address 256
     * onward, and that the first VM function that should start executing is the OS function `Sys.init`.
     *
     * To make `Sys.init` the first function to execute, recall that the Hack computer is wired in such a way that upon reset,
     * it will fetch and execute the instruction located in ROM address 0.
     * Thus, if we want the computer to execute a predetermined code segment when it boots up, we can put this,
     * code in the Hack computer’s instruction memory, starting at address 0.
     *
     * In particular, the VM translator will not only translate the VM commands (push, pop, add, and so on) into assembly
     * instructions—it will also generate assembly code that realizes an envelope in which the program runs.
     */
    private fun writeBootstrap() {
        with(fileWriter) {
            // Bootstrap SP.
            appendLine("@256")
            appendLine("D=A")
            appendLine("@SP")
            appendLine("M=D")

            // Bootstrap `Sys.init`.
            writeCall(
                fullyQualifiedFunctionName = "Sys.init",
                nArgs = 0
            )
        }
    }

    fun addTranslationLogs(type: CommandType, arg1: String, arg2: String) {
        fileWriter.appendLine("// Translating \"$type $arg1 $arg2\"")
    }

    /**
     * Informs that the translation of a new VM file has started.
     */
    fun setFileName(file: File) {
        //curFileName = file.nameWithoutExtension
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

    /**
     * Let foo be a function within the file `Xxx.vm`. The handling of each `label bar` command within `foo` generates,
     * and injects into the assembly code stream, the symbol `Xxx.foo$bar`.
     *
     * When translating `goto bar` and `if-goto bar` commands (within `foo`) into assembly, the label `Xxx.foo$bar`
     * must be used instead of bar.
     */
    private fun getFullLabelName(labelName: String) = "$curFunctionFullyQualifiedName$${labelName}"

    /**
     * Write assembly code that effects the label command.
     *
     * Labels are used to label the current location in the function's code. Only labeled locations can be jumped to.
     * The scope of the label is the function in which it is defined.
     * A `label` command can be before or after the `goto` commands that refer to it.
     */
    fun writeLabel(name: String) {
        fileWriter.appendLine("(${getFullLabelName(name)})")
    }

    /**
     * Write assembly code that effects the goto command.
     *
     * Goto effects an unconditional goto operation, causing execution to continue from the location marked by the label.
     */
    fun writeGoto(labelName: String) {
        with(fileWriter) {
            appendLine("@${getFullLabelName(labelName)}")
            appendLine("0;JMP")
        }
    }

    /**
     * Write assembly code that effects the if-goto command.
     */
    fun writeIf(labelName: String) {
        writePop(SymbolicAddress("R13"))

        with(fileWriter) {
            appendLine("@R13")
            appendLine("D=M")
            appendLine("@${getFullLabelName(labelName)}")
            appendLine("D;JNE")
        }
    }

    /**
     * Write assembly code that effects the function command.
     *
     * The handling of each function `foo` command within the file `Xxx.vm` generates, and injects into the assembly
     * code stream, a symbol `Xxx.foo` that labels the entry-point to the function's code.
     *
     * In the subsequent assembly process, the assembler translates this symbol into the physical address where the
     * function starts.
     */
    fun writeFunction(fullyQualifiedFunctionName: String, nVars: Int) {
        curFunctionFullyQualifiedName = fullyQualifiedFunctionName

        with(fileWriter) {
            // Injects a function entry label into the code.
            appendLine("($fullyQualifiedFunctionName)")

            // This works in tandem with the code that sets the "LCL" virtual register in [writeCall].
            // The net effect of this is that all the local variables are initialized to zeroes.
            repeat(nVars) {
                writePush(Constant(0))
            }
        }
    }

    private fun generateReturnAddress(fullyQualifiedFunctionName: String): SymbolicAddress {
        val returnAddressPrefix = "$fullyQualifiedFunctionName\$ret."

        val runningInteger = functionReturnAddressRunningInteger.getOrPut(returnAddressPrefix) { 0 }
        functionReturnAddressRunningInteger[returnAddressPrefix] = runningInteger + 1

        return SymbolicAddress("$returnAddressPrefix$runningInteger")
    }

    /**
     * Write assembly code that effects the call command.
     */
    fun writeCall(fullyQualifiedFunctionName: String, nArgs: Int) {
        // First, no need to push the callee's arguments into the stack here.
        // It is expected that whoever calls the function have pushed [nArgs] argument values onto the stack.
        // I.e. SP to (SP - nArgs) all contains the function arguments.

        // Next, generate a return address and pushes it to the stack.
        val returnAddress = generateReturnAddress(fullyQualifiedFunctionName)
        writePush(Constant(returnAddress.name))

        // Next, saves the caller's memory segments so they can be returned to when the callee completes.
        writePush(SymbolicAddress("LCL"))
        writePush(SymbolicAddress("ARG"))
        writePush(SymbolicAddress("THIS"))
        writePush(SymbolicAddress("THAT"))

        with(fileWriter) {
            // Repositions "ARG" to "SP - 5 - nArgs". SP refers to the stack pointer address at this point.
            // Subtracting 5 from SP moves the address to the top of the caller's frame (the return address pushed on to the stack).
            // Subtracting nArgs, positions the address to the first argument of the callee pushed on the stack.
            // Setting this to the "ARG" virtual register has the net effect of setting the argument memory segment for the callee.
            // This is because "ARG" specifies the base address of the memory segment.
            // So accessing "argument 0" accesses the first callee argument pushed to the stack, and so on.
            appendLine("@SP")
            appendLine("D=M")
            appendLine("@${5 + nArgs}") // "SP - 5 - nArgs = SP - (5 + nArgs).
            appendLine("D=D-A")
            appendLine("@ARG")
            appendLine("M=D")

            // Repositions "LCL" to "SP".
            // This works in tandem with the [writeFunction] which initializes all the local variables to zeroes.
            // The idea behind this repositioning is the same as above for "ARG".
            // By setting "LCL", we set the local memory segment for the callee.
            appendLine("@SP")
            appendLine("D=M")
            appendLine("@LCL")
            appendLine("M=D")

            // Goto function. This transfers control flow to the callee.
            appendLine("@${fullyQualifiedFunctionName}")
            appendLine("0;JMP")

            // Injects the return address label into the code.
            appendLine("(${returnAddress.name})")
        }
    }

    /**
     * Write assembly code that effects the return command.
     */
    fun writeReturn() {
        with(fileWriter) {
            // Repositions the return value for the caller.
            // The effect of this is that the value returned by the calleee will be moved to the position on the stack
            // that used to hold the callee's "argument 0".
            writePop(SymbolicAddress("R13"))
            appendLine("@R13")
            appendLine("D=M") // We want R13's value.
            appendLine("@ARG")
            appendLine("A=M")
            appendLine("M=D")

            // Repositions SP for the caller.
            // This places the SP for the caller one more beyond what used to be the callee's "argument 0".
            // When combined with the repositioning of the return value above, the net effect is that the callee's
            // return value is placed at the top of the stack for the caller.
            appendLine("@ARG")
            appendLine("D=M")
            appendLine("@1")
            appendLine("D=D+A")
            appendLine("@SP")
            appendLine("M=D")

            // Recall from [writeCall] that the virtual register "LCL" points to the position just below the caller's frame (on the stack).
            // We exploit this knowledge with some arithmetic to restore the caller's frame.
            // Here, we only want to get the caller's return address.
            appendLine("@LCL")
            appendLine("D=M")
            appendLine("@5")
            appendLine("A=D-A") // Gives us the position on the stack of the return address.
            appendLine("D=M") // Get the value of the return address.
            appendLine("@R13")
            appendLine("M=D") // Puts the return address in a temporary variable.

            restoreMemorySegmentFrame(virtualRegisterName = "THAT", positionFromBehind = 1)
            restoreMemorySegmentFrame(virtualRegisterName = "THIS", positionFromBehind = 2)
            restoreMemorySegmentFrame(virtualRegisterName = "ARG", positionFromBehind = 3)
            // Must come last, since function is based on inner knowledge of how "LCL" was set.
            restoreMemorySegmentFrame(virtualRegisterName = "LCL", positionFromBehind = 4)

            // Goto the return address.
            appendLine("@R13")
            appendLine("A=M")
            appendLine("0;JMP")
        }
    }

    private fun restoreMemorySegmentFrame(virtualRegisterName: String, positionFromBehind: Int) {
        with(fileWriter) {
            // Recall from [writeCall] that the virtual register "LCL" points to the position just below the caller's frame (on the stack).
            // We exploit this knowledge with some arithmetic to restore the caller's frame.
            appendLine("@LCL")
            appendLine("D=M")

            // Restores [virtualRegisterName] for the caller.
            // This allows all relative memory segment to continue working with the caller's context.
            appendLine("@${positionFromBehind}")
            appendLine("A=D-A") // Gives us the position on the stack for the [virtualRegisterName].
            appendLine("D=M") // Get the value at that position on the stack.
            appendLine("@${virtualRegisterName}")
            appendLine("M=D")
        }
    }

    fun close() {
        fileWriter.flush()
        fileWriter.close()
    }

    sealed interface MemorySegment {

        fun writeToDRegister(fileWriter: Writer)

        fun readFromDRegister(fileWriter: Writer)

        data class Constant internal constructor(
            /**
             * A "constant string" is not a valid part of the Hack VM language.
             * However, this "hack" lets us push the value of a symbolic address to the stack.
             * A [SymbolicAddress] can't be used because its value is what is pushed to the stack.
             * However, sometimes we are interested in the address.
             */
            val value: String
        ) : MemorySegment {

            constructor(value: Int): this(value.toString())

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
                    fileWriter.appendLine("@R15")
                    fileWriter.appendLine("M=D")
                    writePush(SymbolicAddress("R15"))
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
                    // Save "-1" into R15, since it can't be expressed directly in assembly.
                    fileWriter.appendLine("D=0")
                    fileWriter.appendLine("D=D-1")
                    fileWriter.appendLine("@R15")
                    fileWriter.appendLine("M=D")
                    writePush(SymbolicAddress("R15")) // The condition is true and '-1' represents true.
                    fileWriter.appendLine("(comparison_true_end_$comparisonIndex)")
                }
            }
        }
    }
}