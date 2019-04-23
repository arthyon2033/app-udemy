package com.arthyon.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arthyon.cursomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
