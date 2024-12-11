import http from 'k6/http';
import { check } from 'k6';

export const options = {
    vus: 10,  // Número de usuarios virtuales
    duration: '10s',  // Duración de la prueba
    thresholds: {
        http_req_duration: ['p(90) < 600'],
    }
};

// Lista de URLs a probar
const urls = [
    'http://localhost:8080/apc/shopping-cart/all-purchases',  // URL 1 All shopping carts of logged-in user.
    'http://localhost:8080/apc/admin/reports/users-with-most-purchases-by-shopping-carts',  // URL 2 
];

export default function () {

    const headers = { 
        'Content-Type': 'application/json', 
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQWRtaW5AbWFpbC5jb20iLCJpYXQiOjE3MzM5MjY0MzF9.ByzsZo66qRby3HA38aXqTOx8OVwex7okLuFvrorXKFc'
    };

    // Recorre cada URL y realiza la solicitud
    urls.forEach(url => {
        const res = http.get(url, {headers});

        check(res, {
            'status es 200': (r) => r.status === 200
        });
    });
}