import React, {useEffect, useState} from "react"
import {Container, Image, Modal, ModalBody, ModalFooter, ModalTitle} from 'react-bootstrap'
import {deleteImage, getAllImages, mainUrl} from "../Config/api";
import '../style/picture.css'
import ModalHeader from "react-bootstrap/ModalHeader";
import DeleteIcon from '@material-ui/icons/Delete';
import Button from '@material-ui/core/Button';
import {Grid, Paper} from "@material-ui/core";

function Picture(props){
    const [show, setShow] = useState(false);

    const handleCloseModal = () => setShow(false)

    const handleShowModal = () => setShow(true)

    const handleDeletePicture = () => {
        deleteImage(props.imageName).then((res) =>{
            props.removePIctureElement(props.index)
        }).catch((err) => {
            // console.log(err)
        })
    }

    return (

        <Grid item md={6} xs={12} sm={8} lg={4} xl={4} className={"grid-item"} >
            <Paper variant={"outlined"} square>
                <Grid container justify={"center"} className={"picture-container"}>
                    <Image src={props.src} className={`picture`} onClick={handleShowModal}  />
                </Grid>
            </Paper>
            <Modal show={show} onHide={handleCloseModal} backdrop={"static"} keyboard={false} onClick={handleCloseModal} >
                <ModalHeader >
                    <ModalTitle>
                        {props.title}
                    </ModalTitle>
                </ModalHeader>
                <ModalBody>
                    <img alt={props.imageName} src={props.src}  className={"img-fluid modal-image"}/>
                </ModalBody>
                <ModalFooter>
                    <Button
                        variant="contained"
                        color="secondary"
                        startIcon={<DeleteIcon />}
                        style={{
                            marginRight: "15px"
                        }}
                        onClick={handleDeletePicture}
                    >
                        Delete
                    </Button>
                    <Button
                        variant="contained"
                        color="default"
                    >
                        Close
                    </Button>
                </ModalFooter>
            </Modal>
        </Grid>
    )

}

function PictureTable(){
    const [pictureElements, setPictureElements] = useState([]);
    const [needsReset, setNeedsReset] = useState(true);

    const removePictureElement = (index) => {
        let arr = pictureElements;
        arr.splice(index, 1);
        setPictureElements([...arr]);
        setNeedsReset(true)
    }

    if(needsReset) {
        getAllImages().then((res) => {
            if (res.status === 200) {
                setPictureElements(res.data.pictures.map((item, index) => {
                    return <Picture src={`${mainUrl}/picture/${item}`}
                                    key={index + 1}
                                    index={index}
                                    imageName={item}
                                    removePIctureElement={removePictureElement}
                    />
                }))
            }
        }).catch((err) => {
            // console.log(err)
        })
        setNeedsReset(false)
    }

    return (
        <Container style={{
            display:"flex",
            justifyContent:"center"
        }} className={"picture-table"} >
            <Grid container spacing={2}>
                {pictureElements}
            </Grid>
        </Container>
    )
}

export default PictureTable;