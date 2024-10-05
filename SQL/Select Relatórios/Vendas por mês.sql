-- Esta consulta retorna o valor total de venda de livros por mês dos últimos 6 meses
SELECT TO_CHAR(c.data_compra, 'Month') AS mes,
    SUM(p.Preco * p.Quantidade) AS Total_Vendas
FROM livraria.Compra c
JOIN livraria.Possui p ON c.Num_Nota_Fiscal = p.Num_Nota_Fiscal_Compra
GROUP BY mes
ORDER BY mes DESC
LIMIT 6;