import React from 'react';
import {Button, Card} from "react-bootstrap";
import styles from "../styles/Courses.module.css";

const CourseCard = (props) => {
    return (
        <Card className={styles.card} style={{ width: '18rem' }}>
            <Card.Img variant="top" src="" />
            <Card.Body>
                <Card.Title>{props.title}</Card.Title>
                <Card.Text>
                    {props.description}
                </Card.Text>
                <Button variant="primary">Пройти</Button>
            </Card.Body>
        </Card>
    );
};

export default CourseCard;