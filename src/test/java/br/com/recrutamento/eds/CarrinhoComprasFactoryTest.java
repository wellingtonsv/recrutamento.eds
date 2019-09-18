package br.com.recrutamento.eds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static br.com.recrutamento.eds.StringUtils.EMPTY;

import java.math.BigDecimal;

public class CarrinhoComprasFactoryTest {

	private CarrinhoComprasFactory buyFactory;
	
	@Before
	public void setup() {
		buyFactory = new CarrinhoComprasFactory();
	}
	
	@Test
	public void naoDeveCriarCarrinhoSemIdentificacaoClienteNulo() {
		CarrinhoCompras carrinho = buyFactory.criar(null);
		Assert.assertNull(carrinho);
	}
	
	@Test
	public void naoDeveCriarCarrinhoSemIdentificacaoClienteVazio() {
		CarrinhoCompras carrinho = buyFactory.criar(EMPTY);
		Assert.assertNull(carrinho);
	}
	
	@Test
	public void deveCriarCarrinhoComSucesso() {
		String idCliente = "7001A";
		CarrinhoCompras carrinho = buyFactory.criar(idCliente);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAdcionarProdutoIvalidoNoCarrinho(){
		try {
			String idCliente = "7001A";
			CarrinhoCompras carrinho = buyFactory.criar(idCliente);
			Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
			adicionarProdutoInvalido(carrinho);
		} catch (IllegalArgumentException ilxae) {
			Assert.assertTrue("Deve conter mensagem", !ilxae.getMessage().isEmpty());
			Assert.assertEquals("A mensagem deve ser iguais","Falha no carrinho: O produto não foi informado.",ilxae.getMessage());
			throw new IllegalArgumentException(ilxae);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAdcionarProdutoComIdIvalidoNoCarrinho() {
		try {
			String idCliente = "7001A";
			CarrinhoCompras carrinho = buyFactory.criar(idCliente);
			Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
			adicionarProdutoIdInvalido(carrinho);
		} catch (IllegalArgumentException ilxae) {
			Assert.assertTrue("Deve conter mensagem", !ilxae.getMessage().isEmpty());
			Assert.assertEquals("A mensagem deve ser iguais","Falha no carrinho: O Id do produto não foi informado.",ilxae.getMessage());
			throw new IllegalArgumentException(ilxae);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAdcionarProdutoComValorInvalidoNoCarrinho() {
		try {
			String idCliente = "7001B";
			CarrinhoCompras carrinho = buyFactory.criar(idCliente);
			Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
			adicionarProdutoComValorInvalido(carrinho);
		} catch (IllegalArgumentException ilxae) {
			Assert.assertTrue("Deve conter mensagem", !ilxae.getMessage().isEmpty());
			Assert.assertEquals("A mensagem deve ser iguais","Falha na adição de Item: O valor unitário está inválido.",ilxae.getMessage());
			throw new IllegalArgumentException(ilxae);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAdcionarProdutoComValorMenorIgualZeroNoCarrinho() {
		try {
			String idCliente = "7001V";
			CarrinhoCompras carrinho = buyFactory.criar(idCliente);
			Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
			adicionarProdutoComValorNegativo(carrinho);
		} catch (IllegalArgumentException ilxae) {
			Assert.assertTrue("Deve conter mensagem", !ilxae.getMessage().isEmpty());
			Assert.assertEquals("A mensagem deve ser iguais","Falha na adição de Item: O valor unitário não pode ser negativo.",ilxae.getMessage());
			throw new IllegalArgumentException(ilxae);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAdcionarProdutoComQuantidadeInvalidaNoCarrinho() {
		try {
			String idCliente = "7001V";
			CarrinhoCompras carrinho = buyFactory.criar(idCliente);
			Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
			adicionarProdutoComQauntidadeInvalida(carrinho);
		} catch (IllegalArgumentException ilxae) {
			Assert.assertTrue("Deve conter mensagem", !ilxae.getMessage().isEmpty());
			Assert.assertEquals("A mensagem deve ser iguais","Falha no carrinho: A quantidade deve ser maior que zero(0).",ilxae.getMessage());
			throw new IllegalArgumentException(ilxae);
		}
	}
	
	@Test
	public void deveAdicionarProdutoNoCarrinhoComSucesso() {
		String idCliente = "7002AAA";
		CarrinhoCompras carrinho = buyFactory.criar(idCliente);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
		Produto p = criarProduto(1L);
		adicionarProduto(carrinho, p, 1);
		Assert.assertNotNull("Não deve ser vazio.", carrinho.getItens());
		for(Item item:carrinho.getItens()) {
			Produto produtoSalvo = item.getProduto();
			Assert.assertEquals("O id do produto devem ser iguais", p.getCodigo(), produtoSalvo.getCodigo());
			Assert.assertNotNull("A descrição do produto não deve ser vazia.", produtoSalvo.getDescricao());
			Assert.assertEquals("A descrição do produto devem ser iguais", p.getDescricao(), produtoSalvo.getDescricao());
		}
	}
	
	@Test
	public void deveCalcularValorTicketMedio() {
		
		String idCliente = "7003ABC";
		CarrinhoCompras carrinho = buyFactory.criar(idCliente);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
		Produto p = criarProduto(1L);
		adicionarProduto(carrinho, p, new BigDecimal(50.50), 1);
		
		String idCliente2 = "7004ABC";
		CarrinhoCompras carrinho2 = buyFactory.criar(idCliente2);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
		Produto p2 = criarProduto(2L);
		adicionarProduto(carrinho2, p2, new BigDecimal(33.30), 2);
		
		String idCliente3 = "7005ABC";
		CarrinhoCompras carrinho3 = buyFactory.criar(idCliente3);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
		Produto p3 = criarProduto(3L);
		adicionarProduto(carrinho3, p3, new BigDecimal(25), 3);

		BigDecimal valorTicketMedio = buyFactory.getValorTicketMedio();

		Assert.assertEquals(valorTicketMedio, new BigDecimal("64.03"));
	}
		
	@Test
	public void naoDeveInvalidarCarrinhoIdClienteInvalido() {
		// Arrange
		String idCliente = "7015ACB";
		CarrinhoCompras carrinho = buyFactory.criar(idCliente);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
		
		boolean resultado = buyFactory.invalidar(null);
		
		Assert.assertFalse(resultado);
	}
	
	@Test
	public void naoDeveInvalidarCarrinhoIdClienteInexistente() {
		// Arrange
		String idCliente = "7015ACB";
		CarrinhoCompras carrinho = buyFactory.criar(idCliente);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
		
		boolean resultado = buyFactory.invalidar("7015ACB-INEXISTENTE");
		
		Assert.assertFalse(resultado);
	}
	
	@Test
	public void deveInvalidarCarrinhoIdClienteEhValido() {
		// Arrange
		String idCliente = "7016ACB";
		CarrinhoCompras carrinho = buyFactory.criar(idCliente);
		Assert.assertNotNull("O Carrinho não deve está nulo.", carrinho);
		
		boolean resultado = buyFactory.invalidar(idCliente);
		
		Assert.assertTrue(resultado);
	}
	
	//Mocks
	public void adicionarProdutoInvalido(final CarrinhoCompras carrinho) {
		carrinho.adicionarItem(null, null, 0);
	}
	
	public void adicionarProdutoIdInvalido(final CarrinhoCompras carrinho) {
		Produto p = new Produto(-1L, "Descricao_Produto_ID_Inválido");
		carrinho.adicionarItem(p, null, 0);
	}
	
	public void adicionarProdutoComValorInvalido(final CarrinhoCompras carrinho) {
		Produto p = criarProduto(1L);
		carrinho.adicionarItem(p, null, 0);
	}
	
	public void adicionarProdutoComValorNegativo(final CarrinhoCompras carrinho) {
		Produto p = criarProduto(1L);
		carrinho.adicionarItem(p, new BigDecimal(-1), 0);
	}
	
	public void adicionarProdutoComQauntidadeInvalida(final CarrinhoCompras carrinho) {
		Produto p = criarProduto(1L);
		carrinho.adicionarItem(p, new BigDecimal(1), 0);
	}
	
	public void adicionarProduto(final CarrinhoCompras carrinho, final Produto p, int quantidade) {
		carrinho.adicionarItem(p, new BigDecimal(1), quantidade);
	}
	
	public void adicionarProduto(final CarrinhoCompras carrinho, final Produto p, final BigDecimal valor, int quantidade) {
		carrinho.adicionarItem(p, valor, quantidade);
	}
	
	public Produto criarProduto(final Long codigo) {
		Produto produto = new Produto(codigo, "Descricao_Produto_1");
		return produto;
	}
}