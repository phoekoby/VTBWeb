import {makeAutoObservable} from "mobx";

export default class UserStore {
    constructor() {
        this._isAuth = false
        this._user = {id:228,name:'Kostya'}
        this._exp = {}
        this._wallets=[
            {id:1,rub:2000,nft:2,matic:100},
            {id:2,rub:5000,nft:4,matic:20},
            {id:228,rub:10000,nft:6,matic:2},
        ]
        makeAutoObservable(this)
    }

    setIsAuth(bool) {
        this._isAuth = bool
    }

    setWallet(wallets) {
        this._wallets = wallets
    }

    setUser(user) {
        this._user = user
    }

    setExp(exp) {
        this._exp = exp
    }

    get isAuth() {
        return this._isAuth
    }

    get wallets() {
        return this._wallets
    }

    get user() {
        return this._user
    }
    get exp() {
        return this._exp
    }
}