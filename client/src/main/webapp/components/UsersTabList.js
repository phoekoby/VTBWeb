import React from 'react';
import {Tab, Tabs} from "react-bootstrap";
import styles from "../styles/Users.module.css";
import UsersScroll from "./UsersScroll";
import LeadersScroll from "./LeadersScroll";

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
                <LeadersScroll/>
            </Tab>
        </Tabs>
    );
};

export default UsersTabList;