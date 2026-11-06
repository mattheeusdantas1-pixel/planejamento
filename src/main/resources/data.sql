INSERT INTO empresa (nome, missao, visao) VALUES
('Sopa da Roxa',
 'Servir sopas e tapiocas caseiras, com sabor de comida de casa, atendimento acolhedor e preço justo.',
 'Ser a soparia mais lembrada do Recife até 2028, no salão e no delivery.');

INSERT INTO departamento (nome, responsavel) VALUES
('Cozinha', 'Vanessa'),
('Atendimento', 'Mattheeus'),
('Delivery', 'Douglas'),
('Marketing', 'Mattheeus'),
('Financeiro', 'Vanessa');

INSERT INTO objetivo (titulo, descricao, perspectiva, empresa_id) VALUES
('Aumentar as vendas do delivery', 'Fazer o delivery crescer e virar uma parte maior do faturamento', 'FINANCEIRA', 1),
('Reduzir o desperdício na cozinha', 'Diminuir a sobra de sopa e de ingredientes no fim do dia', 'PROCESSOS_INTERNOS', 1),
('Fortalecer a marca nas redes sociais', 'Fazer mais gente conhecer a Sopa da Roxa pelo Instagram', 'CLIENTES', 1),
('Capacitar a equipe', 'Treinar a equipe em atendimento e boas práticas na cozinha', 'APRENDIZADO_CRESCIMENTO', 1);

INSERT INTO meta (descricao, prazo, objetivo_id, departamento_id) VALUES
('Delivery chegar a 40% das vendas', '2027-03-31', 1, 3),
('Tempo médio de entrega abaixo de 35 minutos', '2027-01-31', 1, 3),
('Sobra de sopa no fim do dia menor que 3 litros', '2026-12-31', 2, 1),
('Chegar a 20 mil seguidores no Instagram', '2027-06-30', 3, 4),
('Postar cardápio do dia todos os dias', '2026-12-31', 3, 4),
('Todos da equipe fazerem curso de boas práticas', '2027-02-28', 4, 1);

INSERT INTO metrica (descricao, unidade, valor_alvo, meta_id) VALUES
('Percentual das vendas feitas pelo delivery', '%', 40, 1),
('Tempo médio de entrega', 'minutos', 35, 2),
('Litros de sopa que sobram por dia', 'litros', 3, 3),
('Seguidores no Instagram', 'seguidores', 20000, 4),
('Dias com cardápio postado no mês', 'dias', 30, 5),
('Funcionários com curso concluído', '%', 100, 6);

INSERT INTO indicador (valor, data_medicao, observacao, metrica_id) VALUES
(22, '2026-09-30', 'Fechamento de setembro', 1),
(27, '2026-10-31', 'Fechamento de outubro', 1),
(48, '2026-09-30', 'Média de setembro', 2),
(41, '2026-10-31', 'Média de outubro', 2),
(6.5, '2026-10-31', 'Média de outubro', 3),
(1850, '2026-10-01', 'Contagem no início do mês', 4),
(2300, '2026-11-01', 'Contagem no início do mês', 4),
(18, '2026-10-31', 'Outubro', 5),
(25, '2026-10-31', '1 de 4 funcionários concluiu', 6);
