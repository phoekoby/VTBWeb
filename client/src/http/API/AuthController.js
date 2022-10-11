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
            .then(jwt => {
                if(!jwt || !jwt.length){
                    alert('Ошибка при авторизации')
                    return jwt
                }
                localStorage.setItem('token', jwt)
                API.jwt = jwt
                console.log(API)
                return jwt
            })
    }

}

export const AUTH = new AuthController(API)
