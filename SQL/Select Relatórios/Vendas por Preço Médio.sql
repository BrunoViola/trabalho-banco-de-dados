-- Esta consulta retorna dados acerca da quantidade de livros vendidos por faixa de preço
WITH faixas AS (
    SELECT '0 - 9.99' AS Faixa_Preco, 0 AS Unidades_Vendidas
    UNION ALL
    SELECT '10 - 29.99', 0
    UNION ALL
    SELECT '30 - 49.99', 0
    UNION ALL
    SELECT '50 - 99.99', 0
    UNION ALL
    SELECT 'acima de 100', 0
)
SELECT
	fxs.Faixa_Preco,
	COALESCE(SUM(ps.Quantidade), 0) AS Unidades_Vendidas
FROM faixas fxs
LEFT JOIN livraria.Livro l ON
    (CASE 
        WHEN fxs.Faixa_Preco = '0 - 9.99' THEN l.Preco <= 9.99
        WHEN fxs.Faixa_Preco ='10 - 29.99' THEN l.Preco BETWEEN 10 AND 29.99
        WHEN fxs.Faixa_Preco ='30 - 49.99' THEN l.Preco BETWEEN 30 AND 49.99
        WHEN fxs.Faixa_Preco ='50 - 99.99' THEN l.Preco BETWEEN 50 AND 99.99
        ELSE l.preco >= 100
    END)
LEFT JOIN livraria.Possui ps ON ps.ISBN_Livro = l.ISBN
GROUP BY Faixa_Preco
ORDER BY Faixa_Preco;