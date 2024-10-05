-- Esse script é reponsável por inserir novos clientes e suas compras no banco de dados
-- Inserindo um Primeiro Cliente
INSERT INTO livraria.Cliente(CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado)
VALUES
	('52474411078', 'f', '1980-07-09', 'franciscaxavier@gmail.com', 'Francisca', 'Xavier', 'Pindamonhangaba', 'SP');

BEGIN;
-- Inserindo sua Compra
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('2345678900013456', '52474411078', '48.13');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('2345678900013456', '9788594318015', '1', '15.00'), -- O cão dos baskerville
	('2345678900013456', '9788594318107', '1', '10.73'), -- Um estudo em vermelho
	('2345678900013456', '9788594318169', '1', '22.40'); -- O signo dos quatro
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '2345678900013456';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318015' AND Num_Nota_Fiscal_Compra = '2345678900013456')
WHERE ISBN = '9788594318015';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318107' AND Num_Nota_Fiscal_Compra = '2345678900013456')
WHERE ISBN = '9788594318107';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318169' AND Num_Nota_Fiscal_Compra = '2345678900013456')
WHERE ISBN = '9788594318169';





-------------------------------------------------------------------------

-- Inserindo um Segundo Cliente
INSERT INTO livraria.Cliente(CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado)
VALUES
	('11174411071', 'f', '2005-05-08', 'catvanessa@outlook.com', 'Cátia', 'Vanessa', 'Manaus', 'AM');

BEGIN;
-- Inserindo sua Compra
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('3345678900013456', '11174411071', '45.63');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('3345678900013456', '9788594318015', '1', '15.00'), -- O cão dos baskerville
	('3345678900013456', '9788594318107', '1', '10.73'), -- Um estudo em vermelho
	('3345678900013456', '9786584956193', '1', '19.90'); -- O Príncipe - Edição de Luxo
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '3345678900013456';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318015' AND Num_Nota_Fiscal_Compra = '3345678900013456')
WHERE ISBN = '9788594318015';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318107' AND Num_Nota_Fiscal_Compra = '3345678900013456')
WHERE ISBN = '9788594318107';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9786584956193' AND Num_Nota_Fiscal_Compra = '3345678900013456')
WHERE ISBN = '9786584956193';





-------------------------------------------------------------------------

-- Inserindo um Terceiro Cliente
INSERT INTO livraria.Cliente(CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado)
VALUES
	('98765411070', 'm', '1965-05-08', 'vagner@live.com', 'Vagner', 'Afonso', 'Joinville', 'SC');

BEGIN;
-- Inserindo sua Compra
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('6215675960017453', '98765411070', '87.40');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('6215675960017453', '9788550801483', '1', '46.67'), -- Pai Rico, pai Pobre: Edição de 20 Anos Atualizada e Ampliada
	('6215675960017453', '9788594318107', '1', '10.73'), -- Um estudo em vermelho
	('6215675960017453', '9786525918914', '1', '30.00'); -- One Piece Vol. 107
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '6215675960017453';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788550801483' AND Num_Nota_Fiscal_Compra = '6215675960017453')
WHERE ISBN = '9788550801483';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318107' AND Num_Nota_Fiscal_Compra = '6215675960017453')
WHERE ISBN = '9788594318107';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9786525918914' AND Num_Nota_Fiscal_Compra = '6215675960017453')
WHERE ISBN = '9786525918914';





-------------------------------------------------------------------------

-- Inserindo um Quarto Cliente
INSERT INTO livraria.Cliente(CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado)
VALUES
	('79065120070', 'f', '1999-12-30', 'febraga@live.com', 'Fernanda', 'Braga', 'Palmas', 'TO');

BEGIN;
-- Inserindo sua Compra
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('1000675960018010', '79065120070', '10.73');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('1000675960018010', '9788594318107', '1', '10.73'); -- Um estudo em vermelho
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '1000675960018010';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318107' AND Num_Nota_Fiscal_Compra = '1000675960018010')
WHERE ISBN = '9788594318107';





-------------------------------------------------------------------------

-- Inserindo um Quinto Cliente
INSERT INTO livraria.Cliente(CPF, Sexo, Data_nascimento, Email, Pnome, Snome, Cidade, Estado)
VALUES
	('50176230080', 'm', '2004-11-25', 'patriciabraga@live.com', 'Patrícia', 'Braga', 'Palmas', 'TO');

BEGIN;
-- Inserindo sua Compra
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('1456786070028010', '50176230080', '50.81');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('1456786070028010', '9788594318107', '1', '10.73'), -- Um estudo em vermelho
	('1456786070028010', '9786555396881', '1', '40.08'); -- Michaelis Dicionário Escolar Língua Portuguesa
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '1456786070028010';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318107' AND Num_Nota_Fiscal_Compra = '1456786070028010')
WHERE ISBN = '9788594318107';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9786555396881' AND Num_Nota_Fiscal_Compra = '1456786070028010')
WHERE ISBN = '9786555396881';


-------------------------------------------------------------------------
-- Adicionando compras de clientes já cadastrados acima
-- Inserindo mais uma Compra do Segundo cliente
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('3345678900013467', '11174411071', '178.01');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('3345678900013467', '9788594318947', '1', '13.09'), -- Noite na Taverna
	('3345678900013467', '9786588280799', '1', '92.25'), -- Agulhas ativar: Crochetando roupas com Anne Galante
	('3345678900013467', '9788543105291', '1', '44.92'), -- As coisas que você só vê quando desacelera: Como manter a calma em um mundo frenético
	('3345678900013467', '9786525033723', '1', '27.75'); -- A Prateleira do Amor: Sobre Mulheres, Homens e Relações
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '1456786070028010';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788594318947' AND Num_Nota_Fiscal_Compra = '3345678900013467')
WHERE ISBN = '9788594318947';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9786588280799' AND Num_Nota_Fiscal_Compra = '3345678900013467')
WHERE ISBN = '9786588280799';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788543105291' AND Num_Nota_Fiscal_Compra = '3345678900013467')
WHERE ISBN = '9788543105291';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9786525033723' AND Num_Nota_Fiscal_Compra = '3345678900013467')
WHERE ISBN = '9786525033723';




-- Inserindo mais uma Compra do Quinto cliente
INSERT INTO livraria.Compra(Num_Nota_Fiscal, CPF_Cliente, Total)
VALUES
	('1456786070028021', '50176230080', '65.90');

-- Inserindo relação Possui
INSERT INTO livraria.Possui(Num_Nota_Fiscal_Compra, ISBN_Livro, Quantidade, Preco)
VALUES
	('1456786070028021', '9786555606157', '1', '50.90'), -- Outlive: A arte e a ciência de viver mais e melhor
	('1456786070028021', '9788562409028', '1', '15.00'); -- 38 estratégias para vencer qualquer debate
COMMIT;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '1456786070028021';

-- Atualizando o estoque dos livros comprados
UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9786555606157' AND Num_Nota_Fiscal_Compra = '1456786070028021')
WHERE ISBN = '9786555606157';

UPDATE livraria.Livro
SET Estoque = Estoque - (SELECT Quantidade FROM livraria.Possui WHERE ISBN_Livro = '9788562409028' AND Num_Nota_Fiscal_Compra = '1456786070028021')
WHERE ISBN = '9788562409028';