import {api_url} from "./index";

class Api {

    constructor () {
        this.jwt = false
    }

    request = (route, params, method = 'GET', port = '8080') => {
        const url = api_url.replace(':8080', ':' + port) + route
        return new Promise((resolve, reject) => {
            const xhr = new XMLHttpRequest()
            xhr.open(method, url, true)
            xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8')
            if(method === 'POST'){
                xhr.send(JSON.stringify(params))
            }else{
                xhr.send()
            }
            xhr.onreadystatechange = function(){
                if(xhr.readyState === 4){
                    if(xhr.status === 200){
                        try{
                            const answer = JSON.parse(xhr.responseText)
                            resolve(answer)
                        }catch (e) {
                            resolve(xhr.responseText)
                        }
                    }else{
                        reject('Ошибка при запросе')
                    }
                }
            }
        })
    }

}

export const API = new Api()

