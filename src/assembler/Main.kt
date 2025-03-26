package assembler

import java.io.File

fun main() {
    val rootDir = File("src/assembler/io/")
    Assembler.assemble(File(rootDir, "add"))
    Assembler.assemble(File(rootDir, "max"))
    Assembler.assemble(File(rootDir, "pong"))
    Assembler.assemble(File(rootDir, "rect"))
}
