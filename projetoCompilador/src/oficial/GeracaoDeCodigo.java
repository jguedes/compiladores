package oficial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import oficial.grammarclasses.Exp;
import java_cup.runtime.Symbol;

@SuppressWarnings("unused")
public class GeracaoDeCodigo {

	/**
	 * Array que guarda as instruções
	 */
	private static String[] iMem = new String[1024];

	/**
	 * Array que guarda os dados
	 */
	private static String[] dMem = new String[1024];

	/**
	 * Array que guarda o conteúdo dos registradores
	 */
	private static Integer[] reg = new Integer[8];

	private static final int Ac = 0, Ac1 = 1, r2 = 2, r3 = 3, r4 = 4, gp = 5,
			mp = 6, pc = 7;

	/**
	 * Armazena os identificadores encontrados e o valor é o deslocamento na
	 * memória de dados
	 */
	private static HashMap<String, Integer> tabelaDeSimbolos;

	/**
	 * Seta qual é o registrador que aponta para o símbolo
	 */
	private static HashMap<String, String> tabelaDeRegistradoresParaSimbolos;

	private static HashMap<String, Integer> registrador;

	/**
	 * Seta o registrador como ocupado (valor = true) ou livre (valor = false)
	 */
	private static HashMap<String, Boolean> registradorStatus;

	private static boolean inicializado;

	private static List<String> codigo = new ArrayList<String>();

	public static void gerarCodigo() {

		StringBuilder codigoParaExibir = new StringBuilder();

		for (String linhaDeCodigo : codigo) {
			codigoParaExibir.append(linhaDeCodigo);
			codigoParaExibir.append("\n");
		}

		// JOptionPane.showMessageDialog(null, codigoParaExibir.toString());
		System.out.println(codigoParaExibir.toString());

	}

	private static void inicializar() {

		if (inicializado)
			return;

		// pc aponta para instrução 0(zero)
		reg[pc] = 0;

		// mp aponta para a localização da memória de dados mais alta
		reg[mp] = dMem.length - 1;

		// gp aponta para 0 (zero) na memória de dados sempre
		reg[gp] = 0;

		tabelaDeSimbolos = new HashMap<String, Integer>();

		tabelaDeRegistradoresParaSimbolos = new HashMap<String, String>();

		registrador = new HashMap<String, Integer>();

		registradorStatus = new HashMap<String, Boolean>();

		registrador.put("Ac", 0);
		registrador.put("Ac1", 1);
		registrador.put("r2", 2);
		registrador.put("r3", 3);
		registrador.put("r4", 4);
		registrador.put("gp", 5);
		registrador.put("mp", 6);
		registrador.put("pc", 7);

		registradorStatus.put("Ac", false);
		registradorStatus.put("Ac1", false);
		registradorStatus.put("r2", false);
		registradorStatus.put("r3", false);
		registradorStatus.put("r4", false);
		registradorStatus.put("gp", true);
		registradorStatus.put("mp", true);
		registradorStatus.put("pc", true);

		inicializado = true;

	}

	/**
	 * Procura por registrador que esteja livre na tabela de
	 * resgistradorUtilizado.<br>
	 * <br>
	 * Se não encotrar um registrado livre returna null.
	 * 
	 * @return o nome do registrador livre
	 * 
	 * @param boolean setarComoOcupado
	 */
	private static String getNomeDeRegistradorLivre(boolean setarComoOcupado) {

		for (int i = 0; i < registrador.size(); i++) {

			for (String key : registrador.keySet()) {

				if (registrador.get(key) == i) {

					if (registradorStatus.get(key)) {

						break;

					} else {

						registradorStatus.put(key, setarComoOcupado);

						return key;

					}

				}

			}

		}

		return null;

	}

	/**
	 * Código para registrador-only (RO)<br>
	 * <br>
	 * opcode r,s,t<br>
	 * 
	 * @param opcode
	 * @param r
	 * @param s
	 * @param t
	 * @param comentarioDeCodigo
	 */
	private static void gerarCodigoRO(String opcode, int r, int s, int t,
			String comentarioDeCodigo) {

		if (!opcodeValido("RO", opcode)) {
			return;
		}

		StringBuilder linhaDeCodigo = new StringBuilder();

		String nlinha = codigo.size() + ":";

		linhaDeCodigo.append(nlinha);
		linhaDeCodigo.append("             ");
		linhaDeCodigo.append(opcode);
		linhaDeCodigo.append(" ");
		linhaDeCodigo.append(r);
		linhaDeCodigo.append(", ");
		linhaDeCodigo.append(s);
		linhaDeCodigo.append(", ");
		linhaDeCodigo.append(t);
		linhaDeCodigo.append("             ");
		linhaDeCodigo.append(comentarioDeCodigo);

		codigo.add(linhaDeCodigo.toString());

		// liberarRegistradores(r, s, t);

		// JOptionPane.showMessageDialog(null, linhaDeCodigo.toString());

	}

