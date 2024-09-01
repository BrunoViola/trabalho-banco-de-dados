-- Inserindo um Cliente
INSERT INTO livraria.Cliente(CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado)
VALUES
	('71583320989', 'm', '2002-01-04', 'carlosmendes@gmail.com', 'Carlos', 'Mendes', 'Curitiba', 'PR');

SELECT * FROM livraria.Cliente;

BEGIN;
-- Inserindo uma Compra
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('1234567800012345', '71583320989', '99.20');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('1234567800012345', '9788532649966', '1', '40.20'),
	('1234567800012345', '9786586551525', '2', '59.00');
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '1234567800012345';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788532649966' AND Num_Nota_Fiscal_Compra = '1234567800012345')
WHERE ISBN = '9788532649966';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9786586551525' AND Num_Nota_Fiscal_Compra = '1234567800012345')
WHERE ISBN = '9786586551525';

-- Alterando o preço de um livro
UPDATE livraria.Livro SET Preco = '60.00' WHERE ISBN = '9786586551525';

SELECT * FROM livraria.Livro WHERE ISBN = '9786586551525';