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
main_string_1: DB "JAJA CABRO"
	 DB 0
main_string_2: DB " "
	 DB 0


;.codeArea:
.main:
PUSH main_string_1

POP A
PS A
PUSH main_string_2

POP A
PS A
PUSH 10

POP A
PN A
PUSH 1

POP A
PB A
HLT