	private static void liberarRegistradores(int... args) {

		int size_args = args.length;

		for (String reg_name : registrador.keySet()) {

			if (reg_name.matches("[pc gp mp]"))
				continue;

			for (int reg_num : args) {

				if (registrador.get(reg_name) == reg_num) {

					registradorStatus.put(reg_name, false);

					size_args--;

					if (size_args == 0)
						return;

				}

			}

		}

	}

	/**
	 * Código para registrador-memory (RM)<br>
	 * <br>
	 * opcode r,d(s)<br>
	 * 
	 * @param opcode
	 * @param r
	 * @param s
	 * @param t
	 * @param comentarioDeCodigo
	 */
	private static void gerarCodigoRM(String opcode, int r, int d, int s,
			String comentarioDeCodigo) {

		if (!opcodeValido("RM", opcode)) {
			return;
		}

		StringBuilder linhaDeCodigo = new StringBuilder();

		String nlinha = codigo.size() + ":";

		linhaDeCodigo.append(nlinha);
		linhaDeCodigo.append("             ");
		linhaDeCodigo.append(opcode);
		linhaDeCodigo.append(" ");
		linhaDeCodigo.append(r);
		linhaDeCodigo.append(", ");
		linhaDeCodigo.append(d);
		linhaDeCodigo.append("(");
		linhaDeCodigo.append(s);
		linhaDeCodigo.append(")");
		linhaDeCodigo.append("             ");
		linhaDeCodigo.append(comentarioDeCodigo);

		codigo.add(linhaDeCodigo.toString());

		// liberarRegistradores(r, d, s);

		// JOptionPane.showMessageDialog(null, linhaDeCodigo.toString());

	}

	private static boolean opcodeValido(String tipo, String opcode) {

		String ROs[] = { "IN", "OUT", "ADD", "SUB", "MUL", "DIV" };

		String RMs[] = { "LD", "LDA", "LDC", "ST", "JLT", "JLE", "JGE", "JGT",
				"JEQ", "JNE" };

		String aux[] = tipo.equalsIgnoreCase("RM") ? RMs : ROs;

		for (String opc : aux) {

			if (opc.equalsIgnoreCase(opcode)) {

				return true;

			}

		}

		System.err.println("ERRO: gerarCodigo(" + tipo
				+ "): opcode inválido: [" + opcode + " ].");

		return false;

	}

	private static boolean variavelExisteNaTab(String var_name) {

		for (String s : tabelaDeSimbolos.keySet()) {

			if (s.equals(var_name)) {

				return true;

			}

		}

		return false;
	}

	public static int getRegistradorParaSymbol(Symbol symbol) {
		return registrador.get(tabelaDeRegistradoresParaSimbolos
				.get(getNomeDoSimbolo(symbol)));
	}

	public static String getNomeDoSimbolo(Symbol symbol) {
		return symbol.toString().split("Symbol:")[1].trim();
	}

	private static void introduzirNaTabelaDeRegistradoresParaSimbolos(
			String var_name) {

		String reg = getNomeDeRegistradorLivre(true);

		// //JOptionPane.showMessageDialog(null, "registrador para: " + symbol
		// + " = " + reg);

		tabelaDeRegistradoresParaSimbolos.put(var_name, reg);

	}

	private static void introduzirNaTabelaDeSimbolos(String var_name) {
		tabelaDeSimbolos.put(var_name, tabelaDeSimbolos.size());
	}

	public static void introduzirSimbolo(String var_name) {

		inicializar();

		if (!variavelExisteNaTab(var_name)) {
			introduzirNaTabelaDeSimbolos(var_name);
			// introduzirNaTabelaDeRegistradoresParaSimbolos(var_name);
		}

	}

	private static void emitRM_ST(int r, String var_name) {

		// int r = registrador.get(tabelaDeRegistradoresParaSimbolos
		// .get(getNomeDoSimbolo(symbol)));

		int d = tabelaDeSimbolos.get(var_name);

		int s = registrador.get("gp");

		String comentarioDeCodigo = "dMem[" + d + "] = r" + r;

		gerarCodigoRM("ST", r, d, s, comentarioDeCodigo);

		liberarRegistradores(d, s);

	}

	public static void emitRM_LDC(String var_name, int constante) {

		introduzirSimbolo(var_name);

		// String reg = tabelaDeRegistradoresParaSimbolos
		// .get(getNomeDoSimbolo(var_name));

		String reg_Livre = getNomeDeRegistradorLivre(true);

		int r = registrador.get(reg_Livre);

		int d = constante;

		int s = 0;

		String comentarioDeCodigo = "r" + r + " = " + constante;

		gerarCodigoRM("LDC", r, d, s, comentarioDeCodigo);

		emitRM_ST(r, var_name);

	}

