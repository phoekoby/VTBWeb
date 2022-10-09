import React from 'react';
import {Button, Card} from "react-bootstrap";
import styles from "../styles/Courses.module.css";
import axios from "axios";

const CourseCard = (props) => {
    const {id} = props

    const  fetchCourseById = async(id) => {
        await axios.get('' + id)
    }

    return (
        <Card className={styles.card} style={{ width: '18rem' }}>
            <Card.Img variant="top" src="" />
            <Card.Body>
                <Card.Title>{props.title}</Card.Title>
                <Card.Text>
                    {props.description}
                </Card.Text>
                <Button variant="primary" onClick={fetchCourseById(id)}>Пройти</Button>
            </Card.Body>
        </Card>
    );
};

export default CourseCard;