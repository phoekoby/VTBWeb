import {makeAutoObservable} from "mobx";

export default class ProductStore {

    constructor() {
        this._types = [
            {id:1,name:'какие то штуки 1'},
            {id:2,name:'какие то штуки 2'},
            {id:3,name:'какие то штуки 3'},
            {id:4,name:'какие то штуки 4'},
        ]
        this._products = [
            {id:1,name:'какая то хрень 1',price: 2000},
            {id:2,name:'какая то хрень 2',price: 1000},
            {id:3,name:'какая то хрень 3',price: 5000},
            {id:4,name:'какая то хрень 4',price: 5000},
            {id:5,name:'какая то хрень 5',price: 7000},
            {id:6,name:'какая то хрень 1',price: 2000},
            {id:7,name:'какая то хрень 2',price: 1000},
            {id:8,name:'какая то хрень 3',price: 5000},
            {id:9,name:'какая то хрень 4',price: 5000},
            {id:10,name:'какая то хрень 5',price: 7000},
        ]
        this._selectedType = {}
        this._page = 1
        this._totalCount = 0
        this._limit = 6
        makeAutoObservable(this)
    }

    setTypes(types){
        this._types = types
    }

    get types() {
        return this._types
    }

    setProducts(products){
        this._products = products
    }

    get products() {
        return this._products
    }

    setSelectedType(type){
        this.setPage(1)
        this._selectedType = type
    }

    get selectedType() {
        return this._selectedType
    }

    setPage(page){
        this._page = page
    }

    setTotalCount(totalCount){
        this._totalCount = totalCount
    }

    setLimit(limit){
        this._limit = limit
    }

    get limit() {
        return this._limit
    }
    get totalCount() {
        return this._totalCount
    }
    get page() {
        return this._page
    }
}