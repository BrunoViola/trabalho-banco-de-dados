-- Inserindo as Seções
INSERT INTO livraria.Secao(Nome)
VALUES
    ('Administração e Economia'), --1
    ('Autoajuda'), --2
    ('Educação e Didáticos'), --3
    ('Fantasia e Horror'), --4
    ('HQS e Mangás'), --5
    ('Infantil'), --6
    ('Importados'), --7
    ('Literatura e Ficção'), --8
    ('Religião e Espiritualidade'), --9
    ('Romance'), --10
    ('Saúde e Família'), --11
    ('Política e Filosofia'); --12

-- Inserindo as Gêneros
INSERT INTO livraria.Genero(Nome, ID_Secao)
VALUES
	('Finanças', 1), --1
	('Economia', 1), --2
	('Gestão e Liderança', 1), --3
	('Autoestima', 2), --4
	('Dicionários', 3), --5
	('Ficção Científica', 4), --6
	('Fantasia', 4), --7
	('Mangá', 5), --8
	('Contos de Fadas, Contos Populares e Mitos', 6), --9
	('Biografias e Histórias Reais', 7), --10
	('Ficção', 8), --11
	('Ação e Aventura', 8), --12
	('Espiritualidade', 9), --13
	('Budismo', 9), --14
	('Paranormal', 10), --15
	('Ficção Científica', 10), --16
	('Fantasia', 10), --17
	('Ação e Aventura', 10), --18
	('Segurança e Primeiros Socorros', 11), --19
	('Saúde Mental', 11), --20
	('Relacionamentos', 11), --21
	('Psicologia e Aconselhamento', 11), --22
	('Nutrição', 11), --23
	('Exercícios e Fitness', 11), --24
	('Beleza e Moda', 11), --25
	('Governo e Política', 12), --26
	('Filosofia', 12), --27
	('Estudos Sobre a Mulher', 12), --28
	('Crimes', 12), --29
	('Ciências Sociais', 12); --30

BEGIN;
-- Inserindo as Editoras
INSERT INTO livraria.Editora(Nome)
VALUES
	('Editora Vozes'), --1
	('Vestígio'), --2
	('Intrínseca'), --3
	('Darkside'), --4
	('Appris Editora'), --5
	('Alta Books'), --6
	('Olhares'), --7
	('Editora Sextante'), --78
	('Zahar'), --9
	('Galenus'), --10
	('Principis'), --11
	('Galera'), --12
	('Thomas Nelson Brasil'), --13
	('Panini'), --14
	('Texugo'), --15
	('Melhoramentos'), --16
	('Editora Garnier'), --17
	('Faro Editorial'); --18

-- Inserindo as Autores
INSERT INTO livraria.Autor(Pnome, Snome, Nacionalidade)
VALUES
	('Byung-Chul', 'Han', 'Coreano'), --1
	('Michel', 'Desmurget', 'Francês'), --2
	('Patrick', 'Radden Keefe', 'Estadunidense'), --3
	('Joe', 'Navarro', 'Cubano'), --4
	('Toni', 'Sciarra Poynter', 'Estadunidense'), --5
	('Valeska', 'Zanello', 'Brasileiro'), --6
	('Arthur', 'Schopenhauer', 'Alemão'), --7
	('Nicolau', 'Maquiavel', 'Italiano'), --8
	('Robert', 'Kiyosaki', 'Estadunidense'), --9
	('Anne', 'Galante', 'Brasileiro'), --10
	('Peter', 'Attia', 'Canadense'), --11
	('Bill', 'Gifford', 'Estadunidense'), --12
	('William', 'Davis', 'Estadunidense'), --13
	('Jacques', 'Lacan', 'Francês'), --14
	('Ednei', 'Fernando dos Santos', 'Brasileiro'), --15
	('Fiódor', 'Dostoiévski', 'Russo'), --16
	('Sarah', 'J. Maas', 'Estadunidense'), --17
	('Clive', 'Staples Lewis', 'Britânico'), --18
	('Álvares', 'de Azevedo', 'Brasileiro'), --19
	('Eiichiro', 'Oda', 'Japonês'), --20
	('Ruth', 'Stiles Gannett', 'Brasileiro'), --21
	('Equipe', 'Melhoramentos', 'Brasileiro'), --22
	('Haemin', 'Sunim', 'Coreano'), --23
	('David', 'Goggins', 'Estadunidense'), --24
	('Arthur', 'Conan Doyle', 'Britânico'); --25

