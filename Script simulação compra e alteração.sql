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
	('1234567800012345', '9786586551525', '1', '59.00');
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

-- Alterando descrição de um livro
UPDATE livraria.Livro 
SET Descricao = 'Por que os grandes gurus do Vale do Silício proíbem seus filhos de usar telas? Você sabia que nunca na história da humanidade houve um declínio tão acentuado nas habilidades cognitivas? Você sabia que apenas trinta minutos por dia na frente de uma tela são suficientes para que o desenvolvimento intelectual da criança comece a ser afetado?

O uso da tecnologia digital - smartphones, computadores, tablets, etc. - pelas novas gerações tem sido absolutamente astronômico. Para crianças de 2 a 8 anos de idade, o consumo médio é de cerca de três horas por dia. Entre 8 e 12 anos, a média diária gira em torno de cinco horas. Na adolescência, esse número sobe para quase sete horas, o que significa mais de 2.400 horas por ano, em plena fase de desenvolvimento intelectual.

Ao contrário do que a imprensa e a indústria da tecnologia costumam difundir, o uso das telas, longe de ajudar no desenvolvimento de crianças e estudantes, acarreta sérios malefícios à saúde do corpo (obesidade, problemas cardiovasculares, expectativa de vida reduzida), ao estado emocional (agressividade, depressão, comportamentos de risco) e ao desenvolvimento intelectual (empobrecimento da linguagem, dificuldade de concentração e memória).

O neurocientista Michel Desmurget, diretor de pesquisa do Instituto Nacional de Saúde da França, propõe a primeira síntese de vários estudos que confirmaram os perigos reais das telas e nos alerta para as graves consequências de continuarmos a promover sem senso crítico o uso dessas tecnologias. ' 
WHERE ISBN = '9786586551525';
SELECT * FROM livraria.Livro WHERE ISBN = '9786586551525';

--Deletando uma editora (causando a remoção de livros, mas não deleta o ISBN do Possui)
DELETE FROM livraria.Editora WHERE Nome = 'Vestígio';
SELECT * FROM livraria.editora ORDER BY id ASC;

SELECT * FROM livraria.Possui WHERE Num_Nota_Fiscal_Compra = '1234567800012345';