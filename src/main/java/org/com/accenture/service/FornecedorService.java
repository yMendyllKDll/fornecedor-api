package org.com.accenture.service;

import org.com.accenture.exception.BusinessException;
import org.com.accenture.model.Empresa;
import org.com.accenture.model.Fornecedor;
import org.com.accenture.repository.EmpresaRepository;
import org.com.accenture.repository.FornecedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FornecedorService {
    private static final Logger logger = LoggerFactory.getLogger(FornecedorService.class);
    
    private final FornecedorRepository fornecedorRepository;
    private final EmpresaRepository empresaRepository;
    private final CepService cepService;

    @Autowired
    public FornecedorService(FornecedorRepository fornecedorRepository,
                           EmpresaRepository empresaRepository,
                           CepService cepService) {
        this.fornecedorRepository = fornecedorRepository;
        this.empresaRepository = empresaRepository;
        this.cepService = cepService;
    }

    @Transactional
    public Fornecedor criarFornecedor(Fornecedor fornecedor) {

        if (fornecedorRepository.existsByDocumento(fornecedor.getDocumento())) {
            throw new BusinessException("Documento já cadastrado: " + fornecedor.getDocumento());
        }

        if (!cepService.validarCep(fornecedor.getCep())) {
            throw new BusinessException("CEP inválido: " + fornecedor.getCep());
        }

        if (fornecedor.getEmpresas() != null && !fornecedor.getEmpresas().isEmpty()) {
            for (Empresa empresa : fornecedor.getEmpresas()) {
                if (!empresaRepository.existsById(empresa.getId())) {
                    throw new BusinessException("Empresa não encontrada com ID: " + empresa.getId());
                }
            }
        }

        return fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> listarFornecedores(String nome, String documento) {
        return fornecedorRepository.findByNomeAndDocumento(nome, documento);
    }

    public Fornecedor buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Fornecedor não encontrado com ID: " + id));
    }

    public Set<Empresa> listarEmpresasFornecedor(Long id) {
        Fornecedor fornecedor = buscarPorId(id);
        return fornecedor.getEmpresas() != null ? fornecedor.getEmpresas() : new HashSet<>();
    }

    @Transactional
    public Fornecedor atualizarFornecedor(Long id, Fornecedor fornecedor) {
        if (!fornecedorRepository.existsById(id)) {
            throw new BusinessException("Fornecedor não encontrado com ID: " + id);
        }

        // Verifica se o CEP é válido
        if (!cepService.validarCep(fornecedor.getCep())) {
            throw new BusinessException("CEP inválido: " + fornecedor.getCep());
        }

        // Verifica se as empresas existem
        if (fornecedor.getEmpresas() != null && !fornecedor.getEmpresas().isEmpty()) {
            for (Empresa empresa : fornecedor.getEmpresas()) {
                if (!empresaRepository.existsById(empresa.getId())) {
                    throw new BusinessException("Empresa não encontrada com ID: " + empresa.getId());
                }
            }
        }

        fornecedor.setId(id);
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public void deletarFornecedor(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new BusinessException("Fornecedor não encontrado com ID: " + id);
        }
        fornecedorRepository.deleteById(id);
    }
} 