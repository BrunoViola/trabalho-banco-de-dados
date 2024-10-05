-- A consulta retorna o nome de uma seção e o valor referente à soma dos
-- valores de todos os livros que pertencem à ela
SELECT s.Nome, SUM(l.Estoque*l.Preco) AS Estoque_Valiosos_Secao
FROM livraria.Livro l
JOIN livraria.Pertence p ON p.ISBN_Livro = l.ISBN
JOIN livraria.Genero g ON g.ID = p.ID_genero
JOIN livraria.Secao s ON g.ID_Secao = s.ID
GROUP BY s.Nome
ORDER BY Estoque_Valiosos_Secao DESC;