.init:
MOV D, 232
JMP .main

;.data_Area:
.UNDEF: DB 255

.fact_n: DB 0
;.codeArea:
.fact:
POP C
POP A
PUSH C
PUSH [.fact_n]
MOV [.fact_n], A
;***********Aqui va el cuerpo, operaciones, etc

MOV A, [.fact_n]
MOV B, 0
CMP A, B

JE .then_if
JNE .else_if
.then_if:
MOV A, 1
JMP fact_end
.else_if:
PUSH [.fact_n]
PUSH 1
PUSH [.fact_n]
POP A
POP B
SUB A, B
PUSH A
CALL .fact
POP A
POP B
MUL B
PUSH A
POP A
JMP fact_end
;***********Aqui Termina el cuerpo, operaciones, etc
fact_end:
POP B
MOV [.fact_n], B
POP C
PUSH A
PUSH C
RET
.main:
PUSH 5
CALL .fact
CALL .print_number
HLT
.print_number:
POP C
POP B
PUSH C
MOV C, 0
.print_number_loop_01:
INC C
MOV A, B
DIV 10

PUSH A
MUL 10
SUB B, A
MOV A, B
POP B
PUSH A
CMP B, 0
JNZ .print_number_loop_01
.print_number_loop_02:

POP B
ADD B, 48
MOV [D], B
INC D
DEC C
CMP C, 0
JNZ .print_number_loop_02
.print_number_exit:
POP C
PUSH .UNDEF
PUSH C
RET

.print_string:
POP C
POP B
PUSH C
.print_string_loop_01:
MOV C, [B]
CMP C, 0
JE .print_string_exit
MOV [D], C

INC D
INC B
JMP .print_string_loop_01
.print_string_exit:
POP C
PUSH .UNDEF
PUSH C
RET
