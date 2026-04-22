# ─── Stage 1 : Build ───────────────────────────────────────────────────────────
FROM node:22-alpine AS builder

WORKDIR /app

# Copie les fichiers de dépendances en premier pour profiter du cache Docker
COPY package.json package-lock.json ./
RUN npm ci

# Copie le reste du code source
COPY . .

# Build de production (utilise environment.prod.ts via fileReplacements)
RUN npm run build

# ─── Stage 2 : Serve ───────────────────────────────────────────────────────────
FROM nginx:alpine

# Copie les fichiers buildés vers le dossier servi par Nginx
COPY --from=builder /app/dist/ecommerce/browser /usr/share/nginx/html

# La config nginx est injectée via un volume dans docker-compose
# (nginx.microservices.conf ou nginx.monolith.conf selon l'architecture)

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
