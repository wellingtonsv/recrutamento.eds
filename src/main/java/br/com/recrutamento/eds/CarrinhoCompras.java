package br.com.recrutamento.eds;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
public class CarrinhoCompras {
	
	private List<Item> itens = new LinkedList<Item>();
	private String identificacaoCliente;

    public CarrinhoCompras(String identificacaoCliente) {
		this.identificacaoCliente = identificacaoCliente;
	}
    
	/**
     * Permite a adição de um novo item no carrinho de compras.
     *
     * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
     * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
     * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
     * o passado como parâmetro.
     *
     * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
     *
     * @param produto
     * @param valorUnitario
     * @param quantidade
     */
    public void adicionarItem(Produto produto, BigDecimal valorUnitario, int quantidade) {
    	validarProduto(produto);
		validarValorUnitario(valorUnitario);
		validarQuantidade(quantidade);

		Item item = pesquisaItem(produto);

		if (item != null) {
			atualizarItem(item, valorUnitario, quantidade);
		} 
		else {
			item = new Item(produto, valorUnitario, quantidade);
			itens.add(item);
		}
    }

    /**
     * Permite a remoção do item que representa este produto do carrinho de compras.
     *
     * @param produto
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(Produto produto) {
    	Item item = pesquisaItem(produto);

		if (item == null) {
			return false;
		} else {
			itens.remove(item);
			return true;
		}
    }
    
    /**
     * Permite a remoção do item de acordo com a posição.
     * Essa posição deve ser determinada pela ordem de inclusão do produto na 
     * coleção, em que zero representa o primeiro item.
     *
     * @param posicaoItem
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
	public boolean removerItem(int posicaoItem) {
		// Se o índice for maior ou igual que o tamanho do ArrayList, ele não existe.
		if (posicaoItem >= itens.size()) {
			return false;
		} else {
			itens.remove(posicaoItem);
			return true;
		}
	}

    /**
     * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
     * de todos os itens que compõem o carrinho.
     *
     * @return BigDecimal
     */
	public BigDecimal getValorTotal() {
		BigDecimal valorTotal = BigDecimal.ZERO;
		
		for (Item item : itens) {
			valorTotal = valorTotal.add(item.getValorTotal());
		}
		return valorTotal;
	}

    /**
     * Retorna a lista de itens do carrinho de compras.
     *
     * @return itens
     */
    public Collection<Item> getItens() {
    	return itens;
    }
    
	/**
	 * Atualiza os dados do Item no carrinho.
	 * 
	 * @param item
	 * @param valorUnitario
	 * @param quantidade
	 */
	private void atualizarItem(Item item, BigDecimal valorUnitario, int quantidade) {

		if (!item.getValorUnitario().equals(valorUnitario)) {
			item.setValorUnitario(valorUnitario);
		}
		
		int novaQuantidade = item.getQuantidade() + quantidade;
		item.setQuantidade(novaQuantidade);
	}
	
	/**
	 * Pesquisa se o produto já existe na lista de items do carrinho.
	 * 
	 * @param produto
	 * @return Retorna o item associado ao produto, se não existir o produto retorna null.
	 */
	private Item pesquisaItem(Produto produto) {
		if (null == produto) {
			return null;
		}
		for (Item item : itens) {
			if (item.getProduto().equals(produto)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * Verifica se a quantidade de produto é válida.
	 * 
	 * @param quantidade
	 */
	private void validarQuantidade(int quantidade) {
		if (quantidade < 1) {
			throw new IllegalArgumentException("Falha no carrinho: A quantidade deve ser maior que zero(0).");
		}
	}
	
	/**
	 * Verifica se o valorUnitario do produto é válido.
	 * 
	 * @param valorUnitario
	 */
	private void validarValorUnitario(BigDecimal valorUnitario) {
		if(null == valorUnitario) {
			throw new IllegalArgumentException("Falha na adição de Item: O valor unitário está inválido.");
		}
		
		if (valorUnitario.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Falha na adição de Item: O valor unitário não pode ser negativo.");
		}
	}
	
	/**
	 * Verifica se o Produto que está sendo adicionado ao carrinho não está vazio ou inválido.
	 * 
	 * @param produto
	 */
	private void validarProduto(Produto produto) {
		if (null == produto) {
			throw new IllegalArgumentException("Falha no carrinho: O produto não foi informado.");
		}
		
		if(null != produto.getCodigo() && produto.getCodigo() < 1L) {
			throw new IllegalArgumentException("Falha no carrinho: O Id do produto não foi informado.");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identificacaoCliente == null) ? 0 : identificacaoCliente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrinhoCompras other = (CarrinhoCompras) obj;
		if (identificacaoCliente == null) {
			if (other.identificacaoCliente != null)
				return false;
		} else if (!identificacaoCliente.equals(other.identificacaoCliente))
			return false;
		return true;
	}
}