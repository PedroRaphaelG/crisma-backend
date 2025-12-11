package br.edu.uniesp.crisma.enums;

public enum TipoPessoa {
    CRISMANDO("Crismando"),
    CATEQUISTA("Catequista"),
    COORDENADOR("Coordenador"),
    PADRINHO("Padrinho"),
    OUTRO("Outro");
    
    private final String descricao;
    
    TipoPessoa(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
