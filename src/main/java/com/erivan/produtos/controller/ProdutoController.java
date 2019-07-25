package com.erivan.produtos.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erivan.produtos.model.Produto;
import com.erivan.produtos.repository.ProdutosRepository;

@RestController
public class ProdutoController {
	
	@Autowired
	ProdutosRepository produtoRepository;
	
	//listar todos
	@GetMapping("/produtos")
	// @RequestParam são parametros que serão passados na requisição
	public Page<Produto> listaProduto(@PageableDefault(sort = "codigo", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao){
		// paginacao e ordenacao:  http://localhost:9000/api/produtos?page=0&size=3&sort=nome,asc
		//  http://localhost:9000/api/produtos?page=0&size=3&sort=nome,asc&sort=valor,desc
	    return produtoRepository.findAll(paginacao);
	}
	
	//ordena os produtos
	@GetMapping("/produtosOrdenados")
	public List<Produto> listaProdutosOrdenados(){
		return produtoRepository.ordenaPelaOrdemCrescente();
	}
	
	//cadastrar produto
	@PostMapping("/produto")
	public Produto salvarProduto(@RequestBody @Valid Produto produto) {
		return produtoRepository.save(produto);
	}
	
	//perfil produto
	@GetMapping("/produto/{codigo}")
	public Produto listaProdutoUnico(@PathVariable(value="codigo") long codigo) {
		return produtoRepository.findByCodigo(codigo);
	}
	
	//atualizar produto
	@PutMapping("/produto")
	public Produto atualizaProduto(@RequestBody @Valid Produto produto) {
		return produtoRepository.save(produto);
	}
	

	
	@DeleteMapping("/produto")
	public void deletaProduto(@RequestBody @Valid Produto produto) {
		produtoRepository.delete(produto);
	}
	
}
