import {api_url} from "./index";

export default class Api {

    constructor () {

    }

    request = (route, params, method = 'GET') => {
        return new Promise((resolve, reject) => {
            const xhr = new XMLHttpRequest()
            xhr.open(method, api_url + route, true)
            xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8')
            xhr.send(JSON.stringify(params))
            xhr.onreadystatechange = function(){
                if(xhr.readyState === 4){
                    if(xhr.status === 200){
                        const answer = JSON.parse(xhr.responseText)
                        resolve(answer)
                    }else{
                        reject('Ошибка при запросе')
                    }
                }
            }
        })
    }

}

