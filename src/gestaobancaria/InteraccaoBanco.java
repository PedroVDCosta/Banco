package gestaobancaria;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import contas.Conta;
import transaccoes.Deposito;
import transaccoes.Levantamento;
import transaccoes.Transferencia;

public abstract class InteraccaoBanco {

	protected static Cliente cli; //� medida que os menus avan�am o cliente a ser usado � guardado nesta variavel
	protected static Conta con;//� medida que os menus avan�am a conta a ser usada � guardada nesta variavel
	
	/**
	 * Este metodo est� em repeti��o a mostrar o menu de opera��es disponiveis numa conta.
	 * Para cada uma das op��es existentes e atrav�s de um switch solicita a informa��o
	 * necessaria ao utilizador e invoca os metodos correspondentes.
	 */
	protected static void processaMenuConta(){
		//implementar o codigo deste m�todo
		int opcao = 0;
		
		while(opcao != 7) {
			System.out.println("Menu Opera��es Contas");
			System.out.println();
			System.out.println("1 -> Levantar");
			System.out.println("2 -> Depositar");
			System.out.println("3 -> Transferir");
			System.out.println("4 -> Obter extracto");
			System.out.println("5 -> Obter saldo");
			System.out.println("6 -> Obter informacoes");
			System.out.println("7 -> Sair do Menu");
			Scanner teclado = new Scanner(System.in);
			opcao = teclado.nextInt();
			
			if(con != null && cli != null) {
				switch(opcao) {
				case 1:
					Levantar();
					break;
				case 2:
					Depositar();
					break;
				case 3:
					Transferir();
					break;
				case 4:
					if(con.obterTransaccoes().size() != 0) {
						con.mostrarExtracto();
					}
					else {
						System.out.println("N�o existe um extracto da conta que est� a ser utilizada");
					}
					break;	
				case 5:
					con.mostrarSaldo();
					break;
				case 6:
					System.out.println(con.mostrarInformacoes());
					break;
				case 7:
					processaMenuContas(cli.obterContas());
					break;
				default:
					System.out.println("Opcao invalida!");
				}
			
			}
			else {
				System.out.println("N�o existe nenhuma conta ativa");
			}
		}
	}
	
	/**
	 * Este metodo est� em repeti��o a mostrar o menu de contas disponiveis do cliente.
	 * De notar que APENAS AS CONTAS ACTIVAS s�o mostradas.
	 * Ap�s ser seleccionada uma conta � invocado o metodo processaMenuConta referente � conta escolhida
	 * @param contascliente Cliente sobre o qual se quer visualizar as contas
	 */
	protected static void processaMenuContas(ArrayList<Conta> contascliente) {
		//implementar o codigo deste m�todo
		int contador = 1;
		
		System.out.println("Menu Contas");
		for(Conta cont:contascliente) {
			System.out.println(contador + " -> " + cont.obterTipo() + "-" + cont.obterNib());
			contador++;
		}
		System.out.println(contador + " -> Sair do Menu");
		Scanner teclado = new Scanner(System.in);
		int opcao = teclado.nextInt();
		
		if(opcao < contascliente.size()) {
			con = contascliente.get(opcao-1);
			processaMenuConta();
		}
		
		
		System.out.println("Fim");
		
	}
	
	public static void Levantar() {
		System.out.println("Quanto dinheiro pretende levantar?");
		Scanner teclado = new Scanner(System.in);
		double valor = teclado.nextDouble();

		Levantamento l = new Levantamento(new Date(), con, valor);
		con.adicionarTransaccao(l);
		System.out.println(con.levantar(valor) ? "Dinheiro levantado com sucesso" : "N�o possui o saldo suficiente para esta opera��o");
	}
	
	public static void Depositar() {
		System.out.println("Quanto dinheiro quer depositar?");
		Scanner teclado = new Scanner(System.in);
		double valor = teclado.nextDouble();
		
		Deposito dep = new Deposito(new Date(), con, valor);
		con.adicionarTransaccao(dep);
		con.depositar(valor);
		System.out.println("Deposito efectuado com sucesso");
	}
	
	public static void Transferir() {
		System.out.println("Qual o nib da conta que pretende transferir?");
		Scanner teclado = new Scanner(System.in);
		int nib = teclado.nextInt();
		Conta cont = Banco.obterConta(nib);
		if(cont != null) {
			System.out.println("Qual o valor a transferir?");
			double valor = teclado.nextDouble();
			Transferencia t = new Transferencia(new Date(), con, valor, cont);
			con.adicionarTransaccao(t);
			if(con.transferir(valor, cont)) {
				System.out.println("Transfer�ncia efectuada com sucesso");
			}
			else {
				System.out.println("N�o possui o saldo suficiente para esta opera��o");
			}
		}
		else {
			System.out.println("A conta nao existe");
		}
		
	}
	
	public static void main(String[] args) {
		Banco.iniciar();
		Multibanco.efetuarLogin();
		/*
		Cliente c = Banco.procurarCliente(111);
		cli = c;
		
		processaMenuContas(c.obterContas());*/
	}
}
