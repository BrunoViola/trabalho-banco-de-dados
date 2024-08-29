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
 	Id INT DEFAULT nextval('livraria.Secao_seq'),
 	Nome VARCHAR(30) NOT NULL,  
	 
 	CONSTRAINT pk_Secao PRIMARY KEY(Id)  
); 

CREATE TABLE livraria.Genero ( 
 	Id INT DEFAULT nextval('livraria.Genero_seq'),
 	Nome VARCHAR(30) NOT NULL,
 	Id_Secao INT NOT NULL,
	 
 	CONSTRAINT pk_Genero PRIMARY KEY(Id),
 	CONSTRAINT fk_Secao FOREIGN KEY(Id_Secao) 
 		REFERENCES livraria.Secao(Id) ON DELETE CASCADE
);

CREATE TABLE livraria.Editora ( 
	Id INT DEFAULT nextval('livraria.Editora_seq'),
 	Nome VARCHAR(30) NOT NULL, 
	
 	CONSTRAINT pk_Editora PRIMARY KEY(Id)
); 

CREATE TABLE livraria.Livro ( 
 	ISBN INT NOT NULL,
 	Titulo VARCHAR(100),  
 	Ano INT,  
 	Preco NUMERIC(10,2) NOT NULL,  
 	Estoque INT NOT NULL,  
 	Descricao VARCHAR(3000),  
 	IdGenero INT NOT NULL,
 	IdEditora INT NOT NULL,
 
	CONSTRAINT pk_Livro PRIMARY KEY (ISBN),
 	CONSTRAINT fk_Genero FOREIGN KEY (IdGenero) 
		REFERENCES livraria.Genero(Id) ON DELETE CASCADE,
 	CONSTRAINT fk_Editora FOREIGN KEY (IdEditora) 
		REFERENCES livraria.Editora(Id) ON DELETE CASCADE,
	CHECK(Preco >= 0)
); 

CREATE TABLE livraria.Cliente ( 
 	Sexo CHAR,  
 	Data_nascimento DATE,  
 	Email VARCHAR(30) NOT NULL,  
 	CPF INT NOT NULL,  
 	Snome VARCHAR(30) NOT NULL,  
 	Pnome VARCHAR(30) NOT NULL,  
 	Cidade VARCHAR(30) NOT NULL,  
 	Estado CHAR(2) NOT NULL,  

 	CONSTRAINT pk_Cliente PRIMARY KEY (CPF)
); 

CREATE TABLE livraria.Autor ( 
 	Id INT DEFAULT nextval('livraria.Autor_seq'),
 	Nacionalidade VARCHAR (20),  
 	Pnome VARCHAR(20),
 	Snome VARCHAR(100),  

 	CONSTRAINT pk_Autor PRIMARY KEY(Id),
	CHECK(Pnome IS NOT NULL OR Snome IS NOT NULL)
); 

CREATE TABLE livraria.Compra ( 
 	Num_Nota_Fiscal INT NOT NULL,
 	Data_Compra DATE DEFAULT CURRENT_TIMESTAMP,  
 	Total NUMERIC(10,2),
 	CPF_Cliente INT NOT NULL,
 
 	CONSTRAINT pk_Compra PRIMARY KEY (Num_Nota_Fiscal),
 	CONSTRAINT fk_Cliente FOREIGN KEY (CPF_Cliente) 
		REFERENCES livraria.Cliente(CPF) ON DELETE CASCADE,
	CHECK(Total > 0)
); 

CREATE TABLE livraria.Escrito ( 
 	Id_Autor INT NOT NULL,  
 	ISBN_Livro INT NOT NULL,
	 
	CONSTRAINT pk_Escrito PRIMARY KEY(Id_Autor, ISBN_Livro),
 	CONSTRAINT fk_Autor FOREIGN KEY (Id_Autor) 
		REFERENCES livraria.Autor(Id) ON DELETE CASCADE,
 	CONSTRAINT fk_Livro FOREIGN KEY (ISBN_Livro) 
		REFERENCES livraria.Livro(ISBN) ON DELETE CASCADE
); 

CREATE TABLE livraria.Possui ( 
 	Quantidade INT NOT NULL,  
 	ISBN_Livro INT NOT NULL,  
 	Num_Nota_Fiscal_Compra INT NOT NULL,

 	CONSTRAINT pk_Possui PRIMARY KEY(ISBN_Livro, Num_Nota_Fiscal_Compra), 
 	CONSTRAINT fk_Possui_Livro FOREIGN KEY (ISBN_Livro) 
	 	REFERENCES livraria.Livro(ISBN) ON DELETE CASCADE,
 	CONSTRAINT fk_Compra FOREIGN KEY (Num_Nota_Fiscal_Compra) 
	 	REFERENCES livraria.Compra(Num_Nota_Fiscal) ON DELETE CASCADE,
	CHECK(Quantidade > 0)
); 