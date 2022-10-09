import Controller from "./Controller";

export default class TransactionController extends Controller {

    constructor(api) {
        super(api)
        this.port = '8081'
    }

    makeTransfer = (fromWalletId, toWalletId, currency, amount) => {
        const transfer = {fromWalletId, toWalletId, currency, amount} // здесь появится второй amount в другой валюте
        return new Promise((resolve, reject) => {
            this.api.request('/transaction/transfer', transfer, 'POST', this.port)
                .catch(err => {
                    console.error(err)
                    resolve(false)
                })
                .then(res => {
                    const {id, hash} =  res
                    resolve({id, hash})
                })
        })
    }

    // from - currency_from
    // to   - currency_to
    makeExchange = (fromWalletId, toWalletId, from, to, amount) => {
        const exchange = {fromWalletId, toWalletId, from, to, amount}
        return new Promise((resolve, reject) => {
            this.api.request('/transaction/exchange', exchange, 'POST', this.port)
                .catch(err => {
                    console.error(err)
                    resolve(false)
                })
                .then(res => {
                    const {id, hash} =  res
                    resolve({id, hash})
                })
        })
    }

    // Покупка товара
    makePurchase = (fromWalletId, toWalletId, productId, currency, amount) => {
        const purchase = {fromWalletId, toWalletId, productId, currency, amount}
        return new Promise((resolve, reject) => {
            this.api.request('/transaction/purchase', purchase, 'POST', this.port)
                .catch(err => {
                    console.error(err)
                    resolve(false)
                })
                .then(res => {
                    const {id, hash} =  res
                    resolve({id, hash})
                })
        })
    }

    getTransactionInfo = (hash) => {
        return this.api.request('/transaction/' + hash, '', 'GET', this.port)
    }

}
