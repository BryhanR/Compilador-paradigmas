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

;salute_Data_Area
salute_Data_Area:
salute_string_1: DB "Hello 666!"
	 DB 0


;.codeArea:
.salute:
POP C
PUSH C
;***********Aqui va el cuerpo, operaciones, etc

MOV A, salute_string_1
JMP salute_end
;***********Aqui Termina el cuerpo, operaciones, etc
salute_end:
POP C
PUSH A
PUSH C
RET
.main:
CALL .salute
POP A

PUSH A

POP A
PS A
HLT
