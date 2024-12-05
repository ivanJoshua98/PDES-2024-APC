import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {    
    vus: 10,  // Número de usuarios virtuales
    duration: '5s',  // Duración de la prueba
};

export default function () {
    const payload = JSON.stringify({
        email: 'ivansanmartin@mail.com',
        password: 'Contrasegura.',
      });
    const headers = { 'Content-Type': 'application/json' };
    
    
    const res = http.post('http://localhost:8080/apc/log-in', payload, { headers });  // Cambia la URL según sea necesario

    check(res, {
        'status es 200': (r) => r.status === 200,
    });

    sleep(0);  // Tiempo de espera entre las solicitudes de cada usuario
}
