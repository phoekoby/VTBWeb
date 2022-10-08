import React, {useContext} from 'react';
import {Context} from "../../../index";
import {Navbar, Nav, Button, Container} from "react-bootstrap";
import {NavLink} from "react-router-dom";
import {
    ACHIEVMENTS_ROUTE,
    COURSES_ROUTE,
    LOGIN_ROUTE,
    NOTIFICATIONS_ROUTE,
    SHOP_ROUTE,
    TRANSACTIONS_ROUTE,
    USERS_ROUTE
} from "../utils/consts";
import {observer} from "mobx-react-lite";
import styles from "../styles/Navbar.module.css";
import {useNavigate} from "react-router-dom"



const NavBar = observer(() => {
    const {user} = useContext(Context)
    const navigate = useNavigate()
    const logOut = () => {
        user.setIsAuth(false)
        console.log(user.isAuth)
        user.setUser({})
    }
    return (
        <Navbar className={styles.navbar} bg="dark" variant="dark">
            <Container>
                <NavLink  to={SHOP_ROUTE} className={styles.logo}>ВТБ</NavLink>
                <NavLink to={TRANSACTIONS_ROUTE} className={styles.button}>Транзакции</NavLink>
                <NavLink to={COURSES_ROUTE} className={styles.button}>Курсы</NavLink>
                <NavLink to={USERS_ROUTE} className={styles.button}>Пользователи</NavLink>
                <NavLink to={NOTIFICATIONS_ROUTE} className={styles.button}>Уведомления</NavLink>
                {user.isAuth ?
                    <Nav style={{color: "white"}}>
                        <Button
                            variant={"outline-light"}
                            className={styles.button}
                            onClick={() => logOut()}
                        >
                            Выйти
                        </Button>
                    </Nav>
                    :
                    <Nav style={{color: "white"}}>
                        <Button
                            variant={"outline-light"}
                            className={styles.button}
                            onClick={() => navigate(LOGIN_ROUTE)}
                        >
                            Авторизация
                        </Button>
                    </Nav>
                }
            </Container>
        </Navbar>
    );
});

export default NavBar;