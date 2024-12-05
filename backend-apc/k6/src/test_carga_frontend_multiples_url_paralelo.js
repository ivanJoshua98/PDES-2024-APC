import http from "k6/http";
import { sleep, check } from "k6";

export const options = {
    thresholds: {

      // 90% of requests must finish within 400ms.
      http_req_duration: ['p(90) < 900'],
    },
    
     stages: [
        { duration: '5s', target: 4 },
        { duration: '5s', target: 50 },
        { duration: '5s', target: 0 },
      ], 
  };


export default function () {
 
   const responses = http.batch([["GET","http://localhost:3000/sign-in"],
  ["GET","http://localhost:3000/sign-up"]]);

  check(responses[0], {
    "R1 status es 200": (r) => r.status === 200     
  });

  check(responses[1], {
    "R2 status es 200": (r) => r.status === 200
  }); 
 
 sleep(0);
}
