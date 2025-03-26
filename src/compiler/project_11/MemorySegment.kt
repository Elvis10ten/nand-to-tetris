package compiler.project_11

enum class MemorySegment(val id: String) {
    ARGUMENT("argument"),
    LOCAL("local"),
    STATIC("static"),
    THIS("this"),
    THAT("that"),
    POINTER("pointer"),
    CONSTANT("constant"),
    TEMP("temp")
}