package gestaobancaria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import transaccoes.Deposito;
import transaccoes.CapitalizacaoJuros;
import transaccoes.Levantamento;
import transaccoes.Transaccao;
import transaccoes.Transferencia;

import contas.Conta;
import contas.Prazo;
import contas.Debito;

public class Banco extends InteraccaoBanco {
	private static ArrayList<Cliente> clientes;
	private static int numerocliente; // utilizado para as numeracoes dos clientes
	private static int numeroconta; // utilizado para as numeracoes das contas
	private static final String FICHEIROCLIENTES = "dados/clientes.csv";
	private static final String FICHEIROCONTAS = "dados/contas.csv";
	private static final String FICHEIROTRANSACOES = "dados/transaccoes.csv";
	private static final String FICHEIROCONFIGURACOES = "dados/configuracoes.txt";

	public static void iniciar() {
		clientes = new ArrayList<Cliente>();
		// simularDados();
		carregarDados();

	}

	public static int gerarNumConta() {
		return numeroconta++;
	}

	public static int gerarNumCliente() {
		return numerocliente++;
	}

	public Banco() {
		clientes = new ArrayList<Cliente>();
	}

	// METODOS CLIENTES

	/**
	 * Desactiva o cliente com o id passado como parametro. Para isto deve usar o
	 * metodo procurar cliente do banco para encontrar o cliente com esse id. Caso
	 * exista define o estado como inactivo através do metodo desactivar do cliente
	 * 
	 * @param idcliente id do cliente a desactivar
	 * @return booleano a indicar se foi ou nao possivel de desactivar o cliente
	 */
	public static boolean desactivarCliente(int idcliente) {
		// implementar o codigo deste metodo
		Cliente c = procurarCliente(idcliente);

		if (c != null) {
			c.desactivar();
			return true;
		}
		return false;
	}

	/**
	 * Lista os clientes do banco que tem o nome igual ao recebido como parametro.
	 * Caso seja passada uma string vazia entao sao listados todos os clientes
	 * 
	 * @param criterionome nome do cliente a listar
	 */
	public static void listarClientes(String criterionome) {
		// implementar o codigo deste metodo
		Collections.sort(clientes);
		if (criterionome.equals("")) {
			for (Cliente c : clientes) {
				System.out.println(c.obterInformacoes());
			}
		} else {
			for (Cliente c : clientes) {
				if (c.obterNome().equals(criterionome))
					System.out.println(c.obterInformacoes());
			}
		}
	}

	/**
	 * Procura o cliente com o id recebido como parametro e devolve-o caso exista.
	 * Caso não exista devolve null. Este metodo e utilizado noutros metodos do
	 * banco.
	 * 
	 * @param userid id do cliente a procurar
	 * @return devolve o cliente com o id procurado ou null
	 */
	public static Cliente procurarCliente(int userid) {
		// implementar o codigo deste metodo
		for (Cliente c : clientes) {
			if (c.obteruserID() == userid)
				return c;
		}
		return null;
	}

	/**
	 * Utiliza o userid para encontrar o respectivo cliente e se existir confirma
	 * que a password e a correcta para esse utilizador. Caso isso se verifique
	 * devolve o cliente que fez login
	 * 
	 * @param userid   id do utilizador que esta a fazer login
	 * @param password passwor dod utilizador que esta a fazer login
	 * @return Cliente que acabou de fazer login ou null caso não coincidam as
	 *         credênciais
	 */
	public static Cliente validarLogin(int userid, int password) {
		// implementar o codigo deste metodo
		Cliente c = procurarCliente(userid);

		if (c != null) {
			if (c.obterPassword() == password)
				return c;
		}
		return null;
	}

	/**
	 * Cria um cliente com os dados recebidos e adiciona-o à lista de clientes do
	 * banco
	 * 
	 * @param nome     nome do cliente a adicionar
	 * @param userid   userid do cliente a adicionar
	 * @param password password do cliente a adicionar
	 */
	public static void criarCliente(String nome, int userid, int password) {
		// implementar o codigo deste metodo
		Cliente c = new Cliente(nome, userid, password);
		clientes.add(c);
	}

	public static void criarCliente(String nome, int userid, int password, boolean ativo) {
		// implementar o codigo deste metodo
		clientes.add(new Cliente(nome, userid, password, ativo));
	}

