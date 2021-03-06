package model.service;

import java.util.List;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Categoria;
import model.entities.Product;
import model.exception.ListaVaziaException;
import model.exception.ProductoException;
import model.util.FormatarTabela;

public class ProductService {

    private ProductDao dao;
    private Categoria cat;

    public ProductService(Categoria cat) {
        this.cat = cat;
        this.dao = DaoFactory.createProductDao(cat);
    }

    public Categoria getCat() {
        return cat;
    }

    //---------------------"CRUD"-------------------------//
    public boolean inserir(Product p) {
        try {
            dao.inserir(p);
            cat.adicionarProduto(p);
            return true;
        } catch (DbException e) {
            return false;
        }
    }

    public boolean atualizar(Product p) {
        try {
            dao.atualizar(p);
            return true;
        } catch (DbException e) {
            return false;
        }
    }

    public boolean deletar(Product p) {
        try {
            dao.deletById(p.getId());
            cat.deletarProduto(p);
            return true;
        } catch (DbException e) {
            return false;
        }
    }

    public void editarNome(Product p, String nome) {
        p.setNome(nome);
        dao.atualizar(p);
    }

    public void editarPrecoEstipulado(Product p, double value) {
        p.setPrecoEstipulado(value);
        dao.atualizar(p);
    }

    public void editarPrecoReal(Product p, double value) {
        p.setPrecoReal(value);
        dao.atualizar(p);
    }

    public void marcarComoConcluido(Product p, double value) {
        p.setPrecoReal(value);
        if (!(p.getId() == null)) {
            p.setComprado(true);
            dao.atualizar(p);
        } else {
            p.setComprado(true);
        }
    }

    public void marcarComoNaoConcluido(Product p, double value) {
        p.setPrecoReal(value);
        if (!(p.getId() == null)) {
            p.setComprado(false);
            dao.atualizar(p);
        } else {
            p.setPrecoReal(value);
        }
    }

    public void mudarCategoria(Product p, Categoria cat) {
        p.setCategoria(cat);
        dao.atualizar(p);
    }

    // ---------------------------Listas-------------------------------//

    public List<Product> findAllProduct() {
        return dao.findAll();
    }

    /**
     * @throws ListaVaziaException("Ops, parece que voc� n�o tem nenhum produto na
     * lista.");
     */
    public void listarPordutos() throws ListaVaziaException {
        List<Product> list = dao.findAll();
        if (list.isEmpty()) {
            throw new ListaVaziaException("Ops, parece que voc� n�o tem nenhum produto na lista.");
        }
        int maxLenName = FormatarTabela.maxLenghtName(list) + 2;
        FormatarTabela.printInvoiceHeader(maxLenName + 2, 6);
        for (int i = 0; i < list.size(); i++) {
            FormatarTabela.printInvoice(list.get(i), maxLenName + 2, 5, (i + 1));
        }
    }

    public void listarNaoConcluidos() throws ListaVaziaException {
        List<Product> list = this.getProdutosNaoConcluidos();
        int maxLenName = FormatarTabela.maxLenghtName(list) + 2;
        FormatarTabela.printInvoiceHeader(maxLenName + 2, 5);
        for (int i = 0; i < list.size(); i++) {
            if (!(list.get(i).isComprado())) {
                FormatarTabela.printInvoice(list.get(i), maxLenName + 2, 5, (i + 1));
            }
        }
    }

    public void listarConcluidos() throws ListaVaziaException {
        List<Product> list = this.getProdutosConcluidos();
        int maxLenName = FormatarTabela.maxLenghtName(list) + 2;
        FormatarTabela.printInvoiceHeader(maxLenName + 2, 5);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isComprado()) {
                FormatarTabela.printInvoice(list.get(i), maxLenName + 2, 5, (i + 1));
            }
        }
    }

    public List<Product> getProdutosNaoConcluidos() throws ListaVaziaException {
        List<Product> list = dao.findAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isComprado()) {
                list.remove(i);
                i--;
            }
        }
        if (list.isEmpty()) {
            throw new ListaVaziaException();
        }
        return list;
    }

    public List<Product> getProdutosConcluidos() throws ListaVaziaException {
        List<Product> list = dao.findAll();
        for (int i = 0; i < list.size(); i++) {
            if (!(list.get(i).isComprado())) {
                list.remove(i);
                i--;
            }
        }
        if (list.isEmpty()) {
            throw new ListaVaziaException();
        }
        return list;
    }

    // -----------------------------SOMAS--------------------------------------//
    public double getValorRealGasto() throws ListaVaziaException {
        double sum = 0;
        List<Product> list = this.getProdutosConcluidos();
        for (Product p : list) {
            sum += p.getPrecoReal();
        }
        return sum;
    }

    public double getValorGastoEstipulado() throws ListaVaziaException {
        double sum = 0;
        List<Product> list = dao.findAll();
        for (Product p : list) {
            sum += p.getPrecoEstipulado();
        }
        return sum;
    }

    public double getValorTotalAtual() {
        double sum = 0;
        List<Product> list = dao.findAll();
        for (Product p : list) {
            if (p.getPrecoReal() != 0) {
                sum += p.getPrecoReal();
            } else {
                sum += p.getPrecoEstipulado();
            }
        }
        return sum;
    }

    public double getValorEstipuladoRestante() {
        double sum = 0;
        for (Product p : this.getProdutosNaoConcluidos()) {
            sum += p.getPrecoEstipulado();
        }
        return sum;
    }

    // ----------------------------�teis---------------------------------------//

    public double getValorEconomizado() throws ListaVaziaException {
        double total = 0;
        List<Product> list = this.getProdutosConcluidos();
        for (Product p : list) {
            total += p.getPrecoEstipulado() - p.getPrecoReal();
        }
        return total;
    }

    public double getValorDisponivelParaCompra(Categoria cat) throws NullPointerException {
        double total = 0;
        double orcamento = cat.getOrcamento();
        total = orcamento - this.getValorRealGasto();
        return total;
    }

    //-------------------------------------------------------------------//

    /**
     * @throws ProductoException ( "Parece n�o existe nenhum produto com n�mero "
     * + num + ". Verifique a tabela e tente
     * novamente");
     * @throws ListaVaziaException ("Ops, parece que voc� n�o tem nenhum produto na
     * lista.");
     */
    public Product getProdutoByNumer(int num) throws ProductoException, ListaVaziaException {
        List<Product> list = dao.findAll();
        if (list.isEmpty()) {
            throw new ListaVaziaException("Ops, parece que voc� n�o tem nenhum produto na lista.");
        }
        if (num > list.size()) {
            throw new ProductoException("Parece n�o existe nenhum produto com n�mero " + num + ". Verifique a tabela e tente novamente");
        }
        return list.get(num - 1);
    }
}
