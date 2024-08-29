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
 	Nome VARCHAR(30),  
	 
 	CONSTRAINT pk_Secao PRIMARY KEY(Id)  
); 

CREATE TABLE livraria.Genero ( 
 	Id INT default nextval('livraria.Genero_seq'),
 	Nome VARCHAR(30),
 	Id_Secao INT,
	 
 	CONSTRAINT pk_Genero PRIMARY KEY(Id),
 	CONSTRAINT fk_Secao FOREIGN KEY(Id_Secao) 
 		REFERENCES livraria.Secao(Id) on delete cascade
);

CREATE TABLE livraria.Editora ( 
	Id INT default nextval('livraria.Editora_seq'),
 	Nome VARCHAR(30), 
	
 	CONSTRAINT pk_Editora PRIMARY KEY(Id)
); 

CREATE TABLE livraria.Livro ( 
 	ISBN INT,
 	Titulo INT,  
 	Ano INT,  
 	Preço INT,  
 	Estoque INT,  
 	Descricao INT,  
 	IdGenero INT,
 	IdEditora INT,
 
	CONSTRAINT pk_Livro PRIMARY KEY (ISBN),
 	CONSTRAINT fk_Genero FOREIGN KEY (IdGenero) 
		REFERENCES livraria.Genero(Id) on delete cascade,
 	CONSTRAINT fk_Editora FOREIGN KEY (IdEditora) 
		REFERENCES livraria.Editora(Id) on delete cascade
); 

CREATE TABLE livraria.Cliente ( 
 	Sexo CHAR,  
 	Data_nascimento DATE,  
 	Email VARCHAR(30),  
 	CPF INT,  
 	Snome VARCHAR(30),  
 	Pnome VARCHAR(30),  
 	Cidade VARCHAR(30),  
 	Estado VARCHAR(2),  

 	CONSTRAINT pk_Cliente PRIMARY KEY (CPF)
); 

CREATE TABLE livraria.Autor ( 
 	Id INT default nextval('livraria.Autor_seq'),
 	Nacionalidade VARCHAR (20),  
 	Pnome VARCHAR(20),
 	Snome VARCHAR(100),  

 	CONSTRAINT pk_Autor PRIMARY KEY(Id)
); 

CREATE TABLE livraria.Compra ( 
 	Num_Nota_Fiscal INT,
 	Data_Compra INT,  
 	Total INT,
 	CPF_Cliente INT,
 
 	CONSTRAINT pk_Compra PRIMARY KEY (Num_Nota_Fiscal),
 	CONSTRAINT fk_Cliente FOREIGN KEY (CPF_Cliente) 
		REFERENCES livraria.Cliente(CPF) on delete cascade
); 

CREATE TABLE livraria.Escrito ( 
 	Id_Autor INT,  
 	ISBN_Livro INT,
	 
	CONSTRAINT pk_Escrito PRIMARY KEY(Id_Autor, ISBN_Livro),
 	CONSTRAINT fk_Autor FOREIGN KEY (Id_Autor) 
		REFERENCES livraria.Autor(Id) on delete cascade,
 	CONSTRAINT fk_Livro FOREIGN KEY (ISBN_Livro) 
		REFERENCES livraria.Livro(ISBN) on delete cascade
); 

CREATE TABLE livraria.Possui ( 
 	Quantidade INT,  
 	ISBN_Livro INT,  
 	Num_Nota_Fiscal_Compra INT,

 	CONSTRAINT pk_Possui PRIMARY KEY(ISBN_Livro, Num_Nota_Fiscal_Compra), 
 	CONSTRAINT fk_Possui_Livro FOREIGN KEY (ISBN_Livro) 
	 	REFERENCES livraria.Livro(ISBN) on delete cascade,
 	CONSTRAINT fk_Compra FOREIGN KEY (Num_Nota_Fiscal_Compra) 
	 	REFERENCES livraria.Compra(Num_Nota_Fiscal) on delete cascade
); 