-- Inserindo as Seções
INSERT INTO livraria.Secao(nome)
VALUES
    ('Administração e Economia'),
    ('Autoajuda'),
    ('Educação e Didáticos'),
    ('Fantasia e Horror'),
    ('HQS e Mangás'),
    ('Infantil'),
    ('Importados'),
    ('Literatura e Ficção'),
    ('Religião e Espiritualidade'),
    ('Romance'),
    ('Saúde e Família'),
    ('Política e Filosofia');

SELECT * FROM livraria.secao
ORDER BY id ASC