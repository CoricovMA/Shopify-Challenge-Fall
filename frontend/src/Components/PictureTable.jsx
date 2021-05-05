import React, {useEffect, useState} from "react"
import {Button, Col, Container, Image, Modal, ModalBody, ModalFooter, ModalTitle} from 'react-bootstrap'
import {getAllImages, mainUrl} from "../Config/api";
import '../style/picture.css'
import ModalHeader from "react-bootstrap/ModalHeader";
import 'bootstrap/dist/css/bootstrap.min.css';


function Picture(props){
    const [show, setShow] = useState(false);

    const handleCloseModal = () => setShow(false)

    const handleShowModal = () => setShow(true)

    return (
        <Col md={2} xs={6} sm={6}>
            <Image  src={props.src} className={"picture"} onClick={handleShowModal} />
            <Modal show={show} onHide={handleCloseModal} backdrop={"static"} keyboard={false} onClick={handleCloseModal} >
                <ModalHeader >
                    <ModalTitle>
                        {props.title}
                    </ModalTitle>
                </ModalHeader>
                <ModalBody>
                    <img alt={props.imageName} src={props.src}  className={"img-fluid"}/>
                </ModalBody>
                <ModalFooter>
                    <Button variant={"secondary"} onClick={handleCloseModal} >
                        Close
                    </Button>
                </ModalFooter>
            </Modal>
        </Col>
    )

}

function PictureTable(){
    const [pictureElements, setPictureElements] = useState([]);


    useEffect( () =>{
        getAllImages().then( (res) =>{
            if(res.status === 200){
                setPictureElements(res.data.pictures.map((item, index) => {
                        return <Picture src={`${mainUrl}/picture/${item}`}
                                        key={index+1}
                                        imageName={item}
                        />
                }))
            }
        }).catch( (err) => {
            console.log(err)
        })
    }, [])

    return (
        <Container style={{
            display:"flex",
            justifyContent:"center"
        }}>
                {pictureElements}
        </Container>
    )
}

export default PictureTable;