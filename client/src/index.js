import React, {createContext} from 'react';
import ReactDOM from 'react-dom';
import {App} from './main/webapp/App';
import UserStore from "./main/webapp/store/UserStore";
import DeviceStore from "./main/webapp/store/ProductStore";

export const Context = createContext(null)

ReactDOM.render(
    <Context.Provider value={{
        user: new UserStore(),
        product: new DeviceStore(),
    }}>
        <App />
    </Context.Provider>,
    document.getElementById('root')
);