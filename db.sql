CREATE DATABASE Biblioteca;

USE Biblioteca;

-- Tabela para armazenar os autores
CREATE TABLE Autores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Tabela para armazenar as categorias (gêneros) dos livros
CREATE TABLE Categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Tabela para armazenar os livros
CREATE TABLE Livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor_id INT,
    categoria_id INT,
    data_publicacao DATE,
    status ENUM('disponivel', 'emprestado') DEFAULT 'disponivel',
    FOREIGN KEY (autor_id) REFERENCES Autores(id),
    FOREIGN KEY (categoria_id) REFERENCES Categorias(id)
);

-- Tabela para armazenar os usuários da biblioteca
CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    telefone VARCHAR(20)
);

-- Tabela para armazenar os empréstimos de livros
CREATE TABLE Emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    livro_id INT,
    usuario_id INT,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    status ENUM('ativo', 'devolvido') DEFAULT 'ativo',
    FOREIGN KEY (livro_id) REFERENCES Livros(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id)
);

-- Inserir autores
INSERT INTO Autores (nome) VALUES
('J.K. Rowling'),
('George Orwell'),
('Harper Lee'),
('J.R.R. Tolkien'),
('Agatha Christie');

-- Inserir categorias
INSERT INTO Categorias (nome) VALUES
('Ficção Fantástica'),
('Distopia'),
('Mistério'),
('Ficção Científica'),
('Romance');

-- Inserir livros
INSERT INTO Livros (titulo, autor_id, categoria_id, data_publicacao, status) VALUES
('Harry Potter e a Pedra Filosofal', 1, 1, '1997-06-26', 'disponivel'),
('1984', 2, 2, '1949-06-08', 'disponivel'),
('O Sol é Para Todos', 3, 5, '1960-07-11', 'disponivel'),
('O Senhor dos Anéis: A Sociedade do Anel', 4, 1, '1954-07-29', 'disponivel'),
('Assassinato no Expresso Oriente', 5, 3, '1934-01-01', 'disponivel');

-- Inserir usuários
INSERT INTO Usuarios (nome, email, telefone) VALUES
('Maria Silva', 'maria@email.com', '11987654321'),
('João Souza', 'joao@email.com', '11976543210'),
('Ana Pereira', 'ana@email.com', '11965432109'),
('Carlos Santos', 'carlos@email.com', '11954321098'),
('Fernanda Costa', 'fernanda@email.com', '11943210987');

-- Inserir empréstimos
INSERT INTO Emprestimos (livro_id, usuario_id, data_emprestimo, data_devolucao, status) VALUES
(1, 1, '2024-11-01', '2024-11-15', 'ativo'),
(2, 2, '2024-11-05', '2024-11-19', 'ativo'),
(3, 3, '2024-11-07', '2024-11-21', 'ativo'),
(4, 4, '2024-11-10', '2024-11-24', 'ativo'),
(5, 5, '2024-11-12', '2024-11-26', 'ativo');
