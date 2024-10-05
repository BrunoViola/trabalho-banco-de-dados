-- A consulta abaixo retorna, em ordem decrescente, os 5 autores que mais venderam livros nos últimos 30 dias
-- mostrando seus nomes completos e a soma de todos os seus livros vendidos nesse período
SELECT concat(a.Pnome,' ', a.Snome) AS "Nome Autor", 
      SUM(ps.quantidade) AS Quantidade
FROM livraria.Livro l
JOIN livraria.Possui ps ON ps.ISBN_Livro = l.ISBN
JOIN livraria.Escrito e ON e.ISBN_Livro = l.ISBN
JOIN livraria.Autor a ON e.ID_Autor = a.ID
JOIN livraria.Compra c ON ps.Num_Nota_Fiscal_Compra = c.Num_Nota_Fiscal
WHERE c.Data_Compra >= CURRENT_DATE - 30 AND c.Data_Compra <= CURRENT_DATE
GROUP BY "Nome Autor"
ORDER BY Quantidade DESC
LIMIT 5