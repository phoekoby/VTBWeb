import axios from "axios"

const api_url = 'http://localhost:8080/api/v1'

const $host = axios.create({
    baseURL:api_url
})

const $authHost = axios.create({
    baseURL:api_url
})

const authInterceptor = config => {
    config.headers.authorization = `Bearer ${localStorage.getItem('token')}`
    return config
}

$authHost.interceptors.request.use(authInterceptor)

export {
    $host,
    $authHost,
    api_url,
}