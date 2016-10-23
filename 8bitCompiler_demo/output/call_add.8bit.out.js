.init:
MOV D, 232
JMP .main


;.data_Area:
.UNDEF: DB 255
.boolean_true: DB "TRUE" 
	DB 0
.boolean_false: DB "FALSE"
	DB 0
;add_Data_Area
add_Data_Area:
.add_x: DB 0
.add_y: DB 0

;main_Data_Area
main_Data_Area:
main_string_1: DB "10+56="
	 DB 0


;.codeArea:
.add:
POP C
POP A
POP B
PUSH C
PUSH [.add_x]
PUSH [.add_y]
MOV [.add_x], B
MOV [.add_y], A
;***********Aqui va el cuerpo, operaciones, etc

PUSH [.add_x]
PUSH [.add_y]
POP A
POP B
ADD A, B
PUSH A
POP A
JMP add_end
;***********Aqui Termina el cuerpo, operaciones, etc
add_end:
POP B
MOV [.add_x], B
POP B
MOV [.add_y], B
POP C
PUSH A
PUSH C
RET
.main:
PUSH main_string_1

POP A
PS A
PUSH 10
PUSH 56
CALL .add
POP A

PUSH A

POP A
PN A
HLT
