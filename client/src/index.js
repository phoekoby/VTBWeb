import React, {createContext} from 'react';
import ReactDOM from 'react-dom';
import {App} from './App';
import UserStore from "./store/UserStore";
import DeviceStore from "./store/ProductStore";
import TransactionStore from "./store/TransactionStore";
import CourseStore from "./store/CourseStore";

export const Context = createContext(null)

ReactDOM.render(
    <Context.Provider value={{
        user: new UserStore(),
        product: new DeviceStore(),
        transactionStore: new TransactionStore(),
        courses: new CourseStore(),
    }}>
        <App />
    </Context.Provider>,
    document.getElementById('root')
);