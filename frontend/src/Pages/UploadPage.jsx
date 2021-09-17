import React, {useCallback, useState} from "react";
import Dropzone from 'react-dropzone'
import {uploadImages} from "../Config/api";
import {Button, Col, Container, Row, Toast} from "react-bootstrap";
import '../style/drop.css'

function UploadToast(){
    return (
        <Toast>
            <Toast.Header>
                <img src="holder.js/20x20?text=%20" className="rounded mr-2" alt="" />
                <strong className="mr-auto">Bootstrap</strong>
                <small>just now</small>
            </Toast.Header>
            <Toast.Body>See? Just like this.</Toast.Body>
        </Toast>
    )
}

function ImageDisplay(props){
    return (
        <Col
            className={"col-display"}

            lg={1}
            md={2}
            sm={4}
            xs={4}
        >
            <img
                data-aos={"fade-in"}
                className={"shadow pic-display"}
                style={{
                    marginTop:"20px",
                    height: "100px",
                    width: "80px"
                }}
                src={URL.createObjectURL(props.file)}
                alt={props.file.name}
            />
        </Col>
    )
}

function Drop() {
    const [fileDisplay, setFileDisplay] = useState([])
    const [files, setFiles] = useState([])
    const [toast, setToast] = useState(<></>);

    const [count, setCount] = useState(0);

    const onDrop = useCallback((acceptedFiles) =>{
        acceptedFiles.forEach((file) =>{

            const reader = new FileReader();
            reader.onabort = () => {}
            reader.onerror = () =>{}

            reader.onload = () =>{
                setCount(count+1)
                setFileDisplay(fileDisplay => fileDisplay.concat(<ImageDisplay
                    file={file}
                    key={count}
                    duration={files.length*1000}
                />))
                files.push(file)
                setFiles(files)
            }
            reader.readAsArrayBuffer(file)

        })

    }, [])

    const reset = () =>{
        setFiles([])
        setFileDisplay([])
        window.location.reload();
    }

    const onClickUpload = () =>{
        if(files.length === 0){
            return;
        }
        uploadImages(files)
            .then((res) =>{
                alert(`Successfully uploaded ${fileDisplay.length} ${fileDisplay.length > 1 ? "pictures" : "picture"}`)
                reset()
            }).catch((err) =>{
            console.log(err)
        })
    }

    return (
        <div
            style={{
                paddingTop: "2%"
            }}>
            <div className={"file-display"} >
                <Row>
                    {fileDisplay}
                </Row>
            </div>
            <Container style={{
                display: "flex",
                justifyContent: "center"
            }} >

                <Row>
                    <Col className={"shadow"} style={{
                        padding: "0",
                        borderRadius: "5px"
                    }}>
                        <Dropzone onDrop={onDrop} multiple={true}>
                            {({getRootProps, getInputProps}) => (
                                <section>
                                    <div {...getRootProps()} className="drop-box" >
                                        <input {...getInputProps()} />
                                        <Row>
                                            <Col>
                                                <Button variant="light" className="input-button" >
                                                    Click here
                                                </Button>
                                            </Col>

                                        </Row>
                                        <Row>
                                            <Col>
                                                <p className="drop-text">
                                                    Or drag and drop a file
                                                </p>
                                            </Col>
                                        </Row>

                                    </div>
                                </section>
                            )}
                        </Dropzone>
                    </Col>

                </Row>

            </Container>

            <Container style={{
                display: "flex",
                justifyContent: "center",
                marginBottom: "5%"
            }}>
                <Row>
                    <Col>
                        <Button
                            onClick={onClickUpload}
                            id={"upload-button"}
                        >
                            Upload
                        </Button>
                    </Col>

                </Row>
                <Row>
                    {toast ? toast : undefined}
                </Row>
            </Container>

        </div>

    )

}

export default Drop;
