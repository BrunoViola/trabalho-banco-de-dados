CREATE TABLE Livro 
( 
 Título INT,  
 Ano INT,  
 Preço INT,  
 Estoque INT,  
 Descrição INT,  
 ISBN INT PRIMARY KEY,  
 idGênero INT,  
 idLivro_Vendido INT,  
); 

CREATE TABLE Cliente 
( 
 Sexo INT,  
 Data_nascimento INT,  
 E-mail INT,  
 CPF INT PRIMARY KEY,  
 Snome INT,  
 Pnome INT,  
 Cidade INT,  
 Estado INT,  
 idCompra INT,  
); 

CREATE TABLE Autor 
( 
 Nacionalidade INT,  
 ID INT PRIMARY KEY,  
 Snome INT,  
 Pnome INT,  
); 

CREATE TABLE Editora 
( 
 Nome INT,  
 ID INT PRIMARY KEY,  
 idLivro INT,  
); 

CREATE TABLE Gênero 
( 
 Nome INT,  
 ID INT PRIMARY KEY,  
); 

CREATE TABLE Compra 
( 
 Data_Compra INT,  
 Total INT,  
 Num_Nota_Fiscal INT PRIMARY KEY,  
); 

CREATE TABLE Livro_Vendido 
( 
 Quantidade INT,  
); 

CREATE TABLE Seção 
( 
 Nome INT,  
 ID INT PRIMARY KEY,  
 idGênero INT,  
); 

CREATE TABLE Escrito 
( 
 ID INT PRIMARY KEY,  
 ISBN INT PRIMARY KEY,  
); 

CREATE TABLE Possui 
( 
 Num_Nota_Fiscal INT PRIMARY KEY,  
 idLivro_Vendido INT PRIMARY KEY,  
); 

ALTER TABLE Livro ADD FOREIGN KEY(idGênero) REFERENCES Gênero (idGênero)
ALTER TABLE Livro ADD FOREIGN KEY(idLivro_Vendido) REFERENCES Livro_Vendido (idLivro_Vendido)
ALTER TABLE Cliente ADD FOREIGN KEY(idCompra) REFERENCES Compra (idCompra)
ALTER TABLE Editora ADD FOREIGN KEY(idLivro) REFERENCES Livro (idLivro)
ALTER TABLE Seção ADD FOREIGN KEY(idGênero) REFERENCES Gênero (idGênero)
ALTER TABLE Escrito ADD FOREIGN KEY(ID) REFERENCES Autor (ID)
ALTER TABLE Escrito ADD FOREIGN KEY(ISBN) REFERENCES Livro (ISBN)
ALTER TABLE Possui ADD FOREIGN KEY(Num_Nota_Fiscal) REFERENCES Compra (Num_Nota_Fiscal)
ALTER TABLE Possui ADD FOREIGN KEY(idLivro_Vendido) REFERENCES Livro_Vendido (idLivro_Vendido)
