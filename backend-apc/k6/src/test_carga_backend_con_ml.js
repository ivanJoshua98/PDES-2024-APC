import http from 'k6/http';
import { check } from 'k6';

export const options = {    
    thresholds: {
        // 90% of requests must finish within 900ms.
        http_req_duration: ['p(90) < 5000'],
      },
    stages: [
        { duration: '10s', target: 4 },
        { duration: '60s', target: 50 },
        { duration: '10s', target: 0 },
      ], 
};

export default function () {

    const headers = { 
        'Content-Type': 'application/json', 
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQWRtaW5AbWFpbC5jb20iLCJpYXQiOjE3MzM5MjY0MzF9.ByzsZo66qRby3HA38aXqTOx8OVwex7okLuFvrorXKFc'
    };
    
    
    const res = http.get('http://localhost:8080/products/search/pelota%20de%20futbol', { headers }); 

    check(res, {
        'status es 200': (r) => r.status === 200,
        'get body successfully': (r) => r.body.includes('Pelota'),
    });
}