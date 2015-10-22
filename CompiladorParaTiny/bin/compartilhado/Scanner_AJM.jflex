package compartilhado;
/*FATEC - Ciência da Computação - 6º período*/
/*Compiladores - Prof.: Gabriel Falconieri*/
/*Grupo: Augusto, João Guedes e Mácio*/

/* ****código do usuário***** */

import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

/**Classe scanner para tinny<br>Grupo: Augusto, João Guedes e Mácio*/
%% 
%cup
%line
%column
%class Scanner
%{
	private SymbolFactory sf;
	
	public Scanner(java.io.InputStream r, SymbolFactory sf){
		this(r);
		this.sf=sf;
	}

	private Symbol newSymbol(int sym_Cod) {
	
		return newSymbol(sym_Cod, yytext());
	
	}

	private Symbol newSymbol(int sym_Cod, Object value) {
		
		Symbol symbol = sf.newSymbol(yytext(), sym_Cod);

		symbol.left = yyline + 1;

		symbol.right = yycolumn + 1;

		symbol.value = value;
		
		return symbol;
		
	}
%}

%eofval{
	return sf.newSymbol("EOF",sym.EOF);	
%eofval}

digito = [0-9]
numero = {digito}+
letra = [a-zA-Z]
identificador = {letra}+
 
%%

"if" { return newSymbol(sym.IF); }
"then" { return newSymbol(sym.THEN); }
"else" { return newSymbol(sym.ELSE); }
"end" { return newSymbol(sym.END); }
"repeat" { return newSymbol(sym.REPEAT); }
"until" { return newSymbol(sym.UNTIL); }
"read" { return newSymbol(sym.READ); }
"write" { return newSymbol(sym.WRITE); }
{identificador} { return newSymbol(sym.ID, new Integer(0)); }
"+" { return newSymbol(sym.OP_ADD); }
"-" { return newSymbol(sym.OP_SUB); }
"*" { return newSymbol(sym.OP_MULT); }
"/" { return newSymbol(sym.OP_QUOC); }
"=" { return newSymbol(sym.OP_IGUAL); }
"<" { return newSymbol(sym.OP_MQ); }
{numero} { return newSymbol(sym.NUMBER, new Integer(yytext())); }
";" { return newSymbol(sym.SEMI); }
":=" { return newSymbol(sym.OP_ATRIB); }
"(" { return newSymbol(sym.LPAREN); }
")" { return newSymbol(sym.RPAREN); }
[ |\n|\t\f|\r|\r\n] { }
[\{](.)*[\}] { }
[^] { System.err.println("Erro detectado: linha:" + yyline + ", coluna: " + yycolumn + ", token desconhecido: \"" + yytext() + "\""); }

