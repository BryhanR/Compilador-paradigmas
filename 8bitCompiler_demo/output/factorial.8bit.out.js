.init:
MOV D, 232
JMP .main


;.data_Area:
.UNDEF: DB 255
.boolean_true: DB "TRUE" 
	DB 0
.boolean_false: DB "FALSE"
	DB 0
;fact_Data_Area
fact_Data_Area:
.fact_n: DB 0

;main_Data_Area
main_Data_Area:
main_string_1: DB "fact(5)="
	 DB 0


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

PUSH A
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
PUSH main_string_1

POP A
PS A
PUSH 5
CALL .fact
POP A

PUSH A

POP A
PN A
HLT
