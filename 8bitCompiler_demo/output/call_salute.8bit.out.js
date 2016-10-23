.init:
MOV D, 232
JMP .main


;.data_Area:
.UNDEF: DB 255
.boolean_true: DB "TRUE" 
	DB 0
.boolean_false: DB "FALSE"
	DB 0
;main_Data_Area
main_Data_Area:
main_string_1: DB "Hello 666!"
	 DB 0

;salute_Data_Area
salute_Data_Area:
.salute_s: DB 0


;.codeArea:
.salute:
POP C
POP A
PUSH C
PUSH [.salute_s]
MOV [.salute_s], A
;***********Aqui va el cuerpo, operaciones, etc

PUSH [.salute_s]

POP A
PS A
;***********Aqui Termina el cuerpo, operaciones, etc
salute_end:
POP B
MOV [.salute_s], B
POP C
PUSH A
PUSH C
RET
.main:
PUSH main_string_1
CALL .salute
POP A

HLT
