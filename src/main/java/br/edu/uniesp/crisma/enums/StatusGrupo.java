package br.edu.uniesp.crisma.enums;

public enum StatusGrupo {
    ATIVO("Ativo"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");
    
    private final String descricao;
    
    StatusGrupo(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
