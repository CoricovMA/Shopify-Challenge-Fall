import React from "react";
import '../style/homepage.css'
import {Col, Container, Row} from "react-bootstrap";
import {Paper} from "@material-ui/core";

export default function Homepage(){
    return (
        <Container className={"homepage"}>
            <Row>
                <Col>
                    <h1>
                        Image - Repo
                    </h1>
                    <h3 data-aos={"fade-right"} data-aos-duration={"2000"} >
                        by
                    </h3>
                    <h3 data-aos={"fade-left"} data-aos-duration={"2000"}>
                        <a href={"https://github.com/CoricovMA"} style={{
                            color:"#f4f1de"
                        }}>Alexander Coricovac</a>
                    </h3>
                </Col>
            </Row>
            <Row className={"paper-row"}>
                <Col >
                    <Paper id={"homepage-paper"} elevation={10}>
                        <h1 data-aos={"fade-down"} data-aos-duration={"2000"}>How to use this website</h1>
                        <h6 data-aos={"fade-right"} data-aos-duration={"1500"}>Upload pictures using the <a href={"/upload"}>Upload</a> page</h6>
                        <h6 data-aos={"fade-left"} data-aos-duration={"1500"}>View existing pictures using the <a href={"/gallery"}>Gallery</a> page</h6>
                    </Paper>
                </Col>
            </Row>

        </Container>
    )
}