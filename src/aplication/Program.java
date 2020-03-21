package aplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Scanner ler = new Scanner(System.in);
		
		// instanciando um dao a partir da Fabrica de dao
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		

		// usar a interface caso a regra de negocio mude no futuro
		Seller vendedor = sellerDao.findById(3);

		System.out.println(vendedor);

		// encontrando vendedor pelo departamento
		List<Seller> lista = sellerDao.findByDepartment(new Department(2, null));
		System.out.println("Lista de vendedores por departamento");

		for (Seller seller : lista) {
			System.out.println(seller);
		}

		// recebendo uma lista de todos os vendedores
		List<Seller> lista2 = sellerDao.findAll();
		System.out.println("Lista de todos os vendedores ");

		for (Seller seller : lista2) {
			System.out.println(seller);
		}

		try {

			// incerindo vendedor no banco
			sellerDao.insert(new Seller(null, "Teobaldo", "teobaldo@gmail.com", sdf.parse("16/02/1998"), 4000.0,
					new Department(2, null)));
			System.out.println("Vendendor inserido");
		} catch (ParseException e) {
			throw new DbException(e.getMessage());
		}

		// atualizando os dados de um vendedor
		Seller seler2 = sellerDao.findById(1); // pegando vendedor
		seler2.setName("Lucas");
		seler2.setEmail("lucas.trabalho1533@gmail.com");
		try {
			seler2.setBirthDate(sdf.parse("16/02/1998"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		seler2.setBaseSalary(9000.0);
		seler2.setDepartment(new Department(2, null));

		sellerDao.update(seler2);
		System.out.println("Atualização comcluida");
		
		System.out.println("Digite o id do vendedore para deletar:" );
		int id = ler.nextInt();
		sellerDao.deleteById(id);
		
		System.out.println("Vendedor id: " +id+" Deletado com sucesso");
		
		ler.close();

	}

}
