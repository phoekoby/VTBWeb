import React, {createContext} from 'react';
import ReactDOM from 'react-dom';
import {App} from './main/webapp/App';
import UserStore from "./main/webapp/store/UserStore";
import DeviceStore from "./main/webapp/store/ProductStore";
import TransactionStore from "./main/webapp/store/TransactionStore";
import CourseStore from "./main/webapp/store/CourseStore";

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