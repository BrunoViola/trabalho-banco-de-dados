-- Esta consulta retorna dados acerca da quantidade de livros vendidos por faixa de preço
SELECT 
    CASE 
        WHEN l.Preco < 10 THEN '0 - 9.99'
        WHEN l.Preco < 30 THEN '10 - 29.99'
        WHEN l.Preco < 50 THEN '30 - 49.99'
        WHEN l.Preco < 100 THEN '50 - 99.99'
        ELSE '100 e acima'
    END AS Faixa_Preco,
    SUM(ps.Quantidade) AS Unidades_Vendidas
FROM livraria.Possui ps
JOIN livraria.Livro l ON ps.ISBN_Livro = l.ISBN
JOIN livraria.Pertence p ON l.ISBN = p.ISBN_Livro
JOIN livraria.Genero g ON p.ID_Genero = g.ID
GROUP BY Faixa_Preco
ORDER BY Faixa_Preco;