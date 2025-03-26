package assembler

/**
 * This module provides services for translating symbolic Hack mnemonics into their binary codes according to the
 * language specifications.
 *
 * All the n-bit codes are returned as strings of "0" and "1" characters. For example:
 * * A call to 'dest("DM")' returns the string "011",
 * * A call to 'comp("A+1")' returns the string "0110111",
 * * A cal to 'jump("JNE")' returns the string "101"
 */
object Code {

    /**
     * Returns the 3-bit binary code of the dest mnemonic.
     */
    fun dest(instruction: Parser.Instruction.Compute): String {
        return when (instruction.dest.uppercase()) {
            "" -> "000" // The value is not stored.
            "M" -> "001" // RAM[A].
            "D" -> "010" // D register.
            "DM" -> "011" // D register and RAM[A].
            "A" -> "100" // A register.
            "AM" -> "101" // A register and RAM[A].
            "AD" -> "110" // A register and D register.
            "ADM" -> "111" // A register, D register, and RAM[A].
            else -> throw UnsupportedOperationException("'${instruction.dest}' is not supported.")
        }
    }

    /**
     * Returns the 7-bit binary code of the comp mnemonic.
     */
    fun comp(instruction: Parser.Instruction.Compute): String {
        return when (instruction.comp.uppercase()) {
            "0" -> "0101010"
            "1" -> "0111111"
            "-1" -> "0111010"
            "D" -> "0001100"
            "A" -> "0110000"
            "M" -> "1110000"
            "!D" -> "0001101"
            "!A" -> "0110001"
            "!M" -> "1110001"
            "-D" -> "0001111"
            "-A" -> "0110011"
            "-M" -> "1110011"
            "D+1" -> "0011111"
            "A+1" -> "0110111"
            "M+1" -> "1110111"
            "D-1" -> "0001110"
            "A-1" -> "0110010"
            "M-1" -> "1110010"
            "D+A" -> "0000010"
            "D+M" -> "1000010"
            "D-A" -> "0010011"
            "D-M" -> "1010011"
            "A-D" -> "0000111"
            "M-D" -> "1000111"
            "D&A" -> "0000000"
            "D&M" -> "1000000"
            "D|A" -> "0010101"
            "D|M" -> "1010101"
            else -> throw UnsupportedOperationException("'${instruction.comp}' is not supported.")
        }
    }

    /**
     * Returns the 3-bit binary code of the jump mnemonic.
     */
    fun jump(instruction: Parser.Instruction.Compute): String {
        return when (instruction.jump.uppercase()) {
            "" -> "000" // No jump.
            "JGT" -> "001" // If comp > 0; jump.
            "JEQ" -> "010" // If comp == 0; jump.
            "JGE" -> "011" // If comp >= 0; jump.
            "JLT" -> "100" // If comp < 0; jump.
            "JNE" -> "101" // If comp != 0; jump.
            "JLE" -> "110" // If comp <= 0; jump.
            "JMP" -> "111" // Unconditional jump.
            else -> throw UnsupportedOperationException("'${instruction.jump}' is not supported.")
        }
    }
}