-- Inserindo as Livros
INSERT INTO livraria.Livro(ISBN, Titulo, Ano, Preco, Estoque, ID_Editora)
VALUES
	('9788532649966', 'Sociedade do cansaço', 2015, 40.2, 20, 1), --1
	('9786586551525', 'A fábrica de cretinos digitais: Os perigos das telas para nossas crianças', 2021, 59.00, 13, 2), --2
	('9786555603873', 'Império da dor: A ascensão e queda de uma das mais poderosas famílias americanas e seu criminoso império farmacêutico', 2023, 46.44, 15, 3), --3
	('9786555983012', 'Personalidades Perigosas', 2023, 72.00, 2, 4), --4
	('9786525033723', 'A Prateleira do Amor: Sobre Mulheres, Homens e Relações', 2022, 27.75, 1, 5), --5
	('9788562409028', '38 estratégias para vencer qualquer debate', 2014, 15.00, 51, 18), --6
	('9786584956193', 'O Príncipe - Edição de Luxo', 2023, 19.90, 29, 16), --7
	('9788550801483', 'Pai Rico, pai Pobre: Edição de 20 Anos Atualizada e Ampliada', 2017, 46.67, 25, 6), --8
	('9786588280799', 'Agulhas ativar: Crochetando roupas com Anne Galante', 2023, 92.25, 6, 7), --9
	('9786555606157', 'Outlive: A arte e a ciência de viver mais e melhor', 2023, 50.90, 34, 3), --10
	('9786555644562', 'Superintestino: Descubra como restaurar seu microbioma para fortalecer sua saúde, regular seu peso e melhorar seu humor', 2022, 44.43, 6, 8), --11
	('9786559791729', 'O Seminário, livro 14: A lógica do fantasma', 2024, 149.90, 9, 9), --12
	('9788563960085', 'Manual de Primeiros Socorros da Educação Física aos Esportes: o Papel do Educador Físico no Atendimento de Socorro', 2014, 56.23, 5, 10), --13
	('9786550970284', 'Noites brancas', 2019, 12.94, 23, 11), --14
	('9788501110121', 'Corte de asas e ruína (Vol. 3 Corte de espinhos e rosas)', 2017, 55.25, 27, 12), --15
	('9786556893204', 'Trilogia Cósmica: Volume Único', 2022, 91.17, 11, 13), --16
	('9788594318947', 'Noite na taverna', 2019, 13.09, 7, 11), --17
	('9786525918914', 'One Piece Vol. 107', 2024, 30.00, 11, 14), --18
	('9786585310352', 'O dragão do meu pai', 2024, 25.40, 2, 15), --19
	('9786555396881', 'Michaelis Dicionário Escolar Língua Portuguesa', 2023, 40.08, 5, 16), --20
	('9788543105291', 'As coisas que você só vê quando desacelera: Como manter a calma em um mundo frenético', 2017, 44.92, 13, 8), --21
	('9786555646139', 'Nada pode me ferir', 2023, 47.42, 12, 8), --22
	('9788594318725', 'O mundo perdido', 2019, 19.90, 48, 11), --23
	('9788594318107', 'Sherlock Holmes - Um estudo em vermelho', 2019, 10.73, 49, 11), --24
	('9788594318169', 'Sherlock Holmes - O signo dos quatro', 2019, 22.40, 47, 11), --25
	('9788594318015', 'Sherlock Holmes - O cão dos Baskerville', 2019, 15.00, 40, 11); --26

-- Inserindo as relações Escrito
INSERT INTO livraria.Escrito(ID_Autor, ISBN_Livro)
VALUES
	(1, '9788532649966'), --1
	(2, '9786586551525'), --2
	(3, '9786555603873'), --3
	(4, '9786555983012'), --4
	(5, '9786555983012'), --5
	(6, '9786525033723'), --6
	(7, '9788562409028'), --7
	(8, '9786584956193'), --8
	(9, '9788550801483'), --9
	(10, '9786588280799'), --10
	(11, '9786555606157'), --11
	(12, '9786555606157'), --12
	(13, '9786555644562'), --13
	(14, '9786559791729'), --14
	(15, '9788563960085'), --15
	(16, '9786550970284'), --16
	(17, '9788501110121'), --17
	(18, '9786556893204'), --18
	(19, '9788594318947'), --19
	(20, '9786525918914'), --20
	(21, '9786585310352'), --21
	(22, '9786555396881'), --22
	(23, '9788543105291'), --23
	(24, '9786555646139'), --24
	(25, '9788594318725'), --25
	(25, '9788594318107'), --26
	(25, '9788594318169'), --27
	(25, '9788594318015'); --28

-- Inserindo as relações Pertence
INSERT INTO livraria.Pertence(ISBN_Livro, ID_Genero)
VALUES
	('9788532649966', 30), --1
    ('9786586551525', 30), --2
    ('9786555603873', 30), --3
    ('9786555603873', 29), --4
    ('9786555983012', 30), --5
    ('9786555983012', 29), --6
    ('9786525033723', 28), --7
    ('9786525033723', 21), --8
    ('9786525033723', 20), --9
    ('9788562409028', 27), --10
    ('9788562409028', 3), --11
    ('9786584956193', 26), --12
    ('9786584956193', 10), --13
    ('9786584956193', 2), --14
    ('9788550801483', 1), --15
    ('9786588280799', 25), --16
    ('9786555606157', 24), --17
    ('9786555606157', 21), --18
    ('9786555644562', 23), --19
    ('9786559791729', 22), --20
    ('9788563960085', 19), --21
    ('9786550970284', 18), --22
    ('9786550970284', 11), --23
    ('9786550970284', 12), --24
    ('9788501110121', 18), --25
    ('9788501110121', 17), --26
    ('9788501110121', 7), --27
    ('9786556893204', 17), --28
    ('9786556893204', 16), --29
    ('9786556893204', 7), --30
    ('9786556893204', 6), --31
    ('9788594318947', 15), --32
    ('9788594318947', 11), --33
    ('9786525918914', 8), --34
    ('9786585310352', 9), --35
    ('9786555396881', 5), --36
    ('9788543105291', 14), --37
    ('9788543105291', 13), --38
    ('9786555646139', 4), --39
    ('9786555646139', 13), --40
    ('9788594318725', 11), --41
    ('9788594318107', 12), --42
    ('9788594318107', 11), --43
    ('9788594318169', 12), --44
    ('9788594318169', 11), --45
    ('9788594318015', 18), --46
    ('9788594318015', 11); --47

COMMIT;


SELECT * FROM livraria.secao
ORDER BY id ASC;

SELECT * FROM livraria.genero
ORDER BY id ASC;

SELECT * FROM livraria.editora
ORDER BY id ASC;

SELECT * FROM livraria.Autor
ORDER BY id ASC;

SELECT * FROM livraria.Livro;

SELECT * FROM livraria.Escrito;

SELECT * FROM livraria.Pertence;
