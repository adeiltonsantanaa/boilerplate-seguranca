INSERT INTO public.usuario (nome_usuario,primeiro_nome,senha,ultimo_nome) VALUES
    ('filhoaguiar20@gmail.com','Adeilton','$2a$10$I7PxA7hHBn4Ua5XZhs76DeVs1aHTzKQlms6r1LrQEA603TP4OUmdG','Santana');
INSERT INTO public.permissao (nome) VALUES
    ('TESTE');
INSERT INTO public.papel (nome) VALUES
    ('ADMIN');
INSERT INTO public.papel_permissao (papel_id,permissao_id) VALUES
    (1,1);
INSERT INTO public.usuario_papel (usuario_id,papel_id) VALUES
    (1,1);