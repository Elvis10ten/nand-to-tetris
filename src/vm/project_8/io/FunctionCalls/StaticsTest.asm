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
// $ FUNCTION Class1.set 0
(Class1.set)
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

// $ POP static 0
// Popping 'SymbolicAddress(name=StaticsTest.0)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'StaticsTest.0' symbolic address's value to the value from the D register. I.e: `A = StaticsTest.0; M[A] = D`.
@StaticsTest.0
M=D

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

// $ POP static 1
// Popping 'SymbolicAddress(name=StaticsTest.1)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'StaticsTest.1' symbolic address's value to the value from the D register. I.e: `A = StaticsTest.1; M[A] = D`.
@StaticsTest.1
M=D

// $ PUSH constant 0
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
// $ FUNCTION Class1.get 0
(Class1.get)
// $ PUSH static 0
// Pushing 'SymbolicAddress(name=StaticsTest.0)' to the stack.
// Setting the D register's value to the value of the 'StaticsTest.0' symbolic address. I.e: `A = StaticsTest.0; D = M[A]`
@StaticsTest.0
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ PUSH static 1
// Pushing 'SymbolicAddress(name=StaticsTest.1)' to the stack.
// Setting the D register's value to the value of the 'StaticsTest.1' symbolic address. I.e: `A = StaticsTest.1; D = M[A]`
@StaticsTest.1
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
// $ FUNCTION Sys.init 0
(Sys.init)
// $ PUSH constant 6
// Pushing 'Constant(value=6)' to the stack.
// Writing '6' constant to the D register. I.e: `A = 6; D = A`.
@6
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ PUSH constant 8
// Pushing 'Constant(value=8)' to the stack.
// Writing '8' constant to the D register. I.e: `A = 8; D = A`.
@8
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ CALL Class1.set 2
// Pushing 'Constant(value=Class1.set$ret.0)' to the stack.
// Writing 'Class1.set$ret.0' constant to the D register. I.e: `A = Class1.set$ret.0; D = A`.
@Class1.set$ret.0
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
@7
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(Class1.set$ret.0)
// $ POP temp 0
// Popping 'Temp(index=0)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Temp(index=0)' temp address's value to the value from the D register. I.e: `A = 5 + 0; M[A] = D`.
@5
M=D

// $ PUSH constant 23
// Pushing 'Constant(value=23)' to the stack.
// Writing '23' constant to the D register. I.e: `A = 23; D = A`.
@23
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ PUSH constant 15
// Pushing 'Constant(value=15)' to the stack.
// Writing '15' constant to the D register. I.e: `A = 15; D = A`.
@15
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ CALL Class2.set 2
// Pushing 'Constant(value=Class2.set$ret.0)' to the stack.
// Writing 'Class2.set$ret.0' constant to the D register. I.e: `A = Class2.set$ret.0; D = A`.
@Class2.set$ret.0
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
@7
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.set
0;JMP
(Class2.set$ret.0)
// $ POP temp 0
// Popping 'Temp(index=0)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Temp(index=0)' temp address's value to the value from the D register. I.e: `A = 5 + 0; M[A] = D`.
@5
M=D

// $ CALL Class1.get 0
// Pushing 'Constant(value=Class1.get$ret.0)' to the stack.
// Writing 'Class1.get$ret.0' constant to the D register. I.e: `A = Class1.get$ret.0; D = A`.
@Class1.get$ret.0
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
@Class1.get
0;JMP
(Class1.get$ret.0)
// $ CALL Class2.get 0
// Pushing 'Constant(value=Class2.get$ret.0)' to the stack.
// Writing 'Class2.get$ret.0' constant to the D register. I.e: `A = Class2.get$ret.0; D = A`.
@Class2.get$ret.0
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
@Class2.get
0;JMP
(Class2.get$ret.0)
// $ LABEL END 
(Sys.init$END)
// $ GOTO END 
@Sys.init$END
0;JMP
// $ FUNCTION Class2.set 0
(Class2.set)
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

// $ POP static 0
// Popping 'SymbolicAddress(name=StaticsTest.0)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'StaticsTest.0' symbolic address's value to the value from the D register. I.e: `A = StaticsTest.0; M[A] = D`.
@StaticsTest.0
M=D

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

// $ POP static 1
// Popping 'SymbolicAddress(name=StaticsTest.1)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'StaticsTest.1' symbolic address's value to the value from the D register. I.e: `A = StaticsTest.1; M[A] = D`.
@StaticsTest.1
M=D

// $ PUSH constant 0
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
// $ FUNCTION Class2.get 0
(Class2.get)
// $ PUSH static 0
// Pushing 'SymbolicAddress(name=StaticsTest.0)' to the stack.
// Setting the D register's value to the value of the 'StaticsTest.0' symbolic address. I.e: `A = StaticsTest.0; D = M[A]`
@StaticsTest.0
D=M
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ PUSH static 1
// Pushing 'SymbolicAddress(name=StaticsTest.1)' to the stack.
// Setting the D register's value to the value of the 'StaticsTest.1' symbolic address. I.e: `A = StaticsTest.1; D = M[A]`
@StaticsTest.1
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
