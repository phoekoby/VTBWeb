import React, {useContext, useState} from 'react';
import {BrowserRouter} from "react-router-dom";
import AppRouter from "./components/AppRouter";
import NavBar from "./components/NavBar";
import {observer} from "mobx-react-lite";
import {Context} from "../../index";
import {Spinner} from "react-bootstrap";



export const App = observer(() => {
    const {user} = useContext(Context)
    const [loading, setLoading] = useState(false)
    // useEffect(() => {
    //     checkToken().then(data => {
    //         user.setUser(data)
    //         user.setIsAuth(true)
    //     }).finally(() => setLoading(false))
    // },[])
    
    if(loading){
        return <Spinner animation={"grow"}/>
    }
    return (
        <BrowserRouter>
            <NavBar />
            <AppRouter />
        </BrowserRouter>
    );
})