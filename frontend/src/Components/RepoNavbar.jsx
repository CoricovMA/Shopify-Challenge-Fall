import React from "react";
import {Nav, Navbar} from "react-bootstrap";

export default function RepoNavbar(){

    return (
        <>
            <Navbar bg="light" expand="lg" id={"nav"}>
                <Navbar.Brand href="/">Image-Repo</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href="/gallery">Gallery</Nav.Link>
                        <Nav.Link href="/upload">Upload</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        </>
    )
}