	/**
	 * Procura em todos os clientes por uma conta com o nib recebido como parâmetro
	 * Devolve o objeto conta caso exista ou null
	 * 
	 * @param nib nib da conta a procurar
	 * @return Conta com o nib especificado
	 */
	public static Conta obterConta(int nib) {
		// implementar o codigo deste metodo
		for (Cliente c : clientes) {
			Conta contaCliente = c.obterConta(nib);
			if (contaCliente != null && contaCliente.obterNib() == nib) {
				return contaCliente;
			}
		}
		return null;
	}

	/**
	 * Procura o cliente com o id recebido por parâmetro. Caso este exista adiciona
	 * o objeto conta recebido por parâmetro às contas desse cliente
	 * 
	 * @param idcliente id do cliente a adicionar a Conta
	 * @param c         Conta a adicionar
	 */
	public static void adicionaConta(int idcliente, Conta c) {
		// implementar o codigo deste metodo
		for (Cliente cli : clientes) {
			if (cli.obteruserID() == idcliente) {
				cli.obterContas().add(c);
			}
		}
	}

	/**
	 * Cria uma conta para o cliente c com o tipo tipoconta ESTE METODO ASSUME QUE O
	 * TIPO DE CONTA E VALIDO (1 - DEBITO / 2 - PRAZO)
	 * 
	 * @param c         O cliente sobre o qual vai ser criada a conta
	 * @param tipoconta o tipo da conta a criar
	 * @return O nib da nova conta criada
	 */
	public static int criarConta(Cliente c, int tipoconta) {
		// implementar o codigo deste metodo
		if (tipoconta == 1) {
			Debito cont = new Debito();
			c.adicionarConta(cont);
			return cont.obterNib();
		} else {
			Prazo cont = new Prazo();
			c.adicionarConta(cont);
			return cont.obterNib();
		}
	}

	public static void simularDados() {
		Cliente joao = new Cliente("joao", 111, 222);
		joao.adicionarConta(new Prazo());
		joao.adicionarConta(new Debito());

		Cliente pedro = new Cliente("pedro", 123, 456);
		pedro.adicionarConta(new Prazo());
		pedro.adicionarConta(new Debito());

		clientes.add(joao);
		clientes.add(pedro);
	}

	private static void carregarDados() {
		File ficheiroclientes = new File(FICHEIROCLIENTES);
		File ficheirocontas = new File(FICHEIROCONTAS);
		File ficheirotransacoes = new File(FICHEIROTRANSACOES);
		File ficheiroconfiguracoes = new File(FICHEIROCONFIGURACOES);

		if (!ficheiroclientes.exists()) {
			System.out.println("Não é possivel carregar dados pois não existe o ficheiro de clientes para carregar");
			return;
		}

		if (!ficheirocontas.exists()) {
			System.out.println("Não é possivel carregar dados pois não existe o ficheiro de contas para carregar");
			return;
		}

		if (!ficheirotransacoes.exists()) {
			System.out.println("Não é possivel carregar dados pois não existe o ficheiro de transações para carregar");
			return;
		}

		try {
			importarClientes(ficheiroclientes);
			importarContas(ficheirocontas);
			importarTransacoes(ficheirotransacoes);
		} catch (Exception e) {
			System.out.println("Erro na importação dos ficheiros");
		}
	}

