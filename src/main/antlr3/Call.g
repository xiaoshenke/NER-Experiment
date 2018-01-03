
grammar Call;

options {
         output=AST;
         //ASTLabelType=CommonTree;
 }

import CommonLexer;
@header{
package wuxian.me.ner.parser;
}

call : ID LEFT params RIGHT -> ^(ID params) | ID WS* params -> ^(ID params) ;

//call : ^(TREE_BEGIN element+) ;

params : param ((',' | WS*) param)* ;

param : ID '=' value | value '=' ID ;

value : INT | FLOAT | CHAR | STRING ;