	public static void emitRO_IN(String var_name) {

		introduzirSimbolo(var_name);

		// String reg = tabelaDeRegistradoresParaSimbolos
		// .get(getNomeDoSimbolo(iD));

		String reg = getNomeDeRegistradorLivre(true);

		int r = registrador.get(reg);

		int s = 0;

		int t = 0;

		String comentarioDeCodigo = "r" + r + " = read";

		gerarCodigoRO("IN", r, s, t, comentarioDeCodigo);

		emitRM_ST(r, var_name);

		liberarRegistradores(r, s, t);

	}

	public static void emitRO_ADD(String var_r, String var_s, String var_t) {

		int r = setarRegistradorParaVariavel(var_r, false);

		int s = setarRegistradorParaVariavel(var_s, true);

		int t = setarRegistradorParaVariavel(var_t, true);

		String comentarioDeCodigo = "r" + r + " = r" + s + " + r" + t;

		gerarCodigoRO("ADD", r, s, t, comentarioDeCodigo);

		emitRM_ST(r, var_r);

		liberarRegistradores(r, s, t);

	}

	/**
	 * Busca no conjunto de registradores um registrador que esteja livre. E
	 * realiza a geração de código para instrução LD (ler na memoria o valor
	 * correspondente a variável e carregá-lo no registrador).
	 * 
	 * @param var_name
	 *            (nome da variável)
	 * @return int (o número correspondente ao registrador)
	 */
	private static int setarRegistradorParaVariavel(String var_name,
			boolean buscarValorNaMemoria) {

		String regLivre = getNomeDeRegistradorLivre(true);

		int r = registrador.get(regLivre);

		if (buscarValorNaMemoria)
			emitRM_LD(r, var_name);

		return r;

	}

	private static void emitRM_LD(int r, String var_d) {

		int d = tabelaDeSimbolos.get(var_d);

		int s = registrador.get("gp");

		String comentarioDeCodigo = "r" + r + " = dMem[" + d + "]";

		// if(d==3){
		// System.out.println(d);
		// }

		gerarCodigoRM("LD", r, d, s, comentarioDeCodigo);

		liberarRegistradores(d);

	}

	private static int definirRegistradorParaVariavel(String var_s) {

		return 0;
	}

	public static String armazenarConstanteNumaVariavelTemporaria(int constante) {

		String var_name = gerarVariavelTemporaria();

		int r = registrador.get(getNomeDeRegistradorLivre(true));

		emitRM_LDC(var_name, constante);

		liberarRegistradores(r);

		return var_name;

	}

	public static String gerarVariavelTemporaria() {

		String var_temp = "temp" + tabelaDeSimbolos.size();

		introduzirSimbolo(var_temp);

		return var_temp;

	}

	public static void emitRM_LD(String var_r, String var_d) {

		int r = registrador.get(getNomeDeRegistradorLivre(true));

		emitRM_LD(r, var_d);

		emitRM_ST(r, var_r);

		liberarRegistradores(r);

	}

	public static void emitRO_SUB(String var_r, String var_s, String var_t) {

		int r = setarRegistradorParaVariavel(var_r, false);

		int s = setarRegistradorParaVariavel(var_s, true);

		int t = setarRegistradorParaVariavel(var_t, true);

		String comentarioDeCodigo = "r" + r + " = r" + s + " - r" + t;

		gerarCodigoRO("SUB", r, s, t, comentarioDeCodigo);

		emitRM_ST(r, var_r);

		liberarRegistradores(r, s, t);
	}

	public static void emitRO_MUL(String var_r, String var_s, String var_t) {

		int r = setarRegistradorParaVariavel(var_r, false);

		int s = setarRegistradorParaVariavel(var_s, true);

		int t = setarRegistradorParaVariavel(var_t, true);

		String comentarioDeCodigo = "r" + r + " = r" + s + " + r" + t;

		gerarCodigoRO("MUL", r, s, t, comentarioDeCodigo);

		emitRM_ST(r, var_r);

		liberarRegistradores(r, s, t);
	}

	public static void emitRO_DIV(String var_r, String var_s, String var_t) {

		int r = setarRegistradorParaVariavel(var_r, false);

		int s = setarRegistradorParaVariavel(var_s, true);

		int t = setarRegistradorParaVariavel(var_t, true);

		String comentarioDeCodigo = "r" + r + " = r" + s + " + r" + t;

		gerarCodigoRO("DIV", r, s, t, comentarioDeCodigo);

		emitRM_ST(r, var_r);

		liberarRegistradores(r, s, t);
	}

}
