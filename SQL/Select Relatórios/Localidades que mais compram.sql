SELECT CONCAT(cl.Cidade, ' - ', cl.Estado) AS Localizacao, SUM(cp.Total) AS Total_Gasto
FROM livraria.Cliente cl
JOIN livraria.Compra cp ON cp.CPF_Cliente = cl.CPF
WHERE cp.Data_Compra BETWEEN '2023-09-29' AND '2024-10-06'
GROUP BY Localizacao
HAVING SUM(cp.Total)>90
ORDER BY Total_Gasto DESC
LIMIT 5;