	private static void importarClientes(File ficheiroclientes) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(ficheiroclientes));
		String texto = br.readLine();

		while (texto != null) {
			String[] dadosficheiro = texto.split(";");

			String nome = dadosficheiro[0];
			int userid = Integer.parseInt(dadosficheiro[1]);
			int password = Integer.parseInt(dadosficheiro[2]);
			boolean ativo = Boolean.parseBoolean(dadosficheiro[3]);

			Banco.criarCliente(nome, userid, password, ativo);
			texto = br.readLine();
		}
	}

	private static void importarContas(File ficheirocontas) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(ficheirocontas));
		String texto = br.readLine();

		while (texto != null) {
			String[] dadosficheiro = texto.split(";");

			SimpleDateFormat std = new SimpleDateFormat();

			int userid = Integer.parseInt(dadosficheiro[0]);
			String tipoconta = dadosficheiro[1];
			int tp = tipoconta.equals("Debito") ? 1 : 2;
			double saldo = Double.parseDouble(dadosficheiro[2]);
			int nib = Integer.parseInt(dadosficheiro[3]);
			boolean ativo = Boolean.parseBoolean(dadosficheiro[4]);

			Date datacriacao = null;
			Date datavalidade = null;

			Calendar cal = Calendar.getInstance();

			// cal.setTime(std.parse(dadosficheiro[5]));

			try {
				datacriacao = std.parse(dadosficheiro[5]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Conta c;
			if (tp == 2) {
				double juros = Double.parseDouble(dadosficheiro[6]);
				try {
					datavalidade = std.parse(dadosficheiro[7]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c = new Prazo(nib, saldo, ativo, juros, datacriacao, datavalidade);
			} else {
				c = new Debito(nib, saldo, ativo, datacriacao);
			}

			Cliente cliente = Banco.procurarCliente(userid);
			cliente.adicionarConta(c);
			texto = br.readLine();
		}
	}

	private static void importarTransacoes(File ficheirotransacoes) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(ficheirotransacoes));
		String texto = br.readLine();

		while (texto != null) {
			String[] dadosficheiro = texto.split(";");

			SimpleDateFormat std = new SimpleDateFormat();

			int nibo = Integer.parseInt(dadosficheiro[0]);
			String tipotransacao = dadosficheiro[1];
			int niborigem = Integer.parseInt(dadosficheiro[2]);
			double valor = Double.parseDouble(dadosficheiro[3]);
			Date datatransacao = null;

			try {
				datatransacao = std.parse(dadosficheiro[4]);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Transaccao temp;

			switch (tipotransacao) {
			case "Deposito":
				temp = new Deposito(datatransacao, Banco.obterConta(nibo), valor);
				break;

			case "Levantamento":
				temp = new Levantamento(datatransacao, Banco.obterConta(nibo), valor);
				break;

			case "Capitalizacao":
				temp = new CapitalizacaoJuros(datatransacao, Banco.obterConta(nibo), valor);
				break;

			case "Transferencia":
				int nibdestino = Integer.parseInt(dadosficheiro[5]);
				temp = new Transferencia(datatransacao, Banco.obterConta(nibo), valor, Banco.obterConta(nibdestino));
				break;

			default:
				temp = null;
				break;
			}

			Banco.obterConta(nibo).adicionarTransaccao(temp);

			texto = br.readLine();
		}

	}

	public static void guardarDados() {
		guardarTransacoes();
		guardarContas();
		guardarClientes();
		guardarConfiguracoes();
	}

	public static void guardarClientes() {
		try {
			PrintWriter pw = new PrintWriter(FICHEIROCLIENTES);

			for (int i = 0; i < clientes.size(); ++i) {
				clientes.get(i).escreverFicheiro(pw);
			}

			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("A pasta para gravação de clientes não existe");
		}
	}

	public static void guardarTransacoes() {
		try {
			PrintWriter pw = new PrintWriter(FICHEIROTRANSACOES);

			for (int i = 0; i < clientes.size(); ++i) {
				ArrayList<Conta> contas = clientes.get(i).obterContas();
				
				for (int i2 = 0; i2 < contas.size(); ++i2) {
					ArrayList<Transaccao> transaccoes = contas.get(i2).obterTransaccoes();
					
					for (int i3 = 0; i3 < transaccoes.size(); ++i3) {
						transaccoes.get(i3).escreverFicheiro(pw);
					}
				}
			}

			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("A pasta para gravação de clientes não existe");
		}
	}

	public static void guardarContas() {
		try {
			PrintWriter pw = new PrintWriter(FICHEIROCONTAS);
			
			for(Cliente c:clientes) {
				for(Conta conta:c.obterContas()) {
					conta.escreverFicheiro(pw, c.obteruserID());
				}
			}

			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("A pasta para gravação de clientes não existe");
		}
	}

	public static void importarConfiguracoes(File ficheiroconfiguracoes) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(ficheiroconfiguracoes));

		String texto = br.readLine();

		numerocliente = Integer.parseInt(texto);

		texto = br.readLine();

		numeroconta = Integer.parseInt(texto);

	}

	public static void guardarConfiguracoes() {
		File ficheiro = new File("Configuracoes.txt");

		try {
			if (ficheiro.exists()) {
				ficheiro.delete();
			} else {
				FileWriter fw = new FileWriter("EscritaDados.txt", true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("Numero Cliente: " + numerocliente);
				pw.println("Numero da Conta: " + numeroconta);
				pw.flush();
				pw.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Erro : " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
