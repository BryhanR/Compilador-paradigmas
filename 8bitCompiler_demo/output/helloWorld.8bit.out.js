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
main_string_1: DB "Hello World!"
	 DB 0


;.codeArea:
.main:
PUSH main_string_1

POP A
PS A
HLT
