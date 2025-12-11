package br.edu.uniesp.crisma.enums;

public enum StatusCrismando {
    ATIVO("Ativo"),
    CRISMADO("Crismado"),
    DESISTENTE("Desistente"),
    TRANSFERIDO("Transferido");
    
    private final String descricao;
    
    StatusCrismando(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
