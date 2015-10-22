/*FATEC - Ciência da Computação - 6º período*/
/*Compiladores - Prof.: Gabriel Falconieri*/
/*Grupo: Augusto, João Guedes e Mácio*/

/* ****código do usuário***** */
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.awt.FileDialog;
import java.awt.Frame;
import javax.swing.JOptionPane;
/**Classe scanner para tinny<br>Grupo: Augusto, João Guedes e Mácio*/
%% 
%unicode
%yylexthrow Exception
%function getToken
%type Integer
%line
%column
%char

/*macros*/
digito = [0-9]
letra = [a-zA-Z]
numero = {digito}+
identificador = {letra}+
keyword = "if"|"then"|"else"|"end"|"repeat"|"until"|"read"|"write"
operador = "+"|"-"|"*"|"/"
simboloespecial = {operador}|"="|"<"|";"|":="|"("|")"
tokenignorado = [ |\n|\t\f|\r|\r\n]
comentario = [\{]({numero}|{letra}|{simboloespecial}|{tokenignorado})*[\}]

%class Scanner_Grupo3
 
%{

	static File arquivoDeSaida;
	static FileWriter writer;
	static PrintWriter saida;
	static List<String> textosPorLinha = new ArrayList<String>();
	StringBuffer string = new StringBuffer();
	int linhaDoTexto = 0;

		public static void criarArquivoDeSaida(String nomeDoArquivo){
		
			arquivoDeSaida = new File(nomeDoArquivo);
		
			if(!arquivoDeSaida.exists()){    
			
				System.out.println("arquivo não existe");    
				
			 	System.out.println("criando arquivo " + nomeDoArquivo + " em " + arquivoDeSaida.getPath());
			 	
			 	try {
			 	
					arquivoDeSaida.createNewFile();
					
				} catch (IOException e) {
				
					e.printStackTrace();
					
				}   
			 	
			 	System.out.println("arquivo " + nomeDoArquivo + ", criado em " + arquivoDeSaida.getPath());
			 	
			} else {
			
				System.out.println("arquivo de saída encontrado em " + arquivoDeSaida.getPath());
			
			} 
	
		}
	 
		public static void abrirArquivoParaEscrita(){
	 
			try {
			
				writer = new FileWriter(arquivoDeSaida, false);
				
				saida = new PrintWriter(writer, true);
				
			} catch (IOException e) {
			
				e.printStackTrace();
				
			}
	 
	 	}
	 	 
	 	private static String selecionarArquivo(){

			/*return "/home/joao/Downloads/fatorial.tiny";*/

	 		FileDialog fd = new FileDialog(new Frame(), "Selecionar Arquivo", FileDialog.LOAD);
	 	
			fd.setVisible(true);
	
			return fd.getDirectory().concat(fd.getFile());
	
	 	}
	 
	 	public static void setTextosPorLinha(String file) throws Exception{
    		  	
      BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
			String textoDaLinha;	
				
	 		while ((textoDaLinha = bufferedReader.readLine()) != null){
    		
    		textosPorLinha.add(textoDaLinha);
    			
    	}
    		
    	bufferedReader.close();
    		    		
	 	}

		public static void gerarArquivoDeSaida(String arquivoDeEntrada){
      	 
			int resposta = JOptionPane.showConfirmDialog(null, "Gerar Arquivo?");
		
			if (resposta == JOptionPane.YES_OPTION) {
		
				criarArquivoDeSaida(arquivoDeEntrada.replaceAll(".tiny",".out"));
			
      	abrirArquivoParaEscrita();
      
			}        		
		
		}
	 
	 	public static void main(String argv[]){
	 
			String arquivo = selecionarArquivo();
		
			System.out.println("Arquivo selecionado: " + arquivo);
	 
    	if (arquivo.length() == 0) {
    
      	System.out.println("Usage : java Scanner_Grupo3 <inputfile>");
      
    	} else {
    
      	Scanner_Grupo3 scanner = null;
      
      	try {
      	
      		setTextosPorLinha(arquivo);
    	
    			gerarArquivoDeSaida(arquivo);
    	
      		scanner = new Scanner_Grupo3( new FileReader(arquivo) );
      	
      		while ( !scanner.zzAtEOF )scanner.getToken();
      		        
      	}catch (java.io.FileNotFoundException e) {
      
        	System.out.println("Arquivo não encotrado : \""+arquivo+"\"");
        
      	}catch (java.io.IOException e) {
      
        	System.out.println("IO error scanning file \""+arquivo+"\"");
        
        	System.out.println(e);
        
      	}catch (Exception e) {
      
        	System.out.println("Unexpected exception:");
        
        	e.printStackTrace();
        
      	}
      
    	}
    
	 		System.exit(0);
	 	
	 	}
	 
	 	public void buffer(String texto){
	 	
	 		String textoDaLinha = "";
	 		 	
	 		if(linhaDoTexto == yyline){
	 	
	 			linhaDoTexto++;
	 	
	 			textoDaLinha = linhaDoTexto + ": " + textosPorLinha.get(yyline);
	 	
	 		}
	 	
	 		textoDaLinha = textoDaLinha.concat(texto);
	 	
	 		string.append(textoDaLinha);
	 	
	 	}
	 
%}
 
%init{	
%init}

%eof{
	
	string.append("\t" + (yyline + 1) + ": EOF");
	
	System.out.println(string.toString());
 	
	if(writer != null){ 	
	
		try {
		
			saida.println(string.toString());
		
			saida.close();
			
			writer.close();
			
		} catch (IOException e) {
		
			e.printStackTrace();
			
		} 
		
	}
	
%eof}
 
%%
{comentario} { buffer(""); }
{keyword} { buffer("\n\t" + (yyline + 1) + ": reserved word: " + yytext()); }
{numero} { buffer("\n\t" + (yyline + 1) + ": NUM, val= " + yytext()); }
{identificador} { buffer("\n\t" + (yyline + 1) + ": ID, name= " + yytext()); }
{simboloespecial} { buffer("\n\t" + (yyline + 1) + ": " + yytext()); }
{tokenignorado} { buffer(yytext()); }
[^] { throw new Exception("Erro detectado: linha:" + yyline + ", coluna: " + yycolumn + ", token: " + "token desconhecido"); }

