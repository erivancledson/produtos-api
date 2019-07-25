package com.erivan.produtos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erivan.produtos.model.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Long>{

	Produto findByCodigo (long codigo);
	
	//ordena pela ordem crescente
	List<Produto> ordenaPelaOrdemCrescente();
}
