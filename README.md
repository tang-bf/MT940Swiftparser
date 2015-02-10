# MT940Swiftparser

Created with the parser generator ANLTR4 to read and update swift mt940 messages.

## Why another parser?


I wanted to parse swift raw data, analyze it and rewrite the customer references.
I used the input format from Commerzbank, a german bank. Others parser libraries didn't support the reference format.

## ANTLR4

If you want to change the parser syntax you might want to take a look at the antlr website
http://www.antlr.org

## Initiate the parser

```java
/**
 * Initialize the input stream and the parser
 */
CharStream input = new ANTLRInputStream( ":20:00ZQYDSR63Y4OGWA\n:61:1402190218D1156,14NCMZ100207602400//99100/074\n:86:833?00CASH CONCENTRATING BUCHUNG?1099100?20ACMS W/100 2076024 00\n-" );
SwiftMt940Lexer lexer = new SwiftMt940Lexer( input );
CommonTokenStream tokenStream = new CommonTokenStream( lexer );
SwiftMt940Parser parser = new SwiftMt940Parser( tokenStream );

/**
 * You can use the SwiftInputListener or create your own to parse the document
 * You have to implement the interface de.oette.swiftparser.SwiftMt940BaseListener
 */
SwiftInputListener listener = new SwiftInputListener( );

//Initiate a parse tree and walk along the tree. The ParseTreeWalker calls the listener methods
ParseTree parseTree = parser.root();
ParseTreeWalker walker = new ParseTreeWalker();
walker.walk( listener, parseTree );

/**
 * If you use the SwiftInputListener then you can fetch the transaction collection
 */
SwiftMt940TransactionCollection collection = listener.getCollection();
System.out.println( collection );
```

You should get the following output

```
SwiftMt940TransactionCollectionImpl
{
		SwiftMt940TransactionImpl
		{
			positionInSwiftBlock=0
			gCode=833
			additionalReference=null
			bic=null
			iban=null
			name=null
			customerReference=[ACMS W/100 2076024 00]
			amount=null
		}

}
```