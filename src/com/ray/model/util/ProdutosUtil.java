package com.ray.model.util;

import java.text.NumberFormat;

import com.ray.model.entities.Categoria;
import com.ray.model.entities.User;
import com.ray.model.exception.ListaVaziaException;
import com.ray.model.service.ProductService;

public class ProdutosUtil {

    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
   
    // -------------------- M�TODOS FUN��ES �TEIS ----------------------//
    /**
     * @apiNote Exceptions tratadas nesse m�todo: NullPointerException,
     *          ListaVaziaException
     */
    public static String quantidadeGasta(ProductService service, Categoria cat) {
	try {
	    double orcamento = cat.getOrcamento();
	    double valorGasto = service.getValorRealGasto();
	    String complemento = ". ";
	    if (orcamento == 0) {
		complemento = " E voc� n�o tem um orcamento para essa lista";
	    } else {
		if (valorGasto > orcamento) {
		    complemento += "Voc� j� ultrapassou seu or�amento em R$" + (valorGasto - orcamento);
		} else {
		    complemento += "Voc� ainda tem " + currencyFormatter.format((orcamento - valorGasto))
			    + " para gastar de acordo com seu or�amento de " + currencyFormatter.format(orcamento);
		}
	    }
	    return "Voc� j� gastou " + currencyFormatter.format(valorGasto) + complemento;
	} catch (NullPointerException e) {
	    return "Voc� n�o tem or�amento para esta lista. Adicione um no menu principal";
	} catch (ListaVaziaException e) {
	    return "Nenhum produto comprado at� o momento, sendo assim, voc� n�o gastou nada.";
	}
    }

    /**
     * @apiNote Exceptions tratadas nesse m�todo: NullPointerException,
     *          ListaVaziaException
     */
    public static String disponivelParaComprar(ProductService service, Categoria cat) {
	 Double orcamento = 0.0;
	 double somaTotalNaoConcluidos = service.getValorEstipuladoRestante();
	 if(somaTotalNaoConcluidos == 0 && !service.listIsEmpty()) {
	     return "Todos os produtos foram comprados :)";
	 }
	try {
	    double disponivel = 0;
	    String complemento = "";
	    orcamento = cat.getOrcamento();
	    if (orcamento == 0.0 || orcamento == null) {
		throw new NullPointerException();
	    }
	    disponivel = orcamento - service.getValorRealGasto();
	    if (disponivel < 0) {
		complemento = HtmlColors.RED +" Ixi! Voc� passou do seu orcamento em " + currencyFormatter.format((-(disponivel)));
		complemento += ". Voc� n�o tem mais nada dispon�vel para gastar";
		complemento += ". Or�amento para lista " + cat.getName() + ": "
			+ currencyFormatter.format(orcamento);
	    } else {
		if(disponivel > 0) {
		complemento = HtmlColors.GREEN +" Voc� tem dispon�vel " + currencyFormatter.format(disponivel)
			+ ", de acordo com seu or�amento para lista " + cat.getName();
		double valorTotalNaoConcluidos = disponivel - somaTotalNaoConcluidos;
		if(valorTotalNaoConcluidos != 0) {
		   
		    if (valorTotalNaoConcluidos < 0) {
			complemento += HtmlColors.RED + "<br> Se comprar todos os produtos da lista, passar� em " + currencyFormatter.format(-(valorTotalNaoConcluidos)) + " do seu orcamento";
		    }else {
			complemento += "<br> Se comprar todos os produtos da lista, ficar� com " + currencyFormatter.format(valorTotalNaoConcluidos);
		    }
		}
		}else if (disponivel == 0 && somaTotalNaoConcluidos == 0){
		complemento += "Nada! Voc� atingiu sua meta e comprou todos os produtos exatamente de acordo com seu or�amento! Seguiu a lista risca, Parab�ns!";
		}else {
		complemento += "Voc� antigiu seu or�amento e a partir de agora, tudo passar� de seu orcamento"
			+ HtmlColors.RED + "<br>Comprando tudo, ao final, passar� em " + currencyFormatter.format(somaTotalNaoConcluidos) + " do seu or�amento."
				+ "<br>Totalizando " + currencyFormatter.format(somaTotalNaoConcluidos + service.getValorRealGasto());
		}
	    }
	    complemento += HtmlColors.BLACK;
	    return complemento;
	} catch (ListaVaziaException e) {
	    if (orcamento == 0.0 || orcamento == null) {
		return "Voc� n�o tem or�amento para esta lista, portanto, imposs�vel saber quanto ainda tem dispon�vel para compra :( . Adicione um or�amento no menu principal";
	    } else 
		return e.getMessage().isEmpty() || e.getMessage().equals("Puxa, nenhum produto foi comprado at� o momento :(") ? "Voc� ainda n�o comprou nenhum produto da lista. Ent�o voc� ainda tem "
			+ currencyFormatter.format(orcamento) + " dispon�vel para gastar" : e.getMessage();
	} catch (NullPointerException e) {
	    return "Voc� n�o tem or�amento para esta lista. Por isso, infelizmente, n�o � poss�vel saber o quanto voc� ainda tem dispon�vel. Adicione um or�amento no menu principal.";
	}
    }


    
    /**
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException
     */
    public static String valorEconomizado(ProductService service) {
	try {
	    double valorEconomizado = service.getValorEconomizado();
	    if (valorEconomizado < 0) {
		return HtmlColors.RED +"Eita! Voc� n�o economizou nada! Voc� gastou " + currencyFormatter.format((-(valorEconomizado)))
			+ " a mais do que planejava" + HtmlColors.BLACK;
	    }else if(valorEconomizado == 0) {
		return "At� o momento, voc� est� seguindo sua lista a risca! N�o economizou nada e tamb�m n�o gastou mais do que deveria. Est� indo bem!";
	    }
	    return HtmlColors.GREEN + "Voc� economizou " + currencyFormatter.format(valorEconomizado) + " Parab�ns!" + HtmlColors.BLACK;
	} catch (ListaVaziaException e) {
	    return e.getMessage().isEmpty() || e.getMessage().equals("Puxa, nenhum produto foi comprado at� o momento :(") ? "Voc� ainda n�o comprou nenhum produto da lista. No momento, imposs�vel saber valor economizado :(" : e.getMessage();
	}
    }

