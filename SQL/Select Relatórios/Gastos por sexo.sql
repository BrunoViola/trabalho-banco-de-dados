-- A consulta retorna resultados que possiblitam uma comparação entre o
-- gasto médio por sexo na livraria
SELECT cl.Sexo, AVG(cp.Total) AS Total_Vendas
FROM livraria.Cliente cl
JOIN livraria.Compra cp ON cp.CPF_Cliente = cl.CPF
GROUP BY cl.Sexo
ORDER BY Total_Vendas DESC;