import {makeAutoObservable} from "mobx";
import {API} from "../http/Api";
import WalletController from "../http/API/WalletController";

export default class TransactionStore {

    constructor() {
        this._types = [
            {id:1,name:'какие то штуки 1'},
            {id:2,name:'какие то штуки 2'},
            {id:3,name:'какие то штуки 3'},
            {id:4,name:'какие то штуки 4'},
        ]
        this._transactions = []
        this._selectedType = {}
        this._page = 0
        this._hasMore = false
        this.walletId = false

        this.walletController = new WalletController(API)
        this.walletController.getWallets().then(res => {
            this.walletId = res ? res[0] : false
            this.loadMore()
        })

        makeAutoObservable(this)
    }

    loadMore(){
        this.walletController.getHistory(this.walletId, ++this._page)
            .then(transactions => {
                console.log(transactions)
                if(transactions && transactions.length){
                    this._hasMore = true
                    this._transactions.push(...transactions)
                }else{
                    this._hasMore = false
                }
            })
    }

    get hasMore(){
        return this._hasMore
    }

    setTypes(types){
        this._types = types
    }

    get types() {
        return this._types
    }

    setTransactions(transactions){
        this._transactions = transactions
    }

    get transactions() {
        return this._transactions
    }

    setSelectedType(type){
        this._selectedType = type
    }

    get selectedType() {
        return this._selectedType
    }

    setPage(page){
        this._page = page
    }

    get page() {
        return this._page
    }
}