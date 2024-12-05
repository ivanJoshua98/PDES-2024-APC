import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,  // Número de usuarios virtuales
    duration: '10s',  // Duración de la prueba
};

// Lista de URLs a probar
const urls = [
    'http://localhost:8080/apc/shopping-cart/all-purchases',  // URL 1 All shopping carts of logged-in user.
    'http://localhost:8080/apc/admin/reports/users-with-most-purchases-by-shopping-carts',  // URL 2 
];

export default function () {

    const headers = { 
        'Content-Type': 'application/json', 
        'Authorization':  defaultConfig.token
    };

    // Recorre cada URL y realiza la solicitud
    urls.forEach(url => {
        const res = http.get(url, {headers});

        check(res, {
            'status es 200': (r) => r.status === 200
        });
        
        sleep(0);  // Tiempo de espera entre solicitudes para cada URL
    });
}