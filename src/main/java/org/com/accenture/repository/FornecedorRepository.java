package org.com.accenture.repository;

import org.com.accenture.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByDocumento(String documento);
    
    Optional<Fornecedor> findByDocumento(String documento);
    
    @Query("SELECT f FROM Fornecedor f WHERE " +
           "(:nome IS NULL OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:documento IS NULL OR f.documento LIKE CONCAT('%', :documento, '%'))")
    List<Fornecedor> findByNomeAndDocumento(@Param("nome") String nome, @Param("documento") String documento);
} 