    /**
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException
     */
    public static void listarNaoConcluidos(ProductService service) {
	try {
	    service.listarNaoConcluidos();
	} catch (ListaVaziaException e) {
	    System.out.println("Todos os produtos da lista foram comprados :)");
	}
    }

    /**
     * @apiNote Exceptions tratadas nesse m�todo: ListaVaziaException
     */
    public static void listarConcluidos(ProductService service) {
	try {
	    service.listarConcluidos();
	} catch (ListaVaziaException e) {
	    System.out.println("Nenhum produto da lista foi comprado :(");
	}
    }

  
    public static void mostrarSomaTotal(ProductService service) {
	System.out.println("Valor Total Estipulado: " + currencyFormatter.format(service.getValorTotalEstipulado()));
	System.out.println("Valor Total: " + currencyFormatter.format(service.getValorTotalAtual()));
    }
    
    public static String getTotalEstipuladoHtml(ProductService service) {
	return HtmlColors.BLUE + currencyFormatter.format(service.getValorTotalEstipulado()) + HtmlColors.BLACK;
    }
    
    public static String getValorTotalHtml(ProductService service) {
   	return HtmlColors.BLUE + currencyFormatter.format(service.getValorTotalAtual()) + HtmlColors.BLACK;
    }
    
    
    public static String mostrarInfosProdutos(User user, ProductService service, double orcamento) {
	int qntProdutos = 0, qntProdutosComprados = 0;
	double valorRealGasto = 0, valorEstipulado = service.getValorTotalEstipulado();
	double valorEstipuladoRestante = 0;
	try {
	    qntProdutos = service.findAllProduct().size();
	} catch (ListaVaziaException e) {
	    qntProdutos = 0;
	}
	try {
	    qntProdutosComprados = service.getProdutosConcluidos().size();
	} catch (ListaVaziaException e) {
	    qntProdutosComprados = 0;
	}
	try {
	    valorRealGasto = service.getValorRealGasto();
	} catch (ListaVaziaException e) {
	    valorRealGasto = 0;
	}
	try {
	    valorEstipuladoRestante = service.getValorEstipuladoRestante();
	} catch (ListaVaziaException e) {
	    valorEstipuladoRestante = 0;
	}
	
	StringBuilder infos = new StringBuilder();
	infos.append(!(qntProdutos == 1) ? "Voc� possui <strong>" + qntProdutos + "</strong> produtos na lista atual" : "Voc� possui <strong>" + qntProdutos + "</strong> produto na lista atual");
	infos.append("<br>");
	infos.append(!(qntProdutosComprados == 1) ? "Voc� j� comprou <strong>" + qntProdutosComprados + "</strong> produtos de um total de <strong>" + qntProdutos + "</strong>" : "Voc� j� comprou <strong>" + qntProdutosComprados + "</strong> produto de um total de <strong>" + qntProdutos + "</strong>" );
	infos.append("<br>");
	infos.append("Voc� j� gastou <strong>" + currencyFormatter.format(valorRealGasto)+ "</strong>");
	infos.append("<br>");
	infos.append("Falta gastar <strong>" + currencyFormatter.format(valorEstipuladoRestante) + "</strong>");
	infos.append("<br>");
	infos.append("O valor estipulado atual � de <strong>" + currencyFormatter.format(valorEstipulado) + "</strong>");
	infos.append("<br>");
	infos.append("O valor total atual � de <strong>" + currencyFormatter.format(service.getValorTotalAtual()) + "</strong>");
	infos.append("<br>");
	infos.append("Or�amento: <strong>" + currencyFormatter.format(orcamento) + "</strong>");
	infos.append("<br>");
	return infos.toString();
    }
    
  /*  public static String quantidadeProdutos(ProductService service, double orcamento) {
	int qntProdutos = (int) tratarErro(service.findAllProduct().size());
	return (!(qntProdutos == 1) ? "Voc� possui <strong>" + qntProdutos + "</strong> produtos na lista atual" : "Voc� possui <strong>" + qntProdutos + "</strong> produto na lista atual");
    }
    
    private static Number tratarErro(Number someFunction) {
	try {
	   return someFunction;
	} catch (ListaVaziaException e) {
	    return 0;
	}
    }*/
}
