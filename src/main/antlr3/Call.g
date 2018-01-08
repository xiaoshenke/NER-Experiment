
grammar Call;

options {
         output=AST;
         //ASTLabelType=CommonTree;
 }

import CommonLexer;
@header{
package wuxian.me.ner.parser;
}

//Todo: support 0 argument call
call : ID LEFT params RIGHT -> ^(ID params) | ID WS* params -> ^(ID params) | ID ;

params : param ((',' | WS*) param)* ;

param : ID '=' value -> ^(ID value) | value '=' ID -> ^(ID value) ;

fragment
value : INT | FLOAT | CHAR | STRING ;
