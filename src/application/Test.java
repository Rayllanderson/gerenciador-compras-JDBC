package application;

import java.util.List;

import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Categoria;
import model.entities.Product;
import model.entities.User;
import model.view.TelaPrincipal;

public class Test {
    
    
    public static void main(String[] args) {
	TelaPrincipal telaPrincipal = new TelaPrincipal();
	CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
	Categoria cat = null;
	/*ProductDao product = DaoFactory.createProductDao();
	
	Product p1 = product.findById(1);
	System.out.println(p1);
	System.out.println("_____________");
	List<Product> list = product.findAll();
	list.forEach(System.out::println);*/
	
	/*User oi = telaPrincipal.logar();
	System.out.println("Logado com sucesso!");
	System.out.println("Bem vindo, " + oi.getName());*/
	
	//teste INSERIR categoria  ----------------- FUNCIONANDO
	/*cat = new Categoria(null, "feira", oi);
	categoriaDao.inserir(cat);*/
	
	//teste FINDBYID --------------------------- FUNCIONANDO
	/*cat = categoriaDao.findById(4);
	System.out.println(cat);*/
	
	//teste FINDALL --------------------------- FUNCIONANDO
	/*List <Categoria> list = categoriaDao.findAll();
	list.forEach(System.out::println);*/
	
	//teste ATUALIZAR -------------------------- FUNCIONANDO
	/*cat = categoriaDao.findById(4);
	cat.setName("compras semanais");
	categoriaDao.atualizar(cat);*/
	
	//teste DELETAR -------------------------- FUNCIONANDO
	//categoriaDao.deletById(5);
	
    }
    
}
