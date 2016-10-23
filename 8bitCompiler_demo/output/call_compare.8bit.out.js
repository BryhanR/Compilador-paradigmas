.init:
MOV D, 232
JMP .main


;.data_Area:
.UNDEF: DB 255
.boolean_true: DB "TRUE" 
	DB 0
.boolean_false: DB "FALSE"
	DB 0
;compare_Data_Area
compare_Data_Area:
.compare_x: DB 0
.compare_y: DB 0

;main_Data_Area
main_Data_Area:
main_string_1: DB "10>5="
	 DB 0
main_string_2: DB " 5>10="
	 DB 0


;.codeArea:
.compare:
POP C
POP A
POP B
PUSH C
PUSH [.compare_x]
PUSH [.compare_y]
MOV [.compare_x], B
MOV [.compare_y], A
;***********Aqui va el cuerpo, operaciones, etc

MOV A, [.compare_x]
MOV B, [.compare_y]

CMP A, B

JA .then_if
JBE .else_if
.then_if:
MOV A, 1
JMP compare_end
.else_if:
MOV A, 0
JMP compare_end
;***********Aqui Termina el cuerpo, operaciones, etc
compare_end:
POP B
MOV [.compare_x], B
POP B
MOV [.compare_y], B
POP C
PUSH A
PUSH C
RET
.main:
PUSH main_string_1
CALL .print_string
POP A

PUSH 10
PUSH 5
CALL .compare
POP A

PUSH A
CALL .print_boolean
POP A

PUSH main_string_2
CALL .print_string
POP A

PUSH 5
PUSH 10
CALL .compare
POP A

PUSH A
CALL .print_boolean
POP A

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
.print_boolean:
POP C
POP A
PUSH C
CMP A, 1
JE .print_boolean_true
JNE .print_boolean_false
.print_boolean_true:
 PUSH .boolean_true
JMP .end_print_boolean
.print_boolean_false:
PUSH .boolean_false
JMP .end_print_boolean
.end_print_boolean:
CALL .print_string
POP A
POP C
PUSH .UNDEF
PUSH C
RET
