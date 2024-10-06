-- Aqui será obtidas as faixas de idades dos clientes de maneira bem dividida
WITH faixas AS (
    SELECT 'Menos de 14 anos' AS Faixa_Etaria, 0 AS Quantidade
    UNION ALL
    SELECT '14-17 anos', 0
    UNION ALL
    SELECT '18-24 anos', 0
    UNION ALL
    SELECT '25-44 anos', 0
    UNION ALL
    SELECT '45-64 anos', 0
    UNION ALL
    SELECT '65-74 anos', 0
    UNION ALL
    SELECT '75 anos ou mais', 0
)
SELECT 
    fxs.Faixa_Etaria,
    COALESCE(COUNT(c.CPF), 0) AS Quantidade
FROM faixas fxs
LEFT JOIN livraria.Cliente c ON 
    (CASE 
        WHEN fxs.Faixa_Etaria = 'Menos de 14 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) < 14
        WHEN fxs.Faixa_Etaria = '14-17 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 14 AND 17
        WHEN fxs.Faixa_Etaria = '18-24 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 18 AND 24
        WHEN fxs.Faixa_Etaria = '25-44 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 25 AND 44
        WHEN fxs.Faixa_Etaria = '45-64 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 45 AND 64
        WHEN fxs.Faixa_Etaria = '65-74 anos' THEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) BETWEEN 65 AND 74
        ELSE EXTRACT(YEAR FROM AGE(CURRENT_DATE, c.data_nascimento)) >= 75
    END)
GROUP BY fxs.Faixa_Etaria
ORDER BY 
	CASE 
		WHEN fxs.Faixa_Etaria LIKE 'Menos%' THEN 0 -- Se a fx começar com 'Menos', 0 será atribuido para que ele seja exibido antes
		ELSE 1
		END,
		fxs.Faixa_Etaria ASC;