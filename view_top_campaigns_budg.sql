CREATE OR ALTER VIEW top5_campaigns_budget AS
SELECT TOP 5
    id_campaing,
    name,
    budget
FROM
    marketing.dim_campaigns
ORDER BY
    budget DESC;