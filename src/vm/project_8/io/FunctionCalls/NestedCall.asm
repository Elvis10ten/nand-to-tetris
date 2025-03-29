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
// $ FUNCTION Sys.init 0
(Sys.init)
// $ PUSH constant 4000
// Pushing 'Constant(value=4000)' to the stack.
// Writing '4000' constant to the D register. I.e: `A = 4000; D = A`.
@4000
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP pointer 0
// Popping 'Pointer(index=0)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Pointer(index=0)' pointer address's value to the value from the D register. I.e: `A = 3; M[A] = D`.
@3
M=D

// $ PUSH constant 5000
// Pushing 'Constant(value=5000)' to the stack.
// Writing '5000' constant to the D register. I.e: `A = 5000; D = A`.
@5000
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP pointer 1
// Popping 'Pointer(index=1)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Pointer(index=1)' pointer address's value to the value from the D register. I.e: `A = 4; M[A] = D`.
@4
M=D

// $ CALL Sys.main 0
// Pushing 'Constant(value=Sys.main$ret.0)' to the stack.
// Writing 'Sys.main$ret.0' constant to the D register. I.e: `A = Sys.main$ret.0; D = A`.
@Sys.main$ret.0
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
@Sys.main
0;JMP
(Sys.main$ret.0)
// $ POP temp 1
// Popping 'Temp(index=1)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Temp(index=1)' temp address's value to the value from the D register. I.e: `A = 5 + 1; M[A] = D`.
@6
M=D

// $ LABEL LOOP 
(Sys.init$LOOP)
// $ GOTO LOOP 
@Sys.init$LOOP
0;JMP
// $ FUNCTION Sys.main 5
(Sys.main)
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
// $ PUSH constant 4001
// Pushing 'Constant(value=4001)' to the stack.
// Writing '4001' constant to the D register. I.e: `A = 4001; D = A`.
@4001
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP pointer 0
// Popping 'Pointer(index=0)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Pointer(index=0)' pointer address's value to the value from the D register. I.e: `A = 3; M[A] = D`.
@3
M=D

// $ PUSH constant 5001
// Pushing 'Constant(value=5001)' to the stack.
// Writing '5001' constant to the D register. I.e: `A = 5001; D = A`.
@5001
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP pointer 1
// Popping 'Pointer(index=1)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Pointer(index=1)' pointer address's value to the value from the D register. I.e: `A = 4; M[A] = D`.
@4
M=D

// $ PUSH constant 200
// Pushing 'Constant(value=200)' to the stack.
// Writing '200' constant to the D register. I.e: `A = 200; D = A`.
@200
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP local 1
// Popping 'Relative(baseSymbolicAddress=LCL, index=1)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting 'LCL + 1' address's value to the value of the D register.
// Temporarily store D's value in R13.
@R13
M=D
// Use the addressing functionality to get the relative address and store the address in R14.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@1
A=D+A
D=A
@R14
M=D
// Retrieve D from R13.
@R13
D=M
// Retrieve address from R14 and store the value of D in it.
@R14
A=M
M=D

// $ PUSH constant 40
// Pushing 'Constant(value=40)' to the stack.
// Writing '40' constant to the D register. I.e: `A = 40; D = A`.
@40
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP local 2
// Popping 'Relative(baseSymbolicAddress=LCL, index=2)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting 'LCL + 2' address's value to the value of the D register.
// Temporarily store D's value in R13.
@R13
M=D
// Use the addressing functionality to get the relative address and store the address in R14.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@2
A=D+A
D=A
@R14
M=D
// Retrieve D from R13.
@R13
D=M
// Retrieve address from R14 and store the value of D in it.
@R14
A=M
M=D

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

// $ POP local 3
// Popping 'Relative(baseSymbolicAddress=LCL, index=3)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting 'LCL + 3' address's value to the value of the D register.
// Temporarily store D's value in R13.
@R13
M=D
// Use the addressing functionality to get the relative address and store the address in R14.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@3
A=D+A
D=A
@R14
M=D
// Retrieve D from R13.
@R13
D=M
// Retrieve address from R14 and store the value of D in it.
@R14
A=M
M=D

// $ PUSH constant 123
// Pushing 'Constant(value=123)' to the stack.
// Writing '123' constant to the D register. I.e: `A = 123; D = A`.
@123
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ CALL Sys.add12 1
// Pushing 'Constant(value=Sys.add12$ret.0)' to the stack.
// Writing 'Sys.add12$ret.0' constant to the D register. I.e: `A = Sys.add12$ret.0; D = A`.
@Sys.add12$ret.0
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
@6
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.add12
0;JMP
(Sys.add12$ret.0)
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

// $ PUSH local 2
// Pushing 'Relative(baseSymbolicAddress=LCL, index=2)' to the stack.
// Setting the D register's value to the value of the 'LCL + 2' address. I.e: `A = LCL + 2; D = M[A]`.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@2
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

// $ PUSH local 3
// Pushing 'Relative(baseSymbolicAddress=LCL, index=3)' to the stack.
// Setting the D register's value to the value of the 'LCL + 3' address. I.e: `A = LCL + 3; D = M[A]`.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@3
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

// $ PUSH local 4
// Pushing 'Relative(baseSymbolicAddress=LCL, index=4)' to the stack.
// Setting the D register's value to the value of the 'LCL + 4' address. I.e: `A = LCL + 4; D = M[A]`.
// Resolving relative address into a full address and setting it into the A register.
@LCL
D=M
@4
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
// $ FUNCTION Sys.add12 0
(Sys.add12)
// $ PUSH constant 4002
// Pushing 'Constant(value=4002)' to the stack.
// Writing '4002' constant to the D register. I.e: `A = 4002; D = A`.
@4002
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP pointer 0
// Popping 'Pointer(index=0)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Pointer(index=0)' pointer address's value to the value from the D register. I.e: `A = 3; M[A] = D`.
@3
M=D

// $ PUSH constant 5002
// Pushing 'Constant(value=5002)' to the stack.
// Writing '5002' constant to the D register. I.e: `A = 5002; D = A`.
@5002
D=A
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Write the value of D into the memory location at the top of the stack. I.e: `M[A] = D`
M=D
// Incrementing SP. I.e: `A = SP; M[A] = M[A] + 1`
@SP
M=M+1

// $ POP pointer 1
// Popping 'Pointer(index=1)' from the stack.
// Decrementing SP. I.e: `A = SP; M[A] = M[A] - 1`
@SP
M=M-1
// Obtain the value that the SP pointer points to, and set it to the A register. I.e: `A = M[M[SP]]`
@SP
A=M
// Putting the stack top into the D register. I.e: `A = M[SP]; D = M[A]`
D=M
// Setting the 'Pointer(index=1)' pointer address's value to the value from the D register. I.e: `A = 4; M[A] = D`.
@4
M=D

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

// $ PUSH constant 12
// Pushing 'Constant(value=12)' to the stack.
// Writing '12' constant to the D register. I.e: `A = 12; D = A`.
@12
D=A
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
