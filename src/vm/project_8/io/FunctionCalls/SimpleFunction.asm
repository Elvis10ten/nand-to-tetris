@256
D=A
@SP
M=D
// Pushing 'Constant(value=Sys.init$ret.0)' to the stack.
// Writing 'Sys.init$ret.0' constant to the D register. I.e: `A = Sys.init$ret.0; D = A`.
@Sys.init$ret.0
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1
// Pushing 'SymbolicAddress(name=LCL)' to the stack.
// Setting the D register's value to the value of the 'LCL' symbolic address. I.e: `A = LCL; D = M[A]`
@LCL
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1
// Pushing 'SymbolicAddress(name=ARG)' to the stack.
// Setting the D register's value to the value of the 'ARG' symbolic address. I.e: `A = ARG; D = M[A]`
@ARG
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1
// Pushing 'SymbolicAddress(name=THIS)' to the stack.
// Setting the D register's value to the value of the 'THIS' symbolic address. I.e: `A = THIS; D = M[A]`
@THIS
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1
// Pushing 'SymbolicAddress(name=THAT)' to the stack.
// Setting the D register's value to the value of the 'THAT' symbolic address. I.e: `A = THAT; D = M[A]`
@THAT
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1
@SP
D=M
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(Sys.init$ret.0)
// $ FUNCTION SimpleFunction.test 2
(SimpleFunction.test)
// Pushing 'Constant(value=0)' to the stack.
// Writing '0' constant to the D register. I.e: `A = 0; D = A`.
@0
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1
// Pushing 'Constant(value=0)' to the stack.
// Writing '0' constant to the D register. I.e: `A = 0; D = A`.
@0
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1
// $ PUSH local 0
// Pushing 'Relative(baseSymbolicAddress=LCL, index=0)' to the stack.
// Setting the D register's value to the value of the 'LCL + 0' address. I.e: `A = LCL + 0; D = M[A]`.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@0
A=D+A
// Finally, we have the address, set it to the D register.
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ PUSH local 1
// Pushing 'Relative(baseSymbolicAddress=LCL, index=1)' to the stack.
// Setting the D register's value to the value of the 'LCL + 1' address. I.e: `A = LCL + 1; D = M[A]`.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@1
A=D+A
// Finally, we have the address, set it to the D register.
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ ARITHMETIC_LOGICAL add 
// Execute the 'ADD' operation.
// Pop the top two values into the R13 and R14 virtual registers.
// Popping 'SymbolicAddress(name=R13)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R13' symbolic address's value to the value from the D register. I.e: `A = R13; M[A] = D`.
@R13
M=D
// Popping 'SymbolicAddress(name=R14)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R14' symbolic address's value to the value from the D register. I.e: `A = R14; M[A] = D`.
@R14
M=D
// Get the two operands from the R13 and R14 virtual registers.
@R14
D=M
@R13
// Apply the 'ADD' operator to the operands.
D=D+M
// Push the result back into the stack.
@R15
M=D
// Pushing 'SymbolicAddress(name=R15)' to the stack.
// Setting the D register's value to the value of the 'R15' symbolic address. I.e: `A = R15; D = M[A]`
@R15
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ ARITHMETIC_LOGICAL not 
// Execute the 'NOT' operation.
// Pop a value into the R13 virtual register.
// Popping 'SymbolicAddress(name=R13)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R13' symbolic address's value to the value from the D register. I.e: `A = R13; M[A] = D`.
@R13
M=D
// Get the single operand from the R13 virtual register.
@R13
D=M
// Apply the 'NOT' operator to the operand.
D=!D
// Push the result back into the stack.
@R15
M=D
// Pushing 'SymbolicAddress(name=R15)' to the stack.
// Setting the D register's value to the value of the 'R15' symbolic address. I.e: `A = R15; D = M[A]`
@R15
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ PUSH argument 0
// Pushing 'Relative(baseSymbolicAddress=ARG, index=0)' to the stack.
// Setting the D register's value to the value of the 'ARG + 0' address. I.e: `A = ARG + 0; D = M[A]`.
// Resolving relative address into a full address and setting it into the A register.
@ARG
D=M
@0
A=D+A
// Finally, we have the address, set it to the D register.
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ ARITHMETIC_LOGICAL add 
// Execute the 'ADD' operation.
// Pop the top two values into the R13 and R14 virtual registers.
// Popping 'SymbolicAddress(name=R13)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R13' symbolic address's value to the value from the D register. I.e: `A = R13; M[A] = D`.
@R13
M=D
// Popping 'SymbolicAddress(name=R14)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R14' symbolic address's value to the value from the D register. I.e: `A = R14; M[A] = D`.
@R14
M=D
// Get the two operands from the R13 and R14 virtual registers.
@R14
D=M
@R13
// Apply the 'ADD' operator to the operands.
D=D+M
// Push the result back into the stack.
@R15
M=D
// Pushing 'SymbolicAddress(name=R15)' to the stack.
// Setting the D register's value to the value of the 'R15' symbolic address. I.e: `A = R15; D = M[A]`
@R15
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ PUSH argument 1
// Pushing 'Relative(baseSymbolicAddress=ARG, index=1)' to the stack.
// Setting the D register's value to the value of the 'ARG + 1' address. I.e: `A = ARG + 1; D = M[A]`.
// Resolving relative address into a full address and setting it into the A register.
@ARG
D=M
@1
A=D+A
// Finally, we have the address, set it to the D register.
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ ARITHMETIC_LOGICAL sub 
// Execute the 'SUBTRACT' operation.
// Pop the top two values into the R13 and R14 virtual registers.
// Popping 'SymbolicAddress(name=R13)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R13' symbolic address's value to the value from the D register. I.e: `A = R13; M[A] = D`.
@R13
M=D
// Popping 'SymbolicAddress(name=R14)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R14' symbolic address's value to the value from the D register. I.e: `A = R14; M[A] = D`.
@R14
M=D
// Get the two operands from the R13 and R14 virtual registers.
@R14
D=M
@R13
// Apply the 'SUBTRACT' operator to the operands.
D=D-M
// Push the result back into the stack.
@R15
M=D
// Pushing 'SymbolicAddress(name=R15)' to the stack.
// Setting the D register's value to the value of the 'R15' symbolic address. I.e: `A = R15; D = M[A]`
@R15
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ RETURN  
@LCL
D=M
// Popping 'SymbolicAddress(name=R13)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'R13' symbolic address's value to the value from the D register. I.e: `A = R13; M[A] = D`.
@R13
M=D
@R13
D=M
@ARG
A=M
M=D
@ARG
D=M
@1
D=D+A
@SP
M=D
@LCL
D=M
@5
A=D-A
D=M
@R13
M=D
@LCL
D=M
@1
A=D-A
D=M
@THAT
M=D
@LCL
D=M
@2
A=D-A
D=M
@THIS
M=D
@LCL
D=M
@3
A=D-A
D=M
@ARG
M=D
@LCL
D=M
@4
A=D-A
D=M
@LCL
M=D
@R13
A=M
0;JMP
