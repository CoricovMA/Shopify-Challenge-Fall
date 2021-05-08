import React from "react";
import '../style/homepage.css'
import {Container} from "react-bootstrap";

export default function Homepage(){
    return (
        <Container className={"homepage"} >
            <h1>
                Image - Repo
            </h1>
            <h3 data-aos={"fade-right"} data-aos-duration={"2000"} >
                by
            </h3>
            <h3 data-aos={"fade-left"} data-aos-duration={"2000"}>
                 Alexander Coricovac
            </h3>
        </Container>
    )
}