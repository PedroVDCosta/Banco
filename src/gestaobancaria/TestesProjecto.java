package gestaobancaria;
import java.util.ArrayList;
import java.util.Date;

/*
import java.util.ArrayList;
import java.util.Date;
*/
import transaccoes.CapitalizacaoJuros;
import transaccoes.Deposito;
import transaccoes.Levantamento;
import transaccoes.Transaccao;
import transaccoes.Transferencia;

import contas.Conta;
import contas.Debito;
import contas.Prazo;

public class TestesProjecto {

	
	public static void testarClientes(){
		Cliente joao = new Cliente("joao",111,222);
		Cliente pedro = new Cliente("pedro",123,456);
		
		System.out.println(joao.obterInformacoes());
		System.out.println(pedro.obterInformacoes());
	}
	
	
	
	public static void testarContas(){
		Conta deb = new Debito();
		Conta pr = new Prazo();
		

		
		System.out.println("Saldo da conta debito " + deb.obterSaldo());
		deb.depositar(500);
		deb.levantar(150);		
		System.out.println("Informações conta debito " + deb.mostrarInformacoes());
		System.out.println("Nib da conta " + deb.obterNib());
		deb.transferir(100, pr);
		deb.levantar(1200);
		System.out.println("Saldo da conta debito " + deb.obterSaldo());
		
		System.out.println("Saldo da conta prazo " + pr.obterSaldo());
		pr.depositar(900);
		pr.levantar(400);		
		System.out.println("Saldo pr " + pr.obterSaldo());
		System.out.println("Informações conta prazo " + pr.mostrarInformacoes());
		System.out.println("Nib da conta " + pr.obterNib());
		System.out.println("Saldo da conta prazo " + pr.obterSaldo());
	}
	
	
	public static void testarContasComClientes(){
		Cliente joao = new Cliente("joao",111,222);
				
		Conta deb = new Debito();
		Conta pr = new Prazo();
		
		joao.adicionarConta(deb);
		joao.adicionarConta(pr);
		
		ArrayList<Conta> contasjoao = joao.obterContas();
		
		//percorrer todas as contas do joao e mostrarInformacoes
		for (int i = 0; i<contasjoao.size();++i){
			Conta contacorrente = contasjoao.get(i);
			System.out.println(contacorrente.mostrarInformacoes());
		}
		
		Conta contadebitojoao = joao.obterConta(deb.obterNib());
		
		if (contadebitojoao!= null){
			System.out.println("Conta Debito do joao encontrada");
		}
	}
	
	
	
	public static void testarTransaccoes(){
		Conta deb = new Debito();
		Conta deb2 = new Debito();
		
		Transaccao dep = new Deposito(new Date(), deb, 500);
		Transaccao lev = new Levantamento(new Date(), deb, 100);		
		Transaccao cap = new CapitalizacaoJuros(new Date(), deb, 25);
		Transaccao transf = new Transferencia(new Date(), deb, 25,deb2);
		
		System.out.println(dep.mostrar());
		System.out.println(lev.mostrar());
		System.out.println(cap.mostrar());
		System.out.println(transf.mostrar());
		System.out.println();
		System.out.println(dep.obterTipo());
		System.out.println(lev.obterTipo());
		System.out.println(cap.obterTipo());
		System.out.println(transf.obterTipo());
	}
	
	
	
	public static void testarTransaccoesComContas(){
		
		Conta deb = new Debito();
		Conta deb2 = new Debito();
		
		Transaccao dep = new Deposito(new Date(), deb, 500);
		Transaccao lev = new Levantamento(new Date(), deb, 100);		
		Transaccao cap = new CapitalizacaoJuros(new Date(), deb, 25);
		Transaccao transf = new Transferencia(new Date(), deb, 25,deb2);
		
		deb.adicionarTransaccao(dep);
		deb.adicionarTransaccao(lev);
		deb.adicionarTransaccao(cap);
		deb.adicionarTransaccao(transf);
		
		ArrayList<Transaccao> transaccoes = deb.obterTransaccoes();
		
		System.out.println("A conta tem " + transaccoes.size() + " transacções");
		deb.mostrarExtracto();
	}
	
	
	
	public static void testarBancoClientes(){
		Banco.iniciar();
		
		Banco.criarCliente("joao", 123, 456);
		Banco.criarCliente("pedro", 232, 454);
		
		System.out.println("Listar clientes sem criterio \n");
		Banco.listarClientes("");
		
		System.out.println("Listar clientes com criterio 'pedro' \n");
		Banco.listarClientes("pedro");
		
		Cliente cli = Banco.procurarCliente(123);
		System.out.println("Mostrar o cliente com id 123:\n" + cli.obterInformacoes() + "\n"); 
		
		
		Banco.desactivarCliente(123);
		
		System.out.println("Listar clientes após desactivação");
		Banco.listarClientes("");		
	}
	
	
	
	public static void testarBancoContas(){
		Banco.iniciar();
		
		Banco.criarCliente("joao", 123, 456);
		
		Cliente joao = Banco.procurarCliente(123);
		
		
		Banco.criarConta(joao, 1); //Debito
		Banco.criarConta(joao, 2); //Credito
			
		ArrayList<Conta> contasjoao = joao.obterContas();
		
		for (Conta c:contasjoao){
			System.out.println(c.mostrarInformacoes() + "\n");
		}
	}
	
	/*
	public static void testarInteraccaoBanco(){
		Cliente c = new Cliente("joao",123,456);
		
		Conta deb = new Debito();
		Conta pr = new Prazo();
		c.adicionarConta(deb);
		c.adicionarConta(pr);
		InteraccaoBanco.cli =c; 
		
		InteraccaoBanco.processaMenuContas(InteraccaoBanco.cli.obterContas());
	}
	*/	
	public static void main(String[] args) {
		testarClientes();
		testarContas();
		testarContasComClientes();
		testarTransaccoes();
		testarTransaccoesComContas();		
		testarBancoClientes();
		testarBancoContas();
		//testarInteraccaoBanco();
		//obterInformacoes();
		
	}
}
