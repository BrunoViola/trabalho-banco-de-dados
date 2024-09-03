CREATE SCHEMA IF NOT EXISTS livraria;

CREATE SEQUENCE livraria.Secao_seq
	START 1 INCREMENT 1;

CREATE SEQUENCE livraria.Genero_seq
	START 1 INCREMENT 1;

CREATE SEQUENCE livraria.Editora_seq
	START 1 INCREMENT 1;

CREATE SEQUENCE livraria.Autor_seq
	START 1 INCREMENT 1;

CREATE TABLE livraria.Secao ( 
 	ID INT DEFAULT nextval('livraria.Secao_seq'),
 	Nome VARCHAR(50) NOT NULL,  
	 
 	CONSTRAINT pk_Secao PRIMARY KEY(ID),
	CONSTRAINT uk_Secao_Nome UNIQUE(Nome)  
); 

CREATE TABLE livraria.Genero ( 
 	ID INT DEFAULT nextval('livraria.Genero_seq'),
 	Nome VARCHAR(100) NOT NULL,
 	ID_Secao INT NOT NULL,
	 
 	CONSTRAINT pk_Genero PRIMARY KEY(ID),
	CONSTRAINT uk_Genero UNIQUE(Nome, ID_Secao),
 	CONSTRAINT fk_Secao FOREIGN KEY(ID_Secao) 
 		REFERENCES livraria.Secao(ID) ON DELETE CASCADE
);

CREATE TABLE livraria.Editora ( 
	ID INT DEFAULT nextval('livraria.Editora_seq'),
 	Nome VARCHAR(50) NOT NULL, 
	
 	CONSTRAINT pk_Editora PRIMARY KEY(ID),
	CONSTRAINT uk_Editora UNIQUE(Nome)
); 

CREATE TABLE livraria.Livro ( 
 	ISBN BIGINT NOT NULL,
 	Titulo VARCHAR(200),  
 	Ano INT,  
 	Preco NUMERIC(10,2) NOT NULL,  
 	Estoque INT NOT NULL,  
 	Descricao VARCHAR(3000),  
 	ID_Editora INT NOT NULL,
 
	CONSTRAINT pk_Livro PRIMARY KEY (ISBN),
 	CONSTRAINT fk_Editora FOREIGN KEY (ID_Editora) 
		REFERENCES livraria.Editora(ID) ON DELETE CASCADE,
	CHECK(Preco >= 0)
); 

CREATE TABLE livraria.Cliente ( 
	CPF CHAR(11) NOT NULL,
 	Sexo CHAR,  
 	Data_nascimento DATE,  
 	Email VARCHAR(50) NOT NULL,    
	Pnome VARCHAR(30) NOT NULL,
 	Snome VARCHAR(50) NOT NULL,    
 	Cidade VARCHAR(30) NOT NULL,  
 	Estado CHAR(2) NOT NULL,  

 	CONSTRAINT pk_Cliente PRIMARY KEY (CPF)
); 

CREATE TABLE livraria.Autor ( 
 	ID INT DEFAULT nextval('livraria.Autor_seq'),  
 	Pnome VARCHAR(30),
 	Snome VARCHAR(50), 
	Nacionalidade VARCHAR (20),

 	CONSTRAINT pk_Autor PRIMARY KEY(ID),
	CHECK(Pnome IS NOT NULL OR Snome IS NOT NULL)
); 

CREATE TABLE livraria.Compra ( 
 	Num_Nota_Fiscal BIGINT NOT NULL,
 	Data_Compra DATE DEFAULT CURRENT_TIMESTAMP,  
 	Total NUMERIC(10,2),
 	CPF_Cliente CHAR(11) NOT NULL,
 
 	CONSTRAINT pk_Compra PRIMARY KEY (Num_Nota_Fiscal),
 	CONSTRAINT fk_Cliente FOREIGN KEY (CPF_Cliente) 
		REFERENCES livraria.Cliente(CPF),
	CHECK(Total > 0)
); 

CREATE TABLE livraria.Escrito ( 
 	ID_Autor INT NOT NULL,  
 	ISBN_Livro BIGINT NOT NULL,
	 
	CONSTRAINT pk_Escrito PRIMARY KEY(ID_Autor, ISBN_Livro),
 	CONSTRAINT fk_Autor FOREIGN KEY (ID_Autor) 
		REFERENCES livraria.Autor(ID) ON DELETE CASCADE,
 	CONSTRAINT fk_Livro FOREIGN KEY (ISBN_Livro) 
		REFERENCES livraria.Livro(ISBN) ON DELETE CASCADE
); 

CREATE TABLE livraria.Possui ( 
	Num_Nota_Fiscal_Compra BIGINT NOT NULL,
	ISBN_Livro BIGINT NOT NULL,
 	Quantidade INT NOT NULL,
	Preco NUMERIC(10,2) NOT NULL,
 	
 	CONSTRAINT pk_Possui PRIMARY KEY(ISBN_Livro, Num_Nota_Fiscal_Compra), 
 	CONSTRAINT fk_Possui_Livro FOREIGN KEY (ISBN_Livro) 
	 	REFERENCES livraria.Livro(ISBN) ON DELETE CASCADE,
 	CONSTRAINT fk_Compra FOREIGN KEY (Num_Nota_Fiscal_Compra) 
	 	REFERENCES livraria.Compra(Num_Nota_Fiscal) ON DELETE CASCADE,
	CHECK(Quantidade > 0),
	CHECK(Preco >= 0)
); 

CREATE TABLE livraria.Pertence (
	ISBN_Livro BIGINT NOT NULL,  
 	ID_Genero INT NOT NULL,
	 
	CONSTRAINT pk_Pertence PRIMARY KEY(ISBN_Livro, ID_Genero),
	CONSTRAINT fk_Livro FOREIGN KEY (ISBN_Livro) 
		REFERENCES livraria.Livro(ISBN) ON DELETE CASCADE,
 	CONSTRAINT fk_Genero FOREIGN KEY (ID_Genero) 
		REFERENCES livraria.Genero(ID) ON DELETE CASCADE
)