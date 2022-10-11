
// /createAccount
class Auth {

    constructor() {
        this.user = false
        this.jwt = undefined
    }

    checkAuth = () => {
        if(!this.user){
            window.changePage('auth')
        }
    }

    authorize = (login, password) => {
        return new Promise((resolve, reject) => {
            resolve(new User({id: 1, firstname: 'Test', email: 'test@te.st'}))
            window.api.request('/authorize', {login, password}, 'POST')
                .catch(err => {
                    console.error(err)
                    reject('Ошибка')
                })
                .then(data => {
                    if(!data || data.jwt === undefined){
                        reject('Ошибка при авторизации')
                    }else{
                        this.jwt = data.jwt
                    }
                })
        })
    }

}
