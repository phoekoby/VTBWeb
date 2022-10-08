import Api from "./Api"
import {$host,$authHost} from "./index";
import jwtDecode from "jwt-decode";


const api = new Api()

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
    const {data} = await $host.post('/authorize',{email,password})
    localStorage.setItem('token',data.token)
    return jwtDecode(data.token)
}

export const checkToken = async() => {
    console.log('checkToken')
    const {data} = await $authHost.get('/authorize')
    localStorage.setItem('token',data.token)
    return jwtDecode(data.token)
}

