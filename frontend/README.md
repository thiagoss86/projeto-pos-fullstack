# Cars Frontend (React + Vite + Tailwind)

Frontend para integrar com o backend Spring Boot deste repositório.

## Requisitos
- Node 18+
- Backend rodando em `http://localhost:8080`

## Como rodar
```bash
npm install
npm run dev
```

O Vite está configurado com proxy para `/api` em `http://localhost:8080`, então o frontend chama:
- `GET /api/carros` (paginado via headers `page` e `size`)
- `POST /api/carros`, `PUT /api/carros/{id}`, `DELETE /api/carros/{id}`
- `GET /api/carros/search`
- `POST /api/usuarios/login`
- `GET /api/usuarios/my-profile` (com `authorization: Bearer <jwt>`)

## Mockup (visão geral)
- Topbar com navegação: **Carros**, **Meu perfil**, **Sair**
- **Login**: formulário de e-mail/senha, armazena JWT em `localStorage`
- **Carros**:
  - Filtros (modelo/fabricante/país)
  - Tabela com paginação
  - Modal para criar/editar
  - Excluir com confirmação
- **Meu perfil**: exibe `id` e `email` retornados pelo backend

## Observações
- O backend atual valida token apenas no endpoint `/api/usuarios/my-profile`. Os endpoints de `/api/carros` estão públicos.
- O botão **Sair** apenas remove o token local (não existe logout server-side no backend atual).
