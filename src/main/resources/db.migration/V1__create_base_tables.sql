-- ============================================================================
-- Migration V1: Criação das Tabelas Base
-- Sistema de Gerenciamento de Crisma
-- ============================================================================

-- Tabela: pessoa (entidade base para herança)
CREATE TABLE pessoa (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    data_nascimento DATE NOT NULL,
    tipo_pessoa VARCHAR(20) NOT NULL CHECK (tipo_pessoa IN ('CRISMANDO', 'CATEQUISTA', 'COORDENADOR', 'PADRINHO', 'OUTRO')),
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_cpf_valido CHECK (cpf ~ '^[0-9]{11}$'),
    CONSTRAINT chk_email_valido CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- Tabela: endereco
CREATE TABLE endereco (
    id BIGSERIAL PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    logradouro VARCHAR(200) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_cep_valido CHECK (cep ~ '^[0-9]{8}$'),
    CONSTRAINT chk_estado_valido CHECK (estado ~ '^[A-Z]{2}$')
);

-- Tabela: igreja
CREATE TABLE igreja (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    email VARCHAR(100),
    padre_responsavel VARCHAR(100) NOT NULL,
    endereco_id BIGINT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_igreja_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id),
    CONSTRAINT chk_cnpj_valido CHECK (cnpj ~ '^[0-9]{14}$')
);

-- Índices para otimização
CREATE INDEX idx_pessoa_cpf ON pessoa(cpf);
CREATE INDEX idx_pessoa_email ON pessoa(email);
CREATE INDEX idx_pessoa_tipo ON pessoa(tipo_pessoa);
CREATE INDEX idx_pessoa_ativo ON pessoa(ativo);
CREATE INDEX idx_igreja_cnpj ON igreja(cnpj);
CREATE INDEX idx_endereco_cep ON endereco(cep);

-- Comentários das tabelas
COMMENT ON TABLE pessoa IS 'Tabela base para todas as pessoas envolvidas no sistema de crisma';
COMMENT ON TABLE endereco IS 'Endereços das igrejas e locais de eventos';
COMMENT ON TABLE igreja IS 'Cadastro de igrejas participantes do programa de crisma';
