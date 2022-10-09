import React, {useContext, useEffect, useState} from 'react';
import {BrowserRouter} from "react-router-dom";
import AppRouter from "./components/AppRouter";
import NavBar from "./components/NavBar";
import {observer} from "mobx-react-lite";
import {Context} from "../../index";
import {Spinner} from "react-bootstrap";
import {checkToken} from "./http/userApi";
import {AUTH} from "./http/API/AuthController";
import Auth from "./pages/Auth";


// const api_url = 'localhost:8080/api/v1'
// class Api {
//
//     constructor () {
//
//     }
//
//     request = (route, params, method = 'GET') => {
//         return new Promise((resolve, reject) => {
//             const xhr = new XMLHttpRequest()
//             xhr.open(method, api_url + route, true)
//             xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8')
//             xhr.send(JSON.stringify(params))
//             xhr.onreadystatechange = function(){
//                 if(xhr.readyState === 4){
//                     if(xhr.status === 200){
//                         const answer = JSON.parse(xhr.responseText)
//                         resolve(answer)
//                     }else{
//                         reject('Ошибка при запросе')
//                     }
//                 }
//             }
//         })
//     }
//
// }
//
// const api = new Api()
// const checkToken = () => {
//     return api.request('/authorize', {login, password}, 'POST')
//         .catch(err => {
//             console.error(err)
//             reject('Ошибка')
//         })
//         .then(data => {
//             if(!data || data.jwt === undefined){
//                 reject('Ошибка при авторизации')
//             }else{
//                 this.jwt = data.jwt
//             }
//         })
// }

localStorage.removeItem('token')

export const App = observer(() => {
    const {user} = useContext(Context)
    const [loading, setLoading] = useState(false)
    const [authorized, setAuthorized] = useState(!!AUTH.jwt)
    console.log({authorized})
    useEffect(() => {
        AUTH.authenticate()
        // checkToken().then(data => {
        //     user.setUser(data)
        //     user.setIsAuth(true)
        // }).finally(() => setLoading(false))
    },[])
    console.log('App')
    
    if(loading){
        return <Spinner animation={"border"}/>
    }
    return (
        <BrowserRouter>
            <NavBar />
            {
                authorized ? (
                    <AppRouter/>
                ) : (
                    <Auth/>
                )
            }
        </BrowserRouter>
    );
})