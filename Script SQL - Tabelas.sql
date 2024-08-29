CREATE SCHEMA IF NOT EXISTS livraria;

create sequence livraria.Secao_seq
	start 1 increment 1;

create sequence livraria.Genero_seq
	start 1 increment 1;

create sequence livraria.Editora_seq
	start 1 increment 1;

create sequence livraria.Autor_seq
	start 1 increment 1;

CREATE TABLE livraria.Secao ( 
 	Id INT default nextval('livraria.Secao_seq'),
 	Nome VARCHAR(30) not null,  
	 
 	CONSTRAINT pk_Secao PRIMARY KEY(Id)  
); 

CREATE TABLE livraria.Genero ( 
 	Id INT default nextval('livraria.Genero_seq'),
 	Nome VARCHAR(30) not null,
 	Id_Secao INT not null,
	 
 	CONSTRAINT pk_Genero PRIMARY KEY(Id),
 	CONSTRAINT fk_Secao FOREIGN KEY(Id_Secao) 
 		REFERENCES livraria.Secao(Id) on delete cascade
);

CREATE TABLE livraria.Editora ( 
	Id INT default nextval('livraria.Editora_seq'),
 	Nome VARCHAR(30) not null, 
	
 	CONSTRAINT pk_Editora PRIMARY KEY(Id)
); 

CREATE TABLE livraria.Livro ( 
 	ISBN INT not null,
 	Titulo VARCHAR(100),  
 	Ano INT,  
 	Preco NUMERIC(10,2) not null,  
 	Estoque INT not null,  
 	Descricao VARCHAR(3000),  
 	IdGenero INT not null,
 	IdEditora INT not null,
 
	CONSTRAINT pk_Livro PRIMARY KEY (ISBN),
 	CONSTRAINT fk_Genero FOREIGN KEY (IdGenero) 
		REFERENCES livraria.Genero(Id) on delete cascade,
 	CONSTRAINT fk_Editora FOREIGN KEY (IdEditora) 
		REFERENCES livraria.Editora(Id) on delete cascade,
	CHECK(Preco >= 0)
); 

CREATE TABLE livraria.Cliente ( 
 	Sexo CHAR,  
 	Data_nascimento DATE,  
 	Email VARCHAR(30) not null,  
 	CPF INT not null,  
 	Snome VARCHAR(30) not null,  
 	Pnome VARCHAR(30) not null,  
 	Cidade VARCHAR(30) not null,  
 	Estado CHAR(2) not null,  

 	CONSTRAINT pk_Cliente PRIMARY KEY (CPF)
); 

CREATE TABLE livraria.Autor ( 
 	Id INT default nextval('livraria.Autor_seq'),
 	Nacionalidade VARCHAR (20),  
 	Pnome VARCHAR(20),
 	Snome VARCHAR(100),  

 	CONSTRAINT pk_Autor PRIMARY KEY(Id),
	CHECK(Pnome is not null or Snome is not null)
); 

CREATE TABLE livraria.Compra ( 
 	Num_Nota_Fiscal INT not null,
 	Data_Compra DATE default CURRENT_TIMESTAMP,  
 	Total NUMERIC(10,2),
 	CPF_Cliente INT not null,
 
 	CONSTRAINT pk_Compra PRIMARY KEY (Num_Nota_Fiscal),
 	CONSTRAINT fk_Cliente FOREIGN KEY (CPF_Cliente) 
		REFERENCES livraria.Cliente(CPF) on delete cascade,
	CHECK(Total > 0)
); 

CREATE TABLE livraria.Escrito ( 
 	Id_Autor INT not null,  
 	ISBN_Livro INT not null,
	 
	CONSTRAINT pk_Escrito PRIMARY KEY(Id_Autor, ISBN_Livro),
 	CONSTRAINT fk_Autor FOREIGN KEY (Id_Autor) 
		REFERENCES livraria.Autor(Id) on delete cascade,
 	CONSTRAINT fk_Livro FOREIGN KEY (ISBN_Livro) 
		REFERENCES livraria.Livro(ISBN) on delete cascade
); 

CREATE TABLE livraria.Possui ( 
 	Quantidade INT not null,  
 	ISBN_Livro INT not null,  
 	Num_Nota_Fiscal_Compra INT not null,

 	CONSTRAINT pk_Possui PRIMARY KEY(ISBN_Livro, Num_Nota_Fiscal_Compra), 
 	CONSTRAINT fk_Possui_Livro FOREIGN KEY (ISBN_Livro) 
	 	REFERENCES livraria.Livro(ISBN) on delete cascade,
 	CONSTRAINT fk_Compra FOREIGN KEY (Num_Nota_Fiscal_Compra) 
	 	REFERENCES livraria.Compra(Num_Nota_Fiscal) on delete cascade,
	CHECK(Quantidade > 0)
); 