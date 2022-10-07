import React, {useContext} from 'react';
import {Context} from "../index";
import {Navbar, Nav, Button, Container} from "react-bootstrap";
import {NavLink} from "react-router-dom";
import {SHOP_ROUTE} from "../utils/consts";
import {observer} from "mobx-react-lite";
import styles from "../styles/Navbar.module.css";

const NavBar = observer(() => {
    const {user} = useContext(Context)
    return (
        <Navbar className={styles.navbar} bg="dark" variant="dark">
            <Container>
                <NavLink  to={SHOP_ROUTE} className={styles.logo}>ВТБХАК</NavLink>
                {user.isAuth ?
                    <Nav style={{color: "white"}}>
                        <Button variant={"outline-light"} className={styles.button}>Админ панель</Button>
                        <Button variant={"outline-light"} className={styles.button} onClick={() => {user.setIsAuth(false)}}>Выйти</Button>
                    </Nav>
                    :
                    <Nav style={{color: "white"}}>
                        <Button variant={"outline-light"} className={styles.button} onClick={() => {user.setIsAuth(true)}}>Авторизация</Button>
                    </Nav>
                }
            </Container>
        </Navbar>
    );
});

export default NavBar;