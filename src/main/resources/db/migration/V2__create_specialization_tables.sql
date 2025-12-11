-- ============================================================================
-- Migration V2: Criação das Tabelas de Especialização
-- Sistema de Gerenciamento de Crisma
-- ============================================================================

-- Tabela: grupo
CREATE TABLE grupo (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ano_formacao INTEGER NOT NULL,
    faixa_etaria_minima INTEGER NOT NULL,
    faixa_etaria_maxima INTEGER NOT NULL,
    igreja_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'ATIVO' CHECK (status IN ('ATIVO', 'CONCLUIDO', 'CANCELADO')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_grupo_igreja FOREIGN KEY (igreja_id) REFERENCES igreja(id),
    CONSTRAINT chk_faixa_etaria CHECK (faixa_etaria_minima < faixa_etaria_maxima),
    CONSTRAINT chk_ano_formacao CHECK (ano_formacao >= 2000 AND ano_formacao <= 2100)
);

-- Tabela: crismando
CREATE TABLE crismando (
    id BIGSERIAL PRIMARY KEY,
    pessoa_id BIGINT NOT NULL UNIQUE,
    grupo_id BIGINT NOT NULL,
    data_inscricao DATE NOT NULL DEFAULT CURRENT_DATE,
    primeiro_responsavel VARCHAR(100) NOT NULL,
    segundo_responsavel VARCHAR(100),
    telefone_responsavel VARCHAR(15) NOT NULL,
    status VARCHAR(20) DEFAULT 'ATIVO' CHECK (status IN ('ATIVO', 'CRISMADO', 'DESISTENTE', 'TRANSFERIDO')),
    data_crisma DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_crismando_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    CONSTRAINT fk_crismando_grupo FOREIGN KEY (grupo_id) REFERENCES grupo(id)
);

-- Tabela: catequista
CREATE TABLE catequista (
    id BIGSERIAL PRIMARY KEY,
    pessoa_id BIGINT NOT NULL UNIQUE,
    setor_trabalho VARCHAR(100),
    ano_crisma INTEGER NOT NULL,
    experiencia_anos INTEGER DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ATIVO' CHECK (status IN ('ATIVO', 'INATIVO', 'AFASTADO')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_catequista_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    CONSTRAINT chk_ano_crisma CHECK (ano_crisma >= 1900 AND ano_crisma <= EXTRACT(YEAR FROM CURRENT_DATE)),
    CONSTRAINT chk_experiencia CHECK (experiencia_anos >= 0)
);

-- Tabela: coordenador
CREATE TABLE coordenador (
    id BIGSERIAL PRIMARY KEY,
    pessoa_id BIGINT NOT NULL UNIQUE,
    funcao VARCHAR(100) NOT NULL,
    igreja_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'ATIVO' CHECK (status IN ('ATIVO', 'INATIVO')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_coordenador_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    CONSTRAINT fk_coordenador_igreja FOREIGN KEY (igreja_id) REFERENCES igreja(id)
);

-- Tabela: padrinho
CREATE TABLE padrinho (
    id BIGSERIAL PRIMARY KEY,
    pessoa_id BIGINT NOT NULL UNIQUE,
    data_crisma DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_padrinho_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);

-- Tabela associativa: crismando_padrinho (relacionamento N:N)
CREATE TABLE crismando_padrinho (
    id BIGSERIAL PRIMARY KEY,
    crismando_id BIGINT NOT NULL,
    padrinho_id BIGINT NOT NULL,
    tipo_padrinho VARCHAR(20) NOT NULL CHECK (tipo_padrinho IN ('PADRINHO', 'MADRINHA')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_crismando_padrinho_crismando FOREIGN KEY (crismando_id) REFERENCES crismando(id),
    CONSTRAINT fk_crismando_padrinho_padrinho FOREIGN KEY (padrinho_id) REFERENCES padrinho(id),
    CONSTRAINT uk_crismando_padrinho_tipo UNIQUE (crismando_id, tipo_padrinho)
);

-- Tabela associativa: grupo_catequista (relacionamento N:N)
CREATE TABLE grupo_catequista (
    id BIGSERIAL PRIMARY KEY,
    grupo_id BIGINT NOT NULL,
    catequista_id BIGINT NOT NULL,
    data_inicio DATE NOT NULL DEFAULT CURRENT_DATE,
    data_fim DATE,
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_grupo_catequista_grupo FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    CONSTRAINT fk_grupo_catequista_catequista FOREIGN KEY (catequista_id) REFERENCES catequista(id),
    CONSTRAINT chk_data_inicio_fim CHECK (data_fim IS NULL OR data_fim >= data_inicio)
);

-- Índices para otimização
CREATE INDEX idx_crismando_grupo ON crismando(grupo_id);
CREATE INDEX idx_crismando_status ON crismando(status);
CREATE INDEX idx_catequista_status ON catequista(status);
CREATE INDEX idx_grupo_igreja ON grupo(igreja_id);
CREATE INDEX idx_grupo_status ON grupo(status);
CREATE INDEX idx_grupo_catequista_grupo ON grupo_catequista(grupo_id);
CREATE INDEX idx_grupo_catequista_catequista ON grupo_catequista(catequista_id);
CREATE INDEX idx_crismando_padrinho_crismando ON crismando_padrinho(crismando_id);

-- Comentários das tabelas
COMMENT ON TABLE grupo IS 'Grupos de formação da crisma organizados por faixa etária';
COMMENT ON TABLE crismando IS 'Pessoas que estão em processo de preparação para a crisma';
COMMENT ON TABLE catequista IS 'Catequistas responsáveis pela formação dos crismandos';
COMMENT ON TABLE coordenador IS 'Coordenadores responsáveis pela gestão do programa de crisma';
COMMENT ON TABLE padrinho IS 'Padrinhos e madrinhas dos crismandos';
COMMENT ON TABLE grupo_catequista IS 'Relacionamento entre grupos e catequistas responsáveis';
COMMENT ON TABLE crismando_padrinho IS 'Relacionamento entre crismandos e seus padrinhos/madrinhas';
