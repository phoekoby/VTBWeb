import React from 'react';
import {Button, Card, Container, Form} from "react-bootstrap";
import styles from "../styles/Auth.module.css";
import {NavLink,useLocation} from "react-router-dom";
import {LOGIN_ROUTE, REGISTRATION_ROUTE} from "../utils/consts";

const Auth = () => {
    const location = useLocation()
    const isLogin = location.pathname === LOGIN_ROUTE
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
                    />
                    <Form.Control
                        className={styles.placeholder}
                        placeholder="Введите ваш пароль..."
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

                        <Button className={styles.loginButton} variant={"outline-success"}>
                            {isLogin? 'Войти' : 'Регистрация'}
                        </Button>
                    </div>

                </Form>
            </Card>

        </Container>
    );
};

export default Auth;