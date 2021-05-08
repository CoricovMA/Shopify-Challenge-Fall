import React from "react";
import {Route, Switch} from "react-router-dom"
import Homepage from "../Pages/Homepage";
import RepoNavbar from "../Components/RepoNavbar";
import GalleryPage from "../Pages/GalleryPage";
import UploadPage from "../Pages/UploadPage";
import 'bootstrap/dist/css/bootstrap.min.css';

export default function Router(){
    return (
        <div className={"main-page"}>
            <RepoNavbar/>
            <Switch>
                <Route exact path="/">
                    <Homepage/>
                </Route>
                <Route exact path="/gallery">
                    <GalleryPage/>
                </Route>
                <Route exact path="/upload">
                    <UploadPage/>
                </Route>
            </Switch>
        </div>
    )
}
