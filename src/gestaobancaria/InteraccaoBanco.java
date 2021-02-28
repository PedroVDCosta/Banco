package gestaobancaria;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import contas.Conta;
import transaccoes.Deposito;
import transaccoes.Levantamento;
import transaccoes.Transferencia;

public abstract class InteraccaoBanco {

	protected static Cliente cli; //À medida que os menus avançam o cliente a ser usado é guardado nesta variavel
	protected static Conta con;//À medida que os menus avançam a conta a ser usada é guardada nesta variavel
	
	/**
	 * Este metodo está em repetição a mostrar o menu de operações disponiveis numa conta.
	 * Para cada uma das opções existentes e através de um switch solicita a informação
	 * necessaria ao utilizador e invoca os metodos correspondentes.
	 */
	protected static void processaMenuConta(){
		//implementar o codigo deste método
		int opcao = 0;
		
		while(opcao != 7) {
			System.out.println("Menu Operações Contas");
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
						System.out.println("Não existe um extracto da conta que está a ser utilizada");
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
				System.out.println("Não existe nenhuma conta ativa");
			}
		}
	}
	
	/**
	 * Este metodo está em repetição a mostrar o menu de contas disponiveis do cliente.
	 * De notar que APENAS AS CONTAS ACTIVAS são mostradas.
	 * Após ser seleccionada uma conta é invocado o metodo processaMenuConta referente à conta escolhida
	 * @param contascliente Cliente sobre o qual se quer visualizar as contas
	 */
	protected static void processaMenuContas(ArrayList<Conta> contascliente) {
		//implementar o codigo deste método
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
		System.out.println(con.levantar(valor) ? "Dinheiro levantado com sucesso" : "Não possui o saldo suficiente para esta operação");
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
				System.out.println("Transferência efectuada com sucesso");
			}
			else {
				System.out.println("Não possui o saldo suficiente para esta operação");
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
