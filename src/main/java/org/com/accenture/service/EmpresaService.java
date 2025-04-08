package org.com.accenture.service;

import org.com.accenture.exception.BusinessException;
import org.com.accenture.model.Empresa;
import org.com.accenture.model.Fornecedor;
import org.com.accenture.repository.EmpresaRepository;
import org.com.accenture.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final CepService cepService;

    public EmpresaService(EmpresaRepository empresaRepository, 
                         FornecedorRepository fornecedorRepository,
                         CepService cepService) {
        this.empresaRepository = empresaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.cepService = cepService;
    }

    @Transactional
    public Empresa criarEmpresa(Empresa empresa) {
        if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
            throw new BusinessException("CNPJ já cadastrado: " + empresa.getCnpj());
        }

        if (!cepService.validarCep(empresa.getCep())) {
            throw new BusinessException("CEP inválido: " + empresa.getCep());
        }
        Set<Fornecedor> fornecedoresSalvos = new HashSet<>();
        if (empresa.getFornecedores() != null && !empresa.getFornecedores().isEmpty()) {
            boolean isParana = empresa.getCep().startsWith("8");
            for (Fornecedor fornecedor : empresa.getFornecedores()) {
                validarFornecedor(fornecedor, isParana);
                fornecedoresSalvos.add(processarFornecedor(fornecedor));
            }
        }
        empresa.setFornecedores(fornecedoresSalvos);
        
        return empresaRepository.save(empresa);
    }

    public Set<Fornecedor> listarFornecedoresEmpresa(Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new BusinessException("Empresa não encontrada com ID: " + empresaId));
        
        return empresa.getFornecedores() != null ? empresa.getFornecedores() : new HashSet<>();
    }

    private void validarFornecedor(Fornecedor fornecedor, boolean isParana) {
        if (fornecedor.getTipoPessoa() == Fornecedor.TipoPessoa.FISICA) {
            if (fornecedor.getRg() == null || fornecedor.getRg().trim().isEmpty()) {
                throw new BusinessException("RG é obrigatório para pessoa física");
            }
            
            if (fornecedor.getDataNascimento() == null) {
                throw new BusinessException("Data de nascimento é obrigatória para pessoa física");
            }

            if (isParana) {
                int idade = Period.between(fornecedor.getDataNascimento(), LocalDate.now()).getYears();
                if (idade < 18) {
                    throw new BusinessException("Fornecedor pessoa física não pode ser menor de idade no Paraná");
                }
            }
        }
    }

    private Fornecedor processarFornecedor(Fornecedor fornecedor) {
        if (fornecedor.getId() == null && fornecedor.getDocumento() != null) {
            return fornecedorRepository.findByDocumento(fornecedor.getDocumento())
                .orElseGet(() -> fornecedorRepository.save(fornecedor));
        }
        if (fornecedor.getId() == null) {
            return fornecedorRepository.save(fornecedor);
        }
        
        return fornecedor;
    }

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Empresa não encontrada com ID: " + id));
    }

    @Transactional
    public Empresa atualizarEmpresa(Long id, Empresa empresa) {
        if (!empresaRepository.existsById(id)) {
            throw new BusinessException("Empresa não encontrada com ID: " + id);
        }

        if (!cepService.validarCep(empresa.getCep())) {
            throw new BusinessException("CEP inválido: " + empresa.getCep());
        }

        Set<Fornecedor> fornecedoresSalvos = new HashSet<>();
        if (empresa.getFornecedores() != null && !empresa.getFornecedores().isEmpty()) {
            boolean isParana = empresa.getCep().startsWith("8");
            for (Fornecedor fornecedor : empresa.getFornecedores()) {
                validarFornecedor(fornecedor, isParana);
                fornecedoresSalvos.add(processarFornecedor(fornecedor));
            }
        }
        empresa.setFornecedores(fornecedoresSalvos);
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @Transactional
    public void deletarEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new BusinessException("Empresa não encontrada com ID: " + id);
        }
        empresaRepository.deleteById(id);
    }

    @Transactional
    public Empresa vincularFornecedor(Long empresaId, Long fornecedorId) {
        Empresa empresa = buscarPorId(empresaId);
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
            .orElseThrow(() -> new BusinessException("Fornecedor não encontrado com ID: " + fornecedorId));

        if (empresa.getFornecedores() != null && 
            empresa.getFornecedores().stream().anyMatch(f -> f.getId().equals(fornecedorId))) {
            throw new BusinessException("Fornecedor já está vinculado a esta empresa");
        }

        if (empresa.getFornecedores() == null) {
            empresa.setFornecedores(new HashSet<>());
        }

        empresa.getFornecedores().add(fornecedor);

        return empresaRepository.save(empresa);
    }

    @Transactional
    public void removerVinculoFornecedor(Long empresaId, Long fornecedorId) {
        Empresa empresa = buscarPorId(empresaId);
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
            .orElseThrow(() -> new BusinessException("Fornecedor não encontrado com ID: " + fornecedorId));

        if (empresa.getFornecedores() != null) {
            empresa.getFornecedores().remove(fornecedor);
        }

        if (fornecedor.getEmpresas() != null) {
            fornecedor.getEmpresas().remove(empresa);
        }

        empresaRepository.save(empresa);
        fornecedorRepository.save(fornecedor);
    }
}