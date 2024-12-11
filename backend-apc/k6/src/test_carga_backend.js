import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,  // Número de usuarios virtuales
    duration: '5s',  // Duración de la prueba
    thresholds: {
        http_req_duration: ['p(90) < 100'],
    }
};

export default function () {
    const res = http.get('http://localhost:8080/swagger-ui/index.html');
    
    check(res, {
        'status es 200': (r) => r.status === 200,
        'get body successfully': (r) => r.body.includes('Swagger UI'),
    });
}
