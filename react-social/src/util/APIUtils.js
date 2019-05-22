import { API_BASE_URL, ACCESS_TOKEN, REFRESH_TOKEN, TOKEN_TYPE, EXPIRY_DURATION } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    const requestOptions = Object.assign({}, defaults, options);

    return fetch(options.url, requestOptions)
    .then(response => 
        response.json().then(json => {
            if(!response.ok) {
                if(/^JWT expired/.test(json.message)){
                    console.log("true", json, response);

                    refresh()
                    .then(response => {
                        console.log("refresh response",response);
                        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                        localStorage.setItem(REFRESH_TOKEN, response.refreshToken);
                        localStorage.setItem(TOKEN_TYPE, response.tokenType);
                        localStorage.setItem(EXPIRY_DURATION, response.expiryDuration);

                        const refreshHeaders = new Headers({
                            'Content-Type': 'application/json',
                        });
                        refreshHeaders.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
                        const refreshOptions = Object.assign({}, {headers: refreshHeaders}, options);
                        fetch(options.url, refreshOptions)
                            .then(response =>
                                response.json().then(json => {
                                    if(!response.ok) {
                                        return Promise.reject(json);
                                    }
                                    return json;
                                })
                            );

                    }).catch(error => {
                        console.log("refresh error",response);
                    });

                } else {
                    return Promise.reject(json);
                }
            }
            return json;
        })
    );
};

export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function refresh() {
    const refreshToken = localStorage.getItem(REFRESH_TOKEN);
    const refreshRequest = {refreshToken:refreshToken};
    return request({
        url: API_BASE_URL + "/auth/refresh",
        method: 'POST',
        body: JSON.stringify(refreshRequest)
    });
}