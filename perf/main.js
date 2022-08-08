import http from "k6/http";
import { group, sleep, check, fail } from "k6";
import {apiTrend, options} from "./common.js";
export { options };
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';


function c10k_get( debug) {
    var url = "http://localhost:10001";
    var res = http.get(url);
    if (typeof debug !== 'undefined')
        console.log("init_login: status=" + String(res.status) + "  Body=" + res.body);
    return res;
}

export function handleSummary(data) {
    console.log('Preparing the end-of-test summary...');
    return {
        'stdout': textSummary(data, { indent: ' ', enableColors: true }),
    };
}


export default function (data) {
    var debug = __ENV.DEBUG
    group("test io model", function () {
        var response = c10k_get()
        const check_api = check(response, {
            "status is 200 - init_login_response": (res) => res.status === 200,
        });

        if (check_api == false){
            if (debug === "true")
                console.log("nio api: status=" + String(init_login_response.status) + "  Body=" + init_login_response.body);
            return
        }
        apiTrend.add(response.timings.duration);

    });
};