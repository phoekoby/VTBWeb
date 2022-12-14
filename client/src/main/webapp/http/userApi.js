import {$authHost, $host} from "./index";
import jwtDecode from "jwt-decode";
import {useContext} from "react";
import {Context} from "../../../index";


//const api = new Api()

// export const checkToken = async (login, password) => {
//     return api.request('/authorize', {login, password}, 'POST')
//         .catch(err => {
//             console.error(err)
//             alert('Ошибка')
//         })
//         .then(data => {
//             if(!data || data.jwt === undefined){
//                 alert('Ошибка при авторизации')
//             }
//             return data
//         })
// }


// export const login = async (email, password) => {
//     return api.request('/authorize', {login: email, password}, 'POST')
//         .catch(err => {
//             console.error(err)
//             alert('Ошибка')
//         })
//         .then(data => {
//             if(!data || data.jwt === undefined){
//                 alert('Ошибка при авторизации')
//             }
//             localStorage.setItem('token',data.jwt)
//             return data
//         })
// }

export const login = async (email,password) => {
    console.log('login')

    const {data} = await $host.post('/authorize',{login: email,password})

    localStorage.setItem('token',data.token)
    return jwtDecode(data.token)
}

export const checkToken = async() => {
    console.log('checkToken')
    let token = localStorage.getItem('token')
    if(token){
        return jwtDecode(token)
    }
    const {data} = await $authHost.get('/authorize')
    localStorage.setItem('token',data.token)
    return jwtDecode(data.token)
}

