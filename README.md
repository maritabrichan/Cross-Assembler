# Cross-Assembler

### Project Done by 

Yushan Yang 
Maya McRae
Mohona Mazumdar
Jasmine Lebel
Marita Brichan 
Gechen Ma
Tarun Elango
Aida Kordi
Yu Fei Xiang


## Project Description

The purpose of this project is to construct, using the Java programming language, a cross-assembler that processes Cm Assembly Language, all the while applying agile processes learned in the course. The cross-assembler reads an assembly language source file to generate an in-memory intermediate representation (IR), which is then traversed, outputting an executable file. The assembly language is a source file that consists of low-level programming, a series of binary instructions that has one to one correspondence with machine code, and is represented by the following symbol names: labels, mnemonics and operands. The assembly language source files are provided to the team. 

A cross-assembler is a software system composed of help, verbose, and listing options. It generates a source listing and a label table after pass 1, and it generates errors that do not follow EBNF grammar. In total, an assembler will make two passes. In the first pass, the assembly language code will be traversed by the assembler in order to generate the instructions, such as the mnemonics, labels, operands, etc., that will comprise the machine learning code. Furthermore, the assembler will generate a symbol table, where a label will be related to an offset within a particular instruction, will generate any offset that can be resolved, and will complete the first pass by indicating any offsets that will need to be resolved in pass 2. In the second pass, the assembler will then traverse the sequence of instructions in order to set any non-resolved offsets.

### Please refer to the project report for more information
