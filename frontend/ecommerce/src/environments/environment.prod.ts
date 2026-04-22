export const environment = {
  production: true,
  // Nginx joue le rôle de gateway et reverse-proxy.
  // Le frontend appelle des chemins relatifs → pas de CORS, pas d'adresse hardcodée.
  catalogueServiceUrl: '/api/products',
  orderServiceUrl: '/api/orders',
};
