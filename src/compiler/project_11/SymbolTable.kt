package compiler.project_11

/**
 * This module provides services for building, populating, and using symbol tables that keep track of the symbol properties name,
 * type, kind, and a running index for each kind.
 */
class SymbolTable {

    private val symbols = mutableListOf<Symbol>()

    /**
     * Defines (adds to the table) a new variable of the given [name], [type], and [kind].
     * Assigns to it the index value of that kind, and adds 1 to the index. This is handled by using [getVarCount].
     */
    fun define(
        name: String,
        type: String,
        kind: Kind
    ) {
        symbols += Symbol(
            name,
            type,
            kind,
            getVarCount(kind)
        )
    }

    /**
     * Empties the symbol table, and resets the four indexes to 0.
     * Should be called when starting to compile a subroutine declaration.
     */
    fun reset() {
        symbols.clear()
    }

    /**
     * Returns the number of variables of the given kind already defined in the table.
     */
    fun getVarCount(kind: Kind): Int {
        return symbols.filter { it.kind ==  kind }.size
    }

    /**
     * Returns the symbol object for the named identifier. If the identifier is not found, returns null.
     */
    fun findSymbol(name: String): Symbol? {
        return symbols.find { it.name == name }
    }

    data class Symbol(
        val name: String,
        val type: String,
        val kind: Kind,
        val index: Int
    )

    enum class Kind(val id: String, val memorySegment: MemorySegment) {
        STATIC("static", MemorySegment.STATIC),
        FIELD("field", MemorySegment.THIS),
        ARG("arg", MemorySegment.ARGUMENT),
        VAR("var", MemorySegment.LOCAL);

        companion object {

            fun get(id: String): Kind {
                return entries.single { it.id == id }
            }
        }
    }
}