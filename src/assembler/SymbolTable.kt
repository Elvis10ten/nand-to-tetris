package assembler

/**
 * Since Hack instructions can contain symbolic references, the assembly process must resolve them into actual
 * addresses.
 *
 * The assembler deals with this task using a symbol table, designed to create and maintain the correspondence
 * between symbols and their meaning (in Hack's case, RAM and ROM addresses).
 */
class SymbolTable {

    private val table = mutableMapOf<String, Int>()

    init {
        table["R0"] = 0
        table["R1"] = 1
        table["R2"] = 2
        table["R3"] = 3
        table["R4"] = 4
        table["R5"] = 5
        table["R6"] = 6
        table["R7"] = 7
        table["R8"] = 8
        table["R9"] = 9
        table["R10"] = 10
        table["R11"] = 11
        table["R12"] = 12
        table["R13"] = 13
        table["R14"] = 14
        table["R15"] = 15

        table["SP"] = 0
        table["LCL"] = 1
        table["ARG"] = 2
        table["THIS"] = 3
        table["THAT"] = 4

        table["SCREEN"] = 16384
        table["KBD"] = 24576
    }

    /**
     * Adds <symbol, address> to the table.
     */
    fun addEntry(symbol: String, address: Int) {
        table[symbol] = address
    }

    /**
     * Doest the symbol table contain the given [symbol].
     */
    fun contains(symbol: String): Boolean {
        return table.contains(symbol)
    }

    /**
     * Returns the address associated with the [symbol].
     * Must only be called if [contains] returns true.
     */
    fun getAddress(symbol: String): Int {
        return table.getValue(symbol)
    }
}