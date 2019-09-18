package br.com.recrutamento.eds;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CarrinnhoComprasTest {
	
	private CarrinhoCompras buyCart;

	@Before
	public void setup() {
		buyCart = new CarrinhoCompras("5001-XPTO");
	}

	@Test
	public void deveAdcionarUmItemNoCarrinhoComSucesso() {

		Produto produto = new Produto(1L, "Descricao_Produto_1");
		BigDecimal valorUnitario = new BigDecimal(50);
		int quantidade = 50;

		BigDecimal valorTotalEsperado = new BigDecimal(2500);

		buyCart.adicionarItem(produto, valorUnitario, quantidade);

		BigDecimal valorRetornado = buyCart.getValorTotal();
		Assert.assertEquals("O valor total devem ser iguais.",valorTotalEsperado, valorRetornado);
	}

	@Test
	public void deveAdicionarDoisDiferenteItensComSucesso() {
		
		Produto produto1 = new Produto(1L, "Descricao_Produto_1");
		Produto produto2 = new Produto(2L, "Descricao_Produto_2");
		BigDecimal valorUnitario = new BigDecimal(10);

		BigDecimal valorEsperado = new BigDecimal(500);

		buyCart.adicionarItem(produto1, valorUnitario, 20);
		buyCart.adicionarItem(produto2, valorUnitario, 30);

		BigDecimal valorRetornado = buyCart.getValorTotal();
		Assert.assertEquals("O valor total devem ser iguais.",valorEsperado, valorRetornado);
		Assert.assertFalse("A lista não deve ser vazia.", buyCart.getItens().isEmpty());
		Assert.assertEquals("A lista deve conter dois elementos.",buyCart.getItens().size(), 2);
	}

	@Test
	public void deveAdicionarDoisItensIguaisComSucesso() {

		Produto produto1 = new Produto(1L, "Descricao_Produto");
		Produto produto2 = new Produto(1L, "Descricao_Produto");
		BigDecimal valorUnitario = new BigDecimal(10);

		BigDecimal valorEsperado = new BigDecimal(600);

		buyCart.adicionarItem(produto1, valorUnitario, 20);
		buyCart.adicionarItem(produto2, valorUnitario, 40);

		BigDecimal valorRetornado = buyCart.getValorTotal();
		Assert.assertEquals(valorEsperado, valorRetornado);
		Assert.assertFalse("A lista não deve ser vazia.", buyCart.getItens().isEmpty());
		Assert.assertEquals(buyCart.getItens().size(), 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAdcionarQuantiadeInvalida() {

		Produto produto = new Produto(1L, "Descricao_Produto_1");
		BigDecimal valorUnitario = new BigDecimal(40);
		int quantidade = -4;

		buyCart.adicionarItem(produto, valorUnitario, quantidade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAdcionarValorInvalida() {

		Produto produto = new Produto(2L, "Descricao_Produto_2");
		BigDecimal valorUnitario = new BigDecimal(-1);
		int quantidade = 5;

		buyCart.adicionarItem(produto, valorUnitario, quantidade);
	}

	
	@Test
	public void deveAdcinarItemValidoERemoverUmItem() {

		Produto produto1 = new Produto(1L, "Descricao_Produto_1");
		Produto produto2 = new Produto(2L, "Descricao_Produto_2");
		BigDecimal valorUnitario = new BigDecimal(10);

		buyCart.adicionarItem(produto1, valorUnitario, 2);
		buyCart.adicionarItem(produto2, valorUnitario, 1);

		Assert.assertTrue( buyCart.removerItem(produto1));

		List<Item> itens = new ArrayList<Item>(buyCart.getItens());
		Assert.assertFalse("A lista não deve ser vazia.", itens.isEmpty());
		Produto produtoRestante = itens.get(0).getProduto();
		Assert.assertTrue(produtoRestante.equals(produto2));
	}
	
	@Test
	public void naoDeveRemoverItemInvalido() {

		Produto produto1 = new Produto(1L, "Descricao_Produto_1");
		Produto produtoInvalido = new Produto(2L, "Descricao_Produto_2");
		BigDecimal valorUnitario = new BigDecimal(10);

		buyCart.adicionarItem(produto1, valorUnitario, 2);

		Assert.assertFalse("Não deve remover produto inexistente",buyCart.removerItem(produtoInvalido));

		List<Item> itens = new ArrayList<Item>(buyCart.getItens());
		Assert.assertFalse("A lista não deve ser vazia.", itens.isEmpty());
		Assert.assertEquals(itens.size(), 1);
	}

	@Test
	public void deveRemoverItemValidoPorIndice() {

		final Produto produto1 = new Produto(1L, "Descricao_Produto_1");
		final Produto produto2 = new Produto(2L, "Descricao_Produto_2");
		final BigDecimal valorUnitario = new BigDecimal(10);

		buyCart.adicionarItem(produto1, valorUnitario, 1);
		buyCart.adicionarItem(produto2, valorUnitario, 2);

		boolean removido = buyCart.removerItem(0);

		Assert.assertTrue(removido);

		List<Item> itens = new ArrayList<Item>(buyCart.getItens());
		Assert.assertFalse("A lista não deve ser vazia.", itens.isEmpty());
		Produto produtoRestante = itens.get(0).getProduto();
		Assert.assertTrue(produtoRestante.equals(produto2));
	}
	
	@Test
	public void naoDeveRemoverItemInvalidoPorIndiceInvalido() {

		Produto produto1 = new Produto(1L, "Descricao_Produto_1");
		Produto produto2 = new Produto(2L, "Descricao_Produto_2");
		BigDecimal valorUnitario = new BigDecimal(10);

		buyCart.adicionarItem(produto1, valorUnitario, 1);
		buyCart.adicionarItem(produto2, valorUnitario, 2);

		Assert.assertFalse("Não deve remover nada.",buyCart.removerItem(10));

		List<Item> itens = new ArrayList<Item>(buyCart.getItens());
		Assert.assertEquals(itens.size(), 2);
	}
	
	@Test
	public void devRemoverUmItemPorIndiceEItensDeveSerVazia() {

		Produto produto1 = new Produto(1L, "Descricao_Produto_1");
		BigDecimal valorUnitario = new BigDecimal(10);

		buyCart.adicionarItem(produto1, valorUnitario, 1);

		Assert.assertTrue("Deve ser removido com sucesso.",buyCart.removerItem(0));

		List<Item> itens = new ArrayList<Item>(buyCart.getItens());
		Assert.assertTrue("A lista deve ser vazia.", itens.isEmpty());
		Assert.assertEquals(itens.size(), 0);
	}
}