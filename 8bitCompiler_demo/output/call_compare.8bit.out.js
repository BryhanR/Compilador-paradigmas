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

POP A
PS A
PUSH 10
PUSH 5
CALL .compare
POP A

PUSH A

POP A
PB A
PUSH main_string_2

POP A
PS A
PUSH 5
PUSH 10
CALL .compare
POP A

PUSH A

POP A
PB A
HLT
