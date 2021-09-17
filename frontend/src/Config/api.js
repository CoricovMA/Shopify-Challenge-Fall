import axios from 'axios'

export const mainUrl = "http://localhost:8080";
const myHeaders = {
    "Content-Type": "multipart/form-data; boundary=----------287032381234132612",
    "Access-Control-Allow-Origin": "*"
}

function uploadImages(files){
    let formData = new FormData();

    files.forEach(
        file => {
            formData.append("files", file);
        }
    )

    return axios.post(`${mainUrl}/upload`, formData,
        {
            headers: myHeaders
        }
    )

}

function deleteImage(filename){
    return axios.delete(`${mainUrl}/picture/${filename}`, {headers: myHeaders});
}

function getImage(filename){
    return axios.get(`${mainUrl}/picture/${filename}`, {headers: myHeaders});
}

function getAllImages(){
    return axios.get(`${mainUrl}/pictures`, {headers: myHeaders});
}

export {uploadImages, deleteImage, getAllImages, getImage};