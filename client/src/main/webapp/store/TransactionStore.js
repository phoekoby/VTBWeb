import {makeAutoObservable} from "mobx";

export default class TransactionStore {

    constructor() {
        this._types = [
            {id:1,name:'какие то штуки 1'},
            {id:2,name:'какие то штуки 2'},
            {id:3,name:'какие то штуки 3'},
            {id:4,name:'какие то штуки 4'},
        ]
        this._transactions = [
            {transactionId: 1, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Incoming', amount: 123.45, currency: 'NFT', transactionType: 'TRANSFER'},
            {transactionId: 2, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Outgoing', amount: 123.45, currency: 'RUBLE', transactionType: 'PURCHASE'},
            {transactionId: 3, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Incoming', amount: 123.45, currency: 'MATIC', transactionType: 'EXCHANGE'},
            {transactionId: 4, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Outgoing', amount: 123.45, currency: 'NFT', transactionType: 'TRANSFER'},
            {transactionId: 5, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Incoming', amount: 123.45, currency: 'RUBLE', transactionType: 'PURCHASE'},
            {transactionId: 6, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Outgoing', amount: 123.45, currency: 'MATIC', transactionType: 'EXCHANGE'},
        ]
        this._selectedType = {}
        makeAutoObservable(this)
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
}