package gestaobancaria;

import java.util.Random;
import java.util.Scanner;

import contas.Conta;

public class Balcao extends InteraccaoBanco {
	
	protected static void processaMenuBalcao() {
		int opcao = 0;
		
		while(opcao != 7) {
			System.out.println("Menu Principal");
			System.out.println();
			System.out.println("1 -> Criar Cliente");
			System.out.println("2 -> Desactivar Cliente");
			System.out.println("3 -> Criar Conta");
			System.out.println("4 -> Desactivar Conta");
			System.out.println("5 -> Listar clientes");
			System.out.println("6 -> Operacoes sobre cliente");
			System.out.println("7 -> Sair");
			Scanner teclado = new Scanner(System.in);
			opcao = teclado.nextInt();
			
			switch(opcao) {
			case 1:
				CriarCliente();
				break;
			case 2:
				DesactivarCliente();
				break;
			case 3:
				CriarConta();
				break;
			case 4:
				DesactivarConta();
				break;
			case 5:
				ListarClientes();
				break;
			case 6:
				System.out.println("Qual o id do cliente que pretende operar?");
				int userid = teclado.nextInt();
				Cliente cliente = Banco.procurarCliente(userid);
				if(cliente != null) {
					InteraccaoBanco.cli = cliente;
					processaMenuContas(cliente.obterContas());
				}
				else {
					System.out.println("O cliente com o id inserido não foi encontrado com sucesso");
				}
				break;
			case 7:
				System.out.println("Fim do Balcão");
				break;
			default:
				System.out.println("Opcao invalida!");
			}
		}
	}
	
	public static void CriarCliente() {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Insira o nome do novo cliente");
		String nome = teclado.nextLine();
		
		System.out.println("Insira a password do novo cliente");
		int password = teclado.nextInt();
		
		int numaleatoriocli = Banco.gerarNumCliente();
		Banco.criarCliente(nome, numaleatoriocli, password);
		
		System.out.println("Cliente criado. O id deste cliente é " + numaleatoriocli);
	}
	
	public static void DesactivarCliente() {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Qual o id do cliente que pretende operar?");
		int userid = teclado.nextInt();
		
		Cliente cliente = Banco.procurarCliente(userid);
		if(cliente != null) {
			cliente.desactivar();
			System.out.println("O cliente foi desactivado com sucesso");
		}
		else {
			System.out.println("O cliente com o id inserido não foi encontrado com sucesso");
		}
	}
	
	public static void CriarConta() {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Qual o id do cliente que pretende operar?");
		int userid = teclado.nextInt();
		
		Cliente cliente = Banco.procurarCliente(userid);
		if(cliente != null) {
			System.out.println("Insira o tipo de conta a criar (1 - Debito, 2 - Prazo)");
			int tipoconta = teclado.nextInt();
			if(tipoconta > 2 || tipoconta < 1) {
				System.out.println("Tipo de conta invalido");
			}
			else {
				int nib = Banco.criarConta(cliente, tipoconta);
				System.out.println(Banco.obterConta(nib).mostrarInformacoes());
				System.out.println("A conta do cliente foi criada com sucesso");
			}
		}
		else {
			System.out.println("O cliente com o id inserido não foi encontrado com sucesso");
		}
	}
	
	public static void DesactivarConta() {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Qual o id do cliente que pretende operar?");
		int userid = teclado.nextInt();
		
		Cliente cliente = Banco.procurarCliente(userid);
		if(cliente != null) {
			System.out.println("Insira o nib da conta a desactivar");
			int nib = teclado.nextInt();
			Conta conta = cliente.obterConta(nib);
			if(conta != null) {
				conta.desactivar();
				System.out.println("Conta desactivada");
			}
			else {
				System.out.println("Conta inexistente");
			}
		}
		else {
			System.out.println("O cliente com o id inserido não foi encontrado com sucesso");
		}
	}
	
	public static void ListarClientes() {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Insira o nome do cliente a listar (Enter para listar todos)");
		String criterionome = teclado.nextLine();
		
		System.out.println("Listagem de Clientes:");
		Banco.listarClientes(criterionome);
	}
	
	public static void main(String[] args) {
		Banco.iniciar();
		processaMenuBalcao();
		Banco.guardarDados();
	}
}
