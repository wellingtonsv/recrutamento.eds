package br.com.recrutamento.eds;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe responsável pela criação e recuperação dos carrinhos de compras.
 */
public class CarrinhoComprasFactory {
	
	private Map<String, CarrinhoCompras> session = new LinkedHashMap<String, CarrinhoCompras>();

	public CarrinhoComprasFactory() {
	}

    /**
     * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
     *
     * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
     *
     * @param identificacaoCliente
     * @return CarrinhoCompras
     */
	public CarrinhoCompras criar(String identificacaoCliente) {

		if (null == identificacaoCliente || identificacaoCliente.isEmpty()) {
			return null;
		}

		CarrinhoCompras carrinho = session.get(identificacaoCliente);

		if (null == carrinho) {
			carrinho = new CarrinhoCompras(identificacaoCliente);
			session.put(identificacaoCliente, carrinho);
		}

		return carrinho;
	}

    /**
     * Retorna o valor do ticket médio no momento da chamada ao método.
     * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
     * pela quantidade de carrinhos de compra.
     * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
     * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
     *
     * @return BigDecimal
     */
	public BigDecimal getValorTicketMedio() {
		
		BigDecimal somatorioVlCarrinho = session.values().stream().map(carrinho -> carrinho.getValorTotal())
				.reduce((k, w) -> k.add(w)).get();
		BigDecimal valorTicketMedio =
				somatorioVlCarrinho.divide(new BigDecimal(session.size()), 2, RoundingMode.HALF_UP);
		if (valorTicketMedio != null) {
			return valorTicketMedio;
		}
		return BigDecimal.ZERO;
	}

    /**
     * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
     * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
     *
     * @param identificacaoCliente
     * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
     * e false caso o cliente não possua um carrinho.
     */
    public boolean invalidar(String identificacaoCliente) {
    	
    	if(null == identificacaoCliente || identificacaoCliente.isEmpty()) {
    		return false;
    	}
    	
    	CarrinhoCompras carrinho = session.get(identificacaoCliente);

		if (carrinho == null) {
			return false;
		}
		else {
			session.remove(identificacaoCliente);
			return true;
		}
    }
}