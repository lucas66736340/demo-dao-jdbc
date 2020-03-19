package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	
	
		//insere um Vendedor no banco de dados
		void isert(Seller obj);
		void update(Seller obj);
		void deleteById(Integer id);
		//busca no banco de dados um vendedor pelo id e retorna o mesmo
		Seller findById(Integer id);
		//retorna uma lista com todos os vendedores 
		List<Seller> findAll();
	
}
