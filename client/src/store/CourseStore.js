import {makeAutoObservable} from "mobx";

export default class CourseStore {
    constructor() {
        this._courses = [
            {id:1,title:"Экономика",description:"xddddddd",img:""},
            {id:2,title:"Кринж",description:"жесткий",img:""},
            {id:2,title:"Кринж",description:"жесткий",img:""},
            {id:2,title:"Кринж",description:"жесткий",img:""},
            {id:2,title:"Кринж",description:"жесткий",img:""},
            {id:2,title:"Кринж",description:"жесткий",img:""},

        ]
        makeAutoObservable(this)
    }

    setCourses(courses) {
        this._courses = courses
    }

    get courses() {
        return this._courses
    }
}