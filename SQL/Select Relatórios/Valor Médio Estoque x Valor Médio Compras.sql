SELECT s.Nome, AVG(l.Preco) AS Preco_Medio_Estoque, COALESCE(AVG(psi.Preco),0) as Preco_Medio_Compras
FROM livraria.Livro l
JOIN livraria.Pertence p ON p.ISBN_Livro = l.ISBN
JOIN livraria.Genero g ON g.ID = p.ID_genero
JOIN livraria.Secao s ON g.ID_Secao = s.ID
LEFT JOIN livraria.Possui psi ON psi.ISBN_Livro = l.ISBN
GROUP BY s.Nome
ORDER BY Preco_Medio_Estoque DESC;