import Controller from "./Controller";

export default class WalletController extends Controller {

    constructor(api) {
        super(api)
        this.port = '8081'
    }

    getWallets = (otherUserId = false) => {
        return new Promise((resolve, reject) => {
            const url = '/wallet' + (otherUserId ? '/'+otherUserId : '')
            this.api.request(url, '', 'GET', this.port)
                .catch(err => {
                    console.error(err)
                    resolve(false)
                })
                .then(res => {
                    resolve(res.length ? res : false)
                })
        })
    }

    createNew = () => {
        return new Promise((resolve, reject) => {
            this.api.request('/wallet/createNew', {}, 'POST', this.port)
                .catch(err => {
                    console.error(err)
                    resolve(false)
                })
                .then(res => {
                    res = res ? res * 1 : false
                    resolve(res)
                })
        })
    }

    getHistory = (walletId, page = 1) => {
        return new Promise((resolve, reject) => {
            resolve([
                {transactionId: 1, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Incoming', amount: 123.45, currency: 'NFT', transactionType: 'TRANSFER'},
                {transactionId: 2, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Outgoing', amount: 123.45, currency: 'RUBLE', transactionType: 'PURCHASE'},
                {transactionId: 3, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Incoming', amount: 123.45, currency: 'MATIC', transactionType: 'EXCHANGE'},
                {transactionId: 4, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Outgoing', amount: 123.45, currency: 'NFT', transactionType: 'TRANSFER'},
                {transactionId: 5, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Incoming', amount: 123.45, currency: 'RUBLE', transactionType: 'PURCHASE'},
                {transactionId: 6, hash: 'saldkfjfdagkjfdssdlfjsdfljasdfl', fromId: 1, toId: 2, direction: 'Outgoing', amount: 123.45, currency: 'MATIC', transactionType: 'EXCHANGE'},
            ])
            this.api.request('/wallet/' + walletId + '/history', '', 'GET', this.port)
                .catch(err => {
                    console.error(err)
                    resolve(false)
                })
                .then(res => {
                    const {walletId, transactions} = res
                    resolve({walletId, transactions})
                })
        })
    }

}
