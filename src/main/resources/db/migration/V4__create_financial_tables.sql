-- ============================================================================
-- Migration V4: Criação das Tabelas Financeiras
-- Sistema de Gerenciamento de Crisma
-- ============================================================================

-- Tabela: caixinha (contribuição mensal do grupo)
CREATE TABLE caixinha (
    id BIGSERIAL PRIMARY KEY,
    grupo_id BIGINT NOT NULL,
    mes_referencia INTEGER NOT NULL CHECK (mes_referencia >= 1 AND mes_referencia <= 12),
    ano_referencia INTEGER NOT NULL CHECK (ano_referencia >= 2000 AND ano_referencia <= 2100),
    valor_sugerido DECIMAL(10, 2) NOT NULL,
    descricao TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_caixinha_grupo FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    CONSTRAINT uk_caixinha_grupo_mes_ano UNIQUE (grupo_id, mes_referencia, ano_referencia),
    CONSTRAINT chk_valor_sugerido CHECK (valor_sugerido >= 0)
);

-- Tabela: contribuicao_pessoa (pagamentos individuais)
CREATE TABLE contribuicao_pessoa (
    id BIGSERIAL PRIMARY KEY,
    caixinha_id BIGINT NOT NULL,
    pessoa_id BIGINT NOT NULL,
    valor_pago DECIMAL(10, 2) NOT NULL,
    data_pagamento DATE NOT NULL DEFAULT CURRENT_DATE,
    forma_pagamento VARCHAR(30) CHECK (forma_pagamento IN ('DINHEIRO', 'PIX', 'TRANSFERENCIA', 'OUTRO')),
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contribuicao_caixinha FOREIGN KEY (caixinha_id) REFERENCES caixinha(id),
    CONSTRAINT fk_contribuicao_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    CONSTRAINT chk_valor_pago CHECK (valor_pago >= 0)
);

-- Tabela: despesa (despesas do grupo)
CREATE TABLE despesa (
    id BIGSERIAL PRIMARY KEY,
    grupo_id BIGINT NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data_despesa DATE NOT NULL DEFAULT CURRENT_DATE,
    categoria VARCHAR(50) CHECK (categoria IN ('MATERIAL', 'EVENTO', 'TRANSPORTE', 'ALIMENTACAO', 'OUTRO')),
    responsavel_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_despesa_grupo FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    CONSTRAINT fk_despesa_responsavel FOREIGN KEY (responsavel_id) REFERENCES pessoa(id),
    CONSTRAINT chk_valor_despesa CHECK (valor >= 0)
);

-- Índices para otimização
CREATE INDEX idx_caixinha_grupo ON caixinha(grupo_id);
CREATE INDEX idx_caixinha_mes_ano ON caixinha(mes_referencia, ano_referencia);
CREATE INDEX idx_contribuicao_caixinha ON contribuicao_pessoa(caixinha_id);
CREATE INDEX idx_contribuicao_pessoa ON contribuicao_pessoa(pessoa_id);
CREATE INDEX idx_contribuicao_data ON contribuicao_pessoa(data_pagamento);
CREATE INDEX idx_despesa_grupo ON despesa(grupo_id);
CREATE INDEX idx_despesa_data ON despesa(data_despesa);
CREATE INDEX idx_despesa_categoria ON despesa(categoria);

-- Comentários das tabelas
COMMENT ON TABLE caixinha IS 'Contribuição mensal definida para cada grupo';
COMMENT ON TABLE contribuicao_pessoa IS 'Pagamentos individuais realizados por crismandos e catequistas';
COMMENT ON TABLE despesa IS 'Despesas realizadas pelo grupo durante a formação';
