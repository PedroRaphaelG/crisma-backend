-- ============================================================================
-- Migration V3: Criação das Tabelas de Eventos, Formação e Atividades
-- Sistema de Gerenciamento de Crisma
-- ============================================================================

-- Tabela: formacao
CREATE TABLE formacao (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    conteudo TEXT NOT NULL,
    material TEXT,
    local VARCHAR(200),
    cidade VARCHAR(100),
    data_formacao DATE NOT NULL,
    carga_horaria INTEGER,
    formador_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_formacao_formador FOREIGN KEY (formador_id) REFERENCES catequista(id),
    CONSTRAINT chk_carga_horaria CHECK (carga_horaria > 0)
);

-- Tabela associativa: formacao_catequista (catequistas que participaram da formação)
CREATE TABLE formacao_catequista (
    id BIGSERIAL PRIMARY KEY,
    formacao_id BIGINT NOT NULL,
    catequista_id BIGINT NOT NULL,
    presente BOOLEAN DEFAULT TRUE,
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_formacao_catequista_formacao FOREIGN KEY (formacao_id) REFERENCES formacao(id),
    CONSTRAINT fk_formacao_catequista_catequista FOREIGN KEY (catequista_id) REFERENCES catequista(id),
    CONSTRAINT uk_formacao_catequista UNIQUE (formacao_id, catequista_id)
);

-- Tabela: evento
CREATE TABLE evento (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descricao TEXT,
    tipo_evento VARCHAR(50) NOT NULL CHECK (tipo_evento IN ('RETIRO', 'ENCONTRAO', 'CELEBRACAO', 'REUNIAO', 'ATIVIDADE_EXTERNA', 'OUTRO')),
    local VARCHAR(200) NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    igreja_id BIGINT NOT NULL,
    grupo_id BIGINT,
    obrigatorio BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evento_igreja FOREIGN KEY (igreja_id) REFERENCES igreja(id),
    CONSTRAINT fk_evento_grupo FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    CONSTRAINT chk_data_evento CHECK (data_fim >= data_inicio)
);

-- Tabela: frequencia
CREATE TABLE frequencia (
    id BIGSERIAL PRIMARY KEY,
    crismando_id BIGINT NOT NULL,
    evento_id BIGINT NOT NULL,
    data_registro DATE NOT NULL DEFAULT CURRENT_DATE,
    presente BOOLEAN NOT NULL,
    justificativa TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_frequencia_crismando FOREIGN KEY (crismando_id) REFERENCES crismando(id),
    CONSTRAINT fk_frequencia_evento FOREIGN KEY (evento_id) REFERENCES evento(id),
    CONSTRAINT uk_frequencia_crismando_evento UNIQUE (crismando_id, evento_id)
);

-- Tabela: calendario
CREATE TABLE calendario (
    id BIGSERIAL PRIMARY KEY,
    grupo_id BIGINT NOT NULL,
    semana INTEGER NOT NULL,
    tema VARCHAR(200) NOT NULL,
    data_encontro DATE NOT NULL,
    formacao_id BIGINT,
    conteudo_programatico TEXT,
    material_necessario TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_calendario_grupo FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    CONSTRAINT fk_calendario_formacao FOREIGN KEY (formacao_id) REFERENCES formacao(id),
    CONSTRAINT chk_semana CHECK (semana > 0 AND semana <= 52),
    CONSTRAINT uk_calendario_grupo_semana UNIQUE (grupo_id, semana)
);

-- Tabela associativa: evento_participante (participantes de eventos - genérico)
CREATE TABLE evento_participante (
    id BIGSERIAL PRIMARY KEY,
    evento_id BIGINT NOT NULL,
    pessoa_id BIGINT NOT NULL,
    tipo_participante VARCHAR(30) NOT NULL CHECK (tipo_participante IN ('CRISMANDO', 'CATEQUISTA', 'COORDENADOR', 'CONVIDADO')),
    confirmado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_evento_participante_evento FOREIGN KEY (evento_id) REFERENCES evento(id),
    CONSTRAINT fk_evento_participante_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id),
    CONSTRAINT uk_evento_participante UNIQUE (evento_id, pessoa_id)
);

-- Índices para otimização
CREATE INDEX idx_formacao_data ON formacao(data_formacao);
CREATE INDEX idx_formacao_formador ON formacao(formador_id);
CREATE INDEX idx_evento_data_inicio ON evento(data_inicio);
CREATE INDEX idx_evento_igreja ON evento(igreja_id);
CREATE INDEX idx_evento_grupo ON evento(grupo_id);
CREATE INDEX idx_evento_tipo ON evento(tipo_evento);
CREATE INDEX idx_frequencia_crismando ON frequencia(crismando_id);
CREATE INDEX idx_frequencia_evento ON frequencia(evento_id);
CREATE INDEX idx_frequencia_presente ON frequencia(presente);
CREATE INDEX idx_calendario_grupo ON calendario(grupo_id);
CREATE INDEX idx_calendario_data ON calendario(data_encontro);
CREATE INDEX idx_evento_participante_evento ON evento_participante(evento_id);
CREATE INDEX idx_evento_participante_pessoa ON evento_participante(pessoa_id);

-- Comentários das tabelas
COMMENT ON TABLE formacao IS 'Formações e capacitações para catequistas';
COMMENT ON TABLE formacao_catequista IS 'Participação de catequistas nas formações';
COMMENT ON TABLE evento IS 'Eventos do programa de crisma (retiros, encontros, celebrações)';
COMMENT ON TABLE frequencia IS 'Registro de presença dos crismandos nos eventos';
COMMENT ON TABLE calendario IS 'Calendário de encontros semanais por grupo';
COMMENT ON TABLE evento_participante IS 'Participantes confirmados em eventos';
