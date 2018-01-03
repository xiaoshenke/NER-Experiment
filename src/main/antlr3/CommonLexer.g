/** Not really useful by itself; a library of rules to import into
 *  another grammar.
 */
lexer grammar CommonLexer;

@header{
package wuxian.me.ner.parser;
}

ID : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')* ;

INT : '0'..'9'+ ;

FLOAT:	INT '.' INT?
	|	'.' INT
	;

CHAR:   '\'' ( ESC | ~('\''|'\\') ) '\''
    ;

STRING
    :  '"' ( ESC | ~('\\'|'"') )* '"'
    ;

fragment
ESC :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    ;


WS : (' '|'\t'|'\r'|'\n')+ {$channel=HIDDEN;} ;

LEFT : '(' ;
RIGHT : ')' ;