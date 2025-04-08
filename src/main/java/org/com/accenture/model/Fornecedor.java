package org.com.accenture.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;
import java.util.Objects;

@Getter
@Setter
@ToString(exclude = "empresas")
@Entity
@Table(name = "fornecedores")
@JsonIgnoreProperties({"empresas"})
public class Fornecedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Documento é obrigatório")
    @Column(unique = true)
    private String documento;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve conter 8 dígitos")
    private String cep;

    @Column(name = "tipo_pessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Column(name = "rg")
    private String rg;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ManyToMany(mappedBy = "fornecedores")
    private Set<Empresa> empresas;

    public enum TipoPessoa {
        FISICA,
        JURIDICA
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor fornecedor = (Fornecedor) o;
        return Objects.equals(id, fornecedor.id) &&
               Objects.equals(documento, fornecedor.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documento);
    }
}