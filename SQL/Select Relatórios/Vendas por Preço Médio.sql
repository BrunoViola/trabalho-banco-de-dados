-- Esta consulta retorna dados acerca da quantidade de livros vendidos por faixa de preço
-- 
SELECT AVG(l.Preco) AS Preco_Medio,
       SUM(ps.Quantidade) AS Unidades_Vendidas
FROM livraria.Possui ps
JOIN livraria.Livro l ON ps.ISBN_Livro = l.ISBN
JOIN livraria.Pertence p ON l.ISBN = p.ISBN_Livro
JOIN livraria.Genero g ON p.ID_Genero = g.ID
GROUP BY l.ISBN
ORDER BY Preco_Medio ASC;