import Controller from "./Controller";
import {API} from "../Api";

class AuthController extends Controller {

    constructor(api) {
        super(api)
        this.port = '8080'
        this.jwt = false
    }

    authenticate = (login, password) => {
        console.log('trying to authenticate...')
        return API.request('/authorize', {login, password}, 'POST')
            .catch(err => {
                console.error(err)
                alert('Ошибка')
            })
            .then(data => {
                if(!data || data.jwt === undefined){
                    alert('Ошибка при авторизации')
                }
                localStorage.setItem('token', data.jwt)
                API.jwt = data.jwt
                console.log(API)
                return data
            })
    }

}

export const AUTH = new AuthController(API)
