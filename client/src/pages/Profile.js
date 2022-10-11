import React from 'react';
import {Container, Image} from "react-bootstrap";
import styles from "../styles/Profile.module.css";

const Profile = () => {
    return (
        <Container className={styles.container}>
            <Image className={styles.image}/>
        </Container>
    );
};

export default Profile;