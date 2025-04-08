package org.com.accenture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.com.accenture.model.Empresa;
import org.com.accenture.model.Fornecedor;
import org.com.accenture.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresa", description = "API para gerenciamento de empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Empresa> criarEmpresa(@Valid @RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaService.criarEmpresa(empresa));
    }

    @GetMapping
    @Operation(summary = "Listar todas as empresas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        return ResponseEntity.ok(empresaService.listarEmpresas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma empresa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaService.atualizarEmpresa(id, empresa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/fornecedores")
    @Operation(summary = "Listar fornecedores de uma empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Set<Fornecedor>> listarFornecedoresEmpresa(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.listarFornecedoresEmpresa(id));
    }

    @PostMapping("/{empresaId}/fornecedores/{fornecedorId}")
    @Operation(summary = "Vincular um fornecedor à empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor vinculado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Fornecedor já está vinculado a esta empresa"),
        @ApiResponse(responseCode = "404", description = "Empresa ou fornecedor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Empresa> vincularFornecedor(
            @PathVariable Long empresaId,
            @PathVariable Long fornecedorId) {
        return ResponseEntity.ok(empresaService.vincularFornecedor(empresaId, fornecedorId));
    }

    @DeleteMapping("/{empresaId}/fornecedores/{fornecedorId}")
    @Operation(summary = "Remover vínculo de fornecedor com empresa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vínculo removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empresa ou fornecedor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> removerVinculoFornecedor(
            @PathVariable Long empresaId,
            @PathVariable Long fornecedorId) {
        empresaService.removerVinculoFornecedor(empresaId, fornecedorId);
        return ResponseEntity.noContent().build();
    }
}