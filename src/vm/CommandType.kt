package vm

/**
 * The supported VM commands.
 */
enum class CommandType {
    ARITHMETIC_LOGICAL,
    PUSH,
    POP,
    LABEL,
    GOTO,
    IF,
    FUNCTION,
    RETURN,
    CALL,
}