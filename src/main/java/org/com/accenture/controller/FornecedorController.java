package org.com.accenture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.com.accenture.model.Fornecedor;
import org.com.accenture.model.Empresa;
import org.com.accenture.service.FornecedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/fornecedores")
@Tag(name = "Fornecedores", description = "API para gerenciamento de fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Fornecedor> criarFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok(fornecedorService.criarFornecedor(fornecedor));
    }

    @GetMapping
    @Operation(summary = "Listar fornecedores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedores encontrados com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Fornecedor>> listarFornecedores(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String documento) {
        return ResponseEntity.ok(fornecedorService.listarFornecedores(nome, documento));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fornecedor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }

    @GetMapping("/{id}/empresas")
    @Operation(summary = "Listar empresas de um fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Set<Empresa>> listarEmpresasFornecedor(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.listarEmpresasFornecedor(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Long id, @Valid @RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok(fornecedorService.atualizarFornecedor(id, fornecedor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }
} 