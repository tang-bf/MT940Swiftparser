grammar SwiftMt940;
@header
{
    package de.oette.swiftparser;
}

@members
{
    //Position helps to locate the current Transaction in the block
    private int transactionPosition = 0;
}

root: block+ endToken;

endToken : '-';
block :    block20
         | block25
         | block60F
         | transactionBlock
         | otherblock
         | block62F
         | block62M
         | block64
         | block65;

block20 : ':20:' content;
block25 : ':25:' fullAccount;
fullAccount: targetAccount LETTER? LETTER? LETTER?;
targetAccount : targetBIC OTHER_CHAR NUMBER+;
targetBIC : (NUMBER | LETTER)+;

transactionBlock returns [int position ]: block61 block86
{
        $position = transactionPosition;
        transactionPosition++;
};


block60F : ':60F:' saldoData;
block62F : ':62F:' saldoData;
block62M : ':62M:' saldoData;
block64 : ':64:' saldoData;
block65 : ':65:' saldoData;

saldoData : LETTER date1 currency money;

block61 : ':61:' date1 date2? type moneyWithSeparator content;

date1: NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER;
date2: NUMBER NUMBER NUMBER NUMBER;
type: LETTER | LETTER LETTER;
currency: LETTER LETTER LETTER;

block86 : ':86:' references;
otherblock :    ':' NUMBER NUMBER':' content
            |   ':' NUMBER NUMBER LETTER ':' content;


references: gcode?
            transactionText?
            primNumber?
            customerReference?      /*20*/
            customerReference?      /*21*/
            customerReference?      /*22*/
            customerReference?      /*23*/
            customerReference?      /*24*/
            customerReference?      /*25*/
            customerReference?      /*26*/
            customerReference?      /*27*/
            customerReference?      /*28*/
            customerReference?      /*29*/
            bic?                    /*30*/
            iban?                   /*31*/
            name1?                  /*32*/
            name2?                  /*33*/
            otherReference*;

gcode : content;


transactionText : '?00' content;
primNumber : '?10' content;

customerReference :   '?20' referenceContent
                    | '?21' referenceContent
                    | '?22' referenceContent
                    | '?23' referenceContent
                    | '?24' referenceContent
                    | '?25' referenceContent
                    | '?26' referenceContent
                    | '?27' referenceContent
                    | '?28' referenceContent
                    | '?29' referenceContent;

bic : '?30' content;
iban : '?31' content;
name1: '?32' referenceContent;
name2: '?33' referenceContent;

otherReference :   '?34' referenceContent
                 | '?35' referenceContent
                 | '?36' referenceContent
                 | '?60' referenceContent
                 | '?61' referenceContent
                 | '?62' referenceContent
                 | '?63' referenceContent;


moneyWithSeparator : money LETTER;
money : moneyMajor COMMA moneyMinor MINUS?;
moneyMajor : NUMBER+;
moneyMinor : NUMBER? NUMBER?;

newLine : NEWLINE | NEWLINE NEWLINE;

content :  ( OTHER_CHAR | NUMBER | LETTER | COMMA | MINUS | COLON )+;
referenceContent :  ( COLON | OTHER_CHAR | NUMBER | LETTER | COMMA | MINUS)+;

NUMBER : [0-9];
OTHER_CHAR  : ~([\:\?] | [0-9] | [A-Za-z] | ',' | '-') |[\t\n\r] ;                      //all Characters except for those in the Brackets
LETTER : [A-Za-z];
COMMA : ',';
MINUS : '-';
COLON : ':';

WS : [\t\r\n]+ -> skip ; // skip spaces, tabs, newlines