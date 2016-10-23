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
.fact_f: DB 0
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

PUSH 1
POP A
MOV [.fact_f], A
.while:
MOV A, [.fact_n]
MOV B, 0
CMP A, B

JA .continue_while

JBE .end_while
.continue_while:
; Si hay statements
PUSH [.fact_f]
PUSH [.fact_n]
POP A
POP B
MUL B
PUSH A

POP A
MOV [.fact_f], A
PUSH 1
PUSH [.fact_n]
POP A
POP B
SUB A, B
PUSH A

POP A
MOV [.fact_n], A
MOV A, [.fact_n]
MOV B, 0
CMP A, B

JA .continue_while

JBE .end_while

.end_while:
MOV A, [.fact_f]
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
