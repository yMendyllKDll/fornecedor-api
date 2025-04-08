package org.com.accenture.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.Objects;

@Getter
@Setter
@ToString(exclude = "fornecedores")
@Entity
@Table(name = "empresas")
@JsonIgnoreProperties({"fornecedores"})
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CNPJ é obrigatório")
    @Column(unique = true)
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve conter 14 dígitos")
    private String cnpj;

    @NotBlank(message = "Nome Fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve conter 8 dígitos")
    private String cep;

    @ManyToMany
    @JoinTable(
        name = "empresa_fornecedor",
        joinColumns = @JoinColumn(name = "empresa_id"),
        inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )
    private Set<Fornecedor> fornecedores;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id) &&
               Objects.equals(cnpj, empresa.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cnpj);
    }
}