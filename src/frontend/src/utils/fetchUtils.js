
const baseConfig = {
    mode: "cors",
    cache: "no-cache",
    credentials: "same-origin",
    headers: {
        "Content-Type": "application/json; charset=utf-8",
    },
    redirect: "follow",
    referrer: "no-referrer",
};


const send = (method, payload) => (
    async function (uri, config) {
        let sources = [config];
        if (method === "POST" || method === 'PUT') {
            sources.push({body: JSON.stringify(payload)});
        }
        config = Object.assign({
            method: method,
            ...baseConfig,
        }, ...sources);
        return await fetch(process.env.PUBLIC_URL+uri, config)
    }
);

const getFetch = (uri, config = {}) => (
    send("GET")(uri, config)
);



const postFetch = (uri, data = undefined, config = {}) => (
    send("POST", data)(uri, config)
);

const putFetch = (uri, data, config = {}) => (
    send("PUT", data)(uri, config)
);

const deleteFetch = (uri, config = {}) => (
    send("DELETE")(uri, config)
);

export {getFetch, postFetch, putFetch, deleteFetch};
