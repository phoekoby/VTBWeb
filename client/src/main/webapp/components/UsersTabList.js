import React from 'react';
import {Tab, Tabs} from "react-bootstrap";
import styles from "../styles/Users.module.css";
import UsersScroll from "./UsersScroll";
import TipTransactionModal from "./TipTransactionModal";

const UsersTabList = () => {

    return (
        <Tabs
            defaultActiveKey="profile"
            id="justify-tab-example"
            className={styles.tabs}
        >
            <Tab eventKey="home" title="Профиль">
                <h1>XDD1</h1>
            </Tab>
            <Tab eventKey="profile" title="Другие пользователи">
                <UsersScroll/>
            </Tab>
            <Tab eventKey="longer-tab" title="Таблица лидеров">
                <h1>Таблица Лидеров</h1>
            </Tab>
        </Tabs>
    );
};

export default UsersTabList;