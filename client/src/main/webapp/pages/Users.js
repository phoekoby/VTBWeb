import React from 'react';
import {Container} from "react-bootstrap";
import {observer} from "mobx-react-lite";
import UsersTabList from "../components/UsersTabList";



const Users = observer(() => {
    return (
        <Container>
            <UsersTabList/>

        </Container>
    );
});

export default Users;