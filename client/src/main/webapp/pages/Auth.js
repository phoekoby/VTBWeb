import React, {useContext, useState} from 'react';
import {Button, Card, Container, Form} from "react-bootstrap";
import styles from "../styles/Auth.module.css";
import {NavLink, useLocation, useNavigate} from "react-router-dom";
import {LOGIN_ROUTE, REGISTRATION_ROUTE, SHOP_ROUTE} from "../utils/consts";
import {Context} from "../../../index";
import {observer} from "mobx-react-lite";
import {AUTH} from "../http/API/AuthController";


const Auth = observer(() => {
    const location = useLocation()
    const navigate = useNavigate()
    const isLogin = location.pathname === LOGIN_ROUTE
    const {user} = useContext(Context)
    const [email,setEmail] = useState('')
    const [password,setPassword] = useState('')

    const click = async () => {
        try {
            let data
            if(isLogin){
                // data = await login(email,password)
                AUTH.authenticate(email, password).then(jwt => {
                    user.setIsAuth(jwt && jwt.length)
                    navigate(SHOP_ROUTE)
                })
            }else{
                // data = await registration(email,password)
                // console.log(response)
            }
            //user.setUser(data)
            // user.setIsAuth(true)
            navigate(SHOP_ROUTE)
        }catch (e) {
            alert(e.response.data.message)
        }
    }


    return (
        <Container
            className={styles.container}
            style={{height: window.innerHeight - 54}}
        >
            <Card style={{width:600 }} className={styles.card}>
                <h2 className={styles.header}>{isLogin? 'Авторизация': 'Регистрация'}</h2>
                <Form className={styles.form}>
                    <Form.Control
                        className={styles.placeholder}
                        placeholder="Введите ваш email..."
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        required={true}
                    />
                    <Form.Control
                        className={styles.placeholder}
                        placeholder="Введите ваш пароль..."
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        type="password"
                        required={true}
                    />
                    <div className={styles.formRow}>
                        {isLogin ?
                            <div>
                                Нет аккаунта? <NavLink to={REGISTRATION_ROUTE}>Зарегистрируйся!</NavLink>
                            </div>
                            :
                            <div>
                                Есть аккаунт? <NavLink to={LOGIN_ROUTE}>Войдите!</NavLink>
                            </div>
                        }

                        <Button
                            className={styles.loginButton}
                            variant={"outline-success"}
                            onClick={click}
                        >
                            {isLogin? 'Войти' : 'Регистрация'}
                        </Button>
                    </div>

                </Form>
            </Card>
        </Container>
    );
});

export default Auth;