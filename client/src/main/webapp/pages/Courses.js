import React, {useContext} from 'react';
import {Context} from "../../../index";
import Container from "react-bootstrap/Container";
import CourseCard from "../components/CourseCard";
import styles from "../styles/Courses.module.css";

const Courses = () => {

    const {courses} = useContext(Context)
    console.log(JSON.stringify(courses))

    return (
        <Container className={styles.container}>
            {courses.courses.map(courseItem =>
                <CourseCard
                    id={courseItem.id}
                    title={courseItem.title}
                    description={courseItem.description}
                />
            )}
        </Container>
    );
};

export default Courses;