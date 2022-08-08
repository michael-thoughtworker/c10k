import { Trend, Counter } from "k6/metrics";

export const options = {
    scenarios: {
        sce: {
            executor: 'constant-arrival-rate',
            rate: __ENV.TPS,
            duration: __ENV.DURATION,
            timeUnit: '1s',
            preAllocatedVUs: __ENV.VUS || 3000,
            maxVUs: 20000,
            gracefulStop: '30s',

            // startRate: 5,
            // executor: 'ramping-arrival-rate',
            // stages: [
            // 	{ target: 20, duration: '5s' }, // go from 50 to 200 iters/s in the first 30 seconds
            // 	{ target: 100, duration: '50s' }, // hold at 200 iters/s for 3.5 minutes
            // 	{ target: 0, duration: '5s' }, // ramp down back to 0 iters/s over the last 30 second
            //   ]
        },
    },
    'summaryTrendStats': ['min', 'med', 'avg', 'p(90)', 'p(95)', 'max', 'count'],
    thresholds: {
        checks: [{ threshold: 'rate == 1.00' }], // fail test on any expect() failure
    },
    insecureSkipTLSVerify: true,
};

export let apiTrend = new Trend("c10k_get_api");

