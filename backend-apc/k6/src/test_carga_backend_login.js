import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {    
    vus: 50,  // Número de usuarios virtuales
    duration: '30s',  // Duración de la prueba
    thresholds: {
        // 90% of requests must finish within 900ms.
        http_req_duration: ['p(90) < 9000'],
      },
};

export default function () {
    const payload = JSON.stringify({
        email: 'userBuyer1@mail.com',
        password: 'Credential.',
      });
    const headers = { 'Content-Type': 'application/json' };
    
    
    const res = http.post('http://localhost:8080/apc/log-in', payload, { headers }); 

    check(res, {
        'status es 200': (r) => r.status === 200,
        'get body successfully': (r) => r.body.includes('userName'),
    });
}
