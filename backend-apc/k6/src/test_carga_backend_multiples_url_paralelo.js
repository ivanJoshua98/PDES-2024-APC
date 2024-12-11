import http from "k6/http";
import { check } from "k6";

export const options = {
    thresholds: {

      // 90% of requests must finish within 900ms.
      http_req_duration: ['p(90) < 2000'],
    },
    
     stages: [
        { duration: '5s', target: 4 },
        { duration: '10s', target: 50 },
        { duration: '5s', target: 0 },
      ], 
  };

export default function () {

    const headers = { 
        'Content-Type': 'application/json', 
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQWRtaW5AbWFpbC5jb20iLCJpYXQiOjE3MzM5MjY0MzF9.ByzsZo66qRby3HA38aXqTOx8OVwex7okLuFvrorXKFc'
    };
 
   const responses = http.batch([["GET",'http://localhost:8080/apc/shopping-cart/all-purchases', null, {headers}],
  ["GET",'http://localhost:8080/apc/admin/reports/users-with-most-purchases-by-shopping-carts', null, {headers}]]);

  check(responses[0], {
    "R1 status es 200": (r) => r.status === 200     
  });

  check(responses[1], {
    "R2 status es 200": (r) => r.status === 200
  }); 
 
}