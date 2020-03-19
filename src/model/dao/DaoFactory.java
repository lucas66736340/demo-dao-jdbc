package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	//classe que instancia obejetos que acesssam dados
	
	//expor somente a interface
	public static SellerDao createSellerDao() {
		//internamente vai instanciar uma implementação 
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	
	
}
