CREATE TABLE livraria.Secao 
( 
 ID INT,
 Nome VARCHAR(30),  
 CONSTRAINT pk_Secao PRIMARY KEY(ID)  
); 

CREATE TABLE livraria.Genero 
( 
 ID INT,
 Nome VARCHAR(30),
 ID_Secao INT,
 CONSTRAINT pk_Genero PRIMARY KEY(ID),
 CONSTRAINT fk_Secao FOREIGN KEY(ID_Secao) REFERENCES Secao(ID)
);

CREATE TABLE livraria.Editora 
( 
 ID INT,
 Nome VARCHAR(30),  
 CONSTRAINT pk_Editora PRIMARY KEY(ID)
); 

CREATE TABLE livraria.Livro 
( 
 ISBN INT,
 Titulo INT,  
 Ano INT,  
 Preço INT,  
 Estoque INT,  
 Descricao INT,  
 idGenero INT,
 idEditora INT,
 
 CONSTRAINT pk_Livro PRIMARY KEY (ISBN),
 CONSTRAINT fk_Genero FOREIGN KEY (idGenero) REFERENCES Genero(ID),
 CONSTRAINT fk_Editora FOREIGN KEY (idEditora) REFERENCES Editora(ID)
); 

CREATE TABLE livraria.Cliente 
( 
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

CREATE TABLE livraria.Autor 
( 
 ID INT,
 Nacionalidade VARCHAR (20),  
 Pnome VARCHAR(20),
 Snome VARCHAR(100),  

 CONSTRAINT pk_Autor PRIMARY KEY(ID)
); 



 

CREATE TABLE livraria.Compra 
( 
 Num_Nota_Fiscal INT,
 Data_Compra INT,  
 Total INT,
 CPF_Cliente INT,
 
 CONSTRAINT pk_Compra PRIMARY KEY (Num_Nota_Fiscal),
 CONSTRAINT fk_Cliente FOREIGN KEY (CPF_Cliente) REFERENCES Cliente(CPF)
   
); 



CREATE TABLE livraria.Escrito 
( 
 ID_Autor INT,  
 ISBN_Livro INT,
 
 CONSTRAINT fk_Autor FOREIGN KEY (ID_Autor) REFERENCES Autor(ID),
 CONSTRAINT fk_Livro FOREIGN KEY (ISBN_Livro) REFERENCES Livro(ISBN),
 CONSTRAINT pk_Escrito PRIMARY KEY(ID_Autor, ISBN_Livro) 
); 

CREATE TABLE livraria.Possui 
( 
 Quantidade INT,  
 ISBN_Livro INT,  
 Num_Nota_Fiscal_Compra INT,

 CONSTRAINT pk_Possui PRIMARY KEY(ISBN_Livro, Num_Nota_Fiscal_Compra), 
 CONSTRAINT fk_Possui_Livro FOREIGN KEY (ISBN_Livro) REFERENCES Livro(ISBN),
 CONSTRAINT fk_Compra FOREIGN KEY (Num_Nota_Fiscal_Compra) REFERENCES Compra(Num_Nota_Fiscal)
);