package com.arthyon.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.arthyon.cursomc.domain.Categoria;
import com.arthyon.cursomc.domain.Cidade;
import com.arthyon.cursomc.domain.Cliente;
import com.arthyon.cursomc.domain.Endereco;
import com.arthyon.cursomc.domain.Estado;
import com.arthyon.cursomc.domain.ItemPedido;
import com.arthyon.cursomc.domain.Pagamento;
import com.arthyon.cursomc.domain.PagamentoComBoleto;
import com.arthyon.cursomc.domain.PagamentoComCartao;
import com.arthyon.cursomc.domain.Pedido;
import com.arthyon.cursomc.domain.Produto;
import com.arthyon.cursomc.domain.enums.EstadoPagamento;
import com.arthyon.cursomc.domain.enums.TipoCliente;
import com.arthyon.cursomc.repositories.CategoriaRepository;
import com.arthyon.cursomc.repositories.CidadeRepository;
import com.arthyon.cursomc.repositories.ClienteRepository;
import com.arthyon.cursomc.repositories.EnderecoRepository;
import com.arthyon.cursomc.repositories.EstadoRepository;
import com.arthyon.cursomc.repositories.ItemPedidoRepository;
import com.arthyon.cursomc.repositories.PagamentoRepository;
import com.arthyon.cursomc.repositories.PedidoRepository;
import com.arthyon.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository repoCat;
	
	@Autowired
	private ProdutoRepository repoProd;
	
	@Autowired
	private EstadoRepository repoEst;
	
	@Autowired
	private CidadeRepository repoCid;

	@Autowired
	private ClienteRepository repoCli;

	@Autowired
	private EnderecoRepository repoEnd;
	
	@Autowired
	private PedidoRepository repoPed;
	
	@Autowired
	private PagamentoRepository repoPag;
	
	@Autowired
	private ItemPedidoRepository repoItemPed;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");				
		
		Produto p1 = new Produto(null, "Computador", 2000.00); 
		Produto p2 = new Produto(null, "Impressora", 800.0); 
		Produto p3 = new Produto(null, "Mouse", 60.0);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		repoCat.saveAll(Arrays.asList(cat1, cat2));
		repoProd.saveAll(Arrays.asList(p1, p2, p3));
		repoEst.saveAll(Arrays.asList(est1, est2));
		repoCid.saveAll(Arrays.asList(cid1, cid2, cid3));
		repoCli.saveAll(Arrays.asList(cli1));
		repoEnd.saveAll(Arrays.asList(e1, e2));
		repoPed.saveAll(Arrays.asList(ped1, ped2));
		repoPag.saveAll(Arrays.asList(pagto1, pagto2));
		repoItemPed.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
